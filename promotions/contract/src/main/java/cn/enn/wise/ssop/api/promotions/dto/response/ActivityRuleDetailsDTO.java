package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.ssop.api.promotions.dto.request.MinusRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 返回优惠活动详情
 */
@Data
@ApiModel("返回优惠活动详情")
public class ActivityRuleDetailsDTO {

    @ApiModelProperty("优惠算法 1 早定优惠 2 特价直减 3 满减优惠")
    private Byte algorithms;

    @ApiModelProperty("优惠规则")
    private String discountRule;

    @ApiModelProperty("活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty("活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("早定优惠规则")
    private List<ActivityDiscountRuleDTO.ReservationRole> reservationRole;

    @ApiModelProperty("特价直减规则")
    private List<ActivityDiscountRuleDTO.SaleRole> saleRole;

    @ApiModelProperty("满减算法列表")
    private List<MinusRule.MinusRuleAlgorithm> minusRule;

}
