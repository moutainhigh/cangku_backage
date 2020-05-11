package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.bo.OrderTicket;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.vo.CouponInfoVo;
import cn.enn.wise.platform.mall.bean.vo.GoodsApiResVO;
import cn.enn.wise.platform.mall.bean.vo.NxjOrderContext;
import cn.enn.wise.platform.mall.bean.vo.ThirdOrderContext;
import cn.enn.wise.platform.mall.config.pay.WxPayService;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.ThirdOrderService;
import cn.enn.wise.platform.mall.util.DateUtil;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.GeneUtil;
import cn.enn.wise.platform.mall.util.XmlHelper;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.thirdparty.nxj.NxjSoapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 楠溪江门票订单服务
 *
 * @author baijie
 * @date 2020-02-13
 */
@Service
@Slf4j
public class NxjThirdOrderServiceImpl implements ThirdOrderService {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Resource
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @Resource
    private OrderAppletsMapper orderAppletsMapper;

    @Autowired
    private WzdOrderAppletsServiceImpl wzdOrderAppletsServiceImpl;

    @Autowired
    private WxPayService wxPayService;

    @Resource
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Resource
    private GoodsExtendMapper goodsExtendMapper;

    @Resource
    private OrdersTicketMapper ordersTicketMapper;


    @Resource
    private UserOfCouponMapper userOfCouponMapper;

    @Override
    public Object saveOrder(ThirdOrderContext thirdOrderContext) throws Exception {
        ThirdOrderContext goodsInfo = getGoodsInfo(thirdOrderContext);
        ThirdOrderContext preSaveOrder = preSaveOrder(goodsInfo);
        ThirdOrderContext buildOrderInfo = buildOrderInfo(preSaveOrder);
        ThirdOrderContext orderTicketInfo = buildOrderTicketInfo(buildOrderInfo);
        ThirdOrderContext prePayInfo = getPrePayInfo(orderTicketInfo);

        NxjOrderContext nxjOrderContext = (NxjOrderContext) prePayInfo;

        return nxjOrderContext.getPrePayInfo();
    }

    @Override
    public ThirdOrderContext getGoodsInfo(ThirdOrderContext orderContext) throws RemoteException {
        NxjOrderContext nxjOrderContext = (NxjOrderContext) orderContext;
        nxjOrderContext.setIsPay(true);
        Long goodsId = nxjOrderContext.getGoodsId();
        //查询商品信息
        GoodsApiResVO goodsInfoById = wzdGoodsAppletsMapper.getGoodsInfoById(goodsId);
        if(goodsInfoById == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商品未找到");
        }
        String shipLineInfo = goodsInfoById.getGoodsApiExtendResVoList().get(0).getShipLineInfo();
        if(StringUtils.isEmpty(shipLineInfo)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"楠溪江门票信息为空");
        }
        JSONObject ticketInfo = JSONObject.parseObject(shipLineInfo);

        //封装商品信息

        String supplierId = ticketInfo.getString("UUaid");
        String productId = ticketInfo.getString("UUpid");
        String playTime = nxjOrderContext.getPlayTime();
         //库存检查，实时价格获取
        String realTimeStorage = NxjSoapUtil.binding
                .getRealTimeStorage(
                        NxjSoapUtil.USER_NAME,
                        NxjSoapUtil.PASSWORD,
                        supplierId,
                        productId,
                        playTime,
                        playTime);
        String json = XmlHelper.xml2json(realTimeStorage);

       if(StringUtils.isEmpty(json)){
           throw new BusinessException(GeneConstant.BUSINESS_ERROR,"实时门票价格获取失败!",json);
       }
        List<Object> objects = XmlHelper.parseJson2List(json);
        HashMap<String,Object> priceInfo = (HashMap<String, Object>) objects.get(0);

