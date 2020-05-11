package cn.enn.wise.platform.mall.bean.bo.group;

import cn.enn.wise.platform.mall.bean.bo.autotable.GroupPromotionGoods;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动商品列表
 *
 * @author anhui
 * @since 2019/9/12
 */
@Data
@TableName("group_promotion_goods")
public class GroupPromotionGoodsBo extends GroupPromotionGoods {

    /**
     * 最低价
     */
    @TableField(exist = false)
    private BigDecimal minPrice;

    /**
     * 每单购买数量限制
     */
    @TableField(exist = false)
    private Integer groupLimit;

    /**
     * 活动状态
     */
    @TableField(exist = false)
    private Integer promotionStatus;

}
