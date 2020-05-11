package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.bean.param.SaveThirdOrderParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.pay.PayConstants;
import cn.enn.wise.platform.mall.config.pay.WxPayService;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.HotelOrderService;
import cn.enn.wise.platform.mall.service.ThirdOrderService;
import cn.enn.wise.platform.mall.service.WzdOrderAppletsService;
import cn.enn.wise.platform.mall.service.impl.NxjThirdOrderServiceImpl;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import cn.enn.wise.platform.mall.service.HotelOrderService;

/**
 * @author bj
 * @Description
 * @Date19-5-24 下午4:54
 * @Version V1.0
 **/
@RestController
@RequestMapping("/orders/wzd")
@Api(value = "小程序热气球订单相关接口")
public class WzdOrderAppletsController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(WzdOrderAppletsController.class);
    @Autowired
    private WzdOrderAppletsService wzdOrderAppletsService;

    @Autowired
    private HotelOrderService hotelOrderService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 获取支付信息
     *
     * @return
     */
    @PostMapping("/save")
    @OpenIdAuthRequired
    @ApiOperation(value = "保存订单", notes = "保存订单")
    public ResponseEntity saveOrder(@RequestBody @ApiParam(name = "payParam") PayParam payParam,
                                    HttpServletRequest request,
                                    @Value("#{request.getAttribute('currentUser')}") User user,
                                    @RequestHeader("openId") String openId) throws Exception {
        logger.info("===开始下单===");

        ParamValidateUtil.validatePay(payParam, user, openId);

        GoodsReqParam goodsReqParam = new GoodsReqParam();
        if(payParam.getProjectId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }

        //封装查询参数
        goodsReqParam.setGoodsId(payParam.getGoodsId());
        goodsReqParam.setPeriodId(payParam.getPeriodId());
        goodsReqParam.setProjectId(payParam.getProjectId());
        String playTime = MallUtil.getDateByType(payParam.getTimeFrame());

        goodsReqParam.setOperationDate(playTime);
        payParam.setGoodsReqParam(goodsReqParam);
        payParam.setUserId(user.getId());
        payParam.setOpenId(openId);
        payParam.setIp(IpAddressUtil.getIp(request));
        Object result;

        if(payParam.getOrderType() != null){
            //门票类型的订单
            if(payParam.getOrderType() == 7){
                SaveThirdOrderParam saveThirdOrderParam = new SaveThirdOrderParam();
                saveThirdOrderParam.setOrderType(payParam.getOrderType());
                saveThirdOrderParam.setUserWechatName(payParam.getUserWechatName());
                saveThirdOrderParam.setAmount(payParam.getAmount());
                saveThirdOrderParam.setGoodsId(payParam.getGoodsId());
                saveThirdOrderParam.setPlayTime(playTime);
                saveThirdOrderParam.setDriverMobile(payParam.getDriverMobile());
                saveThirdOrderParam.setFormId(payParam.getFormId());
                saveThirdOrderParam.setPath(payParam.getPath());
                saveThirdOrderParam.setOrderTel(payParam.getPhone());
                saveThirdOrderParam.setExtInfo(payParam.getExtInfo());
                saveThirdOrderParam.setTourismName(payParam.getName());
                saveThirdOrderParam.setUserOfCouponId(payParam.getUserOfCouponId());
                return saveThirdOrder(saveThirdOrderParam,request,user,openId,payParam.getScenicId());

            }else {
                return ResponseEntity.error("错误的订单类型");
            }
        }

        if(payParam.getPromotionId() != null){
            result = wzdOrderAppletsService.savePackageOrder(payParam);

        }else {
            result = wzdOrderAppletsService.saveOrder(payParam);

        }

        return new ResponseEntity(GeneConstant.INT_1, "下单成功", result);

    }



    /**
     * 待支付订单支付
     *
     * @param orderCode
     * @param request
     * @param user
     * @param openId
     * @param scenicId
     * @return
     */
    @PostMapping(value = "/payoriginal")
    @OpenIdAuthRequired
    @ApiOperation(value = "待支付订单支付", notes = "待支付订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query"),
    })
    public ResponseEntity payOriginalOrder(
            @RequestParam("orderCode") String orderCode,
            HttpServletRequest request,
            @Value("#{request.getAttribute('currentUser')}") User user,
            @RequestHeader("openId") String openId,
            Long scenicId) throws Exception {
        logger.info("===支付原有订单==payOldOrder");
        ParamValidateUtil.validatePayOldOrder(orderCode, user, openId, scenicId);

        String ip = IpAddressUtil.getIp(request);
        PayParam payParam = new PayParam();
        payParam.setIp(ip);
        payParam.setUserId(user.getId());
        payParam.setOpenId(openId);
        payParam.setScenicId(scenicId);
        payParam.setOrderCode(orderCode);
        payParam.setPayType("weixin");

        Object result = wzdOrderAppletsService.payOriginalOrder(payParam);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "获取待支付订单支付信息成功", result);

    }

    /**
     * 根据用户Id和订单号查询订单详情
     */
    @GetMapping("/detail")
    @OpenIdAuthRequired
    @ApiOperation(value = "根据订单号查询用户详情", notes = "根据订单号查询用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query")
    })
    public ResponseEntity<Orders> getOrderByIdAndUserId(@Value("#{request.getAttribute('currentUser')}") User user,
                                                        String orderCode) {
       ParamValidateUtil.validateGetOrderByIdAndUserId(user, orderCode);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setOrderCode(orderCode);
        Orders orderByIdAndUserId = wzdOrderAppletsService.getOrderByIdAndUserId(orders);

        return new ResponseEntity<>(GeneConstant.INT_1, "获取订单详情成功", orderByIdAndUserId);


    }

    /**
     * 用户查询所有订单
     *
     * @param user
     */
    @PostMapping("/userorder")
    @OpenIdAuthRequired
    @ApiOperation(value = "查询用户所有订单", notes = "查询用户所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "订单状态,该参数不传输表示查询所有订单,值为2表示查询未使用订单列表", paramType = "query")
    })
    public ResponseEntity<List<Orders>> listOrderByUserId(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody PayParam payParam) {

        Long scenicId = payParam.getScenicId();
        Integer state = payParam.getState();

        ParamValidateUtil.validateListOrderByUserId(user, scenicId, state);
        Long projectId = payParam.getProjectId();

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setState(state);
        orders.setScenicId(scenicId);
        orders.setProjectId(projectId);

        List<Orders> userOrder = wzdOrderAppletsService.getUserOrder(orders);

        return new ResponseEntity<>(GeneConstant.INT_1, "获取订单列表成功", userOrder);

    }

    /**
     * 支付成功,修改订单状态
     */
    @PostMapping("/complate")
    public String complateOrder(HttpServletRequest request) throws Exception {


        String stringXml = getParam(request);
        Map<String,String> map = XmlUtil.doXMLParse(stringXml, 0);


//        Set<Map.Entry<String, String>> entries = map.entrySet();
//        entries.forEach(x -> {logger.info("key:"+x.getKey()+",value:"+x.getValue());});

        wxPayService.checkNotifyParam(map);

        Map<String,String> xmlMap = new HashMap<>(4);
        xmlMap.put(PayConstants.RETURN_CODE,GeneConstant.SUCCESS_UPPERCASE);
        xmlMap.put(PayConstants.RETURN_MSG,GeneConstant.OK);
        String stringresult = WxPayService.map2Xml(xmlMap);

        String wxOrderCode =map.get("out_trade_no");
        //微信支付订单号
        String wxPayCode =map.get("transaction_id");
        logger.info("微信支付订单号:"+wxPayCode);
        if(wxOrderCode.contains("jd")){
            hotelOrderService.updateOrderStatusToSuccess(wxOrderCode);
            return stringresult;
        }

        int i = wzdOrderAppletsService.complateOrder(wxOrderCode,wxPayCode);


        if(i > 0){

            return stringresult;

        }else {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单状态未修改");
        }
    }

    /**
     * 支付成功,修改订单状态
     */
    @PostMapping("/complate/ticket")
    public String complatTicketeOrder(HttpServletRequest request) throws Exception {


        String stringXml = getParam(request);
        Map<String,String> map = XmlUtil.doXMLParse(stringXml, 0);

        wxPayService.checkNotifyParam(map,101L);

        Map<String,String> xmlMap = new HashMap<>(4);
        xmlMap.put(PayConstants.RETURN_CODE,GeneConstant.SUCCESS_UPPERCASE);
        xmlMap.put(PayConstants.RETURN_MSG,GeneConstant.OK);
        String stringresult = WxPayService.map2Xml(xmlMap);

        String wxOrderCode =map.get("out_trade_no");
        //微信支付订单号
        String wxPayCode =map.get("transaction_id");
        logger.info("微信支付订单号:"+wxPayCode);
        if(wxOrderCode.contains("jd")){
            hotelOrderService.updateOrderStatusToSuccess(wxOrderCode);
            return stringresult;
        }

        int i = wzdOrderAppletsService.complateOrder(wxOrderCode,wxPayCode);


        if(i > 0){

            return stringresult;

        }else {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单状态未修改");
        }
    }


    /**
     * 修改订单的拼团id
     */
    @PostMapping("/updategrouporderidbyid")
    @OpenIdAuthRequired
    @ApiOperation(value = "拼团自动转为开团，清空拼团订单中的groupOrderId", notes = "拼团自动转为开团，清空拼团订单中的groupOrderId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query"),
    })
    public ResponseEntity updateOrder(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) throws Exception {
        ParamValidateUtil.validateRefundOrder(user, orderCode);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setOrderCode(orderCode);

        int i = wzdOrderAppletsService.updateOrder(orders);
        if (i > 0) {
            return new ResponseEntity(GeneConstant.INT_1, "取消订单成功", null);
        } else {
            return new ResponseEntity(GeneConstant.INT_0, "取消订单失败", null);
        }
    }

    /**
     * 取消订单
     */
    @PostMapping("/refundorder")
    @OpenIdAuthRequired
    @ApiOperation(value = "用户取消订单", notes = "用户取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query"),
    })
    public ResponseEntity refundOrder(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode,String reason) throws Exception {
        ParamValidateUtil.validateRefundOrder(user, orderCode);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setOrderCode(orderCode);
        orders.setReason(reason);
        int i = wzdOrderAppletsService.refundOrder(orders);
        if (i > 0) {
            return new ResponseEntity(GeneConstant.INT_1, "取消订单成功", null);
        } else {
            return new ResponseEntity(GeneConstant.INT_0, "取消订单失败", null);
        }
    }

    /**
     * 取消未支付的过期的订单
     * @return
     */
    @PostMapping("/expire")
    public ResponseEntity cancelExpireOrder(){
        logger.info("===cancelExpireOrder===start");
        wzdOrderAppletsService.cancelExpireOrder();
        hotelOrderService.cancelExpireOrder();
        logger.info("===cancelExpireOrder===end");
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"执行成功");
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
            logger.error("getParam error", e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("getParam close stream error", e);
                }
        }
        return sb.toString();
    }


    @GetMapping("/reason")
    @ApiOperation("获取用户取消原因集合")
    public ResponseEntity getRefundReasons(){



        return new ResponseEntity(initReason());
    }

    private static List<RefundReason> initReason(){
        List<RefundReason> list = new ArrayList<>();
        list.add(new RefundReason(1L,"排队时间长"));
        list.add(new RefundReason(2L,"身体不舒服"));
        list.add(new RefundReason(3L,"儿童身高不够"));
        list.add(new RefundReason(4L,"同行老人超龄"));
        list.add(new RefundReason(5L,"改玩其他项目"));
        list.add(new RefundReason(6L,"突发天气原因"));
        list.add(new RefundReason(7L,"其他"));
        return list;
    }

    public static String getReason(Long id){

        List<RefundReason> refundReasons = initReason();
        for (RefundReason refundReason : refundReasons) {


            if(refundReason.getId().equals(id)){
                return refundReason.getReason();
            }
        }
        return "";
    }


    @GetMapping("/query")
    @ApiOperation("客户端主动查询微信订单支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode",value = "微信商户订单号",required = true,paramType = "query"),
            }
    )
    @OpenIdAuthRequired
    public ResponseEntity queryOrder(String orderCode,
                                     @RequestHeader("companyId") Long companyId,
                                     @Value("#{request.getAttribute('currentUser')}") User user) throws Exception {
        if(StringUtils.isEmpty(orderCode)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单号不能为空");
        }
        if(user== null || user.getId() == null){
             throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"身份失效");
        }

        OrderQueryVo orderQueryVo  = wzdOrderAppletsService.orderQuery(companyId, orderCode,user);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"订单状态查询成功",orderQueryVo);
    }

    @OpenIdAuthRequired
    @GetMapping("/price")
    @ApiOperation("计算优惠订单价格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userOfCouponId",value = "优惠券记录Id",required = true),
            @ApiImplicitParam(name = "goodsPrice",value = "商品单价",required = true),
            @ApiImplicitParam(name = "amount",value = "商品购买数量",required = true)
    })
    public ResponseEntity getOrderPrice(@Value("#{request.getAttribute('currentUser')}") User user,
                                        Long userOfCouponId,
                                        String goodsPrice,
                                        Integer amount){
        if(userOfCouponId == null || amount == null || StringUtils.isEmpty(goodsPrice)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数错误!");
        }

        OrderPriceVo orderPrice = wzdOrderAppletsService.getOrderPrice(user.getId(), userOfCouponId, new BigDecimal(goodsPrice), amount);
        return ResponseEntity.ok(orderPrice);
    }

    @OpenIdAuthRequired
    @PostMapping("/ship/ticket")
    @ApiOperation("保存船票订单")
    public ResponseEntity saveTicketOrder(@RequestBody PayParam payParam,
                                          HttpServletRequest request,
                                          @Value("#{request.getAttribute('currentUser')}") User user,
                                          @RequestHeader("openId") String openId,
                                          @RequestHeader("companyId")Long companyId) throws Exception {



        ParamValidateUtil.validateSaveTicketParam(payParam,user,openId);
        String redisKey = String.format(AppConstants.TICKET_MESSAGE_CODE, payParam.getPhone());

        String messageCode = redisTemplate.opsForValue().get(redisKey);

        if(!payParam.getMessageCode().equals(messageCode)){
            throw new BusinessException(7,"短信验证码错误，请重新输入");
        }

        redisTemplate.delete(redisKey);

        payParam.setScenicId(companyId);
        payParam.setUserId(user.getId());
        payParam.setOpenId(openId);
        payParam.setIp(IpAddressUtil.getIp(request));
        Object object = wzdOrderAppletsService.saveShipOrder(payParam);

        return ResponseEntity.ok(object);
    }

    @GetMapping("/auth/code")
    @ApiOperation(value = "船票下单发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true,paramType = "query")
    })
    public ResponseEntity sendCode(String phone){
        if(StringUtils.isEmpty(phone)){
            throw  new BeanCreationException("手机号不能为空");
        }

        wzdOrderAppletsService.sendVerificationCode(phone);

        return ResponseEntity.ok();
    }

    @GetMapping("/makeup")
    @ApiModelProperty(value = "船票订单座位信息补录")
    public ResponseEntity markUpOrder(String orderCode) throws Exception {

        Object o = wzdOrderAppletsService.makeUpOrder(orderCode);
        return ResponseEntity.ok(o);
    }


    @PostMapping("/thirdorder/save")
    @OpenIdAuthRequired
    public ResponseEntity saveThirdOrder(@RequestBody SaveThirdOrderParam saveThirdOrderParam,
                                         HttpServletRequest request,
                                         @Value("#{request.getAttribute('currentUser')}") User user,
                                         @RequestHeader("openId") String openId,
                                         @RequestHeader("companyId")Long companyId) throws Exception {
        ThirdOrderService thirdOrderServiceImpl = getThirdOrderServiceImpl(saveThirdOrderParam.getOrderType());

        Object prePayInfo = thirdOrderServiceImpl.saveOrder(buildOrderContext(saveThirdOrderParam,request,user,openId,companyId));

        return ResponseEntity.ok(prePayInfo);
    }

    /**
     * 根据订单类型构建对应的orderContext对象
     * @param saveThirdOrderParam
     * @param request
     * @param user
     * @param openId
     * @param companyId
     * @return
     */
    private ThirdOrderContext buildOrderContext(SaveThirdOrderParam saveThirdOrderParam, HttpServletRequest request, User user, String openId, Long companyId) {
        Integer orderType = saveThirdOrderParam.getOrderType();
        saveThirdOrderParam.setUserWechatName(GeneUtil.filterEmoji(saveThirdOrderParam.getUserWechatName()));
        if(GeneConstant.INTEGER_7.equals(orderType)){
            //楠溪江门票订单
            NxjOrderContext nxjOrderContext = new NxjOrderContext();
            BeanUtils.copyProperties(saveThirdOrderParam,nxjOrderContext);
            nxjOrderContext.setOpenId(openId);
            nxjOrderContext.setIp(IpAddressUtil.getIp(request));
            nxjOrderContext.setOrderType(orderType);
            nxjOrderContext.setScenicId(companyId);
            nxjOrderContext.setUser(user);

            return nxjOrderContext;
        }else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未知订单类型");
        }

    }

    /**
     * 获取第三方订单处理对象
     * @param orderType
     */
    public ThirdOrderService getThirdOrderServiceImpl(Integer orderType){

        if(GeneConstant.INTEGER_7.equals(orderType)){
            //楠溪江门票订单

            return applicationContext.getBean(NxjThirdOrderServiceImpl.class);
        }else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未知订单类型");
        }

    }
}

