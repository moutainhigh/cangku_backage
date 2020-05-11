package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.goods.dto.response.GoodsDetailDTO;
import cn.enn.wise.ssop.api.goods.dto.response.GoodsExtendGroupDTO;
import cn.enn.wise.ssop.api.goods.facade.GoodsExtendFacade;
import cn.enn.wise.ssop.api.goods.facade.GoodsFacade;
import cn.enn.wise.ssop.api.member.dto.response.MemberDTO;
import cn.enn.wise.ssop.api.member.facade.MemberFacade;
import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.api.promotions.facade.DistributorAppFacade;
import cn.enn.wise.ssop.service.promotions.mapper.*;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.ssop.service.promotions.util.AquiredLockWorker;
import cn.enn.wise.ssop.service.promotions.util.DateUtil;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.ssop.service.promotions.util.RedisLocker;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * GroupOrder服务实现类
 *
 * @author jiabaiye
 */
@Service("groupOrderService")
public class GroupOrderService extends ServiceImpl<GroupOrderMapper, GroupOrder>  {

    private Logger logger = LoggerFactory.getLogger(GroupOrderService.class);

    @Autowired
    private GroupOrderMapper groupOrderMapper;

    @Autowired
    private ActivityGroupRuleService activityGroupRuleService;

    @Autowired
    private GroupOrderItemMapper groupOrderItemMapper;

    @Autowired
    private ActivityBaseMapper activityBaseMapper;

    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private RedisLocker redisLocker;

    @Autowired
    private GoodsExtendFacade goodsExtendFacade;

    @Autowired
    private GoodsFacade goodsFacade;

    @Autowired
    private MemberFacade memberFacade;

    @Autowired
    private DistributorAppFacade distributorAppFacade;
    /**
     * 拼团列表
     * @param param
     * @return
     */
    public R<QueryData<GroupOrderDTO>> listByPage(GroupOrderListParam param) {

        //返回值
        QueryData<GroupOrderDTO> resPageInfoVO = new QueryData<>();
        resPageInfoVO.setPageNo(param.getPageNo());
        resPageInfoVO.setPageSize(param.getPageSize());
        GroupOrderListParam paramVo=param;
        Integer offset =0;
        if(param.getPageNo()>=1){
            offset=(param.getPageNo()-1)*param.getPageSize();
        }
        paramVo.setOffset(offset);
        paramVo.setLimit(param.getPageSize());
        // 数据列表
        List<GroupOrderDTO> list=groupOrderMapper.getGroupOrderListByPage(paramVo);
        // 总数
        Map<String,Object> totalMap= groupOrderMapper.getGroupOrderListCounts(paramVo);

        Long total = Long.parseLong(totalMap==null?"0":totalMap.get("total").toString());
        resPageInfoVO.setRecords(list);
        resPageInfoVO.setTotalCount(total);
        return new R<>(resPageInfoVO);
    }

    //拼团详情
    public GroupOrderDetailDTO getGroupOrderById(Long id) {

        GroupOrderDetailDTO result = new GroupOrderDetailDTO();
        GroupOrder order =this.getById(id);
        if(order ==null){
            return new GroupOrderDetailDTO();
        }
        GroupOrderDTO groupOrderDTO = new GroupOrderDTO();
        BeanUtils.copyProperties(order,groupOrderDTO,getNullPropertyNames(order));
        result.setGroupInfo(groupOrderDTO);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("group_order_id",order.getId());
        //List<GroupOrderItemBo> itemBoList =groupOrderItemMapper.selectList(wrapper);

        List<Long> idList= new ArrayList<>();
        List<OrderDTO> orderList = new ArrayList<>();
//        for(GroupOrderItemBo item:itemBoList){
//
//            OrderBean bean = new OrderBean();
//            bean.setId(item.getOrderId());
//            orderList.addAll(orderDao.findAllOrderList(bean));
//        }
        result.setOrderList(orderList);

        return result;
    }

