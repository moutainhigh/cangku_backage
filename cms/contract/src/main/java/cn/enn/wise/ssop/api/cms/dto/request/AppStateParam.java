package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppStateParam {


    @ApiModelProperty("目标状态")
    @NotNull(message="目标状态为空")
    private int state;




}