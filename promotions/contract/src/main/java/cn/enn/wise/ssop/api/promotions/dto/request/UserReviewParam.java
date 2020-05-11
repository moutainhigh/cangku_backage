package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yangshauiquan
 * 用户活动回访参数
 */
@Data
@ApiModel("用户活动回访参数")
public class UserReviewParam {

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty("活动id")
    @NotNull
    private Long activityBaseId;

    @ApiModelProperty("回访内容")
    private String reviewInfo;

}
