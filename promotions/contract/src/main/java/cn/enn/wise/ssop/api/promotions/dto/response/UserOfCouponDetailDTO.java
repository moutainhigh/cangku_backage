package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("用户领取信息")
public class UserOfCouponDetailDTO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券状态 1 有效 2 无效")
    private Byte couponStatus;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("券码")
    private String couponCode;
}
