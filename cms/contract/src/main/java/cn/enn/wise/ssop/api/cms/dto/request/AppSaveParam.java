package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppSaveParam {


    @ApiModelProperty("id, 不传为新增数据")
    private Long id;

    @ApiModelProperty("合作伙伴id")
    @NotNull
    private Long partnerId;

    @ApiModelProperty("客户端类型 1 微信小程序")
    @NotNull
    private int clientType;

    @ApiModelProperty("appId")
    @NotNull
    private String appId;





}