package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.redlock.AquiredLockWorker;
import cn.enn.wise.platform.mall.util.redlock.RedisLocker;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * GroupOrder服务实现类
 *
 * @author jiabaiye
 * @since 2019-09-12
 */
@Service
public class GroupOrderServiceImpl extends ServiceImpl<GroupOrderMapper,GroupOrderBo> implements GroupOrderService {

    private Logger logger = LoggerFactory.getLogger(GroupOrderServiceImpl.class);

    @Autowired
    private WzdOrderAppletsService wzdOrderAppletsService;

    @Resource
    private GroupOrderMapper groupOrderMapper;

    @Resource
    private GroupOrderItemMapper groupOrderItemMapper;

    @Resource
    private OrderDao orderDao;

    @Resource
    private GroupPromotionMapper groupPromotionMapper;

    @Autowired
    private RedisLocker redisLocker;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Resource
    private GroupRuleMapper groupRuleMapper;

    @Resource
    private GroupPromotionGoodsMapper groupPromotionGoodsMapper;

    @Value("${companyId}")
    private String companyId;

    @Resource
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @Resource
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private OrderAppletsMapper orderAppletsMapper;


    /**
     * 验证是否可以参加拼团(groupOrderId=0 创建团的判断，groupOrderId ！=0参加拼团判断)
     * @param groupOrderId
     * @return 验证后的编码和信息
     */
    @Override
    public ResponseEntity<Object> isIntoGroupOrder(Long groupOrderId,Long groupPromotionId,Long userId) {

        logger.debug("debug验证是否可以拼团");
        //groupOrderId=0创建团的判断
        if(groupOrderId==0){
            GroupPromotionBo groupPromotionBo = groupPromotionMapper.getGroupPromotionById(groupPromotionId);
            //有这个活动并且是活动中的团
            if(groupPromotionBo!=null&&groupPromotionBo.getStatus()==GeneConstant.BYTE_2){
                //拼团的人是否已经参与拼团并且还处于未成团状态
                List<GroupOrderItemBo> groupOrderItemBo = groupOrderItemMapper.getGroupOrderItemByGroupPromotionIdAndUserId(groupPromotionId,userId);
                if(groupOrderItemBo!=null&&groupOrderItemBo.size()>0){
                    return new ResponseEntity(GeneConstant.INT_2, "您有未成团的拼团活动，不能再次开团");
                }else{
                    return new ResponseEntity(GeneConstant.INT_1, "可以发起拼团", groupPromotionBo);
                }

            }else{
                return new ResponseEntity(GeneConstant.INT_2, "拼团活动已经结束");
            }
        }else{
            //判断活动是否已经结束了
            GroupPromotionBo groupPromotionBo = groupPromotionMapper.getGroupPromotionById(groupPromotionId);
            //有这个活动并且是活动中的团
            if(groupPromotionBo==null){
                return new ResponseEntity(GeneConstant.INT_5, "拼团活动已经结束");
            }else if(groupPromotionBo.getStatus()!=GeneConstant.BYTE_2){
                return new ResponseEntity(GeneConstant.INT_5, "拼团活动已经结束");
            }
            //参加拼团判断
            GroupOrderVo groupOrderBo = groupOrderMapper.getGroupOrderByIdAndAvailableTime(groupOrderId);
            //有这个活动是待成团状态并且是还没到结束时间
            if(groupOrderBo!=null){
                List<GroupOrderItemBo> groupOrderItemBo = groupOrderItemMapper.getGroupOrderItemByGroupOrderIdAndUserId(groupOrderId,userId);
                if(groupOrderItemBo!=null&&groupOrderItemBo.size()>0){
                    return new ResponseEntity(GeneConstant.INT_2, "您已经参与这个拼团活动了");
                }else{
                    Long insertNum =groupOrderItemMapper.getCountInsertOrderByGoodsAndUserId(groupOrderBo.getGroupPromotionId(),userId,groupOrderBo.getGoodsId());
                    Long limitNum = groupOrderMapper.getGroupLimitById(groupOrderBo.getGroupPromotionId());
                    if(limitNum>insertNum){
                        return new ResponseEntity(GeneConstant.INT_1, "可以参与拼团", groupOrderBo);
                    }
                    return new ResponseEntity(GeneConstant.INT_4, "您参团的次数"+limitNum+"次已经用完！");
                }
            }else{
                return new ResponseEntity(GeneConstant.INT_3, "手慢了，此团已满，请重新选择");
            }
        }
    }


