package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class ScenicFineDTO implements Serializable {

    @ApiModelProperty(value = "景点id")
    private Integer id;

    @ApiModelProperty(value = "景点名称")
    private String name;

    @ApiModelProperty(value = "景点标签")
    private String tag;

    @ApiModelProperty(value = "中心维度")
    private Double lat;

    @ApiModelProperty(value = "中心经度")
    private Double lon;

    @ApiModelProperty(value = "图片路径")
    private String picUrl;

    @ApiModelProperty(value = "音频路径")
    private String redLangue;

    @ApiModelProperty(value = "距离 单位米")
    private Integer distance = 0;

    @ApiModelProperty("景区名称")
    private String scenicAreaName;

}
