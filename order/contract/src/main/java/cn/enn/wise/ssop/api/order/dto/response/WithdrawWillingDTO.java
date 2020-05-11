package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 分销商可提现信息
 * <p>可提现意愿数据</p>
 */
@ApiModel("分销商可提现信息")
@Data
public class WithdrawWillingDTO {

    @ApiModelProperty("可提现总金额")
    private String total;

    @ApiModelProperty("本次可提现金额")
    private String applySum;

    @ApiModelProperty("分销商状态")
    private int distributorStatus;

    @ApiModelProperty("本次可选提现单")
    List<OrderDistributorDTO> orderList;

}