    /**
     * 查询是否已经拼团成功
     * @param groupOrderId
     * @return 拼团状态编码和拼团信息
     */
    @Override
    public ResponseEntity<Object> isGroupOrderSuccess(Long groupOrderId) {

        logger.debug("debug是否拼团成功");
        //groupOrderId=0团id为空
        if(groupOrderId==null || groupOrderId==0){
            return new ResponseEntity(GeneConstant.INT_2, "团id不能为空");
        }else{
            //参加拼团判断
            GroupOrderBo groupOrderBo = groupOrderMapper.getGroupOrderById(groupOrderId);
            //有这个活动是待成团状态并且是还没到结束时间
            if(groupOrderBo!=null){
                if(groupOrderBo.getStatus()==GeneConstant.BYTE_3){
                    return new ResponseEntity(GeneConstant.INT_1, "拼团成功", groupOrderBo);
                }else if(groupOrderBo.getStatus()==GeneConstant.BYTE_2){
                    return new ResponseEntity(GeneConstant.INT_2, "正在拼团中",groupOrderBo);
                }else if(groupOrderBo.getStatus()==GeneConstant.BYTE_4){
                    return new ResponseEntity(GeneConstant.INT_2, "拼团失败",groupOrderBo);
                }else{
                    return new ResponseEntity(GeneConstant.INT_2, "正在拼团中",groupOrderBo);
                }
            }else{
                return new ResponseEntity(GeneConstant.INT_2, "团不存在",groupOrderBo);
            }
        }
    }
    @Override
    public ResponseEntity getGroupOrderList(Long goodsId,Long companyId) {

        logger.debug("debug是否拼团成功");
        //查询拼团表根据商品id
        List<GroupOrderVo> groupOrderVoList = groupOrderMapper.getGroupOrderForList(goodsId);
        for(GroupOrderVo groupOrderVo :groupOrderVoList){
            List<Map<String,Object>> list = remoteServiceUtil.getMemberInfo(groupOrderVo.getUserId().toString(),companyId);
            if(list!=null&&list.size()>0){
                Map<String,Object> map = list.get(0);
                groupOrderVo.setWxImg(map.get("headImg").toString());
                groupOrderVo.setWxName(map.get("NAME").toString());
            }
        }
        return new ResponseEntity(GeneConstant.INT_1, "查询成功",groupOrderVoList);
    }

    @Override
    public ResponseEntity getGroupOrderListDetail(Long goodsId) {

        logger.debug("debug是否拼团成功");
        //查询拼团表根据商品id
        List<GroupOrderBo> groupOrderBoList = groupOrderMapper.getGroupOrderByGoodsId(goodsId);
        List<GroupOrderItemBo> groupOrderItemBoList = groupOrderItemMapper.getGroupOrderItemByGoodsId(goodsId);
        for(GroupOrderBo groupOrderBo :groupOrderBoList){
            List<GroupOrderItemBo> goiList = new ArrayList<>();
            for(GroupOrderItemBo groupOrderItemBo:groupOrderItemBoList){
                if(groupOrderBo.getId().equals(groupOrderItemBo.getGroupOrderId())){
                    goiList.add(groupOrderItemBo);
                }
            }
            groupOrderBo.setItemList(goiList);
        }
        return new ResponseEntity(GeneConstant.INT_1, "查询成功",groupOrderBoList);
    }

