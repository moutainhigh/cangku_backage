package cn.enn.wise.platform.mall.bean.vo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 15:58
 **/
@Data
@ApiModel("优惠卷统计")
public class CouCountInfo {

    @ApiModelProperty(value = "核销总券数")
    private Long num;

    @ApiModelProperty(value = "核销总额")
    private Double totalPrice;

    @ApiModelProperty(value = "抵扣总额")
    private Double kouPrice;

    @ApiModelProperty(value = "结算总额")
    private Double finPrice;

    private PageInfo<CouVo> pageInfo;
}
