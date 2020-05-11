package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 需要计算的活动
 * @author yangshuaiquan
 */
@Data
@ApiModel("活动返回的参数")
public class ActivityPriceDTO {

    @ApiModelProperty("商品")
    private List<GoodsPriceDTO> goodsPriceDTOS;

    @ApiModelProperty("优惠活动ID")
    private Long activityBaseId;

    @ApiModelProperty("优惠活动规则ID")
    private Long activityRuleId;

    @ApiModelProperty("优惠活动类型 1 早定优惠  2 特价优惠  3 满减优惠")
    private Byte algorithms;

    @ApiModelProperty("某个优惠活动种类")
    private Byte saleType;

    @ApiModelProperty("商品优惠价格")
    private Integer salePrice;
}
