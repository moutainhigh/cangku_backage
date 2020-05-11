package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryParam {


    @ApiModelProperty(value = "主键",example = "null")
    private Long id;

    /**
     * 分类名称
     */
    @NotNull
    @ApiModelProperty(value = "分类名称",required = true)
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
    @NotNull
    @ApiModelProperty("本级排序,默认0")
    private Integer sort;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty(value = "1启用  2禁用, 默认1",example = "1")
    private Byte state=1;

    /**
     * 父id
     */
    @ApiModelProperty("父id ,默认0 根目录")
    private Long parentId=0L;





}