    public OrderQueryDTO createGroupOrder(Long userId, GroupOrderCreateParam groupOrderCreateParam) {
        OrdersParam info = groupOrderCreateParam.getOrdersParam();
        OrderGoodsParam orderGoodsParam = groupOrderCreateParam.getOrderGoodsParam();
        OrderSaleParam orderSaleParam = groupOrderCreateParam.getOrderSaleParam();
        Long orderId = info.getId();
        Long extendId = orderGoodsParam.getGoodsId();
        Long activityBaseId = orderSaleParam.getSaleId();
        Long activityGroupRuleId = orderSaleParam.getRuleId();
        //查询商品和拼团活动数据
        ActivityGroupRuleDetailDTO activityGroupRuleDetailDTO =  activityGroupRuleService.getActivityGroupRuleByBaseId(activityBaseId);
        //成团有效期
        int groupValidHours = activityGroupRuleDetailDTO.getActivityGroupRuleDTO().getGroupValidHours();
        //成团人数
        Integer groupSize = activityGroupRuleDetailDTO.getActivityGroupRuleDTO().getGroupSize();
        //计算拼团失效时间
        Timestamp createTime = Timestamp.valueOf(LocalDateTime.now());
        Long afterHourTimesTamp = DateUtil.getAfterHourTimesTamp(createTime.getTime(), groupValidHours);
        GroupOrder build = GroupOrder
                .builder()
                .groupActivityId(activityGroupRuleDetailDTO.getActivityGroupRuleDTO().getActivityRuleId())
                .goodsId(extendId)
                .groupSize(groupSize)
                .status(GeneConstant.BYTE_2)
                .availableTime(new Timestamp(afterHourTimesTamp))
                .userId(userId)
                .build();
        //创建拼团订单
        int effect1 = groupOrderMapper.saveGroupOrder(build);
        if(effect1 > 0){
            GroupOrder groupOrderBo = groupOrderMapper.selectById(build.getId());
            //创建拼团明细
            GroupOrderItem orderItem = GroupOrderItem
                    .builder()
                    .groupOrderId(build.getId())
                    .groupOrderCode(groupOrderBo.getGroupOrderCode())
                    .userId(userId)
                    .joinTime(createTime)
                    .orderId(orderId)
                    .isHeader(GeneConstant.BYTE_1)
                    .status(GeneConstant.BYTE_1)
                    .goodsExtendId(extendId)
                    .goodsCount(orderGoodsParam.getAmount())
                    .groupOrderType(GeneConstant.BYTE_2)
                    .build();
            groupOrderItemMapper.insert(orderItem);
           // wzdOrderAppletsMapper.updateGroupOrderId(orderId,build.getId());
            return OrderQueryDTO
                    .builder()
                    .isSuccess(1)
                    .groupOrderStatus(2)
                    .groupSize(groupSize)
                    .remainingNumber(groupSize-1)
                    .remainingSeconds(groupValidHours * 60 * 60)
                    .groupOrderId(build.getId())
                    .build();
        }
        return OrderQueryDTO.builder().isSuccess(2).build();
    }


