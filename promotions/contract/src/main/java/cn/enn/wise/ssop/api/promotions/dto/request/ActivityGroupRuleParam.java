package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiaby
 */
@Data
@ApiModel("拼团活动规则参数")
public class ActivityGroupRuleParam {

//    @ApiModelProperty("主键")
//    private Long id;

//    @ApiModelProperty(value = "活动规则Id")
//    @NotNull
//    private Long activityRuleId;

    @NotNull
    @ApiModelProperty(value = "拼团有效期,单位小时")
    private Integer groupValidHours;

    @NotNull
    @ApiModelProperty(value = "拼团人数(2-10)")
    private Integer groupSize;

    @NotNull
    @ApiModelProperty(value = "是否自动成团,1是 2否")
    private Byte isautoCreateGroup;

    @NotNull
    @ApiModelProperty(value = "自动成团人数")
    private Byte autoCreateGroupLimit;
//
//    @ApiModelProperty(value = "1普通团 2 超级团")
//    private Byte groupType;

    @NotNull
    @ApiModelProperty(value = "每人限购数量")
    private Integer groupLimit;

//    @ApiModelProperty(value = "1 可用 2 不可用")
//    private Byte state;

    @NotNull
    @ApiModelProperty("备注")
    private String description;

    @NotNull
    @ApiModelProperty(value = "体验时段 1 上午 2 下午 3 全天")
    private Byte period;
}