    @Override
    public ResponseEntity insertGroupOrder(Long groupId,  Long userId,String orderCode)  throws Exception {

        logger.debug("debug参与拼团");
        //参加拼团判断
        GroupOrderVo groupOrderBo = groupOrderMapper.getGroupOrderByIdAndAvailableTime(groupId);
        //有这个活动是待成团状态并且是还没到结束时间
        if(groupOrderBo!=null){
            List<GroupOrderItemBo> groupOrderItemBo = groupOrderItemMapper.getGroupOrderItemByGroupOrderIdAndUserId(groupId,userId);
            if(groupOrderItemBo!=null&&groupOrderItemBo.size()>0){
                return new ResponseEntity(GeneConstant.INT_2, "您已经参与这个拼团活动了");
            }else{
                Long insertNum =groupOrderItemMapper.getCountInsertOrderByGoodsAndUserId(groupOrderBo.getGroupPromotionId(),userId,groupOrderBo.getGoodsId());
                Long limitNum = groupOrderMapper.getGroupLimitById(groupOrderBo.getGroupPromotionId());
                if(limitNum<=insertNum){
                    return new ResponseEntity(GeneConstant.INT_4, "您参团的次数"+limitNum+"次已经用完！");
                }
            }
        }

        Long ordId = 0L;
        //TODO 使用小程序mapper
        List<Order> orderList = orderDao.findOrderInfo(orderCode.split(","));

        if(orderList!=null&&orderList.size()>0){
            Order order = orderList.get(0);
            ordId = order.getId();
        }else{
            return new ResponseEntity(GeneConstant.INT_2, "订单不存在！");
        }

        final Long orderId = ordId;
        Integer i = redisLocker.lock("GroupOrderId:"+groupId, new AquiredLockWorker<Integer>() {
            //锁
            @Override
            public Integer invokeAfterLockAquire() throws Exception {
                //获取团信息，判断是否可以拼团
                GroupOrderBo groupOrderBo = groupOrderMapper.getGroupOrderById(groupId);
                //TODO 返回值注释
                //有这个活动是待成团状态并且是还没到结束时间
                if(groupOrderBo!=null&&groupOrderBo.getStatus()!=GeneConstant.BYTE_2){
                    //
                    return 3;
                }
                //插入拼团信息
                GroupOrderItem groupOrderItem = new GroupOrderItem();
                groupOrderItem.setGroupOrderId(groupId);
                groupOrderItem.setGroupOrderCode(groupOrderBo.getGroupOrderCode());
                groupOrderItem.setUserId(userId);
                groupOrderItem.setOrderId(orderId);

                groupOrderItem.setIsHeader(GeneConstant.BYTE_2);
                groupOrderItem.setStatus(GeneConstant.BYTE_1);
                groupOrderItem.setGoodsCount(1);
                groupOrderItem.setGroupOrderType(GeneConstant.BYTE_2);
                groupOrderItem.setJoinTime(new Timestamp(System.currentTimeMillis()));
                groupOrderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
                groupOrderItem.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                groupOrderItemMapper.insert(groupOrderItem);
                //判断是否已经成团
                List<GroupOrderItemBo> itemList = groupOrderItemMapper.getGroupOrderItemByGroupOrderId(groupId);
                //item中参团的人数已经达到成团人数，更新团状态为拼团成功
                if(itemList!=null&&itemList.size()==groupOrderBo.getGroupSize()){
                    groupOrderBo.setStatus(GeneConstant.BYTE_3);
                    groupOrderBo.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
                    groupOrderMapper.updateById(groupOrderBo);
                    return 1;
                }
                return 2;
            }
            @Override
            public Integer falseResult(){
                return 3;
            }
        });
        HashMap<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("groupOrderId",groupId);
        if(i==GeneConstant.INT_3){
            wzdOrderAppletsMapper.updateGroupOrderId(orderId,null);
            return new ResponseEntity(GeneConstant.INT_3, "手慢了，此团已满，自动为您创建新团");
        }else if(i==GeneConstant.INT_1){
            try{
                List<GroupOrderItemBo> itemList = groupOrderItemMapper.getGroupOrderItemByGroupOrderId(groupId);
                for(GroupOrderItem groupOrderItem:itemList){
                    Orders order= orderAppletsMapper.getOrderById(groupOrderItem.getOrderId());
                    wzdOrderAppletsService.synchronizeOrderToBBD(order);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                return new ResponseEntity(GeneConstant.INT_1, "参与拼团成功，并且成团",resultMap);
            }
        }
        return new ResponseEntity(GeneConstant.INT_1, "参与拼团成功",resultMap);
    }


    @Override
    public GroupOrderDetailVo getGroupOrderById(Long id) {

        GroupOrderDetailVo result = new GroupOrderDetailVo();
        GroupOrderBo order =this.getById(id);
        if(order ==null){
            return new GroupOrderDetailVo();
        }

        result.setGroupInfo(order);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("group_order_id",order.getId());
        List<GroupOrderItemBo> itemBoList =groupOrderItemMapper.selectList(wrapper);

        List<Long> idList= new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        for(GroupOrderItemBo item:itemBoList){

            OrderBean bean = new OrderBean();
            bean.setId(item.getOrderId());
            orderList.addAll(orderDao.findAllOrderList(bean));
        }

        result.setOrderList(orderList);

        return result;
    }

    @Override
    public ResPageInfoVO<List<GroupOrderVo>> listByPage(ReqPageInfoQry<GroupOrderParam> param) {

        //返回值
        ResPageInfoVO<List<GroupOrderVo>> resPageInfoVO = new ResPageInfoVO<>();
        resPageInfoVO.setPageNum(param.getPageNum());
        resPageInfoVO.setPageSize(param.getPageSize());

        GroupOrderParam paramVo=param.getReqObj();
        Long offset =0L;
        if(param.getPageNum()>=1){
            offset=(param.getPageNum()-1)*param.getPageSize();
        }
        paramVo.setOffset(offset);
        paramVo.setLimit(param.getPageSize());

        // 数据列表
        List<GroupOrderVo> list=groupOrderMapper.getGroupOrderListByPage(paramVo);
        // 总数
        Map<String,Object> totalMap= groupOrderMapper.getGroupOrderListCounts(paramVo);

        Long total = Long.parseLong(totalMap==null?"0":totalMap.get("total").toString());
        resPageInfoVO.setRecords(list);
        resPageInfoVO.setTotal(total);
        return resPageInfoVO;
    }
    /**
     * 自动成团定时任务
     * 每一分钟轮询一次
     * 两个人或者两个人以上到时间的进行自动成团处理
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void autoCreateGroupOrder(){
        //处理参团的时候订单和团单明细不一致问题，从订单信息中向团单信息中同步
        List<Order> orderList = groupOrderItemMapper.getOrderNotInItem();
        for(Order order : orderList){
            final Long orderId = order.getId();
            try{
                Integer i = redisLocker.lock("GroupOrderId:"+order.getGroupOrderId(), new AquiredLockWorker<Integer>() {
                    //锁
                    @Override
                    public Integer invokeAfterLockAquire() throws Exception {
                        //获取团信息，判断是否成团
                        GroupOrderBo groupOrderBo = groupOrderMapper.getGroupOrderById(order.getGroupOrderId());
                        //插入拼团信息
                        GroupOrderItem groupOrderItem = new GroupOrderItem();
                        groupOrderItem.setGroupOrderId(order.getGroupOrderId());
                        groupOrderItem.setGroupOrderCode(groupOrderBo.getGroupOrderCode());
                        groupOrderItem.setUserId(order.getUserId());
                        groupOrderItem.setOrderId(orderId);
                        groupOrderItem.setIsHeader(GeneConstant.BYTE_2);
                        groupOrderItem.setStatus(GeneConstant.BYTE_1);
                        groupOrderItem.setGoodsCount(1);
                        groupOrderItem.setGroupOrderType(GeneConstant.BYTE_2);
                        groupOrderItem.setJoinTime(new Timestamp(System.currentTimeMillis()));
                        groupOrderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        groupOrderItem.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                        groupOrderItemMapper.insert(groupOrderItem);
                        //判断是否已经成团
                        List<GroupOrderItemBo> itemList = groupOrderItemMapper.getGroupOrderItemByGroupOrderId(order.getGroupOrderId());
                        //item中参团的人数已经达到成团人数，更新团状态为拼团成功

                        if(itemList!=null&&itemList.size()==groupOrderBo.getGroupSize()){
                            groupOrderBo.setStatus(GeneConstant.BYTE_3);
                            groupOrderBo.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
                            groupOrderMapper.updateById(groupOrderBo);
                            return 1;
                        }
                        return 2;
                    }
                    @Override
                    public Integer falseResult(){
                        return 3;
                    }
                });
            }catch (Exception e){
                logger.error("error:",e);
            }

        }

        //查询到结束时间的未成团的团信息
        List<GroupOrderBo> groupOrderBos=groupOrderMapper.getGroupOrderListByTime();

        logger.error("402:"+JSON.toJSONString(groupOrderBos));
        for(GroupOrderBo groupOrder:groupOrderBos){
            //获取参团详细信息
            List<GroupOrderItemBo> itemList = groupOrderItemMapper.getGroupOrderItemByGroupOrderId(groupOrder.getId());
            logger.error("406:"+JSON.toJSONString(itemList));
            if(itemList==null){
                logger.error("408:"+JSON.toJSONString(itemList));
                //设置状态为拼团失败
                groupOrder.setStatus(new Byte("4"));
            }else{
                //根据团信息中的活动id查询规则中是否可以自动成团以及自动成团人数
                GroupRuleBo groupRule =  groupRuleMapper.getGroupRuleByGroupPromotionId(groupOrder.getGroupPromotionId());
                logger.error("414:"+JSON.toJSONString(groupRule));
                //判断可否成团
                if(groupRule!=null&&groupRule.getIsAutoCreateGroup()==1&&itemList.size()>=groupRule.getAutoCreateGroupLimit()){
                    logger.error("414:"+JSON.toJSONString(groupRule));
                    //可以成团
                    //设置团状态为拼团成功

                    groupOrder.setStatus(new Byte("3"));
                }else{
                    logger.error("422:"+ JSON.toJSONString(itemList));
                    //人数不够成团失败或者不允许自动成团
                    //设置团状态为拼团失败
                    groupOrder.setStatus(new Byte("4"));
                    for(GroupOrderItemBo groupOrderItemBo :itemList){
                        orderDao.updateOrderStateById(groupOrderItemBo.getOrderId(),5);
                    }
                }
            }
            groupOrder.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            //更新团状态
            logger.error("433:"+JSON.toJSONString(itemList));
            groupOrderMapper.updateById(groupOrder);
            if(groupOrder.getStatus()==GeneConstant.BYTE_3){
                try{
                    for(GroupOrderItem groupOrderItem:itemList){
                        Orders order= orderAppletsMapper.getOrderById(groupOrderItem.getOrderId());
                        wzdOrderAppletsService.synchronizeOrderToBBD(order);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            logger.error("435:"+JSON.toJSONString(itemList));
        }
    }



    @Override
    public GroupOrderInfoVo groupOrderDetail(Long id) {

        GroupOrderInfoVo orderInfoVo = groupOrderMapper.getAppletsGroupOrderInfoVo(id);

        //  获取用户信息
        List<Map<String, Object>> memberInfo1 = remoteServiceUtil.getMemberInfo(orderInfoVo.getUserIds(), Long.parseLong(companyId));
        //循环匹配用户id，确保头像顺序
        List<Map<String, Object>> memberInfo = new ArrayList<>();
        String strIds[] = orderInfoVo.getUserIds().split(",");
        for(int i = 0;i<strIds.length;i++){
            for(int l = 0;l<memberInfo1.size();l++){
                if(strIds[i].equals(memberInfo1.get(l).get("id").toString())){
                    memberInfo.add(memberInfo1.get(l));
                    break;
                }
            }
        }
        orderInfoVo.setHeadUrls(memberInfo);
        if(orderInfoVo.getStatus() == GeneConstant.BYTE_2 && orderInfoVo.getRemainingSeconds() <= 0){
            orderInfoVo.setStatus(GeneConstant.BYTE_4);
        }

        ProjectInfoVo projectInfoById = remoteServiceUtil.getProjectInfoById(orderInfoVo.getProjectId());
        if(projectInfoById != null){
            orderInfoVo.setHeadImg(projectInfoById.getHeadImage());
            orderInfoVo.setProjectPresent(projectInfoById.getDescription());

        }

        return orderInfoVo;
    }

    @Override
    public Map<String, String> getMemberIfInTheGroup(Long userId,Long goodsId,Long promotionId) {

        //查询当前有效活动信息
        GroupPromotionBo groupPromotionBo = groupPromotionMapper.selectOne(new QueryWrapper<GroupPromotionBo>().eq("status", 2).eq("id",promotionId));

        if(groupPromotionBo == null){

            throw new BusinessException(GeneConstant.INT_4,"该拼团活动已失效");
        }

        //查询商品是否是参与拼团的商品
        GroupPromotionGoodsBo promotionGoodsBo = groupPromotionGoodsMapper.selectOne(new QueryWrapper<GroupPromotionGoodsBo>().eq("goods_id", goodsId).eq("group_promotion_id", promotionId));
        if(promotionGoodsBo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该商品暂时没有参加拼团活动");
        }
        Long groupPromotionBoId = groupPromotionBo.getId();

        //查询用户是否存在拼团中
        //Map<String, String> memberIfInTheGroup = groupOrderMapper.getMemberIfInTheGroup(userId,groupPromotionBoId,goodsId);
        Map<String, String> memberIfInTheGroup =new HashMap<String, String>();
        //当用户不在团内时
        //if(MapUtils.isEmpty(memberIfInTheGroup)){

        //查询是否超过限制次数
        Map<String, Object> buyAmount = groupOrderMapper.getBuyAmount(promotionId, userId);
        int groupLimit = (int) buyAmount.get("groupLimit");
        int amount = Integer.parseInt(buyAmount.get("amount").toString());
        if(groupLimit <= amount){
            throw new BusinessException(GeneConstant.INT_5,"该商品购买限制"+groupLimit+"次");
        }

        memberIfInTheGroup = new HashMap<>(4);
        memberIfInTheGroup.put(GeneConstant.IS_CREATE_GROUP_ORDER,"true");
        memberIfInTheGroup.put("groupPromotionId",String.valueOf(groupPromotionBoId));
        // }else {
        //该用户存在在一个团内
        //     memberIfInTheGroup.put(GeneConstant.IS_CREATE_GROUP_ORDER,"false");
        // }

        return memberIfInTheGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderQueryVo createGroupOrder(Long userId,String orderCode,Long promotionId) throws Exception{

        Orders orders = new Orders();
        orders.setOrderCode(orderCode);

        //根据微信订单号查询系统订单
        Orders info = wzdOrderAppletsMapper.getOrderInfoByOrderCode(orders);

        if(info.getPayStatus() != GeneConstant.INT_2){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单未支付");
        }

        Long orderId = info.getId();
        Long extendId = info.getGoodsId();
        //根据skuId查询商品Id
        GoodsExtend goodsExtend = goodsExtendMapper.selectById(extendId);
        // 验证用户是否可以创建团
        Map<String, String> memberIfInTheGroup = getMemberIfInTheGroup(userId,goodsExtend.getGoodsId(),promotionId);

        //是否能创建拼团订单
        String isCreateGroupOrder = memberIfInTheGroup.get(GeneConstant.IS_CREATE_GROUP_ORDER);
        if(GeneConstant.TRUE.equals(isCreateGroupOrder)){
            Long groupPromotionId = Long.valueOf(memberIfInTheGroup.get("groupPromotionId"));

            //查询商品和拼团活动数据
            GoodsAndGroupPromotionInfoVo groupPromotionInfoVo = groupOrderMapper.getGoodsAndGroupPromotionInfoByExtendId(extendId, groupPromotionId, 1);
            //成团有效期
            int groupValidHours = groupPromotionInfoVo.getGroupValidHours();
            //成团人数
            Integer groupSize = groupPromotionInfoVo.getGroupSize();
            //计算拼团失效时间
            Timestamp createTime = Timestamp.valueOf(LocalDateTime.now());
            Long afterHourTimesTamp = DateUtil.getAfterHourTimesTamp(createTime.getTime(), groupValidHours);

            GroupOrderBo build = GroupOrderBo
                    .builder()
                    .groupPromotionId(groupPromotionId)
                    .goodsNum(groupPromotionInfoVo.getGoodsNum())
                    .goodsId(groupPromotionInfoVo.getGoodsId())
                    .createTime(createTime)
                    .groupSize(groupSize)
                    .status(GeneConstant.BYTE_2)
                    .availableTime(new Timestamp(afterHourTimesTamp))
                    .userId(userId)
                    .build();

            //创建拼团订单
            int effect1 = groupOrderMapper.saveGroupOrder(build);
            if(effect1 > 0){

                GroupOrderBo groupOrderBo = groupOrderMapper.selectById(build.getId());

                //创建拼团明细
                GroupOrderItemBo orderItemBo = GroupOrderItemBo
                        .builder()
                        .groupOrderId(build.getId())
                        .groupOrderCode(groupOrderBo.getGroupOrderCode())
                        .userId(userId)
                        .joinTime(createTime)
                        .orderId(orderId)
                        .isHeader(GeneConstant.BYTE_1)
                        .status(GeneConstant.BYTE_1)
                        .goodsExtendId(extendId)
                        .goodsCount(info.getAmount().intValue())
                        .createTime(createTime)
                        .groupOrderType(GeneConstant.BYTE_2)
                        .build();

                groupOrderItemMapper.insert(orderItemBo);

                wzdOrderAppletsMapper.updateGroupOrderId(orderId,build.getId());

                return OrderQueryVo
                        .builder()
                        .isSuccess(1)
                        .groupOrderStatus(2)
                        .groupSize(groupSize)
                        .remainingNumber(groupSize-1)
                        .remainingSeconds(groupValidHours * 60 * 60)
                        .groupOrderId(build.getId())
                        .build();
            }

        }

        return OrderQueryVo.builder().isSuccess(2).build();
    }

    @Override
    public void updateItemStatusById(String orderNum){
        Orders order = wzdOrderAppletsMapper.getOrderInfoByBatCode(orderNum);
        Integer integer = 3;
        //拼团订单 orderType=3 更新拼团item
        if(integer.equals(order.getOrderType())){
            groupOrderItemMapper.updateStatusByOrderIdAndUserId(order.getId(),order.getUserId());
            //拼团或者参团退单，先修改item为退款，再查询groupOrder下的item是否都退单了，如果都退单了，则修改团状态为拼团失败
            Map  map = groupOrderItemMapper.getGroupOrderSizeByOrderIdAndUserId(order.getId(),order.getUserId());
            Long size = 0L;
            if(size.equals(Long.parseLong(map.get("orderSize").toString()))){
                groupOrderMapper.updateStatusById(map.get("id").toString(),4);
            }
        }
    }
}
