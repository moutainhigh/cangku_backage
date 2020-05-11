package cn.enn.wise.ssop.api.promotions.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 安辉
 * 保存活动基本信息参数
 */
@Data
@ApiModel("添加活动基本信息参数")
public class ActivityBaseAddParam {

    /**
     * 主键
     * id == 0 表示添加
     * id != 0 表示更新
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 景区Id
     */
    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    private String scenicName;

    /**
     * 活动类型 1 优惠活动 2 拼团活动 3 抽奖活动
     */
    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;

    /**
     * 活动目标
     */
    @ApiModelProperty("活动目标")
    @NotNull
    private String activityAim;

    /**
     * 活名称
     */
    @ApiModelProperty("活名称")
    private String activityName;

    /**
     * 活动标题
     */
    @ApiModelProperty("活动标题")
    private String activityTitle;

    /**
     * 活动开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动结束时间")
    private String endTime;

    /**
     * 状态 1 正在进行中  2 未开始
     */
    @ApiModelProperty("状态 1 正在进行中  2 未开始")
    private Byte state;

    /**
     * 预计成本
     */
    @ApiModelProperty("预计成本")
    private Integer cost;

    /**
     * 活动负责人
     */
    @ApiModelProperty("活动负责人")
    private String activityLeador;

    /**
     * 负责人电话
     */
    @ApiModelProperty("负责人电话")
    private String leadorPhone;

    /**
     * 活动总量
     */
    @ApiModelProperty("活动总量")
    private Integer activityCount;

    /**
     * 活动描述
     */
    @ApiModelProperty("活动描述")
    private List<ActivityDescriptionParam> description;

    /**
     * 分摊部门
     */
    @ApiModelProperty("分摊部门")
    private List<ActivityBaseDivideDepartmentParam> departmentList;

    /**
     * 客群
     */
    @ApiModelProperty("客群")
    private List<Long> activityGroup;

    /**
     * 推广图
     */
    @ApiModelProperty("推广图")
    private String generalizeImg;

    /**
     * 景区id
     */
    @ApiModelProperty("景区id")
    private Long scenicAreaId;
}
