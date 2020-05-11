package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商联系人信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商联系人信息返回参数")
public class DistributorContactDTO {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty("姓名")
    private String contactName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("QQ")
    private String qq;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("微信")
    private String wechat;

    @ApiModelProperty("职务")
    private String position;

    @ApiModelProperty(value = "省Id")
    private Long provinceId;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市Id")
    private Long cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty(value = "区Id")
    private Long areaId;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("详细地址")
    private String contactAddress;

}
