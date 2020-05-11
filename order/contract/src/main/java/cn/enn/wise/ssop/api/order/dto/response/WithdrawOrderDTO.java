package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 提现单信息
 */
@Data
@ApiModel("提现单信息,用于提现单详情")
public class WithdrawOrderDTO extends OrderDetailResponse {


    @ApiModelProperty("当前订单可提现金额")
    private String withdrawSum;

    @ApiModelProperty("实付金额")
    private String actualPayStr;



}
