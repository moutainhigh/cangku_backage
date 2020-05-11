package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "合作伙伴详情")
@Data
public class PartnerDTO {

    
    @ApiModelProperty("主键")
    private Long id;

    
    @ApiModelProperty("合作伙伴")
    private String name;


    @ApiModelProperty("地址")
    private String address;


    @ApiModelProperty("介绍")
    private String descs;


    @ApiModelProperty("开户账号")
    private String bankAccount;


    @ApiModelProperty("银行用户名")
    private String bankName;


    @ApiModelProperty("开户行")
    private String bankAddress;


    @ApiModelProperty("联系人名称")
    private String contactName;


    @ApiModelProperty("联系方式")
    private String contactPhone;


    @ApiModelProperty("联系邮箱地址")
    private String contactEmail;


    @ApiModelProperty("服务器白名单")
    private String ipWhiteList;


    @ApiModelProperty("服务器通知地址")
    private String serverNoticeUrl;


    @ApiModelProperty("appKey")
    private String appkey;


    @ApiModelProperty("appSecret")
    private String appsecret;


    @ApiModelProperty("创建时间")
    private Date createTime;


    @ApiModelProperty("修改时间")
    private Date updateTime;


    @ApiModelProperty("禁启用状态 1启用 2禁用")
    private int state;


    @ApiModelProperty("是否删除 1正常 2已删除")
    private int isDelete;
}
