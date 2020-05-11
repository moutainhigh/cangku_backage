package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 活动返回类型
 */
@Data
@ApiModel("返回活动类型")
public class ActivityTypeDTO {

    @ApiModelProperty("产品ID")
    private Long goodsId;

    @ApiModelProperty("活动类型：1优惠活动  2拼团活动")
    private String activityType;

}
