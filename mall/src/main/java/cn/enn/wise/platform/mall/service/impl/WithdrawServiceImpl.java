package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.param.WithdrawApplyParam;
import cn.enn.wise.platform.mall.bean.param.WithdrawQueryParam;
import cn.enn.wise.platform.mall.bean.vo.DistributeInfoVO;
import cn.enn.wise.platform.mall.bean.vo.WithdrawOrderVO;
import cn.enn.wise.platform.mall.bean.vo.WithdrawWillingVO;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.WithdrawMapper;
import cn.enn.wise.platform.mall.service.WithdrawLimitService;
import cn.enn.wise.platform.mall.service.WithdrawSerialService;
import cn.enn.wise.platform.mall.service.WithdrawService;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@Slf4j
public class WithdrawServiceImpl extends ServiceImpl<WithdrawMapper, WithdrawRecord> implements WithdrawService {


    @Autowired
    private WithdrawSerialService serialService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private WithdrawMapper withdrawMapper;
    @Autowired
    private WithdrawLimitService limitService;
    @Autowired
    private WithdrawalService withdrawalService;


    @Value("${url.wzdPath}")
    private String DOMAIN_PREFIX;

    @Value("${url.hostprefix}")
    private String TARGET_DOMAIN;
    @Value("${url.distributeBasePath}")
    private String DISTRIBUTE_HOST;

