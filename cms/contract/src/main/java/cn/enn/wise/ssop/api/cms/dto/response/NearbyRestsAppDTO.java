package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @date:2020/4/10
 * @author:hsq
 */
@Data
public class NearbyRestsAppDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 目的名称
     */
    @ApiModelProperty(value = "目的名称")
    private String name;

    /**
     * 中心维度
     */
    @ApiModelProperty(value = "中心维度")
    private Double lat;

    /**
     * 中心经度
     */
    @ApiModelProperty(value = "中心经度")
    private Double lon;

    /**
     * 封面图片/视频url
     */
    @ApiModelProperty(value = "封面图片url")
    private String coverUrl;

    /**
     * 目的地位置
     */
    @ApiModelProperty(value = "目的地位置")
    private String address;
}
