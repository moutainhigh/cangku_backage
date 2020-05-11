package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @date:2020/4/4
 * @author:hsq
 */
@Data
public class AdvertiseAppDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 封面图片/视频url
     */
    @ApiModelProperty(value = "封面图片/视频url",required = true)
    private String coverUrl;

    /**
     * 广告路径
     */
    @ApiModelProperty(value = "广告路径",required = true)
    private String address;
}
