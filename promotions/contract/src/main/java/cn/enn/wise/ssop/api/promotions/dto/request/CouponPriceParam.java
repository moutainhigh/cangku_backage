package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 计算的优惠卷
 * @author yangshuaiquan
 */
@Data
@ApiModel("优惠券参数")
public class CouponPriceParam {

    @ApiModelProperty("优惠券id")
    @NotNull
    private Long couponId;

    @ApiModelProperty("优惠券类型 1 体验券 2 满减券 3 代金券")
    @NotNull
    private Byte couponType;
}