    @Override
    public boolean certifyWithdraw(Long distributorId) {
        if(distributorId==null || distributorId.longValue()<=1L){
            return false;
        }
        String targetUrl = TARGET_DOMAIN + DOMAIN_PREFIX + DISTRIBUTE_HOST + "/guide/app/havebankcard";
        Map<String,String> param = new HashMap();
        param.put("id",distributorId.toString());
        String json =  HttpClientUtil.post(targetUrl,param);
        ResponseEntity<String> entity = new Gson().fromJson(json,new TypeToken<ResponseEntity>(){}.getType());
        if(entity.getResult()==1){
            return true;
        }
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveRecord(WithdrawApplyParam param) {
        try {
            WithdrawWillingVO vo = getDistributeInfo(param.getDistributorId(), param.getStartDate(), param.getEndDate());
            if (vo == null || vo.getOrderList() == null || vo.getOrderList().size() == 0) {
                return null;
            }
            if(vo.getDistributorStatus()!=1 && vo.getDistributorStatus()==2){
                return "-1";
            }
            //
            String withdrawSerial = getOrderSerial();
            String total = vo.getTotal();
            List<DistributeInfoVO> list = vo.getOrderList();
            WithdrawRecord record = new WithdrawRecord();
            //
            record.setApplyDateTime(new Date());
            record.setDistributeDateBetween(param.getStartDate()+"/"+param.getEndDate());
            log.info("====> 账户类型：{}",param.getAccountType()==0?"微信":"银行卡");
            record.setDistributorAccountType((byte) param.getAccountType());
            record.setDistributorAccountSign(param.getAccountSign());
            record.setDistributorAccountNum(param.getAccountNum());
            record.setDistributorAccountUser(param.getAccountUser());
            record.setDistributorCellphone(list.get(0).getDistributorCellphone());
            record.setDistributorName(list.get(0).getDistributorName());
            record.setDistributorId(list.get(0).getDistributorId());
            record.setDistributorMessage(list.get(0).getDistributorMessage());
            //
            StringBuilder sbOrderIds = new StringBuilder();
            StringBuilder sbProfits = new StringBuilder();
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(2);
            numberFormat.setRoundingMode(RoundingMode.DOWN);
            double apply = 0.0;
            List<String> orders = param.getOrderId();

            for(DistributeInfoVO o : list){
                String cOrderId = o.getOrderId().toString();
                if(!StringUtils.isEmpty(cOrderId) && orders.contains(cOrderId)){
                    sbOrderIds.append(cOrderId);
                    sbOrderIds.append(",");
                    if(o.getProfit()!=null){
                        double cProfit = o.getProfit()==null?0.00:o.getProfit().doubleValue();
                        sbProfits.append(numberFormat.format(cProfit));
                        apply += cProfit;
                    }
                    sbProfits.append(",");
                }
            }
            record.setOrdersId(sbOrderIds.toString());
            record.setOrdersProfit(sbProfits.toString());
            record.setPermit((byte) -2);
            record.setPutOut((byte)-2);
            record.setWithdraw(numberFormat.format(apply));
            record.setWithdrawSerial(withdrawSerial);
            record.setWithdrawTotal(total);
            record.setDistributeStatusUpdated((byte)0);
            log.info("====> 账户类型：{}",record.getDistributorAccountType());
            save(record);
            //
            limitService.updateLimit(record.getDistributorId(),Double.valueOf(apply));
            return withdrawSerial;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private String getOrderSerial(){
        String serial = String.format("%03d", serialService.getAndUpdateSerial());
        //
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        String withdrawSerial = LocalDate.now().format(formatter) + serial;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("withdraw_serial",withdrawSerial);
        WithdrawRecord record = getOne(wrapper);
        if(record==null){
            return withdrawSerial;
        }else{
            return getOrderSerial();
        }
    }


    @Override
    public WithdrawWillingVO getDistributeInfo(Long id, String startDate, String endDate) {
        String targetUrl = TARGET_DOMAIN + DOMAIN_PREFIX + DISTRIBUTE_HOST + "/order/withdraw-info?distributorId="+id.toString()+"&startDate="+startDate+"&endDate="+endDate;
        String json =  HttpClientUtil.get(targetUrl);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ResponseEntity<WithdrawWillingVO> entity = gson.fromJson(json,new TypeToken<ResponseEntity<WithdrawWillingVO>>(){}.getType());
        WithdrawWillingVO vo = entity.getValue();
        List<DistributeInfoVO> list = vo.getOrderList();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        for (DistributeInfoVO one : list){
            if(one.getGoodsPrice()!=null){
                String t = numberFormat.format(one.getGoodsPrice().doubleValue());
                one.setSinglePrice(t);
                if(one.getAmount()!=null){
                    t = t+"*"+one.getAmount().intValue();
                }
                one.setSinglePriceMultiAmount(t);
            }else{
                one.setSinglePrice("0.00");
                one.setSinglePriceMultiAmount("0.00");
            }
        }
        vo.setOrderList(list);
        return vo;
    }

    /**
     * 4 已返利 5 已发放 9 已创建提现申请单
     * @param ordersId
     * @param status
     * @return
     */
    public boolean updateDistributeStatus(String ordersId,Integer status){
        String targetUrl = TARGET_DOMAIN + DOMAIN_PREFIX + DISTRIBUTE_HOST + "/order/withdraw-status?ordersId="+ordersId+"&status="+status.toString();
        String json =  HttpClientUtil.get(targetUrl);
        ResponseEntity<String> entity = new Gson().fromJson(json,new TypeToken<ResponseEntity<String>>(){}.getType());
        if(entity.getResult()==0){
            return true;
        }
        return false;
    }

    @Override
    public WithdrawRecord getWithdrawInfoBySerial(String withdrawSerial) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.eq("withdraw_serial",withdrawSerial);
        WithdrawRecord r =  getOne(wrapper);
        if(r==null){
            return null;
        }else{
            List<WithdrawRecord> list = new ArrayList();
            list.add(r);
            fulfillOrders(list);
            return list.get(0);
        }
    }

    @Override
    public void modifyWithdrawDistributeStatus(String withdrawSerial) {
        UpdateWrapper<WithdrawRecord> wrapper = new UpdateWrapper();
        wrapper.eq("withdraw_serial",withdrawSerial);
        WithdrawRecord record = new WithdrawRecord();
        record.setDistributeStatusUpdated((byte) 1);
        update(record,wrapper);
    }

    @Override
    public void clearWithdrawPermitStatus(String withdrawSerials) {
        if(StringUtils.isEmpty(withdrawSerials)){
            return;
        }
        String[] withdrawArr = withdrawSerials.split(",");
        for(String a : withdrawArr){
            if(!StringUtils.isEmpty(a)){
                UpdateWrapper<WithdrawRecord> wrapper = new UpdateWrapper();
                wrapper.eq("withdraw_serial",a);
                WithdrawRecord record = new WithdrawRecord();
                record.setPermit((byte) -2);
                record.setPermitComment(null);
                record.setPermitDateTime(null);
                record.setPermitUserId(null);
                record.setPermitUserName(null);
                update(record,wrapper);
            }
        }
    }

    @Override
    public List<WithdrawRecord> listRecordsInDate(Long distributorId, String startDate, String endDate) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.eq("distributor_id",distributorId);
        wrapper.ge("apply_date_time",startDate+" 00:00:00");
        wrapper.le("apply_date_time",endDate+" 23:59:59");
        wrapper.orderByDesc("apply_date_time");
        List<WithdrawRecord> rst =  list(wrapper);
        // 补充订单数据
        fulfillOrders(rst);
        return rst;
    }

    @Override
    public void sendComplementMessage(String withdrawSerials) {
        if(StringUtils.isBlank(withdrawSerials)){
            return;
        }
        String[] arr = withdrawSerials.split(",");
        for(String one : arr){
            sendShortMessage(one);
        }
    }

    @Override
    public List<WithdrawRecord> listRecordsByPage(WithdrawQueryParam param) {
        if(param.getPageNum()<=0){
            param.setPageNum(1);
        }
        if(param.getPageSize()<=0){
            param.setPageSize(30);
        }
        param.setOffset((param.getPageNum()-1)*param.getPageSize());
        List<WithdrawRecord> rst = withdrawMapper.selectByParam(param);
        fulfillOrders(rst);
        return rst;
    }

    @Override
    public Long listRecordsByPageCount(WithdrawQueryParam param) {
        return withdrawMapper.selectByParamCount(param);
    }

    @Override
    public void putOut(String withdrawSerials) {
        if(StringUtils.isBlank(withdrawSerials)){
            return;
        }
        String[] arr = withdrawSerials.split(",");
        for(String one : arr){
            if(!gotPermit(one)){
                continue;
            }
            UpdateWrapper<WithdrawRecord> wrapper = new UpdateWrapper();
            wrapper.eq("withdraw_serial",one);
            WithdrawRecord entity = new WithdrawRecord();
            entity.setPutOut((byte) 1);
            entity.setPutOutDateTime(new Date());
            entity.setPutOutUserId(0L);
            entity.setPutOutUserName("WZD-ADMIN");
            update(entity,wrapper);
        }
    }

    private boolean gotPermit(String withdrawSerial) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.eq("withdraw_serial",withdrawSerial);
        WithdrawRecord record = getOne(wrapper);
        if(record==null){
            return false;
        }
        if(record.getPermit().intValue()==1){
            return true;
        }
        return false;
    }

