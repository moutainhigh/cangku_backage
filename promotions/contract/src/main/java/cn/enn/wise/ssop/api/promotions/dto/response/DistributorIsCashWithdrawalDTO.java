package cn.enn.wise.ssop.api.promotions.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商补充是否可以提现信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商补充是否可以提现信息返回参数")
public class DistributorIsCashWithdrawalDTO {

    @ApiModelProperty("分销商补充信息")
    private DistributorAddDTO distributorAddDTO;

    @ApiModelProperty("分销商是否可以提现 1 可以 2 不可以")
    private Integer isCashWithdrawal;




}
