package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.tracing.dtrace.ArgsAttributes;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyAppListDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;


    /**
     * 封面图片
     */
    @ApiModelProperty("封面图片/视频")
    private String coverUrl;


    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;


    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp publishTime;

    /**
     * 是否点赞
     */
    @ApiModelProperty("是否点赞")
    private Boolean isVote;

    /**
     * 浏览量
     */
    @ApiModelProperty("浏览量")
    private Double viewNumber;


    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long memberId;

    /**
     * 文章id
     */
    @ApiModelProperty("文章id")
    private Long articleId;

    /**
     * 类型
     */
    @ApiModelProperty("1攻略 2知识")
    private Byte type;

    /**
     * 点赞量
     */
    @ApiModelProperty("点赞量")
    private Long voteNumber;
}
