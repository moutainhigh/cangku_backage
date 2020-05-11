package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽奖记录列表
 * @author 安辉
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("抽奖记录列表")
public class DrawRecordListParam extends QueryParam {
    @ApiModelProperty("抽奖活动id")
    private Long activityBaseId;
}