package cn.enn.wise.ssop.service.order.controller;

import cn.enn.wise.ssop.api.order.dto.request.PayParam;
import cn.enn.wise.ssop.api.order.dto.response.PayResponseDto;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityBaseDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.OrderQueryDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityAppletsFacade;
import cn.enn.wise.ssop.api.promotions.facade.ActivityFacade;
import cn.enn.wise.ssop.api.promotions.facade.GroupOrderAppletsFacade;
import cn.enn.wise.ssop.service.order.config.OrderMqConfig;
import cn.enn.wise.ssop.service.order.config.constants.PayConstants;
import cn.enn.wise.ssop.service.order.config.enums.OrderSalesTypeEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.handler.mq.sender.MessageSender;
import cn.enn.wise.ssop.service.order.model.MessageBody;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.OrderSale;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.ssop.service.order.service.OrderSaleService;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.service.PayService;
import cn.enn.wise.ssop.service.order.service.impl.BaseOrderServiceImpl;
import cn.enn.wise.ssop.service.order.thirdparty.IThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrderFactory;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrderItem;
import cn.enn.wise.ssop.service.order.thirdparty.shenda.GeneConstant;
import cn.enn.wise.ssop.service.order.thirdparty.shenda.XmlUtil;
import cn.enn.wise.ssop.service.order.wx.*;
import cn.enn.wise.uncs.base.exception.BusinessException;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;
import static cn.enn.wise.uncs.common.http.GeneUtil.replaceBlank;

/**
 * @author 安辉
 */
@Slf4j
@RestController
@RequestMapping("/pay")
@Api(value ="支付", tags = {"支付API"})
public class PayController {

    @Autowired
    MessageSender sender;

    @Autowired
    PayService payService;

    @Autowired
    GroupOrderAppletsFacade groupOrderAppletsFacade;

