package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.BbdOrderChangeParam;
import cn.enn.wise.platform.mall.bean.param.BbdOrderSaveParams;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.OfflineService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 离线订单业务实现层
 *
 * @author baijie
 * @date 2019-08-06
 */
@Service
public class OfflineServiceImpl implements OfflineService {

    private static  final Logger logger = LoggerFactory.getLogger(OfflineServiceImpl.class);

    @Autowired
    private OfflineOrderMapper  offlineOrderMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @Autowired
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Autowired
    private  GoodsProjectMapper goodsProjectMapper;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Resource
    private GoodsRelatedBBDMapper goodsRelatedBBDMapper;

    @Resource
    private OrderAppletsMapper orderAppletsMapper;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrdersTicketMapper ordersTicketMapper;

    /**
     * 定时调度的线程池
     */
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    @Override
    public ResponseEntity saveOrUpdateOfflineOrder(OrderReqVo orderReqVo) throws Exception {

        Orders orders = new Orders();

        //1 补全离线订单信息
        complateOfflineOrder(orderReqVo, orders);
        Integer isDistributeOrder = orders.getIsDistributeOrder();
        if(isDistributeOrder == 1){
            //检查是否能够生成分销订单
            checkisDistributorOrder(orderReqVo, orders);
            //判断订单需要更新还是添加
            int saveOrUpdateCount;
            saveOrUpdateCount = saveOrUpdateOrder(orderReqVo, orders);
            //添加或更新订单成功
            if (saveOrUpdateCount <= 0) {
                throw new BusinessException(GeneConstant.BUSINESS_ERROR, "编辑离线订单失败！");
            }
            //查询订单号
            orders.setOrderCode(wzdOrderAppletsMapper.getOrderById(orders.getId()).getOrderCode());
            //添加时才会创建分销订单,修改时不会
            if(orderReqVo.getId() == null){
                createDistributeOrder(orderReqVo.getGoodsId(), orders);
            }
            //修改分销订单策略
            ResponseEntity responseEntity = remoteServiceUtil.updateDistributeOrderStrategyItemId(orders.getId(),orderReqVo.getStrategyItemId());
            if(responseEntity == null){

                throw new BusinessException(GeneConstant.BUSINESS_ERROR, "修改分销订单策略失败!");

            }

            if (orderReqVo.getOfflineStatus() == 2) {

                //1 更改分销订单状态流转为1 待支付 -> 已支付
                Byte status =1;
                messageSender.sendSyncDistributorOrderStatus(orders.getId(), status);

                logger.info("延迟执行:当前时间="+DateUtil.getNowDate());
                executorService.schedule(()-> {

                        Byte status1 = 2;
                        //2
                        messageSender.sendSyncDistributorOrderStatus(orders.getId(),status1);
                        logger.info("延迟执行:当前时间="+DateUtil.getNowDate());

                },5, TimeUnit.SECONDS);

                executorService.schedule(()->{
                    //3 更改分销订单状态流转为3 开始计算返利
                    Byte status2 = 3;
                    messageSender.sendSyncDistributorOrderStatusByAmount(orders.getId(), status2,orders.getAmount().intValue());
                    logger.info("延迟执行:当前时间="+DateUtil.getNowDate());
                },20,TimeUnit.SECONDS);




                return new ResponseEntity(GeneConstant.SUCCESS_CODE, GeneConstant.SUCCESS_CHINESE);

            }
        }else {
            //判断订单需要更新还是添加
            int saveOrUpdateCount;
            saveOrUpdateCount = saveOrUpdateOrder(orderReqVo, orders);
            //添加或更新订单成功
            if (saveOrUpdateCount <= 0) {
                throw new BusinessException(GeneConstant.BUSINESS_ERROR, "编辑离线订单失败！");
            }
        }

            return new ResponseEntity(GeneConstant.SUCCESS_CODE, GeneConstant.SUCCESS_CHINESE);

    }

