package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 满减规则
 */
@Data
public class MinusRule implements Serializable {
    public MinusRule() {
    }

    @ApiModelProperty("优惠条件 1 订单金额(元) 2 订单人头(位) [preferentialTerms]")
    private Byte discountConditions;

    @ApiModelProperty("满减算法列表")
    private List<MinusRuleAlgorithm> minusRuleAlgorithmList;


    /**
     * 满减规则算法  范围，优惠方式，优惠金额
     */
    public static class MinusRuleAlgorithm implements Serializable{
        public MinusRuleAlgorithm() {
        }

        @ApiModelProperty("范围开始")
        public Integer start;

        @ApiModelProperty("范围结束")
        public Integer end;

        @ApiModelProperty("优惠体现 1 优惠券 2 减免 [discountMode]")
        public Byte saleType;

        @ApiModelProperty("优惠券id")
        public Long couponId;

        @ApiModelProperty("优惠券名称")
        public String couponName;

        @ApiModelProperty("优惠券发放数量")
        public Integer issueCount;

        @ApiModelProperty("领取次数/人")
        public Integer numberForDay;

        @ApiModelProperty("减免金额")
        public Integer salePrice;


    }
}

