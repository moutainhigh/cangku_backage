package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 优惠券价格统计Vo
 *
 * @author baijie
 * @date 2020-04-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponPriceVo {


    @ApiModelProperty("优惠券类型")
    private String couponType;

    @ApiModelProperty("总数")
    private BigDecimal total;

    @ApiModelProperty("待使用")
    private BigDecimal toBeUsed;

    @ApiModelProperty("已使用")
    private BigDecimal used;

    @ApiModelProperty("已过期")
    private BigDecimal expired;

}
