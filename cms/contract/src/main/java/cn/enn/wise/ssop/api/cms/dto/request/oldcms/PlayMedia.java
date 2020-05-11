package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bj
 * @Description
 * @Date19-4-24 下午3:13
 * @Version V1.0
 **/
@Data
@ApiModel("媒体文件管理")
public class PlayMedia implements Serializable {


    /**  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = -3350342498946658003L;
	private Integer id;
    /**
     * 音视频图片名称
     */
    @ApiModelProperty("音视频图片名称")
    private String name;

    /**
     *类型（1图片、2视频、3音频、4文档）
     */
    @ApiModelProperty(value = "类型（1图片、2视频、3音频、4文档）")
    private String type;


    /**存储路径
     *
     */
    @ApiModelProperty(value = "存储路径")
    private String url;


    /**
     *是否可用（0不可用，1可用）
     */
    @ApiModelProperty(value = "是否可用（0不可用，1可用）")
    private String isUsed;


    /**
     *描述
     */
    @ApiModelProperty(value = "描述")
    private String comment;


    /**
     *数据更新时间
     */
    @ApiModelProperty(value = "数据更新时间")
    private String updateTime;


    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer views;


    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer upvote;


    /**
     *封面url
     */
    @ApiModelProperty(value = "")
    private String coverUrl;


    /**
     *景点Id
     */
    @ApiModelProperty(value = "景点Id")
    private Integer scenicId;


    /**
     *景点名称
     */
    @ApiModelProperty(value = "景点名称")
    private String scenicName;


    /**
     *时长,单位微秒
     */
    @ApiModelProperty(value = "时长,单位微秒")
    private Long videoTime;



}