    private void createDistributeOrder(Long  goodsId, Orders orders) {
        // 1创建分销订单
        DistributeOrderVo distributeOrder = new DistributeOrderVo();
        distributeOrder.setDistributorId(String.valueOf(orders.getDistributorId()));
        distributeOrder.setOrderId(String.valueOf(orders.getId()));
        distributeOrder.setGoodsName(orders.getGoodsName());
        distributeOrder.setGoodsCount(String.valueOf(orders.getAmount()));
        distributeOrder.setGoodsPrice(String.valueOf(orders.getSiglePrice()));
        distributeOrder.setGoodsId(String.valueOf(goodsId));
        distributeOrder.setOrderPrice(String.valueOf(orders.getGoodsPrice()));
        distributeOrder.setContacts(String.valueOf(orders.getPhone()));
        distributeOrder.setSnapshot(JSONObject.toJSONString(orders));
        distributeOrder.setRoleId(orders.getRoleId());

        ResponseEntity saveDistributeOrderResult = remoteServiceUtil.getSaveDistributeOrderResult(distributeOrder);
        if (saveDistributeOrderResult == null) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "创建分销订单失败!请检查策略配置");

        }

        int result = saveDistributeOrderResult.getResult();
        if (result != 1) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "创建分销订单失败!请检查策略配置.");

        }
    }

    /**
     * 添加或更新订单
     * @param orderReqVo
     * @param orders
     * @return
     * @throws Exception
     */
    private int saveOrUpdateOrder(OrderReqVo orderReqVo, Orders orders) throws Exception {
        int i;
        if (orderReqVo.getId() == null) {
            //生成订单号
            i = wzdOrderAppletsMapper.insertOrder(orders);

        } else {
            //更新操作、、需要先查询是否可以更新
            i = updateOfflineOrder(orderReqVo, orders);

        }
        return i;
    }

    /**
     * 补全离线订单信息
     * @param orderReqVo
     * @param orders
     */
    private void complateOfflineOrder(OrderReqVo orderReqVo, Orders orders) {
        Long goodsId = orderReqVo.getGoodsId();
        Goods goods = goodsMapper.selectById(goodsId);
        Long projectId = goods.getProjectId();
        GoodsProject goodsProject = goodsProjectMapper.selectById(projectId);
        orders.setProjectCode(goodsProject.getProjectCode());
        GoodsExtend goodsExtend;
        orders.setProjectId(projectId);
        if(orderReqVo.getIsByPeriodOperation() == 1){
            orders.setGoodsId(orderReqVo.getGoodsExtendId());

            goodsExtend = goodsExtendMapper.selectById(orderReqVo.getGoodsExtendId());
        }else {
            GoodsApiResVO goodsInfoById = wzdGoodsAppletsMapper.getGoodsInfoById(goodsId);

            GoodsApiExtendResVo goodsApiExtendResVo = goodsInfoById.getGoodsApiExtendResVoList().get(0);
            goodsExtend = new GoodsExtend();
            goodsExtend.setSalePrice(goodsApiExtendResVo.getSalePrice());
            orders.setGoodsId(goodsApiExtendResVo.getId());

        }


        if(goods == null  || goodsExtend  == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商品不存在");

        }

        if (!"prod".equals(profiles)) {
            orders.setProfiles(profiles);

        } else {
            orders.setProfiles("");
        }
        orders.setMaxNumberOfUsers(goods.getMaxNum());
        orders.setGoodsName(goods.getGoodsName());
        orders.setAmount(orderReqVo.getAmount());
        orders.setSiglePrice(goodsExtend.getSalePrice());
        orders.setGoodsPrice(orderReqVo.getOrderPrice());
        orders.setPhone(orderReqVo.getPhone());
        //订单来源 现场转化
        orders.setOrderSource(3);
        //订单状态 体验完成
        orders.setState(9);
        //离线订单
        orders.setOrderType(2);
        //付款成功
        orders.setPayStatus(2);
        orders.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        orders.setEnterTime(orderReqVo.getEnterTime());
        orders.setCreateTime(orderReqVo.getCreateTime());
        orders.setOfflineStatus(orderReqVo.getOfflineStatus());
        orders.setScenicId(orderReqVo.getScenicId());
        // type 二销产品
        orders.setType(6L);
        orders.setPayType("现金");
        orders.setName(orderReqVo.getName());
        orders.setActualPay(orderReqVo.getOrderPrice());
        orders.setShouldPay(orderReqVo.getOrderPrice());
        orders.setOfflineUser(orderReqVo.getOfflineUser());
        orders.setOfflineStatus(orderReqVo.getOfflineStatus());
        Integer isDistributeOrder = orderReqVo.getIsDistributeOrder();
        orders.setIsDistributeOrder(isDistributeOrder);
        //是分销订单
        if(isDistributeOrder == 1){

            orders.setProfitStatus(1);
        }else {

            orders.setProfitStatus(2);
        }
    }



    @Override
    public OrderResVo getOfflineOrderInfo(Long id) {

        //查询离线订单详情
        OrderResVo orderById = offlineOrderMapper.selectOfflineOrderById(id);
        Long goodsExtendId = orderById.getGoodsId();

        //查询商品信息
        GoodsExtend goodsExtend = goodsExtendMapper.selectById(goodsExtendId);
        Long goodsId = goodsExtend.getGoodsId();
        //查询商品sku信息
        Goods goods = goodsMapper.selectById(goodsId);
        Integer isByPeriodOperation = goods.getIsByPeriodOperation();
        orderById.setIsByPeriodOperation(isByPeriodOperation);
        Long projectId = goods.getProjectId();
        //查询项目信息
        GoodsProject goodsProject = goodsProjectMapper.selectById(projectId);
        String servicePlaceId = goodsProject.getServicePlaceId();
        orderById.setServicePlaceId(Integer.parseInt(servicePlaceId.split(",")[0]));
        orderById.setProjectId(projectId);
        orderById.setGoodsExtendId(goodsExtendId);
        orderById.setGoodsId(goodsId);

        if(orderById.getIsDistributeOrder() == 1){
//            {"companyName":"西藏大峡谷景区","remark":"111","source":1,"applyTimes":1,"verifyStatus":1,"id":2173,"level":1,"updateUserId":1,"updateUserName":"admin","updateTime":"2019-07-09T15:06:37.000+0800","userName":"侧身睡等等","userId":4023,"expiredTime":"2020-07-08T18:00:00.000+0800","companyId":7,"phone":"15116959636","createTime":"2019-07-07T18:44:38.000+0800","idCardNumber":"130623198612212139","applyDate":"2019-07-07","userRole":"44","status":1}
            orderById.setDistributorName(JSONObject.parseObject(orderById.getSnapshot()).getString("userName"));
            orderById.setDistributorPhone(JSONObject.parseObject(orderById.getSnapshot()).getString("phone"));
            orderById.setRoleId(JSONObject.parseObject(orderById.getSnapshot()).getString("userRole"));
            orderById.setStrategyItemId(JSONObject.parseObject(orderById.getSnapshot()).getLong("strategyItemId"));
        }

        return orderById;
    }

    @Override
    public Boolean deleteOfflineOrderById(Long id) {
        OrderResVo orderResVo = offlineOrderMapper.selectOfflineOrderById(id);
        if(orderResVo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单不存在");
        }
        if(orderResVo.getOfflineStatus()!=1){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单不可删除");
        }
        if(orderResVo.getOrderType() != 2){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"非离线订单不可删除");
        }

        int i = offlineOrderMapper.deleteOfflineOrderById(id);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新离线订单
     * @param orderReqVo
     * @param orders
     * @return
     */
    private int updateOfflineOrder(OrderReqVo orderReqVo, Orders orders) {
        int i;
        //更新操作、、需要先查询是否可以更新
        Orders orderById = offlineOrderMapper.selectOfflineOrderById(orderReqVo.getId());
        if ( orderById.getOfflineStatus() == 1) {

            orders.setId(orderById.getId());
            i = offlineOrderMapper.updateOfflineOrder(orders);

        } else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "该订单不存在或者无法进行更新");

        }
        return i;
    }

    /**
     * 检查是否能够生成分销订单
     * @param orderReqVo
     * @param orders
     */
    public void checkisDistributorOrder(OrderReqVo orderReqVo,Orders orders) {
        //是分销订单
        // #region 1 查询分销商身份是否合法
        ResponseEntity checkUserResult = remoteServiceUtil.getCheckUserResult(orderReqVo.getDistributorPhone(),orders.getScenicId());

        if (checkUserResult != null && checkUserResult.getResult() == 1) {
            //分销商id
            JSONObject checkUserResultValue = (JSONObject) (checkUserResult.getValue());

            String id = checkUserResultValue.get("id").toString();
            orders.setDistributorId(Long.valueOf(id));
            //获取用户角色信息
            String userRole = checkUserResultValue.getString("userRole");
            orders.setRoleId(userRole);
            checkUserResultValue.put("strategyItemId",orderReqVo.getStrategyItemId());
            orders.setSnapshot(checkUserResultValue.toJSONString());

        }else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该分销商无效,请核对后再保存");

        }
        //#region end

        // #region 2
        ResponseEntity checkStrategyItemsResult = remoteServiceUtil.getCheckStrategyItemsResult(String.valueOf(orderReqVo.getGoodsId()), orders.getRoleId(),orders.getScenicId());

        if(checkStrategyItemsResult != null && checkStrategyItemsResult.getResult() == 1){
            logger.info("查询到商品的策略："+((JSONObject) (checkStrategyItemsResult.getValue())).toJSONString());

        }else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到分销策略,请检查该商品的分销策略配置");
        }
    }


    @Override
    public Map<String,Object> saveBbdOfflineOrder(BbdOrderSaveParams bbdOrderParams) throws Exception {

        Map<String,Object> resultMap = new HashMap<>();

        String productId = bbdOrderParams.getProductId();
        Date departureDate = bbdOrderParams.getDepartureDate();
        Long amount = bbdOrderParams.getAmount();
        String tourismName = bbdOrderParams.getTourismName();
        String phone = bbdOrderParams.getPhone();
        String businessNumber = bbdOrderParams.getBusinessNumber();
        BigDecimal orderPrice = bbdOrderParams.getOrderPrice();
        String thirdOrderNo = bbdOrderParams.getThirdOrderNo();

        //查询百邦达订单号
        Orders batCodeOther = wzdOrderAppletsMapper.getOrderByBatCodeOther(thirdOrderNo);
        if(batCodeOther!= null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单号重复,thirdOrderNo="+thirdOrderNo);
        }

        //查询百邦达商品信息
        List<GoodsRelatedBBDBo> goodsRelatedBBDBo = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("bbd_goods_id", productId));

