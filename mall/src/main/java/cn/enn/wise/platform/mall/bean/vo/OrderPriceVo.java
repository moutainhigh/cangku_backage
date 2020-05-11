package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单价格展示Vo
 *
 * @author baijie
 * @date 2019-12-18
 */
@Data
@ApiModel("订单价格展示Vo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPriceVo {

    @ApiModelProperty("订单金额")
    private BigDecimal orderPrice;

    @ApiModelProperty("优惠金额")
    private BigDecimal couponPrice;

    @ApiModelProperty("购买数量")
    private Integer amount;

    @ApiModelProperty("优惠券领取记录表Id")
    private Long userOfCouponId;

    @ApiModelProperty("优惠多少金额")
    private BigDecimal discount;


}
