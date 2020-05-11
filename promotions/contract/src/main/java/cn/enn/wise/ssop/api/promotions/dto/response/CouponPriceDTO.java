package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 计算的优惠卷
 * @author yangshuaiquan
 */
@Data
@ApiModel("优惠券返回的参数")
public class CouponPriceDTO {

    @ApiModelProperty("商品")
    private List<GoodsPriceDTO> goodsPriceDTOS;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券类型 1 体验券 2 满减券 3 代金券")
    private Byte couponType;

    @ApiModelProperty("商品优惠价格")
    private Integer salePrice;
}
