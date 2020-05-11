package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单信息,用于提现订单详情")
public class WithdrawOrderVO extends Order {


    @ApiModelProperty("当前订单可提现金额")
    private String withdrawSum;

    @ApiModelProperty("实付金额")
    private String actualPayStr;



}
