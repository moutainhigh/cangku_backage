package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SimpleAnnouncementDTO {


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty("1启用  2禁用")
    private Byte state;

    /**
     * 类别id
     */
    @ApiModelProperty("类别id")
    private String categoryId;

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    private String categoryName;

    /**
     * 发布者
     */
    @ApiModelProperty("发布者")
    private String creatorName;

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
     * 浏览量
     */
    @ApiModelProperty("浏览量")
    private Integer viewNumber;

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
