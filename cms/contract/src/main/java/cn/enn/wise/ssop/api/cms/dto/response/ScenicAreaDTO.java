package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date:2020/4/4
 * @author:hsq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenicAreaDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键  -1为景区外")
    private Long id;

    /**
     * 封面图片/视频url
     */
    @ApiModelProperty(value = "景区名称")
    private String name;

}