        if(priceInfo.get("UUerrorcode") != null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"实时门票价格获取失败!",json);

        }
        //保存商品信息票信息和购买价格到context中
        nxjOrderContext.setGoodsInfo(goodsInfoById);
        nxjOrderContext.setTicketInfo(ticketInfo);
        nxjOrderContext.setBuyPrice(new BigDecimal(priceInfo.get("buy_price").toString()));
        //nxjOrderContext.setBuyPrice(new BigDecimal(1));
        return nxjOrderContext;
    }

    @Override
    public ThirdOrderContext preSaveOrder(ThirdOrderContext orderContext) throws Exception {
        NxjOrderContext nxjOrderContext = (NxjOrderContext) orderContext;
        //获取票付通预下单信息
        JSONObject ticketInfo = nxjOrderContext.getTicketInfo();

        String tid = ticketInfo.getString("UUid");
        String tnum = String.valueOf(nxjOrderContext.getAmount());
        String playTime = nxjOrderContext.getPlayTime();
        String orderTel = nxjOrderContext.getOrderTel();
        String supplierId = ticketInfo.getString("UUaid");
        String payMode = "2";
        List<Map<String, Object>> extInfo = nxjOrderContext.getExtInfo();
        StringBuilder userNameString = new StringBuilder();
        StringBuilder idCardString = new StringBuilder();
        for (Map<String, Object> stringObjectMap : extInfo) {
            idCardString
                    .append(stringObjectMap.get("idCard").toString())
                    .append(",");
            userNameString
                    .append(stringObjectMap.get("tourismName").toString())
                    .append(",");
        }
        String personId = idCardString.toString().substring(0,idCardString.toString().length()-1);
        //票付通预下单
        String orderName = userNameString.toString().substring(0,userNameString.toString().length()-1);;
        String orderPreCheck = NxjSoapUtil.binding.orderPreCheck(
                NxjSoapUtil.USER_NAME,
                NxjSoapUtil.PASSWORD,
                tid,
                tnum,
                playTime,
                orderTel,
                orderName,
                supplierId,
                payMode,
                personId);
        String json = XmlHelper.xml2json(orderPreCheck);
        List<Object> list = XmlHelper.parseJson2List(json);
        if(list==null || list.size()==0){
           throw new BusinessException(GeneConstant.BUSINESS_ERROR,"下单失败，请稍后重试");
        }

        Map<String,Object> result =(HashMap)list.get(0);
        if(result.get("UUerrorcode") != null){
            if("1091".equals(result.get("UUerrorcode").toString())){
                throw new BusinessException(6,"超过购买数量");
            }
        }
        if(result.get("UUdone")==null || !"100".equals(result.get("UUdone").toString())){
           throw new BusinessException(GeneConstant.BUSINESS_ERROR,"系统繁忙，请稍后重试");
        }
        nxjOrderContext.setPersonId(personId);
        return nxjOrderContext;
    }

    @Override
    public ThirdOrderContext buildOrderInfo(ThirdOrderContext orderContext) throws Exception {
        NxjOrderContext nxjOrderContext = (NxjOrderContext) orderContext;

        //获取商品信息
        GoodsApiResVO goodsInfo = nxjOrderContext.getGoodsInfo();

        String wxOrderCode = GeneUtil.getCode();
        Orders.OrdersBuilder builder = Orders.builder();
        String goodsName = goodsInfo.getGoodsName();
        String projectCode = goodsInfo.getProjectCode();
        Long projectId = Long.parseLong(goodsInfo.getProjectId());
        //订单价格和单价获取
        //商品单价由mall系统决定
        BigDecimal singlePrice = goodsInfo.getGoodsApiExtendResVoList().get(0).getSalePrice();

        BigDecimal goodsPrice = singlePrice.multiply(new BigDecimal(nxjOrderContext.getAmount()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path",nxjOrderContext.getPath());
        jsonObject.put("formId",nxjOrderContext.getFormId());
        jsonObject.put("openId",nxjOrderContext.getOpenId());
        jsonObject.put("pftSinglePrice",nxjOrderContext.getBuyPrice());

        builder
                .goodsName(goodsName)
                .orderType(nxjOrderContext.getOrderType())
                .batCode(wxOrderCode)
                .projectCode(projectCode)
                .projectId(projectId)
                .enterTime(new java.sql.Date(DateUtil.formatD(nxjOrderContext.getPlayTime(),"yyyy-MM-dd").getTime()))
                .goodsPrice(goodsPrice)
                .siglePrice(singlePrice)
                .state(GeneConstant.INT_1)
                .amount(Long.valueOf(nxjOrderContext.getAmount()))
                .userId(nxjOrderContext.getUser().getId())
                .scenicId(nxjOrderContext.getScenicId())
                .type(GeneConstant.LONG_5)
                .goodsId(goodsInfo.getGoodsApiExtendResVoList().get(0).getId())
                .isDistributeOrder(GeneConstant.INT_0)
                .orderSource(GeneConstant.INT_1)
                .payStatus(GeneConstant.INT_1)
                .actualPay(new BigDecimal("0.00"))
                .shouldPay(goodsPrice)
                .payType("weixin")
                .maxNumberOfUsers(nxjOrderContext.getAmount())
                .offlineStatus(2)
                .profitStatus(2)
                .idNumber(nxjOrderContext.getPersonId())
                .userWechatName(nxjOrderContext.getUserWechatName())
                .name(nxjOrderContext.getTourismName())
                .phone(nxjOrderContext.getOrderTel())
                .snapshot(jsonObject.toJSONString())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .expiredTime(new Timestamp(DateUtil.getNextTime(20).getTime()));
        Orders orders = builder.build();
        if("prod".equals(profiles)){
            orders.setProfiles("");
        }else {
            orders.setProfiles(profiles);
        }
        nxjOrderContext.setOrders(orders);
        //优惠券参数
        setOrderCouponInfo(nxjOrderContext);
        int i = wzdOrderAppletsMapper.insertOrder(orders);
        if(i > 0){
            Orders orderById = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());
        }else {
            throw new BusinessException(GeneConstant.SYSTEM_ERROR,"订单创建失败");
        }
        nxjOrderContext.setOrders(orders);
        log.info("订单保存完毕");
        return nxjOrderContext;
    }

    private void setOrderCouponInfo(NxjOrderContext nxjOrderContext) {

        Long userOfCouponId = nxjOrderContext.getUserOfCouponId();
        Long userId = nxjOrderContext.getUser().getId();
        //用户使用优惠券下单,而且用户必须是单品下单的，可以使用优惠券
        if(userOfCouponId != null){
            Orders orders = nxjOrderContext.getOrders();
            //查询优惠券信息
                CouponInfoVo couponInfo = userOfCouponMapper.getCouponInfo(userId, userOfCouponId);

                if (couponInfo != null) {
                    if(couponInfo.getPromotionStatus() != 2){
                        throw new BusinessException(5,"优惠活动已失效");
                    }
                    if(couponInfo.getStatus() != 1 ){
                        throw new BusinessException(5,"优惠券已失效");
                    }

                    if(couponInfo.getGoodsCouponStatus() != 1){
                        throw new BusinessException(5,"优惠券不可用");
                    }
//                    //是分销单
//                    if(orders.getIsDistributeOrder() == 1){
//                        if(!payParam.getDriverMobile().equals(couponInfo.getPhone())){
//                            throw new BusinessException(5,"该分销商优惠券不可用");
//                        }
//                    }

//                   if(!projectId.equals(couponInfo.getGoodsId())){
//                       throw new BusinessException(3,"该优惠券不适用于该商品");
//                   }

                    //计算优惠金额
                    try {
                        BigDecimal couponPrice =  wzdOrderAppletsServiceImpl.calculatePrice(couponInfo,orders.getSiglePrice(),orders.getGoodsPrice());

                        BigDecimal subtract = orders.getGoodsPrice().subtract(couponPrice);
                        BigDecimal zeroBigdecimal = new BigDecimal("0");
                        if(zeroBigdecimal.compareTo(subtract) == 1){
                            subtract = zeroBigdecimal;
                            couponPrice = orders.getGoodsPrice();
                          nxjOrderContext.setIsPay(false);
                          orders.setPayStatus(2);
                          orders.setState(2);
                          orders.setActualPay(new BigDecimal("0.00"));
                        }

                        orders.setUserOfCouponId(userOfCouponId);
                        orders.setCouponPrice(couponPrice);
                        orders.setShouldPay(subtract);

//
                        //扣除用户优惠券
                        userOfCouponMapper.updateCouponStateAndCouponPrice(orders.getUserOfCouponId(),2,couponPrice);
                    }catch (BusinessException e){
                        log.error("该订单不满足订单优惠条件");
                    }
                }
                nxjOrderContext.setOrders(orders);


        }
    }

    @Override
    public ThirdOrderContext buildOrderTicketInfo(ThirdOrderContext orderContext) {
        NxjOrderContext nxjOrderContext = (NxjOrderContext) orderContext;
        List<OrderTicket> orderTicketsList = new ArrayList<>();
        Orders orders = nxjOrderContext.getOrders();
        Long amount = orders.getAmount();
        String phone = orders.getPhone();

        boolean flag = false;
        //获取优惠金额
        BigDecimal couponPrice = orders.getCouponPrice();
        if(couponPrice != null && new BigDecimal(0).compareTo(couponPrice) == -1){
            flag = true;
        }
        List<Map<String, Object>> extInfo = nxjOrderContext.getExtInfo();
        //累计金额
        BigDecimal countPrice = new BigDecimal(0);
        for (int j = 0; j < amount; j++) {
            OrderTicket ticket = OrderTicket.builder()
                    .ticketCode(orders.getOrderCode())
                    .createTime(orders.getCreateTime())
                    .orderId(orders.getId())
                    .goodsExtendId(orders.getGoodsId())
                    .goodsName(orders.getGoodsName())
                    .projectId(orders.getProjectId())
                    .projectName(nxjOrderContext.getGoodsInfo().getProjectName())
                    .goodsId(nxjOrderContext.getGoodsInfo().getId())
                    .ticketUserName(extInfo.get(j).get("tourismName").toString())
                    .couponPrice(orders.getSiglePrice())
                    .idCard(extInfo.get(j).get("idCard").toString())
                    .phone(phone)
                    .singlePrice(orders.getSiglePrice())
                    .build();
            //计算优惠后的分摊价格
            if(flag){

                //每个商品分摊的价格四舍五入
                BigDecimal divide = couponPrice.divide(new BigDecimal(amount),2,BigDecimal.ROUND_HALF_DOWN);
                log.info("每个商品分摊价格为:"+divide);
                //商品单价 - 优惠价格
                BigDecimal subtract = ticket.getSinglePrice().subtract(divide).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                if(amount-j == 1){
                    //最后一次循环，也就是最后一个商品
                    //最后一个商品单价 = 订单金额 - 优惠金额 - 每个商品分摊价*已经分摊的个数
                    ticket.setCouponPrice(orders.getGoodsPrice().subtract(couponPrice).subtract(countPrice));
                    log.info("最后一个商品的退款金额为:"+ticket.getSinglePrice());
                }else {
                    //加入总金额中
                    countPrice = countPrice.add(subtract);
                    ticket.setCouponPrice(subtract);
                    log.info("每个商品的退款金额为:"+subtract);
                }

            }
            orderTicketsList.add(ticket);
        }
        orderAppletsMapper.insertOrdertTicket(orderTicketsList);

        log.info("子订单保存完毕");
        return nxjOrderContext;
    }

    @Override
    public ThirdOrderContext getPrePayInfo(ThirdOrderContext orderContext) {
        NxjOrderContext nxjOrderContext = (NxjOrderContext) orderContext;
        Orders orders = nxjOrderContext.getOrders();

        Boolean isPay = nxjOrderContext.getIsPay();
        if(!isPay){
            Map<String,Object> map =new HashMap<>();
            map.put("orderCode",orders.getOrderCode());
            nxjOrderContext.setPrePayInfo(map);
            this.processPaySuccessOrder(orders.getBatCode());
            log.info("该笔订单免单:batCode=>{}",orders.getBatCode());
            return nxjOrderContext;

        }
        String totalFee;

        if(orders.getUserOfCouponId() != null){
            totalFee = orders.getShouldPay().multiply(new BigDecimal("100")).setScale(0).toString();
        }else {

            totalFee =  orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();
        }
        //调用支付信息
        Object object =  wxPayService.pay(orders.getScenicId(), orders.getGoodsName(), nxjOrderContext.getOpenId(),orders.getBatCode(), totalFee, nxjOrderContext.getIp(),orders.getOrderCode());
        nxjOrderContext.setPrePayInfo(object);
        return nxjOrderContext;
    }

    @Override
    public void processPaySuccessOrder(String wxOrderCode) {

      try {
          //查询本地订单状态
          Orders orderInfo = wzdOrderAppletsMapper.getOrderInfoByBatCode(wxOrderCode);

          if(StringUtils.isNotEmpty(orderInfo.getTicketOrderCode())){
              log.info("ticketOrderCode不为空，该订单不处理");
              return;
          }

          Map <String,Object> alertMap = new HashMap<String,Object>();
          wzdOrderAppletsServiceImpl.orderAlert(alertMap,orderInfo);
          if(alertMap.get("isAlert") != null){
              log.info("该订单已进行短信告警！");
              return;
          }

          GoodsExtend goodsExtend = goodsExtendMapper.selectById(orderInfo.getGoodsId());
          JSONObject jsonObject = JSONObject.parseObject(goodsExtend.getShipLineInfo());
          //景区ID
          String lid = jsonObject.getString("UUlid");
          //门票Id
          String tid = jsonObject.getString("UUid");
          String snapshot = orderInfo.getSnapshot();
          BigDecimal pftSinglePrice = new BigDecimal(JSONObject.parseObject(snapshot).getString("pftSinglePrice"));
          //tprice 单位分 该价格为票付通预下单时的订单价格，此价格不是实际支付价格，实际支付价格以mall系统定价为准
          String tnum = String.valueOf(orderInfo.getAmount());
          String tprice = pftSinglePrice.multiply(new BigDecimal(tnum)).multiply(new BigDecimal("100")).toString();
          // 查询订单子表
          List<OrderTicket> orderTicketList = ordersTicketMapper.selectList(new QueryWrapper<OrderTicket>().eq("order_id", orderInfo.getId()));
          StringBuilder userNameString = new StringBuilder();
        for (OrderTicket orderTicket : orderTicketList) {
              userNameString
                      .append(orderTicket.getTicketUserName())
                      .append(",");
          }
          String orderName = userNameString.toString().substring(0,userNameString.toString().length()-1);;
          log.info("票付通订单价格=>{}",tprice);
          String smsSend = "0";
          String paymode = "2";
          String orderMode = "0";
          //线路时需要，可为空
          String assembly = null;
          //线路时需要，可为空；场次信息获取接口详情3.7 方 法。必填参数格式：json_encode(array(（int）场馆 id,（int）场次id,（string）分区id));）
          String series = null;
          String concatID = "0";
          String pCode = "0";
          String m = jsonObject.getString("UUaid");
          String memo = "";
          log.info("景区Id =>{},门票Id=>{},wx订单号=>{},价格=>{},amount=>{},playTime=>{},name=>{},phone={},smsSend=>{},payMode=>{},供应商Id=>{},身份证号=>{}"
                  ,lid,tid,wxOrderCode,tprice,tnum,orderInfo.getEnterTime(),orderName,orderInfo.getPhone(),smsSend,paymode,m,orderInfo.getIdNumber());
          if(orderInfo.getOrderType() == 7) {
              //处理门票订单
              //提交票务通订单
              String xml = NxjSoapUtil
                      .binding
                      .PFT_Order_Submit(
                              NxjSoapUtil.USER_NAME,
                              NxjSoapUtil.PASSWORD,
                              lid,
                              tid,
                              wxOrderCode,
                              tprice,
                              tnum,
                              new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getEnterTime()),
                              orderName,
                              orderInfo.getPhone(),
                              orderInfo.getPhone(),
                              smsSend,
                              paymode,
                              orderMode,
                              assembly,
                              series,
                              concatID,
                              pCode,
                              m,
                              orderInfo.getIdNumber(),
                              memo);
              if (StringUtils.isEmpty(xml)) {
                  throw new BusinessException(GeneConstant.BUSINESS_ERROR, "系统繁忙,确认订单失败！票务通没有返回数据，orderCode=" + orderInfo.getOrderCode());
              }
              String json = XmlHelper.xml2json(xml);
              log.info("票务通返回数据=>{}", json);
              List<Object> resultList = (List<Object>) XmlHelper.parseJson2List(json);
              HashMap map = (HashMap) resultList.get(0);
              Object uUordernum = map.get("UUordernum");
              Object uremotenum = map.get("UUremotenum");
//              Object uUcode = map.get("UUcode");
//              Object uqrcodeURL = map.get("UUqrcodeURL");
//              Object uqrcodeIMG = map.get("UUqrcodeIMG");
//              StringBuilder stringBuilder = new StringBuilder();
//              String ticketCodeOther = stringBuilder
//                      .append(uUcode)
//                      .append(",")
//                      .append(uqrcodeURL)
//                      .append(",")
//                      .append(uqrcodeIMG).toString();

              if (!wxOrderCode.equals(uremotenum.toString())) {
                  throw new BusinessException(GeneConstant.BUSINESS_ERROR, "订单号返回不一致");
              }


              // 查询 订单
                log.info("查询票付通订单:票付通订单号=>{},微信订单号=>{}",uUordernum,uremotenum);
              String orderQueryxml = NxjSoapUtil.binding.orderQuery(
                      NxjSoapUtil.USER_NAME,
                      NxjSoapUtil.PASSWORD,
                      uUordernum.toString(),
                      null
              );
              if (StringUtils.isEmpty(orderQueryxml)) {
                  throw new BusinessException(GeneConstant.BUSINESS_ERROR, "系统繁忙,确认订单失败！票务通没有返回数据，uUordernum=" + uUordernum.toString());
              }
              String orderQueryJson = XmlHelper.xml2json(orderQueryxml);

              //更新本地订单
              Orders orders = new Orders();
              orders.setTicketOrderCode(uUordernum.toString());
              orders.setBatCodeOther(orderQueryJson);
              orders.setId(orderInfo.getId());
              wzdOrderAppletsMapper.updateOrderNew(orders);
              log.info("更新票付通订单完毕");
          }
        }catch (Exception e){
          log.error("处理票付通订单异常:",e);
      }



    }
}
