package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安辉
 */
@Data
@ApiModel("描述")
public class ActivityDescriptionParam {
    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;

    /**
     * 文本 或者 图片地址
     */
    @ApiModelProperty("值")
    private String value;
}
