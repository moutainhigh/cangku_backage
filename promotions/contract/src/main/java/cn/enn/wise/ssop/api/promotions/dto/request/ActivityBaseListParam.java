package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 活动列表参数
 * @author 安辉
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("活动列表")
public class ActivityBaseListParam extends QueryParam {

    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("成本部门")
    private String department;

    @ApiModelProperty("状态 1 正在进行中  2 未开始")
    private Byte state;


    @ApiModelProperty("活动开始时间")
    private String startTime;


    @ApiModelProperty("活动结束时间")
    private String endTime;
}