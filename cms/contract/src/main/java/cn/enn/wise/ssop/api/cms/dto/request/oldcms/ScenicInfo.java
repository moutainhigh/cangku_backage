package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author bj
 * @Description 景点内容管理
 * @Date19-4-24 上午11:35
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("景点内容管理")
public class ScenicInfo extends ScenicSpotVo implements Serializable {
    /**  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = -4901429999226518373L;


	/**
     *状态
     */
    @ApiModelProperty(value = "状态")
    private Integer state;


    /**
     * 富文本
     */
    @ApiModelProperty(value = "富文本")
    private String html;

    /**
     * 图片图集
     */
    @ApiModelProperty(value = "图片图集")
    private List<String> url;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("图集数")
    private Integer urlCounts;
}
