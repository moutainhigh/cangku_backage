package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnnouncementSaveParam {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",example = "null")
    private Long id;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容",required = true)
    @NotNull
    private String content;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty(value = "1启用  2禁用",required = true,example = "1")
    @NotNull
    private Byte state;

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id",required = true)
    @NotNull
    private Long categoryId;

    /**
     * 景区ID
     */
    @ApiModelProperty("景区ID")
    @NotNull
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    @NotNull
    private String scenicAreaName;
}
