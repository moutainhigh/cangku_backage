package cn.enn.wise.ssop.service.order.service.impl;


import cn.enn.wise.ssop.api.order.dto.request.WithdrawApplyParam;
import cn.enn.wise.ssop.api.order.dto.request.WithdrawQueryParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderDistributorDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawOrderDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawRecordDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawWillingDTO;
import cn.enn.wise.ssop.service.order.config.AppGlobalData;
import cn.enn.wise.ssop.service.order.mapper.OrderMapper;
import cn.enn.wise.ssop.service.order.mapper.WithdrawRecordMapper;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.ssop.service.order.model.WithdrawRecord;
import cn.enn.wise.ssop.service.order.service.WithdrawLimitService;
import cn.enn.wise.ssop.service.order.service.WithdrawMessageService;
import cn.enn.wise.ssop.service.order.service.WithdrawRecordService;
import cn.enn.wise.ssop.service.order.service.WithdrawSerialService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 提现
 *
 * <p>提现相关业务处理方法</p>
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
@Slf4j
@Service
public class WithdrawRecordServiceImpl extends ServiceImpl<WithdrawRecordMapper, WithdrawRecord> implements WithdrawRecordService {



    @Autowired
    private WithdrawRecordMapper recordMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WithdrawLimitService limitService;
    @Autowired
    private WithdrawSerialService serialService;
    @Autowired
    private WithdrawMessageService withdrawMessageService;


    /**
     * 分销商是否可以提现
     *
     * @param distributorId 分销商ID
     * @return 可否提现，资质是否完备；是，返回true；否则false
     */
    @Override
    public boolean certifyWithdraw(Long distributorId) {

        //TODO 校验分销商提现资质
        return false;
    }

