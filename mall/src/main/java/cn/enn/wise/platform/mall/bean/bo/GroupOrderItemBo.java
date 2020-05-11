package cn.enn.wise.platform.mall.bean.bo;

import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;

import java.sql.Timestamp;

/**
 * 拼团订单明细
 *
 * @author baijie
 * @date 2019-09-18
 */
@TableName("group_order_item")
public class GroupOrderItemBo extends GroupOrderItem {

    @Builder
    public GroupOrderItemBo(Long id, Long groupOrderId, String groupOrderCode, Long userId, Timestamp joinTime, Long orderId, Byte isHeader, Byte status, String refundCode, Long goodsExtendId, Integer goodsCount, Timestamp createTime, Timestamp updateTime, Timestamp refundTime, Byte groupOrderType){

        super(id, groupOrderId, groupOrderCode, userId, joinTime, orderId, isHeader, status, refundCode, goodsExtendId, goodsCount, createTime, updateTime, refundTime, groupOrderType);
    }
}
