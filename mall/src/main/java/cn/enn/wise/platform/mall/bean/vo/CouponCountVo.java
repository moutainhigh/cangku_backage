package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券数量统计Vo
 *
 * @author baijie
 * @date 2020-04-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCountVo {


    @ApiModelProperty("优惠券类型")
    private String couponType;

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("待使用")
    private Long toBeUsed;

    @ApiModelProperty("已使用")
    private Long used;

    @ApiModelProperty("已过期")
    private Long expired;

    @ApiModelProperty("使用率")
    private String usageRate;

}
