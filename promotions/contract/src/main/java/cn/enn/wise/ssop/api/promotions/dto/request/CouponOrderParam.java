package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/4/18 12:06 下午
 */
@Data
@ApiModel("优惠券-订单校验")
public class CouponOrderParam {

    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("券码")
    private String code;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    private Long goodsExtendId;
}
