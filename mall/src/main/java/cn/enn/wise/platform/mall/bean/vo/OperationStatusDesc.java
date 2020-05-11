package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 运营状态描述
 *
 * @author baijie
 * @date 2019-08-26
 */
@Data
@ApiModel("运营状态描述")
public class OperationStatusDesc {


    @ApiModelProperty("状态值描述")
    private String desc;

    @ApiModelProperty("原因")
    private String reasons;

    @ApiModelProperty("影响程度范围")
    private String range;
}
