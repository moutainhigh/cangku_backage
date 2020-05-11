package cn.enn.wise.ssop.api.cms.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StrateryQueryParam extends QueryParam {

    @ApiModelProperty("状态 1开启 2禁用")
    private String state;


}