    public Map<String, String> getMemberIfInTheGroup(Long userId,GroupValidateParam groupValidateParam) {
        //查询当前有效活动信息
        ActivityBase groupPromotionBo = activityBaseMapper.selectOne(new QueryWrapper<ActivityBase>().eq("state", 2).eq("id",groupValidateParam.getActivityBaseId()));
        QueryWrapper<ActivityRule> activityRuleQueryWrapper = new QueryWrapper<>();
        activityRuleQueryWrapper.eq("activity_base_id",groupValidateParam.getActivityBaseId());
        activityRuleQueryWrapper.eq("isdelete",1);
        activityRuleQueryWrapper.last(" limit 1");
        ActivityRule rule = activityRuleMapper.selectOne(activityRuleQueryWrapper);
        if(groupPromotionBo == null){
            //throw new BusinessException(GeneConstant.INT_4,"该拼团活动已失效");
            ACTIVITY_GROUP_INVALID.assertFail();
        }
        if(rule==null){
            //未找到活动规则
            ACTIVITY_RULE_IS_NOT_EXIST.assertFail(groupValidateParam.getActivityBaseId());
        }
        //查询商品是否是参与拼团的商品
        ActivityGoods promotionGoodsBo = activityGoodsMapper.selectOne(new QueryWrapper<ActivityGoods>().eq("goods_id", groupValidateParam.getGoodsId()).eq("activity_rule_id", rule.getId()));
        if(promotionGoodsBo == null){
            //throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该商品暂时没有参加拼团活动");
            GOODS_NOT_IN_ACTIVITY_GROUP.assertFail();
        }
        Long groupPromotionBoId = groupPromotionBo.getId();

        Map<String, String> memberIfInTheGroup =new HashMap<String, String>();

        //查询是否超过限制次数
        Map<String, Object> buyAmount = groupOrderMapper.getBuyAmount(groupValidateParam.getActivityBaseId(), userId);
        int groupLimit = (int) buyAmount.get("groupLimit");
        int amount = Integer.parseInt(buyAmount.get("amount").toString());
        if(groupLimit <= amount){
            //throw new BusinessException(GeneConstant.INT_5,"该商品购买限制"+groupLimit+"次");
            GOODS_LIMIT.assertFail(groupLimit);
        }
        memberIfInTheGroup = new HashMap<>(4);
        memberIfInTheGroup.put(GeneConstant.IS_CREATE_GROUP_ORDER,"true");
        memberIfInTheGroup.put("groupPromotionId",String.valueOf(groupPromotionBoId));

        return memberIfInTheGroup;
    }

    public List<GroupOrderDTO> getGroupOrderList(Long goodsId,Long companyId) {

        logger.debug("debug是否拼团成功");
        //查询拼团表根据商品id
        List<GroupOrderDTO> groupOrderVoList = groupOrderMapper.getGroupOrderForList(goodsId);
        for(GroupOrderDTO groupOrderVo :groupOrderVoList){
//            List<Map<String,Object>> list = remoteServiceUtil.getMemberInfo(groupOrderVo.getUserId().toString(),companyId);
//            if(list!=null&&list.size()>0){
//                Map<String,Object> map = list.get(0);
//                groupOrderVo.setWxImg(map.get("headImg").toString());
//                groupOrderVo.setWxName(map.get("NAME").toString());
//            }
        }
        return groupOrderVoList;
    }