    private void sendShortMessage(String withdrawSerial){
        try{
            withdrawalService.sendWithdrawNoticeSMS(withdrawSerial);
        }catch (Exception e){
            log.error("===> {}",e.getMessage());
            e.printStackTrace();
        }
    }


    private void fulfillOrders(List<WithdrawRecord> rst){
        NumberFormat moneyFormat = NumberFormat.getInstance();
        moneyFormat.setMaximumFractionDigits(2);
        for(WithdrawRecord record : rst){
            String orders = record.getOrdersId();
            List<WithdrawOrderVO> orderList = new ArrayList();
            if(!StringUtils.isEmpty(orders)){
                if(orders.endsWith(",")){
                    orders = orders.substring(0,orders.length()-1);
                }
                if(!StringUtils.isBlank(orders)){
                    String[] arr = orders.split(",");
                    Map<String,String> profitMap = new HashMap();
                    String[] arrOrders = record.getOrdersId().split(",");
                    String[] arrProfits = record.getOrdersProfit().split(",");
                    if(arrOrders!=null && arrOrders.length>0){
                        for(int i=0;i<arrOrders.length;i++){
                            if(!org.springframework.util.StringUtils.isEmpty(arrOrders[i])){
                                profitMap.put(arrOrders[i],arrProfits[i]);
                            }
                        }
                    }
                    for(String id : arr){
                     if(StringUtils.isNotBlank(id)){
                         Long orderId = Long.valueOf(id);
                         Order orders1 = orderDao.findOrderInfo2(orderId);
                         if(orders1!=null){
                             WithdrawOrderVO withdrawOrderVO = new WithdrawOrderVO();
                             BeanUtils.copyProperties(orders1,withdrawOrderVO);
                             // 设置订单可提现金额
                             String withdrawSum = profitMap.get(id);
                             if(StringUtils.isEmpty(withdrawSum)){
                                 withdrawSum = "0.00";
                             }
                             withdrawOrderVO.setWithdrawSum(withdrawSum);
                             String actualPayStr = "0.00";
                             if(orders1.getActualPay()!=null){
                                 actualPayStr = moneyFormat.format(orders1.getActualPay().doubleValue());
                             }
                             withdrawOrderVO.setActualPayStr(actualPayStr);
                             orderList.add(withdrawOrderVO);
                         }
                     }
                    }
                }
            }
            record.setOrdersList(orderList);
        }
    }




}
