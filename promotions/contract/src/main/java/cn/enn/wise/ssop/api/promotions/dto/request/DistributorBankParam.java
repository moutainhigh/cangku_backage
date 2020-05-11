package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分销商财务账号信息
 * @author 耿小洋
 */
@Data
@ApiModel("销商财务账号信息")
public class DistributorBankParam {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @NotNull
    private Long distributorBaseId;

    @ApiModelProperty("银行名称")
    @NotNull
    private String bankName;

    @ApiModelProperty("银行代码")
    @NotNull
    private String bankCode;

    @ApiModelProperty("账户名")
    @NotNull
    private String userName;

    @ApiModelProperty("银行卡号")
    @NotNull
    private String cardNumber;

    @ApiModelProperty("状态 1 正常 2 停用")
    @NotNull
    private Byte state;

    @ApiModelProperty("详细地址")
    private String bankAddress;

    @ApiModelProperty("备注（账户用途）")
    private String remark;
}
