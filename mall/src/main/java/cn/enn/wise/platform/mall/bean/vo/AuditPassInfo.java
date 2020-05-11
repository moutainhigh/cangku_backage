package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuditPassInfo{


        @ApiModelProperty("审批id")
        @NotNull
        public String id;

        @ApiModelProperty("是否通过")
        @NotNull
        private Boolean ispass;

        @ApiModelProperty("原因描述")
        @NotNull
        private String auditDesc;

        @ApiModelProperty("手机验证码")
        @NotNull
        private String phoneAuthCode;

    }