package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分销商联系人信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商联系人信息")
public class DistributorContactParam {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @NotNull
    private Long distributorBaseId;

    @ApiModelProperty("姓名")
    @NotNull
    private String contactName;

    @ApiModelProperty("电话")
    @NotNull
    private String phone;

    @ApiModelProperty("QQ")
    private String qq;

    @ApiModelProperty("邮箱")
    @NotNull
    private String email;

    @ApiModelProperty("微信")
    @NotNull
    private String wechat;

    @ApiModelProperty("职务")
    @NotNull
    private String position;

    @ApiModelProperty(value = "省Id")
    @NotNull
    private Long provinceId;

    @ApiModelProperty("省名称")
    @NotNull
    private String provinceName;

    @ApiModelProperty(value = "城市Id")
    @NotNull
    private Long cityId;

    @ApiModelProperty("城市名称")
    @NotNull
    private String cityName;

    @ApiModelProperty(value = "区Id")
    @NotNull
    private Long areaId;

    @ApiModelProperty("区名称")
    @NotNull
    private String areaName;

    @ApiModelProperty("详细地址")
    private String contactAddress;


}
