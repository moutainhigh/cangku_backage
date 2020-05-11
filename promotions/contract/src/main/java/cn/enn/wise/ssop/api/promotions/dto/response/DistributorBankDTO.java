package cn.enn.wise.ssop.api.promotions.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商财务账号信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商财务账号信息返回参数")
public class DistributorBankDTO {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("银行代码")
    private String bankCode;

    @ApiModelProperty("账户名")
    private String userName;

    @ApiModelProperty("银行卡号")
    private String cardNumber;

    @ApiModelProperty("状态 1 正常 2 停用")
    private Byte state;

    @ApiModelProperty("详细地址")
    private String bankAddress;

    @ApiModelProperty("备注（账户用途）")
    private String remark;
}
