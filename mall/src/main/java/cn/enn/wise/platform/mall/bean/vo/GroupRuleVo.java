package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/12
 */
@Data
@ApiModel("规则列表实体类")
public class GroupRuleVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("策略名称")
    private String name;

    @ApiModelProperty("操作人")
    private String createBy;

    @ApiModelProperty("操作日期")
    private String createTime;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte status;

    @ApiModelProperty("规则id")
    private String isGroupRule;

    @ApiModelProperty("类型 1 团购 2 营销")
    private Byte type;

    @ApiModelProperty("成团人数")
    private Integer size;

    @ApiModelProperty("限购")
    private Integer limit;

    @ApiModelProperty("自动成团")
    private Integer autoFinish;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("有效期")
    private Byte groupValidHours;

    @ApiModelProperty("时段 1 上午 2 下午 3 全天")
    private Byte period;

    @ApiModelProperty("最低成团人数")
    private Byte autoCreateGroupLimit;

    @ApiModelProperty("成团人数")
    private Integer groupSize;

    @ApiModelProperty("限购")
    private Integer groupLimit;

    @ApiModelProperty("自动成团")
    private Byte isAutoCreateGroup;

    @ApiModelProperty("使用中")
    private Byte isInUse;




}
