package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class SimpleKnowledgeDTO {

    @ApiModelProperty("主键")
    private Long id;


    /**
     * 语音讲解
     */
    @ApiModelProperty(value = "语音讲解",required = true)
    private String voiceUrl;


    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;


    /**
     * 1启用  2禁用
     */
    @ApiModelProperty("1启用  2禁用")
    private Byte state;


    /**
     * 点赞数
     */
    @ApiModelProperty("点赞数")
    private Integer voteNumber;

    /**
     * 浏览量
     */
    @ApiModelProperty("浏览量")
    private Integer viewNumber;

    /**
     * 分享量
     */
    @ApiModelProperty("分享量")
    private Integer shareNumber;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp publishTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp createTime;

    /**
     * 类别id
     */
    @ApiModelProperty("类别id")
    private Long categoryId;

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    private String categoryName;


    @ApiModelProperty(value = "封面图片/视频url",required = true)
    private String coverUrl;

    /**
     * 景区ID
     */
    @ApiModelProperty("景区ID")
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    private String scenicAreaName;
}
