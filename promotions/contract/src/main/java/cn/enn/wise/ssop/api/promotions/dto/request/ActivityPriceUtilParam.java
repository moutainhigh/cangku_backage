package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


/**
 * 计算的优惠活动
 * @author yangshuaiquan
 */
@Data
@ApiModel("计算活动或优惠券参数")
public class ActivityPriceUtilParam {

    @ApiModelProperty("商品")
    @NotNull
    private List<GoodsPriceParam> goodsPriceParams;

    @ApiModelProperty("优惠券")
    private List<CouponPriceParam> couponPriceParams;

    @ApiModelProperty("活动")
    private List<ActivityPriceParam> activityPriceParams;

    @ApiModelProperty("优惠类型 1 活动优惠   4 优惠券")
    @NotNull
    private Byte activityType;

    @ApiModelProperty("是否分销商 1 是 2否")
    @NotNull
    private Byte isDistribute;

    @ApiModelProperty("用户Id")
    @NotNull
    private Long userId;

    @ApiModelProperty("产品使用时间时间")
    @NotNull
    private Timestamp orderUseTime;

}