    /**
     * 创建提现单
     *
     * @param param
     * @return
     */
    @Override
    public String saveRecord(WithdrawApplyParam param) {
        try {
            WithdrawWillingDTO vo = getDistributeInfo(param.getDistributorId(), param.getStartDate(), param.getEndDate());

            if(vo!=null && vo.getDistributorStatus()!=1 && vo.getDistributorStatus()==2){
                return "-1";
            }
            if (vo == null || vo.getOrderList() == null || vo.getOrderList().size() == 0) {
                return "-2";
            }
            double thisApply = 0.0;
            if(vo.getApplySum()!=null && !"".equals(vo.getApplySum())){
                thisApply = Double.valueOf(vo.getApplySum());
            }
            int limitStatus = limitService.checkLimit(param.getDistributorId(),thisApply);
            if(limitStatus<0){
                if(limitStatus==-1){
                    // 次数上限
                    return "-3";
                }
                if(limitStatus==-2){
                    //金额上限
                    return "-4";
                }
            }
            //
            String withdrawSerial = getOrderSerial();
            String total = vo.getTotal();
            List<OrderDistributorDTO> list = vo.getOrderList();
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

            for(OrderDistributorDTO o : list){
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
            record.setPermit(-2);
            record.setPutOut(-2);
            record.setWithdraw(numberFormat.format(apply));
            record.setWithdrawSerial(withdrawSerial);
            record.setWithdrawTotal(total);
            record.setDistributeStatusUpdated(0);
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
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,withdrawSerial);
        WithdrawRecord record = getOne(wrapper);
        if(record==null){
            return withdrawSerial;
        }else{
            return getOrderSerial();
        }
    }



    /**
     * 获取分销可提现信息
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public WithdrawWillingDTO getDistributeInfo(Long id, String startDate, String endDate) {

        //TODO 获取指定分销商可提现信息
        return null;
    }

    /**
     * 更新分销单状态
     * <p>4 已返利 5 已发放 9 已创建提现申请单</p>
     * @param ordersId
     * @param status
     * @return
     */
    @Override
    public boolean updateDistributeStatus(String ordersId, Integer status) {

        //TODO 更新分销单状态，需调用其他模块接口

        return false;
    }

    /**
     * 根据提现单号获取提现单详情
     * @param withdrawSerial
     * @return
     */
    @Override
    public WithdrawRecordDTO getWithdrawInfoBySerial(String withdrawSerial) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,withdrawSerial);
        WithdrawRecord r =  getOne(wrapper);
        if(r==null){
            return null;
        }else{
            List<WithdrawRecord> list = new ArrayList();
            list.add(r);
            fulfillOrders(list);
            WithdrawRecordDTO dto = new WithdrawRecordDTO();
            WithdrawRecord record = list.get(0);
            BeanUtils.copyProperties(record,dto);
            return dto;
        }
    }

    /**
     * 根据提现单号更新已经更新分销单状态
     * @param withdrawSerial
     */
    @Override
    public void modifyWithdrawDistributeStatus(String withdrawSerial) {
        UpdateWrapper<WithdrawRecord> wrapper = new UpdateWrapper();
        wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,withdrawSerial);
        WithdrawRecord record = new WithdrawRecord();
        record.setDistributeStatusUpdated(1);
        update(record,wrapper);
    }


    /**
     * 清理审核状态
     * @param withdrawSerials
     */
    @Override
    public void clearWithdrawPermitStatus(String withdrawSerials) {
        if(StringUtils.isEmpty(withdrawSerials)){
            return;
        }
        String[] withdrawArr = withdrawSerials.split(",");
        for(String a : withdrawArr){
            if(!StringUtils.isEmpty(a)){
                UpdateWrapper<WithdrawRecord> wrapper = new UpdateWrapper();
                wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,a);
                WithdrawRecord record = new WithdrawRecord();
                record.setPermit(-2);
                record.setPermitComment(null);
                record.setPermitDateTime(null);
                record.setPermitUserId(null);
                record.setPermitUserName(null);
                update(record,wrapper);
            }
        }
    }

    /**
     * 查询指定日期内的提现订单数据
     * @param distributorId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<WithdrawRecordDTO> listRecordsInDate(Long distributorId, String startDate, String endDate) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.lambda()
                .eq(WithdrawRecord::getDistributorId,distributorId)
                .ge(WithdrawRecord::getApplyDateTime,startDate+" 00:00:00")
                .le(WithdrawRecord::getApplyDateTime,endDate+" 23:59:59")
                .orderByDesc(WithdrawRecord::getApplyDateTime);
        List<WithdrawRecord> rst =  list(wrapper);
        // 补充订单数据
        fulfillOrders(rst);
        return rst.stream().map(o->{
            WithdrawRecordDTO dto = new WithdrawRecordDTO();
            BeanUtils.copyProperties(o,dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 发送短信通知给审批人
     * @param withdrawSerials 提现单ID，使用半角逗号分隔
     */
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
    private void sendShortMessage(String withdrawSerial){
        try{
            withdrawMessageService.sendWithdrawNoticeSMS(withdrawSerial);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * PC 后台根据参数查询
     * @param param
     * @return
     */
    @Override
    public List<WithdrawRecordDTO> listRecordsByPage(WithdrawQueryParam param) {
        if(param.getPageNum()<=0){
            param.setPageNum(1);
        }
        if(param.getPageSize()<=0){
            param.setPageSize(30);
        }
        param.setOffset((param.getPageNum()-1)*param.getPageSize());
        List<WithdrawRecord> rst = recordMapper.selectByParam(param);
        fulfillOrders(rst);
        return rst.stream().map(o->{
            WithdrawRecordDTO dto = new WithdrawRecordDTO();
            BeanUtils.copyProperties(o,dto);
            return dto;
        }).collect(Collectors.toList());
    }


    private void fulfillOrders(List<WithdrawRecord> rst){
        NumberFormat moneyFormat = NumberFormat.getInstance();
        moneyFormat.setMaximumFractionDigits(2);
        for(WithdrawRecord record : rst){
            String orders = record.getOrdersId();
            List<WithdrawOrderDTO> orderList = new ArrayList();
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
                            Orders orders1 = orderMapper.selectById(orderId);
                            if(orders1!=null){
                                WithdrawOrderDTO withdrawOrderVO = new WithdrawOrderDTO();
                                BeanUtils.copyProperties(orders1,withdrawOrderVO);
                                // 设置订单可提现金额
                                String withdrawSum = profitMap.get(id);
                                if(StringUtils.isEmpty(withdrawSum)){
                                    withdrawSum = "0.00";
                                }
                                withdrawOrderVO.setWithdrawSum(withdrawSum);
                                String actualPayStr = "0.00";
                                if(orders1.getActualPayPrice()!=null){
                                    actualPayStr = moneyFormat.format(orders1.getActualPayPrice().doubleValue());
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

    /**
     * 总数据量
     * @param param
     * @return
     */
    @Override
    public Long listRecordsByPageCount(WithdrawQueryParam param) {
        return recordMapper.selectByParamCount(param);
    }

    /**
     * 设置状态为发放
     * @param withdrawSerials 提现单ID，使用半角逗号分隔
     */
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
            wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,one);
            WithdrawRecord entity = new WithdrawRecord();
            entity.setPutOut((byte) 1);
            entity.setPutOutDateTime(new Date());
            entity.setPutOutUserId(AppGlobalData.localCreatorId.get());
            entity.setPutOutUserName(AppGlobalData.localCreatorName.get());
            update(entity,wrapper);
        }
    }


    private boolean gotPermit(String withdrawSerial) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.lambda().eq(WithdrawRecord::getWithdrawSerial,withdrawSerial);
        WithdrawRecord record = getOne(wrapper);
        if(record==null){
            return false;
        }
        if(record.getPermit()==1){
            return true;
        }
        return false;
    }


}
