package cn.enn.wise.platform.mall.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/18
 */
@Data
public class GoodsCouponRuleVo {


    @TableId(type = IdType.AUTO)
    private Long id;


    @ApiModelProperty("活动Id")
    private Long promotionId;


    @ApiModelProperty("优惠券Id")
    private Long goodsCouponId;


    @ApiModelProperty("使用条件 1 订单总价 2 商品单价")
    private String useRule;


    @ApiModelProperty("达到限额可用（满足多少钱可用）")
    private Integer minUse;


    @ApiModelProperty("是否可以转增 1可以 2 不可以")
    private String isSend;


    @ApiModelProperty("是否优惠共享 1可以 2 不可以")
    private String isShare;

    @ApiModelProperty("限制类型 1 不限制 2 限制")
    private String promotionGetType;

    @ApiModelProperty("领取限制数量")
    private Integer promotionGetLimit;

    @ApiModelProperty("叠加使用优惠券id")
    private String overlayUseCouponId;


    @ApiModelProperty("是否可以叠加 1可以 2 不可以")
    private String isOverlay;


    @ApiModelProperty("规则描述")
    private String tag;

    @ApiModelProperty("活动中是否项目专用 1是 2 不是")
    private String isProjectUse;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目专用下 可使用的商品ids")
    private String goodsId;

    @ApiModelProperty("规则描述")
    private String description;

    @ApiModelProperty("有效时间")
    private String validityTimes;
}