//        if(CollectionUtils.isEmpty(goodsRelatedBBDBo)){
//            goodsRelatedBBDBo = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("bbd_goods_id", productId));
//        }

        if(CollectionUtils.isEmpty(goodsRelatedBBDBo)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商品信息获取失败,productId="+productId);
        }
        Long goodsId = goodsRelatedBBDBo.get(0).getGoodsId();

        List<GoodsExtend> goodsExtends = goodsExtendMapper.selectList(new QueryWrapper<GoodsExtend>().eq("goods_id", goodsId));
        if(CollectionUtils.isEmpty(goodsExtends)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"goodsExtend数据获取失败，goodsId="+goodsId);
        }
        Long extendId = goodsExtends.get(0).getId();
        //查询商品信息
        GoodsExtendInfoVO goodsExtendInfoById = goodsMapper.getGoodsExtendInfoById(extendId);
        if(goodsExtendInfoById == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商品信息获取失败,productId="+productId);
        }

        //封装订单对象
        Orders.OrdersBuilder ordersBuilder = Orders.builder();
        ordersBuilder
                .projectCode(goodsExtendInfoById.getProjectCode())
                .projectId(goodsExtendInfoById.getProjectId())
                .goodsId(extendId)
                .maxNumberOfUsers(goodsExtendInfoById.getMaxNum())
                .goodsName(goodsExtendInfoById.getGoodsName())
                .amount(amount)
                .siglePrice(goodsExtendInfoById.getSalePrice())
                .goodsPrice(orderPrice)
                .phone(phone)
                //现场转化
                .orderSource(4)
                //订单状态，待使用
                .state(2)
                //百邦达下单
                .orderType(6)
                .payStatus(2)
                .enterTime(departureDate)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .offlineStatus(2)
                .scenicId(11L)
                .type(6L)
                .payType("现金")
                .name(tourismName)
                .actualPay(orderPrice)
                .shouldPay(orderPrice)
                .offlineUser("百邦达下单")
                .isDistributeOrder(0)
                //百邦达订单编号
                .batCodeOther(thirdOrderNo)
                .profitStatus(2);
        if(!"prod".equals(profiles)){
            ordersBuilder.profiles(profiles);
        }else {
            ordersBuilder.profiles("");
        }

        //构建订单对象
        Orders orders = ordersBuilder.build();

        //分销处理逻辑
        if(StringUtils.isNotEmpty(businessNumber)){
            //根据业务编号查询是否具有分销商身份
            boolean flag = true;
            //检查是否能够生成分销订单
            ResponseEntity checkUserResult = remoteServiceUtil.getCheckUserResult(businessNumber,11L);

            if (checkUserResult != null && checkUserResult.getResult() == 1) {
                //分销商id
                JSONObject checkUserResultValue = (JSONObject) (checkUserResult.getValue());

                String id = checkUserResultValue.get("id").toString();
                orders.setDistributorId(Long.valueOf(id));
                //获取用户角色信息
                String userRole = checkUserResultValue.getString("userRole");
                orders.setRoleId(userRole);
                orders.setSnapshot(checkUserResultValue.toJSONString());

            }else {
              flag = false;

            }

            // #region 2
            ResponseEntity checkStrategyItemsResult = remoteServiceUtil.getCheckStrategyItemsResult(String.valueOf(goodsExtendInfoById.getGoodsId()), orders.getRoleId(),orders.getScenicId());

            if(checkStrategyItemsResult != null && checkStrategyItemsResult.getResult() == 1){
                logger.info("查询到商品的策略："+((JSONObject) (checkStrategyItemsResult.getValue())).toJSONString());

            }else {
                flag = false;
            }

            if(flag){
                orders.setIsDistributeOrder(1);
                orders.setProfitStatus(1);
            }

        }

        //保存订单
        int affect = wzdOrderAppletsMapper.insertOrder(orders);
        if(affect > 0){
            logger.info("订单创建成功!");
            //
            //查询订单号
            Orders order = wzdOrderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(order.getOrderCode());
            if(orders.getIsDistributeOrder() == 1){
                //创建分销订单
                createDistributeOrder(goodsExtendInfoById.getGoodsId(),orders);

                //修改分销订单策略
                remoteServiceUtil.updateStrategyItemByIsScenicService(orders.getId(), bbdOrderParams.getIsScenicService());


                //2 更改分销订单状态流转为 1 待支付 -> 已支付
                Byte status = 1;
                messageSender.sendSyncDistributorOrderStatus(orders.getId(), status);

                logger.info("延迟执行:当前时间="+DateUtil.getNowDate());
                //2 更改分销订单状态流转为 2 已支付 -> 已使用
                executorService.schedule(()-> {

                    Byte status1 = 2;
                    //2
                    messageSender.sendSyncDistributorOrderStatus(orders.getId(),status1);
                    logger.info("延迟执行:当前时间="+DateUtil.getNowDate());

                },10, TimeUnit.SECONDS);

            }
            saveOrderTicket(orders,goodsExtendInfoById,resultMap,bbdOrderParams);

            return resultMap;

        }else {
            logger.error("订单创建失败！系统异常！");
            throw new BusinessException(GeneConstant.SYSTEM_ERROR,"订单创建失败");
        }

    }

    /**
     * 保存订单子表
     * @param orders
     * @param bbdOrderParams
     */
    private void saveOrderTicket(Orders orders, GoodsExtendInfoVO info, Map<String, Object> resultMap, BbdOrderSaveParams bbdOrderParams) {
        List<OrderTicket> orderTickets = new ArrayList<>();

        Long amount = orders.getAmount();
        for (int j = 0; j < amount; j++) {
            OrderTicket ticket = OrderTicket.builder()
                    .ticketCode(orders.getOrderCode())
                    .createTime(orders.getCreateTime())
                    .orderId(orders.getId())
                    .goodsId(info.getGoodsId())
                    .goodsName(orders.getGoodsName())
                    .goodsExtendId(orders.getGoodsId())
                    .singlePrice(orders.getSiglePrice())
                    .couponPrice(orders.getSiglePrice())
                    .projectId(info.getProjectId())
                    .projectName(info.getProjectName())
                    .ticketStateBbd(1)
                    .ticketSerialBbd(bbdOrderParams.getTicketSerialBbd())
                    .ticketQrCodeBbd(bbdOrderParams.getTicketQrCodeBbd())
                    .orderSerialBbd(bbdOrderParams.getOrderSerialBbd())
                    //百邦达票Id
                    .ticketIdBbd(orders.getBatCodeOther())
                    .build();
            orderTickets.add(ticket);

        }
        orderAppletsMapper.insertOrdertTicket(orderTickets);

        resultMap.put("ticketId",orderTickets.get(0).getId());
        resultMap.put("ticketIdBbd",orders.getBatCodeOther());
    }


    @Override
    public void bindOfflineOrder(String phone, User user) throws Exception {

        //查询百邦达线下订单
        List<Orders> orders = orderDao.selectList(new QueryWrapper<Orders>().eq("order_type", 6).eq("phone", phone).isNull("user_id"));
        if(CollectionUtils.isEmpty(orders)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"暂无订单可以绑定");
        }

        List<Long> orderIds = new ArrayList<>();
        orders.stream().forEach(order -> {
            orderIds.add(order.getId());
            logger.info("线上线下订单融合，当前订单ID=>{} ,{}用户ID =>{}，绑定用户Id=>{}",order.getId(),order.getUserId(),user.getId());
        });

        //更新订单表数据的userId
        wzdOrderAppletsMapper.batchUpdateUserId(orderIds,user.getId());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String,Object>> changeBbdTicket(BbdOrderChangeParam bbdOrderChangeParam) {
        List<Map<String,Object>> resultMap = new ArrayList<>();

        //获取需要改签的票
        List<BbdOrderChangeParam.ChangeData> changeData = bbdOrderChangeParam.getChangeData();
        changeData.stream().forEach(ticketChangeInfo -> {

            Map<String,Object> map = new HashMap<>();
            //改签票
         Long ticketId =  changeTicket(ticketChangeInfo);
         map.put("ticketId",ticketId);
         map.put("ticketIdBbd",ticketChangeInfo.getChangeTicketSerialBbd());
         resultMap.add(map);
        });
        return resultMap;
    }
    /**
     * 单张票改签
     * @param info  票改签信息
     * @return 添加后的order_ticket 的id
     */
    private Long changeTicket(BbdOrderChangeParam.ChangeData info) {
        //查询订单子表
        String changeTicketSerialBbd = info.getChangeTicketSerialBbd();
        OrderTicket orderTicket = ordersTicketMapper.selectOne(new QueryWrapper<OrderTicket>().eq("ticket_id_bbd", changeTicketSerialBbd).eq("order_serial_bbd", info.getChangeOrderSerialBbd()));
        if(orderTicket == null){

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,String.format("未找到票,票Id=%s,订单Id=%s", changeTicketSerialBbd,info.getChangeOrderSerialBbd()));
        }
        OrderTicket updateOrderTicket = new OrderTicket();
        updateOrderTicket.setId(orderTicket.getId());
        //410 票的改签状态
        updateOrderTicket.setTicketStateBbd(410);
        updateOrderTicket.setChangeInfo(JSONObject.toJSONString(info));
        logger.info("当前票Id=>{},票状态=>{}，改签Id=>{}",changeTicketSerialBbd,orderTicket.getTicketStateBbd(),info.getTicketSerialBbdId());

        //更新票状态和改签信息
        ordersTicketMapper.updateChangeTicketInfoById(updateOrderTicket);
        //添加新的票
        OrderTicket ticket = OrderTicket.builder()
                .ticketCode(orderTicket.getTicketCode())
                .createTime(orderTicket.getCreateTime())
                .orderId(orderTicket.getOrderId())
                .goodsId(orderTicket.getGoodsId())
                .goodsName(orderTicket.getGoodsName())
                .goodsExtendId(orderTicket.getGoodsExtendId())
                .singlePrice(orderTicket.getSinglePrice())
                .couponPrice(orderTicket.getSinglePrice())
                .projectId(orderTicket.getProjectId())
                .projectName(orderTicket.getProjectName())
                .ticketStateBbd(info.getTicketStateBbd())
                //改签后的佰邦达票号
                .ticketSerialBbd(info.getTicketSerialBbd())
                //改签后的佰邦达票二维码
                .ticketQrCodeBbd(info.getTicketQrCodeBbd())
                .orderSerialBbd(info.getChangeOrderSerialBbd())
                //百邦达票Id
                .ticketIdBbd(info.getTicketSerialBbdId())
                .isTicketPrinted(info.getIsTicketPrinted())
                .id(null)
                .build();

        ordersTicketMapper.insert(ticket);


        return ticket.getId();
    }
}