    public HashMap<String, Object> insertGroupOrder(Long groupId,  Long userId,Long ordId)  throws Exception {

        logger.debug("debug参与拼团");
        //参加拼团判断
        GroupOrderDTO groupOrderBo = groupOrderMapper.getGroupOrderByIdAndAvailableTime(groupId);
        //有这个活动是待成团状态并且是还没到结束时间
        if(groupOrderBo!=null){
            List<GroupOrderItem> groupOrderItemBo = groupOrderItemMapper.selectList(new QueryWrapper<GroupOrderItem>().eq("group_order_id",groupId).eq("user_id",userId).eq("state",GeneConstant.BYTE_1));
            if(groupOrderItemBo!=null&&groupOrderItemBo.size()>0){
                INSERTED_GROUP.assertFail();
            }else{
                Long insertNum =groupOrderItemMapper.getCountInsertOrderByGoodsAndUserId(groupOrderBo.getGroupPromotionId(),userId,groupOrderBo.getGoodsId());
                Long limitNum = groupOrderMapper.getGroupLimitById(groupOrderBo.getGroupPromotionId());
                if(limitNum<=insertNum){
                    INSERT_GROUP_NUMBER_USED.assertFail(limitNum);
                }
            }
        }

//        Long ordId = 0L;
//        //TODO 使用小程序mapper
//        List<Order> orderList = orderDao.findOrderInfo(orderCode.split(","));
//
//        if(orderList!=null&&orderList.size()>0){
//            Order order = orderList.get(0);
//            ordId = order.getId();
//        }else{
//            return new ResponseEntity(GeneConstant.INT_2, "订单不存在！");
//        }

        final Long orderId = ordId;
        Integer i = redisLocker.lock("GroupOrderId:"+groupId, new AquiredLockWorker<Integer>() {
            //锁
            @Override
            public Integer invokeAfterLockAquire() throws Exception {
                //获取团信息，判断是否可以拼团
                GroupOrder groupOrderBo = groupOrderMapper.selectById(groupId);
                //TODO 返回值注释
                //有这个活动是待成团状态并且是还没到结束时间
                if(groupOrderBo!=null&&groupOrderBo.getStatus().equals(GeneConstant.BYTE_2)){
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


                groupOrderItemMapper.insert(groupOrderItem);
                //判断是否已经成团
                List<GroupOrderItem> itemList = groupOrderItemMapper.selectList(new QueryWrapper<GroupOrderItem>().eq("group_order_id",groupId).eq("state",1));
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
            //wzdOrderAppletsMapper.updateGroupOrderId(orderId,null);
            GROUP_IS_FULL.assertFail();
        }else if(i==GeneConstant.INT_1){
//            try{
//                List<GroupOrderItemBo> itemList = groupOrderItemMapper.getGroupOrderItemByGroupOrderId(groupId);
//                for(GroupOrderItem groupOrderItem:itemList){
//                    Orders order= orderAppletsMapper.getOrderById(groupOrderItem.getOrderId());
//                    wzdOrderAppletsService.synchronizeOrderToBBD(order);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                return resultMap;
//            }
            return resultMap;
        }
        return resultMap;
    }


    public List<GroupGoodsListDTO> getGroupGoodsList(){
        List<GroupGoodsListDTO> groupGoodsList = groupOrderMapper.getGroupGoodsList();
        return groupGoodsList;
    }

    public GroupOrderDetaiInfoDTO getGroupOrderDetail(Long groupOrderId){
        QueryWrapper<GroupOrderItem> groupOrderItemQueryWrapper = new QueryWrapper<>();
        groupOrderItemQueryWrapper.eq("group_order_id",groupOrderId);
        List<GroupOrderItem> groupOrderItems = groupOrderItemMapper.selectList(groupOrderItemQueryWrapper);
        QueryWrapper<GroupOrder> groupOrderQueryWrapper = new QueryWrapper<>();
        groupOrderQueryWrapper.eq("id",groupOrderId).eq("status",2);
        GroupOrder groupOrder = groupOrderMapper.selectOne(groupOrderQueryWrapper);
        if(groupOrder==null){
            //团已失效
            GROUP_IS_EXPIRED.assertFail();
        }
        GroupOrderDetaiInfoDTO groupOrderDetaiInfoDTO = new GroupOrderDetaiInfoDTO();
        List<String> userImgs = new ArrayList<>();
        List<Long> userIds=groupOrderItems.stream().map(GroupOrderItem::getUserId).collect(Collectors.toList());
        //查询出所有的用户的信息  userIds
        StringBuffer stringBuffer = new StringBuffer();
        userIds.forEach(c->{
            stringBuffer.append(c).append(",");
        });
        R<List<MemberDTO>> memberInfoList = memberFacade.getMemberInfoList(stringBuffer.toString());
        List<MemberDTO> MemberDTOs = memberInfoList.getData();
        MemberDTOs.forEach(c -> {
            if(c.getId()==groupOrder.getUserId()) {
                groupOrderDetaiInfoDTO.setHeaderImg(c.getHeadImg());
                groupOrderDetaiInfoDTO.setHeaderName(c.getNickname());
            }else {
                userImgs.add(c.getHeadImg());
            }
        });
        groupOrderDetaiInfoDTO.setGroupOrderId(groupOrderId);
        groupOrderDetaiInfoDTO.setAvailableTime(groupOrder.getAvailableTime());
        groupOrderDetaiInfoDTO.setGoodsId(groupOrder.getGoodsId());
        groupOrderDetaiInfoDTO.setGroupSizeDiff(groupOrder.getGroupSize()-groupOrderItems.size());
        groupOrderDetaiInfoDTO.setUserImgs(userImgs);
        return groupOrderDetaiInfoDTO;
    }

    public GroupOrderAppletsDTO getGroupOrderApplets(Long groupOrderId){
        GroupOrderAppletsDTO groupOrderAppletsDTO = new GroupOrderAppletsDTO();
        GroupOrder groupOrder = groupOrderMapper.selectById(groupOrderId);

        QueryWrapper<GroupOrderItem> groupOrderItemQueryWrapper = new QueryWrapper<>();
        groupOrderItemQueryWrapper.eq("group_order_id",groupOrderId);
        List<GroupOrderItem> groupOrderItems = groupOrderItemMapper.selectList(groupOrderItemQueryWrapper);
        //对团的状态进行处理
        groupOrderAppletsDTO.setStatus(groupOrder.getStatus());
        groupOrderAppletsDTO.setAvailableTime(groupOrder.getAvailableTime());
        groupOrderAppletsDTO.setGroupSizeDiff(groupOrder.getGroupSize()-groupOrderItems.size());
        List<UserDTO> userDTOS = new ArrayList<>();
        List<Long> userIds=groupOrderItems.stream().map(GroupOrderItem::getUserId).collect(Collectors.toList());
        //查询出所有的用户的信息  userIds
        StringBuffer stringBuffer = new StringBuffer();
        userIds.forEach(c->{
            stringBuffer.append(c).append(",");
        });
        R<List<MemberDTO>> memberInfoList = memberFacade.getMemberInfoList(stringBuffer.toString());
        List<MemberDTO> MemberDTOs = memberInfoList.getData();
        MemberDTOs.forEach(c -> {
            if(c.getId()==groupOrder.getUserId()) {
                groupOrderAppletsDTO.setHeaderImg(c.getHeadImg());
                groupOrderAppletsDTO.setHeaderName(c.getNickname());
            }else {
                UserDTO userDTO = new UserDTO();
                userDTO.setHeadImg(c.getHeadImg());
                userDTO.setName(c.getNickname());
                userDTOS.add(userDTO);
            }
        });
        //对商品表查询出商品图片和运营时间
        R<GoodsExtendGroupDTO> goodsExtendDetail = goodsExtendFacade.getGoodsExtendDetail(groupOrder.getGoodsId());
        GoodsExtendGroupDTO goodsExtendGroupDTO = goodsExtendDetail.getData();
        R<GoodsDetailDTO> goodsDetailDTOR = goodsFacade.goodsDetail(goodsExtendGroupDTO.getGoodsId());
        GoodsDetailDTO goodsDetailDTO = goodsDetailDTOR.getData();

        StringBuffer groupTime = new StringBuffer();
        groupTime.append(goodsDetailDTO.getGoodsBaseInfoDTO().getOperationStartDate()).append("-").append(goodsDetailDTO.getGoodsBaseInfoDTO().getOperationEndDate());
        StringBuffer goodsName = new StringBuffer();
        goodsName.append(goodsDetailDTO.getGoodsBaseInfoDTO().getGoodsName()).append("-").append(goodsExtendGroupDTO.getGoodsExtendName());

        GroupOrderGoodsDTO groupOrderGoodsDTO = new GroupOrderGoodsDTO();
        groupOrderGoodsDTO.setGoodsId(groupOrder.getGoodsId());
        groupOrderGoodsDTO.setGoodsImg(goodsDetailDTO.getGoodsDescriptionDTO().getThumbnail());
        groupOrderGoodsDTO.setOperationTime(groupTime.toString());
        groupOrderGoodsDTO.setGoodsName(goodsName.toString());
        groupOrderGoodsDTO.setGroupPrice(Integer.valueOf(goodsExtendGroupDTO.getSalePrice()));
        groupOrderAppletsDTO.setUserDTOS(userDTOS);
        groupOrderAppletsDTO.setGroupOrderId(groupOrderId);
        groupOrderAppletsDTO.setGroupOrderGoodsDTO(groupOrderGoodsDTO);
        return groupOrderAppletsDTO;
    }

    public GroupGoodsHotDTO getHotGroupOrderList(){
        GroupGoodsHotDTO groupGoodsHotDTO = new GroupGoodsHotDTO();
        List<Map> hotList =  groupOrderMapper.getGroupGoodsHotList();

        for(Map map:hotList){
            goodsExtendFacade.getGoodsExtendDetail(Long.parseLong(map.get("").toString()));
            List<GroupOrder> groupOrders =  groupOrderMapper.selectList(new QueryWrapper<GroupOrder>().eq("",""));
        }


        return groupGoodsHotDTO;
    }
}
