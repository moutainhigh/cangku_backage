package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.bo.autotable.Contacts;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.pay.WxPayService;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.controller.ProdCommController;
import cn.enn.wise.platform.mall.controller.applets.WzdOrderAppletsController;
import cn.enn.wise.platform.mall.job.RefundJob;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.redlock.AquiredLockWorker;
import cn.enn.wise.platform.mall.util.redlock.RedisLocker;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author bj
 * @Description 小程序订单相关逻辑
 * @Date19-5-24 下午7:14
 * @Version V1.0
 **/

@Service
public class WzdOrderAppletsServiceImpl implements WzdOrderAppletsService {

    private static final Logger logger = LoggerFactory.getLogger(WzdOrderAppletsServiceImpl.class);

    @Value("${spring.profiles.active}")
    private String profiles;

    @Resource
    private UserOfCouponMapper userOfCouponMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private OrderAppletsMapper orderAppletsMapper;

    volatile  boolean flag = true;

    @Resource
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Resource
    private GoodsProjectMapper goodsProjectMapper;

    @Resource
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;


    @Autowired
    private MessageSender messageSender;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;


    @Resource
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private RedisLocker redisLocker;

    @Autowired
    private GroupOrderService groupOrderService;

    @Resource
    private GroupOrderMapper groupOrderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    private ProdCommController prodCommController;

    @Autowired
    private GroupPromotionMapper groupPromotionMapper;

    @Resource
    private ContactsMapper contactsMapper;

    @Autowired
    private GoodsRelatedBBDService goodsRelatedBBDService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsPackageItemMapper goodsPackageItemMapper;

    @Autowired
    private SynchrOrderToBbdFaildService synchrOrderToBbdFaildService;

    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private RefundJob refundJob;

    @Resource
    private OrdersTicketMapper ordersTicketMapper;


    @Autowired
    private ApplicationContext applicationContext;

    // 随意定义的一个值，用于防止指令重排序
    private volatile int a;

    /**
     * 北海——涠洲岛
     * 北海——海口
     * 参数列表
     * #票务订单号#
     * #航向#
     * #航班名称#
     * #仓位#
     * #航班时间#
     *#票张数#
     * #订单总额#
     * #订单登船单URL#
     * #出发点码头#
     */
    String tickerOrderMessage = "订单号:%s,您已购%s %s%s%s船票%s张,订单总金额：%s元,您的座位详情（%s）.请您务必提前1小时抵达%s在自助取票机凭身份证打印纸质登船单，凭登船单及身份证检票乘船。如有疑问请拨打0779-3071866/3069988,祝您旅途愉快！航班结束后须在三十天内提起电子发票开具申请，逾期不受理。";

    /**
     * 支付下单
     *
     * @return
     */
    @Override
    public Object saveOrder(PayParam payParam) throws Exception {
//        if(payParam.getPromotionId() != null&&payParam.getGroupOrderId()==null){
//
//            Map<String, String> memberIfInTheGroup = groupOrderService.getMemberIfInTheGroup(payParam.getUserId(),payParam.getGoodsId(),payParam.getPromotionId());
//
//            if(memberIfInTheGroup != null  && GeneConstant.FALSE.equals(memberIfInTheGroup.get(GeneConstant.IS_CREATE_GROUP_ORDER))){
//            throw new BusinessException(GeneConstant.CREATE_GROUP_ORDER_ERROR,"该用户不可以创建拼团!");
//
//            }
//        }

            logger.info("===开始下单===");
            Orders orders = completionOrderInfo(payParam);
            orders.setUserWechatName(GeneUtil.filterEmoji(orders.getUserWechatName()));
            logger.info("纪念一下===过滤后的微信名字===" + orders.getUserWechatName());

             wzdOrderAppletsMapper.insertOrder(orders);
            Orders orderById = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());

            logger.info("===插入订单成功!===");
            String s = JSONObject.toJSONString(orders);
            logger.info("sendmessage : " + s);
            logger.info("===将订单放入消息队列处理===start");
            String  totalFee = "";
            if(orders.getUserOfCouponId() != null){
                totalFee = orders.getShouldPay().multiply(new BigDecimal("100")).setScale(0).toString();
            }else {

               totalFee =  orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();
            }

            messageSender.sendProcessOrder(s);
            logger.info("===将订单放入消息队列处理===end");

