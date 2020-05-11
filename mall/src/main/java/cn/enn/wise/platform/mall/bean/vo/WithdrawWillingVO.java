package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class WithdrawWillingVO {

    @ApiModelProperty("可提现总金额")
    private String total;

    @ApiModelProperty("本次可提现金额")
    private String applySum;

    @ApiModelProperty("分销商状态")
    private int distributorStatus;

    @ApiModelProperty("本次可选提现单")
    List<DistributeInfoVO> orderList;

}
