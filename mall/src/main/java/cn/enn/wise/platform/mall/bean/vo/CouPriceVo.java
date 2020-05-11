package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 16:09
 **/
@Data
@ApiModel("统计列表")
public class CouPriceVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "面额")
    private Double price;

    @ApiModelProperty(value = "抵扣金额")
    private Double couponPrice;

    @ApiModelProperty(value = "结算金额")
    private Double finPrice;
}