            Object pay = wxPayService.pay(payParam.getScenicId(), orders.getGoodsName(), payParam.getOpenId(),orders.getBatCode(), totalFee, payParam.getIp(),orders.getOrderCode());
            return pay;

    }


    @Override
    public Object savePackageOrder(PayParam payParam) throws Exception {


        Orders orders = buildPackageOrderInfo(payParam);

        int i = 0;

        try {
            orders.setUserWechatName(GeneUtil.filterEmoji(orders.getUserWechatName()));
            i = wzdOrderAppletsMapper.insertOrder(orders);
            Orders orderById = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());
        } catch (Exception e) {
            logger.info("===微信昵称中可能含有非法字符==="+e);
            orders.setUserWechatName("Unknown");
            i = wzdOrderAppletsMapper.insertOrder(orders);
            Orders orderById = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());
        }
        if (i < 1) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===插入订单失败===");
        } else {
            String s = JSONObject.toJSONString(orders);
            logger.info("sendPackageOrdermessage : " + s);
            messageSender.sendProcessOrder(s);

            Object pay = wxPayService.pay(payParam.getScenicId(),
                    orders.getGoodsName(),
                    payParam.getOpenId(),
                    orders.getBatCode(),
                    orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString(),
                    payParam.getIp(),
                    orders.getOrderCode());
            return pay;
        }
    }

    /**
     * 构建拼团订单信息
     * @param payParam
     * @return
     */
    private Orders buildPackageOrderInfo(PayParam payParam) {

        //参加拼团规则
        if(payParam.getPromotionId() != null&&payParam.getGroupOrderId()==null){

            Map<String, String> memberIfInTheGroup = groupOrderService.getMemberIfInTheGroup(payParam.getUserId(),payParam.getGoodsId(),payParam.getPromotionId());

            if(memberIfInTheGroup != null  && GeneConstant.FALSE.equals(memberIfInTheGroup.get(GeneConstant.IS_CREATE_GROUP_ORDER))){
                throw new BusinessException(GeneConstant.CREATE_GROUP_ORDER_ERROR,"该用户不可以创建拼团!");

            }
        }
        //订单构建对象
        Orders.OrdersBuilder builder = Orders.builder();
        //商品购买数量
        Integer amount = payParam.getAmount();

        //商品id
        Long goodsId = payParam.getGoodsId();
        //查询拼团信息
        GroupGoodsInfoVo groupInfoVoByGoodId = goodsMapper.getGroupInfoVoByGoodId(goodsId);
        Assert.notNull(groupInfoVoByGoodId,"查询商品为空");
        if(groupInfoVoByGoodId.getIsPackage() == 1){
            builder.amount(1L);
        }else{
            builder.amount(Long.valueOf(amount));

        }

        BigDecimal salePrice = new BigDecimal("0");
        //判断否是拼团商品,拼团商品按照拼团价来
        Assert.notNull(payParam.getPromotionId(),"拼团活动Id不能为空");
        GoodsAndGroupPromotionInfoVo infoByExtendId = groupOrderMapper.getGoodsAndGroupPromotionInfoByExtendId(groupInfoVoByGoodId.getGoodExtendId(), payParam.getPromotionId(), 1);
        if (infoByExtendId != null) {

            //校验商品数量是否超过限购数量
            if (amount > infoByExtendId.getGroupLimit()) {

                throw new BusinessException(GeneConstant.BUSINESS_ERROR, "商品超过最大限购数量");
            }

            //按照拼团价下单
            salePrice = infoByExtendId.getGroupPrice();

        }


        //计算总价
        BigDecimal  totalPrice = salePrice.multiply(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_DOWN));




        //订单快照为空
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("path",payParam.getPath());
            jsonObject.put("formId",payParam.getFormId());
            jsonObject.put("openId",payParam.getOpenId());


        //微信支付单号
        String wxOrderCode = GeneUtil.getCode();
        builder
                .maxNumberOfUsers(groupInfoVoByGoodId.getMaxNum())
                .userId(payParam.getUserId())
                .siglePrice(salePrice)
                //订单价格
                .goodsPrice(totalPrice)
                .scenicId(payParam.getScenicId())
                .goodsName(groupInfoVoByGoodId.getGoodName())
                .type(GeneConstant.LONG_6)
                .goodsId(groupInfoVoByGoodId.getGoodExtendId())
                .state(GeneConstant.INT_1)
                .payType(payParam.getPayType())
                .enterTime(MallUtil.getDateByTimeFrame(payParam.getTimeFrame()))
                .idNumber(payParam.getIdNumber())
                .name(payParam.getName())
                .unchekedNum(builder.build().getAmount().intValue())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .profitStatus(2)
                .isDistributeOrder(0)
                .orderSource(1)
                .shouldPay(totalPrice)
                .actualPay(new BigDecimal("0.00"))
                .userId(payParam.getUserId())
                .phone(payParam.getPhone())
                .batCode(wxOrderCode)
                .orderType(3)
                .projectId(groupInfoVoByGoodId.getProjectId())
                .groupOrderId(payParam.getGroupOrderId())
                .expiredTime(new Timestamp(DateUtil.getNextTime(2).getTime()))
                .projectCode(groupInfoVoByGoodId.getProjectCode())
                .profiles(profiles)
                .offlineStatus(2)
                .payStatus(1)
                .snapshot(jsonObject.toJSONString())
                .promotionId(payParam.getPromotionId());

        Orders  orders = builder.build();
        if ("prod".equals(profiles)) {
            orders.setProfiles("");
        }else {
            orders.setProfiles(profiles);

        }
        return  orders;
    }

    /**
     * 补全订单信息
     *
     * @param payParam
     * @return
     */
    private Orders completionOrderInfo (PayParam payParam){

        logger.info("===补全订单信息===");
        Orders orders = new Orders();

        Integer isDistributeOrder = 0;

        Integer profitStatus = 2;
        GoodsReqParam goodsReqParam = payParam.getGoodsReqParam();
        //查询票信息
        GoodsApiResVO goodsById;
        if (payParam.getIsByPeriodOperation() == 1) {
            goodsById = wzdGoodsAppletsMapper.getGoodsById(goodsReqParam);
        } else {

            goodsById = wzdGoodsAppletsMapper.getGoodsInfoById(payParam.getGoodsId());
        }

        if (goodsById == null) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===未查询到票信息===");

        }

        goodsById.setIsDistributeGoods(2);
        GoodsApiExtendResVo goodsApiExtendResVo = goodsById.getGoodsApiExtendResVoList().get(0);

        if (goodsApiExtendResVo == null) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===未查询到商品的SKU信息===");

        }

        Long scenicId = payParam.getScenicId();

        List<String> goodsIdByPhone = remoteServiceUtil.getGoodsIdByPhone(payParam.getDriverMobile(), scenicId);

        if (goodsIdByPhone != null) {
            for (String s : goodsIdByPhone) {
                if (String.valueOf(goodsById.getId()).equals(s)) {
                    goodsById.setIsDistributeGoods(1);
                }
            }
        }

        Boolean flag = false;
        //单价
        BigDecimal salePrice = goodsApiExtendResVo.getSalePrice();

        if (StringUtils.isNotEmpty(payParam.getDriverMobile())) {

            logger.info("===当前分销商手机号为===" + payParam.getDriverMobile());
            ResponseEntity checkUserResult = remoteServiceUtil.getCheckUserResult(payParam.getDriverMobile(), scenicId);

            if (checkUserResult != null && checkUserResult.getResult() == 1) {

                String id = ((JSONObject) (checkUserResult.getValue())).get("id").toString();
                //获取用户角色信息
                String userRole = ((JSONObject) (checkUserResult.getValue())).getString("userRole");
                orders.setRoleId(userRole);
                JSONObject snapshotObject = ((JSONObject) (checkUserResult.getValue()));
                snapshotObject.put("attachmentUrl", "");
                orders.setSnapshot(snapshotObject.toJSONString());
                logger.info("===分销商当前有效id===" + id + ",角色信息userRole=" + userRole);
                orders.setDistributorId(Long.valueOf(id));
                //用户具有分销身份
                isDistributeOrder = 1;
                profitStatus = 1;
                //分销商可以分销商品才可以按照分销价格售卖
                if (goodsById.getIsDistributeGoods() == 1) {
                    flag = true;
                }

            }
        }
        Integer amount = payParam.getAmount();
        //是分销商的价格按照分销价格来
        if (flag) {
            salePrice = goodsById.getRetailPrice();
        }
        Long goodsExtendId = goodsApiExtendResVo.getId();

        BigDecimal totalPrice = new BigDecimal(0);
        //计算总价格
        if (goodsById.getIsPackage() == 1) {
            orders.setAmount(1L);
            //多人票
            totalPrice = salePrice;

        } else {
            //单人票
            totalPrice = salePrice.multiply(new BigDecimal(amount));
            orders.setAmount(Long.valueOf(amount));
            if ("5".equals(scenicId.toString()) || "7".equals(scenicId.toString())) {

            } else {
                if ("179".equals(GeneUtil.formatBigDecimal(salePrice).toString()) && amount >= 8) {
                    //价格为179的购票数量大于等于8 按照每张票169算
                    salePrice = new BigDecimal("169");
                }
                if ("199".equals(GeneUtil.formatBigDecimal(salePrice).toString())) {
                    if (amount >= 4 && amount < 8) {
                        salePrice = new BigDecimal("179");
                    }
                    if (amount >= 8) {
                        salePrice = new BigDecimal("169");
                    }
                }

                totalPrice = salePrice.multiply(new BigDecimal(payParam.getAmount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
        }

        orders.setMaxNumberOfUsers(goodsById.getMaxNum());
        orders.setUserId(payParam.getUserId());
        orders.setSiglePrice(salePrice);
        orders.setGoodsPrice(totalPrice);
        orders.setScenicId(scenicId);
        orders.setGoodsName(goodsById.getGoodsName());
        orders.setType(Long.valueOf(GeneConstant.INT_6));
        orders.setGoodsId(goodsExtendId);
        orders.setState(GeneConstant.INT_1);

        orders.setPayType(payParam.getPayType());

        orders.setEnterTime(MallUtil.getDateByTimeFrame(payParam.getTimeFrame()));
        orders.setIdNumber(payParam.getIdNumber());
        orders.setName(payParam.getName());
        orders.setUnchekedNum(orders.getAmount().intValue());
        orders.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        orders.setProfitStatus(profitStatus);
        orders.setIsDistributeOrder(isDistributeOrder);
        orders.setOrderSource(1);
        orders.setShouldPay(totalPrice);
        orders.setPayStatus(1);
        Long projectId = payParam.getProjectId();
        orders.setProjectId(projectId);
        orders.setActualPay(new BigDecimal("0.00"));
        orders.setUserWechatName(payParam.getUserWechatName());
        orders.setPhone(payParam.getPhone());
        String wxOrderCode = GeneUtil.getCode();
        orders.setBatCode(wxOrderCode);
        //根据拼团活动Id区分 1线上订单 2离线订单 3 拼团订单 4 套餐订单
        orders.setOrderType(1);
        orders.setOfflineStatus(2);
        //1、查询商品订单前缀
        GoodsProject goodsProject = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("id", projectId));

        String projectCode = goodsProject.getProjectCode();

        if ("prod".equals(profiles)) {

            orders.setProfiles("");
        }else {
            orders.setProfiles(profiles);

        }
        orders.setProjectCode(projectCode);
        //是套餐售卖
        if(goodsById.getIsSuit() != null && goodsById.getIsSuit() == 1){
          orders.setOrderType(4);
        }
        if(payParam.getGroupOrderId()!=null){
            orders.setGroupOrderId(payParam.getGroupOrderId());
            //拼团订单
            orders.setOrderType(3);
        }
        if(orders.getOrderType() == 3){
            // 拼团订单 2分钟,未支付订单自动过期
            orders.setExpiredTime(new Timestamp(DateUtil.getNextTime(2).getTime()));
        }else {
            // 订单过期时间 20分钟,未支付订单自动过期
            orders.setExpiredTime(new Timestamp(DateUtil.getNextTime(20).getTime()));
        }

        orders.setPromotionId(payParam.getPromotionId());
        //更新订单快照
        String snapshot = orders.getSnapshot();
        //订单快照为空
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(snapshot)){
            jsonObject.put("path",payParam.getPath());
            jsonObject.put("formId",payParam.getFormId());
            jsonObject.put("openId",payParam.getOpenId());
            orders.setSnapshot(jsonObject.toString());
        }else {
            jsonObject =  JSONObject.parseObject(snapshot);
            jsonObject.put("path",payParam.getPath());
            jsonObject.put("formId",payParam.getFormId());
            jsonObject.put("openId",payParam.getOpenId());

        }
        orders.setSnapshot(jsonObject.toString());
        //优惠券下单信息
        orders = setCouponInfo(orders,payParam,goodsById.getId());

        return orders;
    }

    /**
     * 补全优惠券信息
     * @param orders 订单信息
     * @param payParam 支付参数
     */
    private Orders setCouponInfo(Orders orders,PayParam payParam,Long projectId) {
        Long userOfCouponId = payParam.getUserOfCouponId();
        //用户使用优惠券下单,而且用户必须是单品下单的，可以使用优惠券
        if(userOfCouponId != null){

            if(orders.getOrderType() == 1) {

                //查询优惠券信息
                CouponInfoVo couponInfo = userOfCouponMapper.getCouponInfo(orders.getUserId(), userOfCouponId);



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
                   //是分销单
                   if(orders.getIsDistributeOrder() == 1){
                       if(!payParam.getDriverMobile().equals(couponInfo.getPhone())){
                           throw new BusinessException(5,"该分销商优惠券不可用");
                       }
                   }

//                   if(!projectId.equals(couponInfo.getGoodsId())){
//                       throw new BusinessException(3,"该优惠券不适用于该商品");
//                   }

                    //计算优惠金额
                    try {
                        BigDecimal couponPrice =  calculatePrice(couponInfo,orders.getSiglePrice(),orders.getGoodsPrice());
                        orders.setUserOfCouponId(userOfCouponId);
                        orders.setCouponPrice(couponPrice);
                        orders.setShouldPay(orders.getGoodsPrice().subtract(couponPrice));
                        //扣除用户优惠券
                        userOfCouponMapper.updateCouponState(orders.getUserOfCouponId(),2);
                    }catch (BusinessException e){
                        logger.error("该订单不满足订单优惠条件");
                    }
                }
            }

        }

        return orders;
    }

    /**
     * 计算优惠金额
     * @param couponInfo 优惠规则
     * @param goodsPrice 商品单价
     * @param orderPrice 订单金额
     * @return
     */
    @Override
    public BigDecimal calculatePrice(CouponInfoVo couponInfo,BigDecimal goodsPrice,BigDecimal orderPrice) {
        //初始化为优惠的金额
        BigDecimal couponPrice;
        //初始化计算的金额为0
        BigDecimal calculatePrice;
        Integer useRule = couponInfo.getUseRule();
        Integer couponType = couponInfo.getCouponType();
        Integer price = couponInfo.getPrice();
        // 如果减出小数或0 默认的优惠金额就是 0
        BigDecimal priceDecimal = new BigDecimal(100-price <= 0?0:100-price);
        BigDecimal minUseDecimal = new BigDecimal(couponInfo.getMinUse());

        //按照订单总价优惠
        if(useRule == 1){
            logger.info("按照订单金额计算");
            calculatePrice = orderPrice;

            if(minUseDecimal.compareTo(orderPrice)  == 1){
                throw new BusinessException(6,"该订单不满足优惠条件");
            }

        }else {
            logger.info("按照商品金额计算");
            //按照商品总价优惠
            calculatePrice  = goodsPrice;
            if(minUseDecimal.compareTo(goodsPrice)  == 1){
                throw new BusinessException(6,"该订单不满足优惠条件");
            }
        }
        //抵用券
        if(couponType == 1){
            logger.info("抵用券:");
            couponPrice = new BigDecimal(price);
        }else {
            //折扣券的优惠金额等于
            logger.info("折扣券:");
            couponPrice = calculatePrice.multiply(priceDecimal).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN);
        }

        logger.info("优惠金额:"+couponPrice);
        return couponPrice;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object payOriginalOrder(PayParam payParam) throws Exception {

        Orders orders = new Orders();
        orders.setState(1);
        orders.setOrderCode(payParam.getOrderCode());

        orders = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orders);

        if (orders.getId() == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===该订单不存在，或者已经超过支付时间===");
        }

        if(orders.getPromotionId() != null){

            //查询拼团活动是否有效
            GroupPromotionBo groupPromotionBo = groupPromotionMapper.selectById(orders.getPromotionId());
            if(groupPromotionBo == null || groupPromotionBo.getStatus() != 2){
                throw new BusinessException(GeneConstant.INT_4,"该拼团活动已过期");
            }

        }
        String totalFee;
        if(orders.getUserOfCouponId() != null){
            //判断优惠券是否过期
            //查询优惠券信息
            CouponInfoVo couponInfo = userOfCouponMapper.getCouponInfo(orders.getUserId(), orders.getUserOfCouponId());

            if (couponInfo.getPromotionStatus() != 2 ||couponInfo.getGoodsCouponStatus() != 1) {
                //更新订单状态为已取消
                wzdOrderAppletsMapper.updateOrderStatusToCancel(5,orders.getId());
                logger.info("更改订单状态为已取消，orderId="+orders.getId());

                //构建重新生成订单的信息
               PayParam createOrderParam =  buildPayParam(orders);

                throw new BusinessException(5, "优惠活动已失效",createOrderParam);
            }
//            if (couponInfo.getStatus() != 2) {
//                //更新订单状态为已取消
//                wzdOrderAppletsMapper.updateOrderStatusToCancel(5,orders.getId());
//                logger.info("更改订单状态为已取消，orderId="+orders.getId());
//                throw new BusinessException(5, "优惠券已失效");
//            }

            totalFee = orders.getShouldPay().multiply(new BigDecimal("100")).setScale(0).toString();
        }else {

            totalFee =  orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();
        }

        if(orders.getOrderType() == 5){
            String total =  orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();
            //调用支付信息
            Object object =   wxPayService.pay(101L, orders.getGoodsName(), payParam.getOpenId(),orders.getBatCode(), total, payParam.getIp(),orders.getOrderCode());
            return object;

        }

        Object pay = wxPayService.pay(payParam.getScenicId(), orders.getGoodsName(), payParam.getOpenId(), orders.getBatCode(), totalFee, payParam.getIp(),orders.getOrderCode());


        return pay;
    }

    /**
     * 优惠券过期重新发起支付
     * @param orders
     * @return
     */
    private PayParam buildPayParam(Orders orders) {
        GoodsExtendInfoVO goodsExtendInfoById = goodsMapper.getGoodsExtendInfoById(orders.getGoodsId());
        PayParam payParam = new PayParam();
        payParam.setPayType("weixin");
        payParam.setScenicId(orders.getScenicId());
        payParam.setTimeFrame(MallUtil.getTimeFrameByDate(orders.getEnterTime()));
        payParam.setPeriodId(goodsExtendInfoById.getPeriodId().intValue());
        payParam.setAmount(orders.getAmount().intValue());
        payParam.setGoodsId(goodsExtendInfoById.getGoodsId());
        payParam.setPhone(orders.getPhone());
        payParam.setName(orders.getName());
        payParam.setUserWechatName(orders.getUserWechatName());
        payParam.setProjectId(goodsExtendInfoById.getProjectId());
        payParam.setIsByPeriodOperation(goodsExtendInfoById.getIsByPeriodOperation());

        return payParam;
    }


    /**
     * 获取用户订单
     *
     * @param orders
     * @return
     */
    @Override
    public List<Orders> getUserOrder(Orders orders) {
        return wzdOrderAppletsMapper.getUserOrder(orders);
    }

    /**
     * 获取订单详情
     *
     * @param orders
     * @return
     */
    @Override
    public Orders getOrderByIdAndUserId(Orders orders) {

        Orders orderByIdAndUserId = wzdOrderAppletsMapper.getOrderByIdAndUserId(orders);

        //判断是否有核销二维码地址
        if(orderByIdAndUserId == null){

            return null;
        }

        if(StringUtils.isEmpty(orderByIdAndUserId.getQrCode())){

            //初始化订单二维码
            OrderQrCodeVo codeVo = QrCodeUtil.drawLogoQRCode(orderByIdAndUserId.getOrderCode(), " ");
             if(codeVo != null){
                 String proof = codeVo.getNum();
                 String qrCodeUrl = codeVo.getQrCodeUrl();
                 //更新订单二维码和凭证
                wzdOrderAppletsMapper.updateQrCode(qrCodeUrl,proof,orderByIdAndUserId.getId());
                orderByIdAndUserId.setQrCode(qrCodeUrl);
                orderByIdAndUserId.setProof(proof);
            }
        }
        Integer state = orderByIdAndUserId.getState();
        if (state == 1) {
            Timestamp expiredTime = orderByIdAndUserId.getExpiredTime();
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
            long leaveTime = DateUtil.differentMunitessIsNotAbs(currentTime, expiredTime);
            if (leaveTime <= 0) {
                orderByIdAndUserId.setState(5);
                orders.setState(5);
                orders.setActualPay(new BigDecimal(0));
                orders.setId(orderByIdAndUserId.getId());
                //手动修改订单状态
                wzdOrderAppletsMapper.refundOrder(orders);
            } else {
                orderByIdAndUserId.setLeaveTime(leaveTime);
            }

        }
        return orderByIdAndUserId;
    }


    /**
     * 取消订单
     *
     * @param orders
     * @return
     */
    @Override
    public int refundOrder(Orders orders) throws Exception {
        Orders orderInfoByOrderCode = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orders);
        if (orderInfoByOrderCode == null) {
            return 0;
        }

            logger.info("userWeChatName" + orders.getUserWechatName() + "userId" + orders.getUserId() + "===取消订单===" + orders.getOrderCode());
            orders.setState(5);
            //orders.setActualPay(new BigDecimal(0));
            orders.setId(orderInfoByOrderCode.getId());
            int i = wzdOrderAppletsMapper.refundOrder(orders);
          processExpiredShipTicket(orderInfoByOrderCode);

            //如果订单使用了优惠券，并且优惠券是有效的，在用户取消了订单后(未核验状态),优惠券会返还给用户
        processRefundCoupon(orderInfoByOrderCode);


        //订单取消后同步分销订单状态为6，订单流转控制值为4
            if (orderInfoByOrderCode.getState() == 2) {
                messageSender.sendSyncDistributorOrderStatus(orderInfoByOrderCode.getId(), Byte.valueOf("4"));
                //订单取消后通知对应分销商
                String msg = "您好:客人%s于%s取消订单,订单编号%s,取消原因是%s,建议跟进服务。";

            msg = String.format(msg, orderInfoByOrderCode.getName(), DateUtil.getFormat(new Date(), AppConstants.DATE_FORMAT_CHINESE), orderInfoByOrderCode.getOrderCode(), WzdOrderAppletsController.getReason(Long.valueOf(orders.getReason())));
            Map<String, Object> projectMap = goodsProjectMapper.selectByGoodsId(orderInfoByOrderCode.getGoodsId());

            messageSender.sendOrderRefundMessage(msg, Long.valueOf(projectMap.get("projectId").toString()),
                    orderInfoByOrderCode.getIsDistributeOrder(),
                    orderInfoByOrderCode.getDistributorId(),
                    orderInfoByOrderCode.getDistributorPhone().replaceAll("\\\"", ""));
        }

        return i;


    }

    /**
     * 处理返还优惠券
     * @param orders 订单
     */
    private void processRefundCoupon(Orders orders) {
        Long userOfCouponId = orders.getUserOfCouponId();
        if(userOfCouponId != null){
            Map<String, Object> couponStatus = userOfCouponMapper.getCouponStatus(orders.getUserId(),userOfCouponId );
                //未查询到优惠券，更新为已过期状态
                if(couponStatus == null){
                    userOfCouponMapper.updateCouponState(userOfCouponId,3);
                    logger.info("用户支付完成取消订单时,使用了优惠券但优惠券已过期领取记录Id"+userOfCouponId);
                }else {
                    userOfCouponMapper.updateCouponState(userOfCouponId,1);
                    logger.info("返还用户优惠券，优惠券状态"+ JSONObject.toJSONString(couponStatus));
                }
//                //更新userofcouponId 为null
//
//            wzdOrderAppletsMapper.updateUserOfCouponId(orderInfoByOrderCode.getId(),null);
            }
    }

    /**
     * 修改订单的groupOrderId
     *
     * @param orders
     * @return 修改订单信息的条数
     */
    @Override
    public int updateOrder(Orders orders) throws Exception {
        Orders orderInfoByOrderCode = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orders);
        if (orderInfoByOrderCode == null) {
            return 0;
        }
        logger.info("userWeChatName" + orders.getUserWechatName() + "userId" + orders.getUserId() + "===修改订单===" + orders.getOrderCode());
        wzdOrderAppletsMapper.updateGroupOrderId(orderInfoByOrderCode.getId(),null);
        return 1;
    }

    /**
     * 订单完成支付后调用,更改支付状态和订单状态
     */
    @Override
    public  int  complateOrder(String batCode, String wxPayCode) throws Exception {
        logger.info("===> 支付成功执行回调");
        //并发控制两个接口同时访问complateOrder,只有一个可以成功修改数据库状态的
        int i = redisLocker.lock("complate_order", new AquiredLockWorker<Integer>() {
            @Override
            public Integer invokeAfterLockAquire() throws Exception {

                try {
                    //根据订单号查询订单信息
                    Orders orderInfoByOrderCode = wzdOrderAppletsMapper.getOrderInfoByBatCode(batCode);
                    Orders orders = new Orders();
                    orders.setOrderCode(orderInfoByOrderCode.getOrderCode());
                    //支付状态为2的订单代表已经处理过的订单
                    if (orderInfoByOrderCode.getPayStatus() != null && orderInfoByOrderCode.getPayStatus() == 2) {
                        logger.info("===该订单已经支付成功!===");
                        return 0;
                    }

                    if (orderInfoByOrderCode.getPayStatus() == 3) {
                        logger.info("===该笔订单已退款===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState() + ",payStatus=" + orderInfoByOrderCode.getPayStatus());
                        return 0;
                    }
                    //只有待支付的订单才说明,该笔订单未处理过
                    if (orderInfoByOrderCode.getState() != 1) {
                        logger.info("====该订单已经进行过支付处理===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState());
                        return 0;
                    }
                    logger.info("===该笔订单未更改回掉状态===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState());



                    orders.setPayStatus(2);
                    orders.setState(2);
                    orders.setActualPay(orderInfoByOrderCode.getShouldPay());
                    orders.setId(orderInfoByOrderCode.getId());
                    orders.setPayTime(Timestamp.valueOf(LocalDateTime.now()));
                    orders.setTransactionId(wxPayCode);
                    int i1 = wzdOrderAppletsMapper.updateOrderByOrderCode(orders);
                    orderInfoByOrderCode.setTransactionId(wxPayCode);
                    processShipTicket(orderInfoByOrderCode);
                    logger.info("该行代码已经执行了===========================================================================================");
                    if(orderInfoByOrderCode.getOrderType() == 7){
                        ThirdOrderService thirdOrderService;
                        if(orderInfoByOrderCode.getScenicId() == 13){
                            //楠溪江门票
                            thirdOrderService =  applicationContext.getBean(NxjThirdOrderServiceImpl.class);
                            thirdOrderService.processPaySuccessOrder(batCode);
                        }
                    }
                    //拼团订单
                    if(orderInfoByOrderCode.getOrderType() == 3){
                        //TODO 团单Id是 null的话 创建拼团订单
                    }

                    return i1;

                }catch (Exception e){
                    logger.info(e.getMessage());
                    return 2;

                }

            }

            @Override
            public Integer falseResult() {
                return null;
            }
        });

        if(i==2 || i == 0){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"当前未修改订单状态");
        }
        logger.info("===更新订单支付状态===");
        Orders orderInfoByOrderCode = wzdOrderAppletsMapper.getOrderInfoByBatCode(batCode);
        String projectName = "";
        //#region 下单给运营App发送消息
        GoodsExtend goodsExtend = goodsExtendMapper.selectById(orderInfoByOrderCode.getGoodsId());
        try{
            String msg="您好:客人%s于%s下单成功，订单号%s，订单包含%s位游客。";
            String orderTime = orderInfoByOrderCode.getCreateTime().toString().substring(0,16);
            String orderAmount = orderInfoByOrderCode.getAmount().toString();
            String orderCheckInTime =orderInfoByOrderCode.getEnterTime().toString();
            String period = goodsExtend.getTimespan();
//            String orderUseTime = String.format(msg,orderTime,orderAmount,orderCheckInTime+" "+period);
            String orderUseTime = String.format(msg,orderInfoByOrderCode.getName(),DateUtil.getFormat(orderInfoByOrderCode.getCreateTime(), AppConstants.DATE_FORMAT_CHINESE),orderInfoByOrderCode.getOrderCode(),orderAmount);
            Map<String,Object> projectMap = goodsProjectMapper.selectByGoodsId(orderInfoByOrderCode.getGoodsId());

            logger.info("send order message :  orderUseTime:"+orderUseTime+" projectMap:"+projectMap);
            projectName = projectMap.get("projectName").toString();
            messageSender.sendOrderMessage(orderUseTime, Long.valueOf(projectMap.get("projectId").toString()),orderInfoByOrderCode.getIsDistributeOrder(),
                    orderInfoByOrderCode.getDistributorId(),orderInfoByOrderCode.getDistributorPhone().replaceAll("\\\"",""));

        }catch(Exception ex){
            logger.info("下单给运营App发送消息:"+ex);
        }
        //#endregion
        String scenicId = String.valueOf(orderInfoByOrderCode.getScenicId());
        Map<String,String> msgMap = new HashMap<>();
        //封装短信参数
        msgMap.put("companyId",scenicId);
        msgMap.put("type",String.valueOf(5));
        msgMap.put("phone",orderInfoByOrderCode.getPhone());
        msgMap.put("name",orderInfoByOrderCode.getName());
        msgMap.put("goodName",orderInfoByOrderCode.getGoodsName());
        msgMap.put("orderCode",orderInfoByOrderCode.getOrderCode());
        msgMap.put("projectName",projectName);

        if("11".equals(scenicId)){
            if(orderInfoByOrderCode.getOrderType() != 5){
                messageSender.sendSmsV2(msgMap);
                logger.info("===发送支付成功的短信===");
            }
            if(orderInfoByOrderCode.getOrderType() == 5){
                String shipLineInfo = goodsExtend.getShipLineInfo();
                ShipLineInfo lineInfo = JSONObject.parseObject(shipLineInfo, ShipLineInfo.class);

                msgMap.put("content",GeneConstant.MESSAGE_SIGN_SHIP+String.format(tickerOrderMessage,
                        orderInfoByOrderCode.getTicketOrderCode(),
                        goodsExtend.getLineFrom()+"-"+goodsExtend.getLineTo(),
                        goodsExtend.getNickName(),
                        goodsExtend.getCabinName(),
                        DateUtil.getFormat(goodsExtend.getStartTime(),"yyyy-MM-dd HH:mm:ss"),
                        orderInfoByOrderCode.getAmount(),
                        orderInfoByOrderCode.getGoodsPrice(),
                        orderInfoByOrderCode.getBatCodeOther(),
                        goodsExtend.getLineFrom()+lineInfo.getFromInfo()));

                messageSender.sendSmsV3(msgMap);

                logger.info("===发送支付成功的短信===");
            }

            /** 将本地订单按照商品同步到百邦达 **/

            if(orderInfoByOrderCode.getOrderType().intValue()!=3) {
                // 拼团订单在拼团成功后同步
                synchronizeOrderToBBD(orderInfoByOrderCode);
            }

        }else if ("10".equals(scenicId) || "11".equals(scenicId)){
            messageSender.sendSms(orderInfoByOrderCode.getPhone(), "SMS_167973143", new HashMap<String, String>() {{
//            GeneUtil.getSendMessageContent(orderInfoByOrderCode.getName(), orderInfoByOrderCode.getGoodsName(), orderInfoByOrderCode.getOrderCode())
                put("name", orderInfoByOrderCode.getName());
                put("goodName", orderInfoByOrderCode.getGoodsName());
                put("orderCode", orderInfoByOrderCode.getOrderCode());
            }});
        }else {
            logger.info("不发送短信！");
        }

        String snapshot = orderInfoByOrderCode.getSnapshot();
        if(StringUtils.isNotEmpty(snapshot)){
            try {
                JSONObject jsonObject = JSONObject.parseObject(snapshot);
                //发送微信服务通知
                String openId = jsonObject.getString("openId");
                prodCommController.sendTemplateMsg(openId,
                        jsonObject.getString("formId"),
                        orderInfoByOrderCode.getAmount().toString(),
                        projectName,
                        jsonObject.getString("path") +"?orderCode="+orderInfoByOrderCode.getOrderCode()+"&openId="+openId);
            }catch (Exception e){
                logger.error("json 解析错误：",e);
            }
        }

        if (orderInfoByOrderCode.getIsDistributeOrder() == 1) {
            Orders paramOrders = new Orders();
            paramOrders.setId(orderInfoByOrderCode.getId());
            //此状态与分销订单要改变的状态一致
            paramOrders.setState(1);
            logger.info("send message " + JSONObject.toJSONString(paramOrders) +" "+DateUtil.getFormatDateForNow());
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    try {
                        messageSender.sendSyncDistributorOrderStatus(paramOrders.getId(), paramOrders.getState().byteValue());
                        logger.info("延迟执行:当前时间="+DateUtil.getFormatDateForNow());
                        this.cancel();
                    }catch (Exception e){
                        logger.error("sendSyncDistributorOrderStatus:发送同步分销订单的状态异常"+e);

                    }

                }
            },5000);


        }
        return i;
    }

    /**
     * 每十分钟检测本地是否有失败订单，尝试重新同步
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void cronSynchronizeFaildOrderToBBD(){
        List<SynchrOrderToBbdFaild> failds = synchrOrderToBbdFaildService.list(new QueryWrapper<SynchrOrderToBbdFaild>().eq("fixed",0));
        if(failds==null ||failds.size()==0){
            return;
        }
        for(SynchrOrderToBbdFaild f : failds){
            String orderCode = f.getOrderCode();
            if(org.springframework.util.StringUtils.isEmpty(orderCode)){
                continue;
            }
            Orders orderInfoByOrderCode = wzdOrderAppletsMapper.getOrderInfoByBatCode(orderCode);
            if(orderInfoByOrderCode != null){
                synchronizeOrderToBBD(orderInfoByOrderCode);
                SynchrOrderToBbdFaild f2 = new SynchrOrderToBbdFaild();
                f2.setFixed(1);
                synchrOrderToBbdFaildService.update(f2,new UpdateWrapper<SynchrOrderToBbdFaild>().eq("id",f.getId()));
            }

        }

    }


    /**
     * 将本地订单同步到百邦达
     * @param orderInfoByOrderCode
     */
    @Override
    public void synchronizeOrderToBBD(Orders orderInfoByOrderCode){
        try{
            logger.info("===> 1、将本地订单同步至百邦达");
            Long goodsExtendId = orderInfoByOrderCode.getGoodsId();
            logger.info("===> 2、GoodsExtendId:{}",goodsExtendId);
            if(orderInfoByOrderCode.getOrderType()==null){
                logger.error("===> 3、订单类型为空，请输入正确的订单类型： 1 线上订单 2 离线订单 3拼团订单 4 组合套餐订单 5 船票订单 6 百邦达订单");
                return;
            }
            logger.info("====> 4、当前订单类型：{}",orderInfoByOrderCode.getOrderType().intValue());
            if(orderInfoByOrderCode.getOrderType().intValue()==4){
                // 组合商品
                List<GoodsPackageItemsParams> packages = goodsPackageItemMapper.selectGoodsPackageItemsByGoodsExtendId(goodsExtendId);
                logger.info("====> 5、组合单内含商品数：{}",packages.size());
                if(packages==null){
                    logger.error("====> 6、（goodsPackage）未找到指定的组合商品。");
                }
                // 对于每一种商品进行下单
                for(GoodsPackageItemsParams itemsParams : packages){
                    if(itemsParams==null){
                        logger.error("===> 7、PackageItem是空的，跳过");
                        continue;
                    }
                    Long goodsExtendId_ = itemsParams.getGoodsExtendId();
                    if(goodsExtendId_==null || goodsExtendId_.longValue()<=0){
                        logger.error("====> 8、查询组合包数据，数据存储为空！");
                    }
                    logger.info("===> 9、组合商品单，组合商品ID(goodsExtendId)：{},当前商品ID(goodsExtendId_)：{}",goodsExtendId,goodsExtendId_);
                    doSynchronize(orderInfoByOrderCode,goodsExtendId_);
                }
            }else{
                // 非组合商品下单
                logger.info("===> 10、正在对非组合商品下单至百邦达");
                doSynchronize(orderInfoByOrderCode,goodsExtendId);
            }
        }catch (Exception e){
            logger.error("====> 11、将数据同步给百邦达发生错误：{}",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 将本地订单同步至百邦达
     *
     * @param orderInfoByOrderCode
     * @param goodsExtendId_
     */
    private void doSynchronize(Orders orderInfoByOrderCode,Long goodsExtendId_)throws Exception{
        NumberFormat moneyFormat = NumberFormat.getNumberInstance();
        moneyFormat.setMaximumFractionDigits(2);
        Date enterDate = orderInfoByOrderCode.getEnterTime();
        List<GoodsRelatedBBDBo> list = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        list = goodsRelatedBBDService.getGoodsInfo(goodsExtendId_,format.format(enterDate));

        if(list==null || list.size()==0){
            logger.error("===!! 【1】未找到对应商品，商品ID：{}",goodsExtendId_);
            throw new Exception("未找到对应商品，商品ID:"+goodsExtendId_);
        }
        String productId = list.get(0).getBbdGoodsId();
        logger.info("====> 【2】对百邦达商品下单，商品ID："+productId+"，本地商品ID："+goodsExtendId_);
        //
        BBDAddOrderDTO addOrderDTO = new BBDAddOrderDTO();
        logger.info("====> 【3】 <====");
        addOrderDTO.setLocalOrderCode(orderInfoByOrderCode.getOrderCode());
        logger.info("====> 【4】 <====");
        addOrderDTO.setContact(orderInfoByOrderCode.getName());
        logger.info("====> 【5】 <====");
        addOrderDTO.setMobile(orderInfoByOrderCode.getPhone());
        logger.info("====> 【6】 <====");
        // -- 以下3项必填
        addOrderDTO.setPayment(moneyFormat.format(orderInfoByOrderCode.getActualPay().doubleValue()));
        logger.info("====> 【7】 <====");
        addOrderDTO.setProductId(productId);
        logger.info("====> 【8】 <====");
        addOrderDTO.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").format(orderInfoByOrderCode.getEnterTime()));
        logger.info("====> 【9】 <====");
        // 根据订单的OrderCode获取所有的船票
        String orderCode = orderInfoByOrderCode.getOrderCode();
        logger.info("====> 【10】 <====");
        List<OrderTickets> tickets = orderDao.findOrderTicket(new String[]{orderCode});
        logger.info("====> 【11】 <====");
        List<BBDAddOrderTicketDTO> ticketTypeInfoList = new ArrayList();
        logger.info("====> 当前订单下共有{}张船票。",tickets.size());
        if(tickets==null||tickets.size()==0){
            throw new Exception("当前订单号不包含任何船票，订单号："+orderCode);
        }
        for(OrderTickets ticket : tickets){
            logger.info("====> 船票：{},GoodsID：{}",ticket.getGoodsName(),ticket.getGoodsId());
            // 根据GoodsID查询ExtendsID
            List<Long> extendsIn = goodsExtendMapper.selectGoodsExtendIdUsable(ticket.getGoodsId());
            if(!extendsIn.contains(goodsExtendId_)){
                logger.debug("==========>  跳过，非当前商品类型，查询下一条，此用于组合商品情况。");
                continue;
            }
            logger.info("====> 【12】 <====");
            BBDAddOrderTicketDTO dto = new BBDAddOrderTicketDTO();
            logger.info("====> 【13】 <====");
            dto.setAppTicketId(ticket.getId().toString());
            logger.info("====> 【14】 <====");
            if(ticket.getCouponPrice()!=null && ticket.getCouponPrice().doubleValue()>0.0){
                dto.setPrice(moneyFormat.format(ticket.getCouponPrice().doubleValue()));
                logger.info("====> 【15】 <====");
            }else{
                dto.setPrice(moneyFormat.format(ticket.getSinglePrice().doubleValue()));
                logger.info("====> 【16】 <====");
            }
            String ticketType = ticket.getTicketType();
            logger.info("====> 【17】 <====");
            // 成人票、儿童票
            int type = 1;
            if(!org.springframework.util.StringUtils.isEmpty(ticketType)){
                if(ticketType.startsWith("1")){
                    type = 1;
                }
                if(ticketType.startsWith("2") || ticketType.startsWith("3")){
                    type = 2;
                }
            }
            logger.info("====> 【18】 <====");
            dto.setTicketType(type);
            ticketTypeInfoList.add(dto);
        }
        addOrderDTO.setTicketTypeInfoList(ticketTypeInfoList);
        JSONObject jsonObject = BaiBangDaHttpApiUtil.synchronizeAddOrder(addOrderDTO);
        int code = jsonObject.getInteger("code");
        logger.info("===> 百邦达数据返回：\n{}\n\n",jsonObject.toJSONString());
        if(code==1){
            // 完善本地数据, 设置百邦达票号和订单号
            String orderSerial = jsonObject.getJSONObject("data").getString("id");
            JSONArray ticketArr = jsonObject.getJSONObject("data").getJSONArray("seaTicketDetailInfoDTOList");
            logger.info("====> 【19】 <====");
            for(int i=0;i<ticketArr.size();i++){
                JSONObject obj = ticketArr.getJSONObject(i);
                String ticketSerial = obj.getString("ticketNo");
                Long appTicketId = obj.getLong("appTicketId");
                String ticketName = obj.getString("productName");
                logger.info("===> 百邦达票名称：{}",ticketName);
                int ticketStatus = obj.getInteger("status");
                String ticketId = obj.getString("id");
                String qrCode = obj.getString("qrCodeContent");
                a = 1;
                logger.info("====> 【20】 <====");
                try{
                    orderHistoryService.ticketSave(appTicketId,ticketSerial,qrCode);
                    logger.info("====> 张少杰的接口已经执行 <=====");
                }catch (Exception e){
                    e.printStackTrace();
                }
                a = 2;
                logger.info("====> 【21】 <====");
                orderDao.updateOrderTicketBBD(appTicketId,orderSerial,ticketSerial,ticketId,ticketStatus,qrCode);
                logger.info("====> 已将百邦达票号数据保存至本地 <=====");
                a = 3;
            }
            logger.info("====> 订单数据已经完成同步，来自百邦达的票号已经完成保存。");
        }else if(code==98017){
            // 剩余座位数不足，执行退单
            logger.info("====> 【22】 <====");
            refundJob.orderFailureRefund(orderInfoByOrderCode);
        }else{
            logger.error("====> 订单数据发送失败！！ OrderCode:{}",orderCode);
            SynchrOrderToBbdFaild entity = new SynchrOrderToBbdFaild();
            entity.setCDate(LocalDateTime.now());
            entity.setFixed(0);
            entity.setOrderCode(orderCode);
            logger.info("====> 【23】 <====");
            synchrOrderToBbdFaildService.save(entity);
            logger.info("====> 【24】 <====");
        }
    }

    /**
     * 支付成功船票的处理
     * @param orders
     */
    private void processShipTicket(Orders orders) {
        logger.info("处理船票");
       if(orders.getOrderType() == 5){
           //船票支付成功后需要去确认订单支付
           logger.info("ticketOrderCode"+orders.getTicketOrderCode()+",batCode:"+orders.getBatCode()+",tid:"+orders.getTransactionId());
            try {
                ShipBaseVo<ConfirmPayVo> confirmPayBaseVo = LalyoubaShipHttpApiUtil.confirmPay(profiles, orders.getTicketOrderCode(), orders.getBatCode(), orders.getTransactionId(), "2");
                ConfirmPayVo confirmPayVo = confirmPayBaseVo.getData();
                //是否支付更新成功？
                logger.info("确认支付结果"+confirmPayVo.toString());
            }catch (Exception e){
                logger.error("查询支付结果失败");
            }
           //查询订单详情
           ShipBaseVo<OrderDetailVo> orderDetailBaseVo = LalyoubaShipHttpApiUtil.orderDetail(profiles, orders.getTicketOrderCode(), orders.getBatCode());
           OrderDetailVo orderDetailVo = orderDetailBaseVo.getData();
           //查询订单详情结果
           logger.info("查询订单详情结果:"+orderDetailVo);

           if(orderDetailVo !=null){
               //更新电子登船单
               wzdOrderAppletsMapper.updateOrderTicketUrl(orders.getId(),orderDetailVo.getE_tickets_url());
               //该订单已支付
               if("2".equals(orderDetailVo.getStatus())){
                   // 更新座位号
                   List<OrderTicket> ticketByOrderId = orderAppletsMapper.getOrderTicketByOrderId(orders.getId());

                   ticketByOrderId.stream().forEach(ticket -> {
                       List<OinfoVo> oinfo = orderDetailVo.getOinfo();

                       oinfo.stream().forEach( o -> {
                           if(ticket.getTicketUserName().equals(o.getPassengerName())){
                               OrderTicket orderTicket = new OrderTicket();
                               orderTicket.setId(ticket.getId());
                               orderTicket.setSeatNumber(o.getSeatInfo());
                               orderTicket.setTicketId(o.getTkdtID());
                               //更新票状态为已出票
                               orderTicket.setShipTicketStatus(GeneConstant.BYTE_3);
                               //姓名相同，更新座位号和ticketId
                               orderAppletsMapper.updateOrderTicket(orderTicket);
                               logger.info("更新座位号"+orderTicket.toString());
                           }
                       });
                   });
               }
           }



       }

    }

    /**
     * 取消过期的未支付的订单
     */
    @Override
    public void cancelExpireOrder() {
        List<Orders> orders = wzdOrderAppletsMapper.selectExpireOrder();
        if (CollectionUtils.isNotEmpty(orders)) {
            orders.stream().forEach(order -> {
                order.setState(5);
                order.setActualPay(new BigDecimal(0));
                wzdOrderAppletsMapper.refundOrder(order);
                processRefundCoupon(order);
                processExpiredShipTicket(order);
                logger.info("orderCode=" + order.getOrderCode() + ",createTime=" + order.getCreateTime() + ",expireTime=" + order.getExpiredTime() + ",因为未在规定时间内支付,订单已取消");
            });
        }
    }

    /**
     * 取消船票
     * @param order
     */
    private void processExpiredShipTicket(Orders order) {

        //船票订单
        if(order.getOrderType() == 5){
            ShipBaseVo shipBaseVo = LalyoubaShipHttpApiUtil.cancelOrder(profiles, order.getTicketOrderCode());

            //修改船票状态为失败
            orderAppletsMapper.updateOrderTicketShipStatus(order.getId(),2);

            logger.info(shipBaseVo.toString());
        }

    }

    @Override
    public OrderQueryVo orderQuery(Long companyId, String orderCode,User user) throws Exception{
        Orders orders = new Orders();
        orders.setOrderCode(orderCode);
        //根据微信订单号查询系统订单
        Orders info = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orders);
        if(info == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单不存在");
        }
        //订单Id
        Long orderId = info.getId();
        //订单类型
        Integer orderType = info.getOrderType();
        //用户id
        Long userId = info.getUserId();
        //微信订单号
        String outTradeno = info.getBatCode();
        if(!userId.equals(user.getId())){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"当前查询订单为非自己的订单");
        }

        //兼容涠洲岛船票支付
        if(orderType == 5){
            companyId = 101L;
        }

        //查询微信订单
        Map<String, Object> queryResultMap = wxPayService.orderQuery(companyId, outTradeno);
        Integer status = Integer.parseInt(queryResultMap.get("status").toString());
        //构建返回值必须信息
        OrderQueryVo.OrderQueryVoBuilder queryVoBuilder = OrderQueryVo
                .builder()
                .goodsPrice(info.getSiglePrice())
                .orderCode(info.getOrderCode())
                .orderPrice(info.getShouldPay())
                .orderType(orderType)
                .headImg(user.getHeadImg())
                .orderId(orderId);
        //订单查询不是支付成功的状态
        if(status == 1){
            try {
                //调用此方法为验证是否已经更改订单状态为已支付,如果微信回调晚于此方法执行,则由此方法更改订单状态
                complateOrder(outTradeno, queryResultMap.get("transactionId").toString());
            } catch (Exception e) {
                logger.error("BusinessException:{}",e.getMessage());
            }
            Integer orderTypeTmp = 3;
            if(orderTypeTmp.equals(info.getOrderType())){
                //开团
                if(info.getGroupOrderId()==null){
                    ResponseEntity responseEntity = new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
                    try {
                        OrderQueryVo groupOrder = groupOrderService.createGroupOrder(userId, orderCode, info.getPromotionId());
                        //发起拼团成功
                        if(groupOrder.getIsSuccess() == 1){
                            HashMap<String, Object> resultMap = new HashMap<>(8);
                            resultMap.put("groupOrderId",groupOrder.getGroupOrderId());
                            responseEntity.setValue(resultMap);
                            //拼团成功
                            queryVoBuilder.groupOrderType(1)
                                    .responseEntity(responseEntity);
                        }else {
                            responseEntity.setResult(GeneConstant.BUSINESS_ERROR);
                            queryVoBuilder.groupOrderType(1)
                                    .responseEntity(responseEntity);
                        }
                        //创建拼团时不能创建拼团的情况考虑
                    }catch (BusinessException e){
                        logger.error("发起拼团业务异常：{}",e.getMessage());
                        responseEntity.setResult(GeneConstant.BUSINESS_ERROR);
                        queryVoBuilder.groupOrderType(1)
                                .responseEntity(responseEntity);
                    }
                }else{//参团
                    try{
                        ResponseEntity responseEntity = groupOrderService.insertGroupOrder( info.getGroupOrderId(),  user.getId(), orderCode);
                        queryVoBuilder.groupOrderType(2).groupOrderId(info.getGroupOrderId())
                                .responseEntity(responseEntity);
                    }catch (Exception e){
                        logger.error("参与拼团业务异常：{}",e.getMessage());
                        ResponseEntity responseEntity = new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
                        responseEntity.setResult(GeneConstant.BUSINESS_ERROR);
                        queryVoBuilder.groupOrderType(2).groupOrderId(info.getGroupOrderId())
                                .responseEntity(responseEntity);
                    }
                }
            }
            return
                    queryVoBuilder
                            .isPaySuccess(1)
                            .build();
        }else {
            return queryVoBuilder.isPaySuccess(2)
                    .build();
        }
    }




    /**
     * 生成订单号
     * 统一订单号规则：
     * 业务编号+年份后两位+月份两位+4位流水
     * 业务编号：热气球 HQ 门票PT 车ZC 游船YC 餐CY
     *
     * @return
     */
    public String generateOrderCode(Long projectId) {

        String projectCodePrefix = "Project_Code_";
        //1、查询商品订单前缀
        GoodsProject goodsProject = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("id", projectId));

        String projectCode = goodsProject.getProjectCode();

//        if("prod".equals(profiles)){
//            profiles = "";
//        }
        //获取当前月份


        String month = new SimpleDateFormat("MM").format(new Date());

        Integer nowYear = DateUtil.getNowYear();

        String year  = String.valueOf(nowYear).substring(2);
        String key = projectCodePrefix .concat(projectCode).concat(year).concat("_").concat(month);
        String orderCountValue = redisTemplate.opsForValue().get(key);

        Long num ;
        if(StringUtils.isEmpty(orderCountValue)){

            //初始化数据进redis
            //查询数据库中当前月份,当前项目编号中最大的订单编号;
            String code = wzdOrderAppletsMapper.getMaxOrderCode(projectCode.concat(year).concat(month));
            Integer count  = 0;
            //HQ19070235test
            if(StringUtils.isNotEmpty(code)){
                //数据库中有数据
                count =  Integer.parseInt(code.substring(6, 10));
            }

            num = initRedisNum(key, count);

        }else {
            num =   redisTemplate.opsForValue().increment(key);

        }

        //创建订单号
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(projectCode)
                .append(year)
                .append(month)
                .append(String.format("%04d",num))
                .append(profiles);
        return stringBuilder.toString();
    }


    /**
     * 初始化redis 数据，多线程情况下保证安全
     * @param key
     * @param count
     * @return
     */
    public  synchronized Long  initRedisNum(String key,Integer count){

        if(flag){

            redisTemplate.opsForValue().set(key,count.toString());

            flag = false;
            return redisTemplate.opsForValue().increment(key);

        }else {

            return redisTemplate.opsForValue().increment(key);
        }

    }



    /**
     * 同步项目体验人数
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void syncProjectNum(){

        //获取当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String beforeMinutes = DateUtil.beforeMinutes(30);

        List<Map<String,Object>> map = wzdOrderAppletsMapper.selectProjectOrder(beforeMinutes, currentTime);

        messageSender.sendProjectYesterdayNumsQueue(map);

    }


    @Override
    public OrderPriceVo getOrderPrice(Long userId,Long userOfCouponId,BigDecimal goodsPrice,Integer amount) {
        BigDecimal amountDecimal = new BigDecimal(amount);
        //计算订单总价，单价*数量
        BigDecimal orderPrice = goodsPrice.multiply(amountDecimal);
        BigDecimal couponPrice ;
        try {
            CouponInfoVo couponInfo = userOfCouponMapper.getCouponInfo(userId, userOfCouponId);

            if(couponInfo.getPromotionStatus() != 2){
                throw new BusinessException(5,"优惠活动已失效");
            }
            if(couponInfo.getStatus() != 1 ){
                throw new BusinessException(5,"优惠券已失效");
            }

            if(couponInfo.getGoodsCouponStatus() != 1){
                throw new BusinessException(5,"优惠券不可用");
            }

            couponPrice =  calculatePrice(couponInfo, goodsPrice, orderPrice);
        }catch (Exception e){
            logger.error("订单不满足优惠条件:",e);
            couponPrice = null;
        }
        BigDecimal subtract = orderPrice.subtract(couponPrice);
        BigDecimal zeroBigdecimal = new BigDecimal("0");
        if(zeroBigdecimal.compareTo(subtract) == 1){
            subtract = zeroBigdecimal;
            couponPrice = orderPrice;
        }

        OrderPriceVo orderPriceVo = OrderPriceVo.builder()
                .amount(amount)
                .orderPrice(orderPrice)
                .discount(couponPrice)
                .couponPrice(couponPrice == null?orderPrice:subtract)
                .userOfCouponId(userOfCouponId)
                .build();


        return orderPriceVo;
    }


    @Override
    public Object saveShipOrder(PayParam payParam) throws Exception {


        //查询商品信息
        GoodsExtend goodsExtend = goodsExtendMapper.selectById(payParam.getGoodsId());
        if(goodsExtend == null){
            throw  new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到船票信息");
        }
        String shipLineInfo = goodsExtend.getShipLineInfo();
        ShipLineInfo lineInfo = JSONObject.parseObject(shipLineInfo, ShipLineInfo.class);
        //查询船票详细信息
        ShiftInfo ticketInfo = LalyoubaShipHttpApiUtil.getShipLineTicketInfo(profiles,
                lineInfo.getStartPortID().toString(),
                lineInfo.getArrivePortID().toString(),
                goodsExtend.getLineDate(),
                lineInfo.getLineID(),
                goodsExtend.getCabinName(),
                lineInfo.getShipID(),
                lineInfo.getStartTime());
        if(ticketInfo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"实时查询船票信息失败!");
        }
//        //获取用户联系人信息
        List<Contacts> contacts = contactsMapper.selectBatchIds(payParam.getContacts());
        if(CollectionUtils.isEmpty(contacts)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"联系人信息获取失败!");
        }
        checkContact(contacts);
        String wxOrderCode = GeneUtil.getCode();
        List<TicketsItems> itemsList = buildTicketItems(contacts,ticketInfo);
        logger.info("下单请求参数，profiles=>{},itemList=>{},wxOrderCode=>{},startPortId=>{},arrivePortId=>{},usernmae=>{},phone=>{},remark=>{}",
                "prod",
                JSONObject.toJSON(itemsList),
                wxOrderCode,
                Integer.valueOf(lineInfo.getStartPortID().toString()),
                Integer.valueOf(lineInfo.getArrivePortID().toString()),
                payParam.getName(),
                payParam.getPhone(),
                payParam.getRemark());
        ShipBaseVo<CreateOrderVo> createOrderVo = LalyoubaShipHttpApiUtil.createOrder(profiles,
                itemsList,
                wxOrderCode,
                null,
                Integer.valueOf(lineInfo.getStartPortID().toString()),
                Integer.valueOf(lineInfo.getArrivePortID().toString()),
                payParam.getName(),
                payParam.getPhone(),
                payParam.getRemark());

        if(createOrderVo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"下单锁位失败!");
        }

        if(createOrderVo.getStatus() != 1){
            String message = createOrderVo.getMessage();
            if(message.contains("售空")){
                throw new BusinessException(7,"该航班已售空，请重新选择!");
            }else if(message.contains("重复锁票")){
                throw new BusinessException(7,"您已占座成功，请勿重复提交订单");
            }else if(message.contains("剩余座位数不够")){
                throw new BusinessException(7,"剩余座位数不够,请重新选择!");
            }else if(message.contains("大于1岁小于15岁才可购买儿童票")){
                throw new BusinessException(7,message);
            } else if(message.contains("姓名与证件号")){
                throw new BusinessException(7,"姓名与证件号不匹配");
            }else {
                throw new BusinessException(7,message);
            }

        }
        logger.info("请求涠洲岛船票API:post"+createOrderVo.toString());
        //锁位成功，保存订单信息
        Orders.OrdersBuilder builder = Orders.builder();
        builder
                .goodsName(ticketInfo.getCabinName())
                .ticketOrderCode(createOrderVo.getData().getOrderSerial())
                .batCode(wxOrderCode)
                .projectCode("KL")
                .projectId(1000L)
                .enterTime(new java.sql.Date(DateUtil.formatD(goodsExtend.getLineDate(),"yyyy-MM-dd").getTime()))
                .timespan(lineInfo.getStartTime());
        builder = buildShipTicketOrder(builder,itemsList,payParam);
        Orders orders = builder.build();
        if("prod".equals(orders.getProfiles())){
            orders.setProfiles("");
        }
        //保存子订单信息
        int i = wzdOrderAppletsMapper.insertOrder(orders);
        if(i > 0 ){
            Orders orderById = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());

            //保存子订单信息
          List<OrderTicket> tickets =   buildTicketChild(orders,itemsList);
            orderAppletsMapper.insertOrdertTicket(tickets);
            logger.info("子订单插入完毕");

        }

       String totalFee =  orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();
        //调用支付信息
      Object object =   wxPayService.pay(101L, orders.getGoodsName(), payParam.getOpenId(),orders.getBatCode(), totalFee, payParam.getIp(),orders.getOrderCode());

        return object;
    }

    /**
     * 检查联系人是否合法
     * @param contacts
     */
    private void checkContact(List<Contacts> contacts) {

        //携童票数量不能超过成人票数量
        Map<Integer, List<Contacts>> ticketType = contacts.stream().collect(Collectors.groupingBy(x -> x.getTicketType()));
        List<Contacts> adultList = ticketType.get(GeneConstant.INT_101);
//        List<Contacts> childList = ticketType.get(GeneConstant.INT_203);
        List<Contacts> babyList = ticketType.get(GeneConstant.INT_404);

        int adultSize = CollectionUtils.isEmpty(adultList)?0:adultList.size();
//        int child = CollectionUtils.isEmpty(childList)?0:childList.size();
        int babySize = CollectionUtils.isEmpty(babyList)?0:babyList.size();
        if(adultSize <= 0){
            throw new BusinessException(7,"必须有一张成人票!");
        }
        if(babySize > adultSize){
            throw new BusinessException(7,"一个成人只能够携带一名儿童");
        }
    }

    /**
     * 构建子订单信息
     * @param orders 订单信息
     * @param itemsList 子订单信息
     * @return
     */
    private List<OrderTicket> buildTicketChild(Orders orders, List<TicketsItems> itemsList) {
        List<OrderTicket> orderTicketsList = new ArrayList<>();
        for (TicketsItems items : itemsList) {
            OrderTicket info = OrderTicket.builder()
                    .ticketCode(orders.getOrderCode())
                    .createTime(orders.getCreateTime())
                    .orderId(orders.getId())
                    .goodsExtendId(orders.getGoodsId())
                    .goodsName(orders.getGoodsName())
                    .projectId(orders.getProjectId())
                    .goodsId(orders.getGoodsId())
                    .ticketUserName(items.getName())
                    .ticketType(items.getTicketType().toString() .length() >4 ?"101":items.getTicketType().toString())
                    .idCard(items.getIDNo())
                    .phone(items.getPhone())
                    .shipTicketStatus(GeneConstant.BYTE_1)
                    .singlePrice(new BigDecimal(items.getTicketPrice()))
                    .babyInfo(items.getBabyinfo())
                    .build();
            orderTicketsList.add(info);
        }

        return orderTicketsList;
    }

    /**
     * 构建订单信息
     * @param builder 订单构建类
     * @param itemsList 船票订单实体
     * @param payParam  支付参数
     * @return
     */
    private Orders.OrdersBuilder  buildShipTicketOrder(Orders.OrdersBuilder builder, List<TicketsItems> itemsList, PayParam payParam) {

        BigDecimal orderPrice = new BigDecimal("0");
        for (TicketsItems ticketsItems : itemsList) {
            orderPrice = orderPrice.add(new BigDecimal(ticketsItems.getTicketPrice().toString()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path",payParam.getPath());
        jsonObject.put("formId",payParam.getFormId());
        jsonObject.put("openId",payParam.getOpenId());


        Orders.OrdersBuilder ordersBuilder = builder
//               .goodsPrice(orderPrice)
                .goodsPrice(new BigDecimal("0.01"))
                .state(GeneConstant.INT_1)
                .amount(Long.valueOf(itemsList.size()))
                .userId(payParam.getUserId())
                .scenicId(payParam.getScenicId())
                //类型 门票
                .type(GeneConstant.LONG_5)
                .goodsId(payParam.getGoodsId())
                .payType(payParam.getPayType())
                .isDistributeOrder(GeneConstant.INT_0)
                .orderSource(GeneConstant.INT_1)
                .payStatus(GeneConstant.INT_1)
                .shouldPay(orderPrice)
                .userWechatName(GeneUtil.filterEmoji(payParam.getUserWechatName()))
                .phone(payParam.getPhone())
                .name(payParam.getName())
                .orderType(GeneConstant.INT_5)
                .snapshot(jsonObject.toJSONString())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .expiredTime(new Timestamp(DateUtil.getNextTime(20).getTime()))
                .profiles(this.profiles);
        return ordersBuilder;
    }

    /**
     * 构建涠洲岛船票锁位订单明细
     * @param contacts
     * @param ticketInfo
     * @return
     */
    private List<TicketsItems> buildTicketItems(List<Contacts> contacts, ShiftInfo ticketInfo) {

        List<TicketsItems> list = new ArrayList<>();

        LinkedList<Contacts> babyInfo = new LinkedList<>();
        contacts.stream().forEach( contactsInfo ->{
            Integer ticketType = contactsInfo.getTicketType();
            //不是携童票
            if(!GeneConstant.INT_404.equals(ticketType)){
                TicketsItems.TicketsItemsBuilder itemsBuilder = TicketsItems.builder()
                        .TicketType(Long.valueOf(ticketType))
                        .LineID(ticketInfo.getLineID())
                        .ShipID(ticketInfo.getShipID())
                        .ClTime(ticketInfo.getClTime())
                        .ClCabinID(ticketInfo.getClCabinID())
                        .name(contactsInfo.getName())
                        .IDNo(contactsInfo.getIdCard())
                        .phone(contactsInfo.getPhoneNum())
                        .type(GeneConstant.INT_1);
                //成人票价
               if(GeneConstant.INT_101.equals(ticketType)){
                   String discountType = ticketInfo.getDiscountType();
                   if(!"0".equals(discountType)){
                                //成人票优惠票类型
                                //当为0时，不存在优惠票，只有全价票,当有值时不卖成人票，只卖这个票型
                       itemsBuilder.TicketPrice(Float.valueOf(ticketInfo.getDiscountPrice()))
                                   .TicketType(Long.valueOf(discountType));
                   }else {
                       itemsBuilder.TicketPrice(ticketInfo.getTicketFullPrice());

                   }
                 }
               //儿童票价
               if(GeneConstant.INT_203.equals(ticketType)){
                   itemsBuilder.TicketPrice(ticketInfo.getTicketChildPrice());
                 }
                TicketsItems ticketsItems = itemsBuilder.build();
                list.add(ticketsItems);
            }else {
               babyInfo.add(contactsInfo);
            }

        });
        if(babyInfo.size() > 0){

            //携童信息需要单独封装
            for (int i = 0 ;i<list.size(); i++){
                TicketsItems ticketsItems = list.get(i);
                //成人才能携带儿童
                if("101".equals(ticketsItems.getTicketType().toString()) || ticketsItems.getTicketType().toString().length() > 4){
                    Contacts baby = babyInfo.poll();
                    if(baby != null){
                        ticketsItems.setBabyinfo(baby.getName() +" "+baby.getIdCard());
                    }else {
                        //暂无携童信息
                        break;
                    }

                }
            }
        }



        return list;
    }

    @Override
    public void sendVerificationCode(String phone) {
        String redisKey = String.format(AppConstants.TICKET_MESSAGE_CODE, phone);

        // 发送审核验证码　存redis
        int random = new Random().nextInt(999999);
        String smsCode = new DecimalFormat("000000").format(random);

        //发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("companyId", String.valueOf(11));
        map.put("phone", phone);
        map.put("content",GeneConstant.MESSAGE_SIGN_SHIP + String.format(AppConstants.SEND_CODE_TEMPLATE,smsCode));
        try{
            messageSender.sendSmsV3(map);
            redisTemplate.opsForValue().set(redisKey,smsCode,60*3, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("短信发送失败，参数：[{}]",map);
        }
    }


    @Override
    public Object makeUpOrder(String orderCode) throws Exception {

        Map<String,Object> map = new HashMap<>();

        Orders orderParam = new Orders();
        orderParam.setOrderCode(orderCode);

        //查询船票订单
        Orders orders = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orderParam);

        if(orders == null){
            logger.error("补录订单订单未查询到为空=>{}",orderCode);
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到订单");
        }
        List<OrderTicket> orderTickets = ordersTicketMapper.selectList(new QueryWrapper<OrderTicket>().eq("ticket_code", orderCode));
        if(CollectionUtils.isEmpty(orderTickets)){
            logger.error("补录订单子订单查询为空=>{}",JSONObject.toJSONString(orders));
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到子订单信息");
        }
        map.put("orderCode",orderCode);
        map.put("orderStatus",orders.getState());
        map.put("orderPayStatus",orders.getPayStatus());
        //订单类型为船票订单
        if(orders.getOrderType() == 5){
            logger.info("订单类型为船票订单");
            //查询微信订单详情
            Map<String, Object> wxOrderInfo = wxPayService.orderQuery(101L, orders.getBatCode());
            Integer status = (Integer) wxOrderInfo.get("status");
            if(status == 1){

                Boolean isNotNormal = false;

                logger.info("微信查询订单支付成功");
                for (OrderTicket orderTicket : orderTickets) {
                    //支付成功了，但是票的座位号为空，此单为异常订单
                    if(StringUtils.isEmpty(orderTicket.getTicketId())){
                        logger.info("票的ticketId 为空");
                        isNotNormal = true;
                        break;

                    }
                }
                //票的座位号为空的，是不正常的订单
                if(isNotNormal){
                    orderAlert(map, orders);

                    if(orders.getPayStatus() == 1 && orders.getState() ==1){
                        logger.info("该笔订单未进行订单回调,执行回调逻辑");
                        complateOrder(orders.getBatCode(),wxOrderInfo.get("transactionId").toString());

                    }else {
                        logger.info("订单支付回调成功，但是某种原因导致未生成座位信息");
                        processShipTicket(orders);
                    }
                    map.put("orderIsNormal","NotNormal");

                    return map;
                }else {
                    map.put("orderIsNormal","normal");
                    return map;
                }

            }else {

            logger.error("补录订单，该笔订单未支付");
            map.put("wxPayResult",wxOrderInfo);
            return map;
            }

        }else {
            logger.error("补录订单不为船票订单");
            return map;
        }

    }

    public void orderAlert( Map<String, Object> map, Orders orders) {
        String orderCode = orders.getOrderCode();
        //判断订单创建时间与当前时间差是否超过20分钟，超过20分钟发短信通知开发人员
        Timestamp createTime = orders.getCreateTime();
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        long leaveTime = DateUtil.differentMunitessIsNotAbs(createTime,currentTime);
        if (leaveTime  > 30) {
            //发短信预警
            String sms = redisTemplate.opsForValue().get("smsAlert");

            if(StringUtils.isNotEmpty(sms)){
                String[] alertPhones = sms.split(",");
                for (String phone : alertPhones) {
                    //查询是否已经发送过短信
                    String isSendMessage = redisTemplate.opsForValue().get("smsAlert_" + phone + "_" + orderCode);
                    if(StringUtils.isEmpty(isSendMessage)){
                        //没有发送过短信
                        //发送短信
                        HashMap<String, String> messageMap = new HashMap<>();
                        messageMap.put("companyId", String.valueOf(11));
                        messageMap.put("phone", phone);
                        messageMap.put("content", GeneConstant.MESSAGE_SIGN_SHIP + String.format(AppConstants.ORDER_SMS_ALERT,orderCode));
                        messageSender.sendSmsV3(messageMap);
                        redisTemplate.opsForValue().set("smsAlert_" + phone + "_" + orderCode,orderCode);
                        map.put("isAlert",true);

                    }

                }
            }
        }
    }
}
