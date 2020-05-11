package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeStrategyListDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long categoryId;


    /**
     * 图标url
     */
    @ApiModelProperty("图标url")
    private String iconUrl;

    /**
     * 封面图片
     */
    @ApiModelProperty("封面图片")
    private String coverUrl;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty("副标题")
    private String subtitle;

}
