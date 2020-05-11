package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("知识简介")
@Data
public class KnowledgesListVo implements Serializable {

    /**  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = -1224981841598544814L;

	@ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "简介")
    private String shortContent;

    @ApiModelProperty(value = "封面url")
    private String coverUrl;

}
