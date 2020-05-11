package cn.enn.wise.ssop.api.cms.dto.response;

import cn.enn.wise.ssop.api.cms.dto.request.RichText;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class StrategyAppDTO implements Serializable {

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
     * 内容
     */
    @ApiModelProperty("内容")
    private List<RichText> contentList;

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
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Long voteCount;
}
