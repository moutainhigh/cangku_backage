package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AnnouncementDTO {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",example = "null")
    private Long id;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty(value = "1启用  2禁用",required = true,example = "1")
    private Byte state;

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id",required = true)
    private Long categoryId;

    /**
     * 景区ID
     */
    @ApiModelProperty("景区ID")
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    private String scenicAreaName;
}
