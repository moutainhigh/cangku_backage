package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分销商账号信息表
 * @author jiaby
 */
@Data
@ApiModel("分销商账号相关参数信息")
public class DistributorAccountParam {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty(value = "分销商名称")
    private String distributorName;

    @ApiModelProperty("账号")
    private String accountNumber;

    @ApiModelProperty("密码")
    private String accountPassword;

    @ApiModelProperty("状态 1正常 2 锁定")
    private Byte state;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("是否短信通知 1是 2 否")
    private Byte sendMessage;

    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("是否默认密码 1默认 2不默认")
    private Byte isdefaultPassword;

    @ApiModelProperty(value = "账号Id")
    private Long distributorAccountId;


}
