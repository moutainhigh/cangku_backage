package cn.enn.wise.ssop.api.promotions.dto.response;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动基础信息表返回类型
 * @author 安辉
 */
@Data
@ApiModel("活动基础信息表返回类型")
public class ActivityBaseDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty("活动编号")
    private String activityCode;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;

    @ApiModelProperty("活动目标")
    private String activityAim;

    @ApiModelProperty("活名称")
    private String activityName;

    @ApiModelProperty("活动标题")
    private String activityTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动开始时间")
    private Timestamp startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动结束时间")
    private Timestamp endTime;

    @ApiModelProperty("状态 1 正在进行中  2 未开始")
    private Byte state;

    @ApiModelProperty("预计成本")
    private Integer cost;

    @ApiModelProperty("活动负责人")
    private String activityLeador;

    @ApiModelProperty("负责人电话")
    private String leadorPhone;

    @ApiModelProperty("活动总量")
    private Integer activityCount;

    @ApiModelProperty("活动描述")
    private JSONArray description;

    @ApiModelProperty("分摊部门")
    private JSONArray departmentList;

    @ApiModelProperty("创建人名称")
    private String creatorName;

    @ApiModelProperty("更改人名称")
    private String updatorName;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    @ApiModelProperty("客群")
    private JSONArray activityGroup;

    @ApiModelProperty("推广图")
    private String generalizeImg;

    @ApiModelProperty("编辑步骤")
    private Byte editStep;
}
