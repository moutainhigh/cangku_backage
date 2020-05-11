package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
@ApiModel("规则请求参数")
public class TagParam {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("pid")
    private Long pid;

    @ApiModelProperty("分类")
    private Long categoryId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("分类")
    private Byte type;



}
