package cn.enn.wise.ssop.api.promotions.dto.response;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动推广图片列表参数
 * @author jiaby
 */
@Data
@ApiModel("活动推广图片列表返回值")
public class ActivityImgDTO {

    @ApiModelProperty("活动ID")
    private Long activityBaseId;

    @ApiModelProperty("推广图")
    private String generalizeImg;

    @ApiModelProperty("活动描述")
    private String description;
}
