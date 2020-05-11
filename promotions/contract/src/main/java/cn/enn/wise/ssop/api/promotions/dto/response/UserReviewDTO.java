package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yangshauiquan
 * 用户活动回访返回的参数
 */
@Data
@ApiModel("用户活动回访返回的参数")
public class UserReviewDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty("活动id")
    private Long activityBaseId;

    @ApiModelProperty("回访内容")
    private String reviewInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("回访信息创建时间")
    private Timestamp createTime;
    
}
