package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分销商账号返回信息
 * @author jiaby
 */
@Data
@ApiModel("分销商账号返回信息返回参数")
public class DistributorAccountDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @NotNull
    private Long distributorBaseId;

    @ApiModelProperty(value = "分销商名称")
    private String distributorName;

    @ApiModelProperty("账号")
    @NotNull
    private String accountNumber;

    @ApiModelProperty("密码")
    private String accountPassword;

    @ApiModelProperty("状态 1正常 2 锁定")
    private Byte state;

    @ApiModelProperty("电话")
    @NotNull
    private String phone;

    @ApiModelProperty("是否短信通知 1是 2 否")
    private Byte sendMessage;

    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("是否默认密码 1默认 2不默认")
    private Byte isdefaultPassword;

    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty(value = "城市Id")
    private Long cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty(value = "区Id")
    private Long areaId;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty(value = "渠道Id")
    private Long channelId;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    private Byte channelType;

    @ApiModelProperty(value = "账号Id")
    private Long distributorAccountId;



}
