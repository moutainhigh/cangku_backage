package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PartnerSaveParam {


    @ApiModelProperty("id，不传为增加合作伙伴")
    private Long id;

    @ApiModelProperty("合作伙伴")
    @NotBlank(message = "合作伙伴名称为空")
    private String name;

    @ApiModelProperty("地址")
    @NotBlank(message = "合作伙伴地址为空")
    private String address;

    @ApiModelProperty("介绍")
    @NotBlank(message = "合作伙伴介绍为空")
    private String descs;

    @ApiModelProperty("开户账号")
    private String bankAccount;

    @ApiModelProperty("银行用户名")
    private String bankName;

    @ApiModelProperty("开户行")
    private String bankAddress;

    @ApiModelProperty("联系人名称")
    @NotBlank(message = "联系人名称为空")
    private String contactName;

    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式为空")
    private String contactPhone;

    @ApiModelProperty("联系邮箱地址")
    private String contactEmail;

    @ApiModelProperty("服务器白名单")
    private String ipWhiteList;

    @ApiModelProperty("服务器通知地址")
    private String serverNoticeUrl;



}