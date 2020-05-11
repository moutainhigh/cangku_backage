package cn.enn.wise.platform.mall.bean.bo;

import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrder;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jiabaiye
 * 2019-09-12
 */
@TableName("group_order")
@Data
public class GroupOrderBo extends GroupOrder {

    //private Integer itemSize;

    @TableField(exist = false)
    private List<GroupOrderItemBo> itemList;

    @Builder
    public GroupOrderBo(Long id, Long groupPromotionId, String groupOrderCode, String goodsNum, Long goodsId, Timestamp createTime, Integer groupSize, Byte status, Timestamp endTime, Timestamp availableTime, Long userId){
        super(id, groupPromotionId, groupOrderCode, goodsNum, goodsId, createTime, groupSize, status, endTime,availableTime, userId);
    }

}
