package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 景点位置信息
 */
@Data
public class ScenicSpotInfo {

    @ApiModelProperty("景点名称")
    private String scenicSpotName;

    @ApiModelProperty("经度")
    private double longitude;

    @ApiModelProperty("纬度")
    private double latitude;

}