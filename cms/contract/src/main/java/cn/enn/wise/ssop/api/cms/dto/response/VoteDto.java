package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date:2020/4/2 18:30
 * @author:hsq
 */
public class VoteDto implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

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
    @ApiModelProperty("类型")
    private Byte type;
    /**
     * 点赞时间
     */
    @ApiModelProperty("点赞时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp voteTime;

    /**
     * 攻略id
     */
    @ApiModelProperty(value = "攻略id",required = true)
    @NotNull
    private Long strategyId;

}
