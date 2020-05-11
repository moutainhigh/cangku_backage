package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 *发布状态
 */
@ApiModel("景点详情")
@Data
@EqualsAndHashCode(callSuper=false)
public class ScenicSpotVo extends PageVo implements Serializable {

    /**  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = 5846766675481733742L;

	@ApiModelProperty(value = "景点id")
    private Integer id;

    @ApiModelProperty(value = "景点名称")
    private String name;

    @ApiModelProperty(value = "景点图片urls，逗号分割")
    private String urls;

    @ApiModelProperty(value = "简介")
    private String info;

    @ApiModelProperty(value = "语音Url")
    private String audiourl;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "经度")
    private Double lon;

    @ApiModelProperty(value = "知识ids")
    private String knowledgesIds;

    @ApiModelProperty(value = "知识List")
    private List<KnowledgesListVo> knowledgesList;

    @ApiModelProperty(value = "知识IdList")
    private List<String> knowledgesIdList;

    @ApiModelProperty(value = "活动信息ids")
    private String actinfoIds;

    @ApiModelProperty(value = "活动信息List")
    private List<ActInfoListVo> actinfoList;

    @ApiModelProperty(value = "活动信息IdList")
    private List<String> actinfoIdList;

    @ApiModelProperty(value = "知识names 以逗号分割")
    private String knowledgesNames;

    @ApiModelProperty(value = "活动names 以逗号分割")
    private String actinfoNames;

}
