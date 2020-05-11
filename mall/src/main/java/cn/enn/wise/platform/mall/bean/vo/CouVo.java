package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-02 14:51
 **/
@Data
@ApiModel("优惠卷核算")
public class CouVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;

    @ApiModelProperty(value = "面额")
    private Double price;

    @ApiModelProperty(value = "优惠金额")
    private Double couponPrice;

    @ApiModelProperty(value = "结算金额")
    private Double finPrice;

}
