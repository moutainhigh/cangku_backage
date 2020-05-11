package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "分类详情")
@Data
public class CategoryDTO {

    
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 副标题
     */
    @NotNull
    @ApiModelProperty(value = "副标题",required = true)
    private String subtitle;

    /**
     * 本级排序
     */
    @ApiModelProperty("本级排序")
    private Integer sort;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty("1启用  2禁用")
    private Byte state;

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private Long parentId;
}
