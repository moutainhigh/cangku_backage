package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.GroupOrderItemBo;
import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * GroupOrder Mapper 接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-09-12
 */
public interface GroupOrderItemMapper extends BaseMapper<GroupOrderItemBo> {





    /**
     * 根据商品id查询团中正在拼团的详细信息
     * @param goodsId
     * @return 开团信息
     */
    List<GroupOrderItemBo> getGroupOrderItemByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * 插入参团详细信息到item中
     * @param groupOrderItem
     * @return
     */
   int  insert(GroupOrderItem groupOrderItem);

    /**
     * 根据团id查询参与拼团的item的详细信息
     * @param groupOrderId
     * @return 参与拼团的item的详细信息
     */
   List<GroupOrderItemBo> getGroupOrderItemByGroupOrderId(@Param("groupOrderId") Long groupOrderId);

    /**
     * 创建拼团人员是否已经参与这个活动
     * @param groupPromotionId
     * @param userId
     * @return 团item信息
     */
   List<GroupOrderItemBo> getGroupOrderItemByGroupPromotionIdAndUserId(@Param("groupPromotionId")Long groupPromotionId,@Param("userId")Long userId);

    /**
     * 参与拼团人员是否已经参与这个活动
     * @param groupOrderId
     * @param userId
     * @return 团item信息
     */
    List<GroupOrderItemBo> getGroupOrderItemByGroupOrderIdAndUserId(@Param("groupOrderId")Long groupOrderId,@Param("userId")Long userId);

    /**
     *
     * @param orderId
     * @param userId
     * @return
     */
    Map getGroupOrderSizeByOrderIdAndUserId(@Param("orderId")Long orderId, @Param("userId")Long userId);

    Long getCountInsertOrderByGoodsAndUserId(@Param("groupPromotionId")Long groupPromotionId,@Param("userId")Long userId,@Param("goodId")Long goodId);

    Integer updateStatusByOrderIdAndUserId(@Param("orderId") Long orderId,@Param("userId") Long userId);

    /**
     * 定时任务中获取order中拼团订单待使用状态，订单信息不再item表中的信息
     * @return
     */
    List<Order> getOrderNotInItem();
}
