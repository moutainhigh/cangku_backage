package cn.enn.wise.ssop.api.cms.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @date:2020/4/2 16:58
 * @author:hsq
 */
@Data
public class VoteSaveParam {

    @ApiModelProperty(value = "主键, 增加不需要传或传null",example = "null")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = " 用户id",required = true)
    @NotNull
    private Long memberId;

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id",required = true)
    @NotNull
    private Long articleId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型",required = true)
    @NotNull
    private Byte type;


    /**
     * 点赞时间
     */
    @ApiModelProperty(value = "点赞时间",required = true)
    @NotNull
    private Timestamp voteTime;

    /**
     * 攻略id
     */
    @ApiModelProperty(value = "攻略id",required = true)
    @NotNull
    private Long strategyId;
}
