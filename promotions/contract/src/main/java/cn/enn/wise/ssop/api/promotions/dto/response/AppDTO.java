package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "客户端详情")
@Data
public class AppDTO {

    
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("合作伙伴id")
    private Long partnerId;

    @ApiModelProperty("客户端类型 1 微信小程序")
    private int clientType;

    @ApiModelProperty("appId")
    private String appId;

    @ApiModelProperty("微信支付商户号id")
    private String mchId;

    @ApiModelProperty("微信支付商户公匙")
    private String mchKey;

    @ApiModelProperty("微信支付商户私匙")
    private Byte[] mchPrivateKey;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("禁启用状态 1启用 2禁用")
    private int state;

    @ApiModelProperty("是否删除 1正常 2已删除")
    private int isDelete;
}
