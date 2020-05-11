package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel("简要活动信息")
@Data
public class ActInfoListVo implements Serializable {

    /**  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = -4978057946356350490L;

	@ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面url")
    private String coverUrl;

    @ApiModelProperty(value = "体验人数")
    private Integer experienceNumber;

    @ApiModelProperty(value = "分销价格")
    private double distributionPrice;

    @ApiModelProperty(value = "价格")
    private double price;

    @ApiModelProperty(value = "描述")
    private String description;



}
