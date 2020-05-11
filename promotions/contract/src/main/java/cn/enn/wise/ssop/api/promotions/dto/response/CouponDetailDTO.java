package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券详情")
public class CouponDetailDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("代金券")
    private CouponCashRuleDetailDTO couponCashRuleDetailDTO;

    @ApiModelProperty("体验券")
    private CouponExperienceRuleDetailDTO couponExperienceRuleDetailDTO;

    @ApiModelProperty("满减券")
    private CouponFullReduceRuleDetailDTO couponFullReduceRuleDetailDTO;
}