    @Autowired
    ActivityAppletsFacade activityAppletsFacade;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderSaleService orderSaleService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @RequestMapping(value = "pay", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prepayId", value = "微信单号id", required = true, paramType = "query")
    })
    public R pay(@RequestBody PayParam param) throws Exception {
        return payService.pay(param.getPrepayId());
    }


    @RequestMapping(value = "unifiedOrder", method = RequestMethod.POST)
    @ApiOperation(value = "微信统一下单", notes = "微信统一下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "编号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "body", value = "消息体", required = true, paramType = "query"),
            @ApiImplicitParam(name = "total", value = "金额", required = true, paramType = "query"),
            @ApiImplicitParam(name = "openId", value = "openid", required = true, paramType = "query")
    })
    public R unifiedOrder(String orderSn, String body, String total, String openId) throws Exception {
        Map result = payService.unifiedOrder(orderSn, body, total, openId);
        return R.success(result);
    }


    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/freeApi/callback")
    @ApiOperation(value = "微信统一下单回调", notes = "微信统一下单回调")
    public Object paySuccess(HttpServletRequest request) {

        log.info("callback:"+request.getPathInfo());

        String stringXml = getParam(request);

        log.info("stringXml:"+stringXml);
        Map<String,String> map = XmlUtil.doXMLParse(stringXml, 0);

        log.info("pay map:"+JSON.toJSONString(map));
//
//        Map<String,String> xmlMap = new HashMap<>(4);
//        xmlMap.put(PayConstants.RETURN_CODE,GeneConstant.SUCCESS_UPPERCASE);
//        xmlMap.put(PayConstants.RETURN_MSG,GeneConstant.OK);
//        String stringresult = map2Xml(xmlMap);
//        //本地订单号
        String orderId =map.get("out_trade_no");

        log.info("orderId:"+orderId);
//        //微信流水号
//        //String wxPayCode =map.get("transaction_id");
//        Boolean isSignOk =  checkParam(map);
//        log.info("isSignOk:"+isSignOk);
//        if(!isSignOk){
//            return null;
//        }
        Boolean updateSuccess = orderService.updateOrderStatus(Long.parseLong(orderId), null, PayStatusEnum.PAYED,
                null,null,null,null,null,
                null,null,null,"支付成功",true);
        log.info("updateSuccess:"+ updateSuccess );
        //if(updateSuccess){
        //    return stringresult;
        //}
        return false;

        //region 发送消息 参数
//        List<ThirdPartyOrderItem> itemList = new ArrayList<>();
//        ThirdPartyOrderItem item = new ThirdPartyOrderItem();
//        item.setConsumerName("安辉");
//        item.setIdNumber("131081198501022318");
//        itemList.add(item);
//
//        ThirdPartyOrder order = new ThirdPartyOrder();
//        order.setAmount(1);
//        order.setDay("2020-04-19");
//        order.setEnterTime("2020-04-19");
//        order.setIdNumber("131081198501022318");
//        order.setItems(itemList);
//
//        order.setOrderCode("cd20200101");
//        order.setOrderName("orderName");
//        order.setProductCode("PST20200405353051");
//        order.setProductName("测试");
//        order.setProductPrice(19L);
//
//        order.setScenicId(5L);
//        order.setOrderPhone("15810633897");
        // endregion

        //region 发送消息
//        MessageBody body = new MessageBody();
//        body.setCompanyId(order.getScenicId().toString());
//        body.setData(JSON.toJSONString(order));
//        body.setTag(OrderMqConfig.OrderQueue.PAY_SUCCESS_QUEUE);
//        body.setTimestamp(new Timestamp(System.currentTimeMillis()));
//        sender.paySuccess(body);
        //endregion

    }
    /**
     * 获取返回值
     * @Title getParam
     * @param request
     * @return
     * @since JDK 1.8
     * @throws
     */
    private String getParam(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(request.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("getParam error", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("getParam close stream error", e);
                }
            }
        }
        return sb.toString();
    }


    /**
     * map转成为xml保温数据
     *
     * @param map 参数集合
     * @return
     */
    public static String map2Xml(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<xml>");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append("<");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(">");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("</");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(">");

        }
        stringBuilder.append("</xml>");
        return stringBuilder.toString();
    }

    /**
     * 检查支付回调参数
     * @param map
     *
     *      * 微信支付回调数据格式
     *      * <xml>
     *      * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     *      * <attach><![CDATA[支付测试]]></attach>
     *      * <bank_type><![CDATA[CFT]]></bank_type>
     *      * <fee_type><![CDATA[CNY]]></fee_type>
     *      * <is_subscribe><![CDATA[Y]]></is_subscribe>
     *      * <mch_id><![CDATA[10000100]]></mch_id>
     *      * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
     *      * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
     *      * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
     *      * <result_code><![CDATA[SUCCESS]]></result_code>
     *      * <return_code><![CDATA[SUCCESS]]></return_code>
     *      * <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>
     *      * <time_end><![CDATA[20140903131540]]></time_end>
     *      * <total_fee>1</total_fee>
     *      * <coupon_fee><![CDATA[10]]></coupon_fee>
     *      * <coupon_count><![CDATA[1]]></coupon_count>
     *      * <coupon_type><![CDATA[CASH]]></coupon_type>
     *      * <coupon_id><![CDATA[10000]]></coupon_id>
     *      * <coupon_fee><![CDATA[100]]></coupon_fee>
     *      * <trade_type><![CDATA[JSAPI]]></trade_type>
     *      * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
     *      * </xml>
     *
     */
    private Boolean checkParam(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        if (!GeneConstant.SUCCESS_UPPERCASE.equals(map.get(PayConstants.RETURN_CODE))) {
            return false;
        }

        //获取返回的签名
        String wxSign = map.get(PayConstants.SIGN);
        map.remove(PayConstants.SIGN);
        String linkString = createLinkString(map);

        //TODO 放到数据库里
        SsopConfig ssopConfig = new SsopConfig();
        //获取商户秘钥
        String key = ssopConfig.getKey();
        String sign = sign(linkString, key, "utf-8").toUpperCase();
        return sign.equals(wxSign);
    }


    /**
     * 签名字符串
     *
     * @param text         需要签名的字符串
     * @param key          密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String inputCharset) {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(getContentBytes(text, inputCharset));
    }
    /**
     * @param content
     * @param charset
     * @return
     * @throws java.security.SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    @RequestMapping(value = "pay/query", method = RequestMethod.GET)
    @ApiOperation(value = "微信订单查询", notes = "微信订单查询")
    public R queryPayStatus(Long orderId, Long groupOrderId) throws Exception {

        // 2 微信统一查询
        SsopConfig ssopConfig = new SsopConfig();
        Map<String, String> params = new HashMap();
        params.put("out_trade_no", orderId.toString());
        Map<String, String> result = new HashMap<>();
        try {
            WXPay pay = new WXPay(ssopConfig);
            result = pay.orderQuery(params);
        } catch (Exception e) {
            OrderExceptionAssert.ORDER_WECHAT_ORDER_QUERY_EXCEPTION.assertFail();
        }
        // region 返回格式
        /**
         <xml>
         <return_code><![CDATA[SUCCESS]]></return_code>
         <return_msg><![CDATA[OK]]></return_msg>
         <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
         <mch_id><![CDATA[10000100]]></mch_id>
         <device_info><![CDATA[1000]]></device_info>
         <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>
         <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>
         <result_code><![CDATA[SUCCESS]]></result_code>
         <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>
         <is_subscribe><![CDATA[Y]]></is_subscribe>
         <trade_type><![CDATA[APP]]></trade_type>
         <bank_type><![CDATA[CCB_DEBIT]]></bank_type>
         <total_fee>1</total_fee>
         <fee_type><![CDATA[CNY]]></fee_type>
         <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>
         <out_trade_no><![CDATA[1415757673]]></out_trade_no>
         <attach><![CDATA[订单额外描述]]></attach>
         <time_end><![CDATA[20141111170043]]></time_end>
         <trade_state><![CDATA[SUCCESS]]></trade_state>
         </xml>
         * */
        log.info("queryParam:" + result);
        // endregion
        if (result.get("result_code").equals("SUCCESS")) {
            List<OrderSale> orderSales = orderSaleService.getOrderSaleList(orderId);
            log.info("orderSales:" + JSON.toJSONString(orderSales));
            Long activityBaseId = 0L;
            for (OrderSale orderSale : orderSales) {
                activityBaseId = orderSale.getActivityBaseId();
            }
            // 没有参加活动，返回订单状态
            if (activityBaseId == 0L) {
                return R.success(result);
            }
            R activityDetail = activityAppletsFacade.detail(activityBaseId);
            log.info("activityDetail:" + JSON.toJSONString(activityDetail));
            Byte activityType = 0;
            if (activityDetail.getCode() == 0) {
                ActivityBaseDTO activityBaseDTO = (ActivityBaseDTO) activityDetail.getData();
                activityType = activityBaseDTO.getActivityType();
                log.info("ActivityBaseDTO:" + JSON.toJSONString(activityBaseDTO));
            }

            if (activityType == OrderSalesTypeEnum.GROUP_BUY.getValue()) {
                // 拼团活动
                if (groupOrderId == 0) {
                    // 参团
                    R r = createGroupOrder(orderId);
                    log.info("r0:" + JSON.toJSONString(r));
                    if (r.getCode() == 0) {
                        log.info("r:" + JSON.toJSONString(r));
                        OrderQueryDTO orderQueryDTO = (OrderQueryDTO) r.getData();
                        result.put("groupOrderId", orderQueryDTO.getGroupOrderId().toString());
                        result.put("orderId", orderId.toString());
                        result.put("groupType","1");

                        Orders orders = orderService.getOrderInfo(orderId);
                        orders.setGroupOrderId(orderQueryDTO.getGroupOrderId());
                        orderService.updateOrder(orders);
                    }
                } else {
                    // 加入团购,返回拼团状态 3 团满 重新开团
                    R r = joinGroupOrder(orderId, groupOrderId);

                    log.info("---->joinGroupOrder:"+JSON.toJSONString(r));
                    if (r.getCode() == 0) {
                        Map<String, String> map = (Map) r.getData();
                        // 参团失败，重新开团
                        if (3 == Integer.parseInt(map.get("code"))) {
                            R reCrete = createGroupOrder(orderId);
                            if (reCrete.getCode() == 0) {
                                OrderQueryDTO orderQueryDTO = (OrderQueryDTO) reCrete.getData();
                                result.put("groupOrderId", orderQueryDTO.getGroupOrderId().toString());
                                result.put("orderId", orderId.toString());
                                result.put("groupType","1");

                                Orders orders = orderService.getOrderInfo(orderId);
                                orders.setGroupOrderId(orderQueryDTO.getGroupOrderId());
                                orderService.updateOrder(orders);
                            }
                        }
                        //code=1 参团成功，成团状态,2 参团成功 未成团状态,3 参团失败 团已满状态，需重新调用开团接口
                        result.put("groupOrderId", map.get("groupOrderId"));
                        result.put("orderId", orderId.toString());
                        result.put("groupType","2");

                        Orders orders = orderService.getOrderInfo(orderId);
                        orders.setGroupOrderId(Long.parseLong(map.get("groupOrderId")));
                        orderService.updateOrder(orders);
                    }
                }
            } else if(activityType == OrderSalesTypeEnum.PROMOTION.getValue()){

            }
        }
        return R.success(result);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    @ApiOperation(value = "微信退款", notes = "微信退款")
    public R orderRefund(String orderSn, String refundNo, Integer total, Integer refund) {

        //微信退款
        SsopConfig ssopConfig = new SsopConfig();
        Map<String, String> params = new HashMap();
        params.put("out_trade_no", orderSn);
        params.put("out_refund_no", refundNo);
        params.put("total_fee", total.toString());
        params.put("refund_fee", refund.toString());
        Map<String, String> result = new HashMap<>();
        try {
            WXPay pay = new WXPay(ssopConfig);
            result = pay.refund(params);
        } catch (Exception e) {
            OrderExceptionAssert.ORDER_WECHAT_REFUND_EXCEPTION.assertFail();
        }
        // region 返回格式
        /**
         <xml>
         <return_code><![CDATA[SUCCESS]]></return_code>
         <return_msg><![CDATA[OK]]></return_msg>
         <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
         <mch_id><![CDATA[10000100]]></mch_id>
         <device_info><![CDATA[1000]]></device_info>
         <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>
         <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>
         <result_code><![CDATA[SUCCESS]]></result_code>
         <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>
         <is_subscribe><![CDATA[Y]]></is_subscribe>
         <trade_type><![CDATA[APP]]></trade_type>
         <bank_type><![CDATA[CCB_DEBIT]]></bank_type>
         <total_fee>1</total_fee>
         <fee_type><![CDATA[CNY]]></fee_type>
         <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>
         <out_trade_no><![CDATA[1415757673]]></out_trade_no>
         <attach><![CDATA[订单额外描述]]></attach>
         <time_end><![CDATA[20141111170043]]></time_end>
         <trade_state><![CDATA[SUCCESS]]></trade_state>
         </xml>
         * */


        // endregion
        return R.success(result);
    }

    @RequestMapping(value = "pay/group/create", method = RequestMethod.POST)
    @ApiOperation(value = "开团", notes = "开团")
    public R createGroupOrder(Long orderId) {
        List<OrderSale> orderSales = orderSaleService.getOrderSaleList(orderId);
        OrderSale sale = new OrderSale();
        Long activityBaseId = 0L;
        for (OrderSale orderSale : orderSales) {
            activityBaseId = orderSale.getActivityBaseId();
            sale = orderSale;
            break;
        }
        if (activityBaseId == 0L) {
            return R.error("参团失败");
        }

        OrderGoods orderGoods = orderGoodsService.getOrderGoodsInfo(orderId);
        Orders orders = orderService.getOrderInfo(orderId);

        GroupOrderCreateParam param = new GroupOrderCreateParam();
        param.setActivityBaseId(activityBaseId);

        OrderGoodsParam goodsParam = new OrderGoodsParam();
        BeanUtils.copyProperties(orderGoods, goodsParam, getNullPropertyNames(orderGoods));
        param.setOrderGoodsParam(goodsParam);

        OrderSaleParam saleParam = new OrderSaleParam();
        BeanUtils.copyProperties(sale, saleParam, getNullPropertyNames(sale));
        param.setOrderSaleParam(saleParam);

        OrdersParam ordersParam = new OrdersParam();
        BeanUtils.copyProperties(orders, ordersParam, getNullPropertyNames(orders));
        param.setOrdersParam(ordersParam);

        log.info("ordersParam:" + param);
        return groupOrderAppletsFacade.createGroupOrder(param);
    }

    @RequestMapping(value = "pay/group/join", method = RequestMethod.POST)
    @ApiOperation(value = "参团", notes = "参团")
    public R joinGroupOrder(Long orderId, Long groupOrderId) throws Exception {
        List<OrderSale> orderSales = orderSaleService.getOrderSaleList(orderId);
        Long activityBaseId = 0L;
        for (OrderSale orderSale : orderSales) {
            activityBaseId = orderSale.getActivityBaseId();
            break;
        }

        OrderGoods orderGoods = orderGoodsService.getOrderGoodsInfo(orderId);
        log.info("orderGoods:"+JSON.toJSONString(orderGoods));
        if(orderGoods != null) {
            log.info("orderGoodsSKUID:"+orderGoods.getSkuId());
            log.info("activityBaseId:"+activityBaseId);
            log.info("groupOrderId:"+groupOrderId);
            R r = groupOrderAppletsFacade.validateCreateGroupOrder(orderGoods.getSkuId(), activityBaseId, groupOrderId, 2);
            log.info("join Group R:"+JSON.toJSONString(r));
            Boolean validate = false;
            if (r.getCode() == 0) {
                validate = (Boolean) r.getData();
            }
            if (validate) {
                GroupInsertParam groupInsertParam = new GroupInsertParam();
                groupInsertParam.setGroupId(groupOrderId);
                groupInsertParam.setOrderId(orderId);
                return groupOrderAppletsFacade.insertGroupOrder(groupInsertParam);
            } else {
                R result = new R();
                result.setCode(4);
                result.setData(null);
                return result;
            }
        }
        return R.error("参团失败:获取订单商品失败");
    }
}
