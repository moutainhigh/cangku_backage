package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("投放渠道")
public class PlatformDTO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 投放平台名称
     */
    @ApiModelProperty("投放平台名称")
    private String name;

    /**
     * 状态 1 可用  2 不可用
     */
    @ApiModelProperty("状态 1 可用  2 不可用")
    private Byte state;
}
