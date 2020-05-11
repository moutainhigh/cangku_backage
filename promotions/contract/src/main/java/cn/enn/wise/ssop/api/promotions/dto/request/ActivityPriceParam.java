package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 需要计算的活动
 * @author yangshuaiquan
 */
@Data
@ApiModel("活动参数")
public class ActivityPriceParam {

    @ApiModelProperty("优惠活动ID")
    @NotNull
    private Long activityBaseId;

    @ApiModelProperty("优惠活动规则ID")
    @NotNull
    private Long activityRuleId;

    @ApiModelProperty("优惠活动类型 1 早定优惠  2 特价优惠  3 满减优惠")
    @NotNull
    private Byte algorithms;

    @ApiModelProperty("某个优惠活动种类")
    @NotNull
    private Byte saleType;
}
