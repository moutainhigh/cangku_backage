package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * 伪富文本格式类
 */
public class RichText {

    public RichText() {
    }

    @ApiModelProperty(value = "内容类型 content/img", required = true)
    public String type;

    @ApiModelProperty(value = "值", required = true)
    public String value;
}