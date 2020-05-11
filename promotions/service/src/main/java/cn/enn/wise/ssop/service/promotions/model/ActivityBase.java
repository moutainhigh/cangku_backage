package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动基础信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_base")
public class ActivityBase extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "activity_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "活动编号")
    @ApiModelProperty("活动编号")
    private String activityCode;

    @ApiModelProperty(value = "景区Id")
    @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区Id")
    private Long scenicId;

    @Column(name = "scenic_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "景区名称")
    @ApiModelProperty("景区名称")
    private String scenicName;

    @Column(name = "activity_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;

    @Column(name = "activity_aim",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "活动目标")
    @ApiModelProperty("活动目标")
    private String activityAim;

    @Column(name = "activity_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "活动名称")
    @ApiModelProperty("活名称")
    private String activityName;

    @Column(name = "activity_title",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "活动标题")
    @ApiModelProperty("活动标题")
    private String activityTitle;

    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "活动开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动开始时间")
    private Timestamp startTime;

    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "活动结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("活动结束时间")
    private Timestamp endTime;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "状态 1未开始 2活动中 3已结束 4 已失效")
    @ApiModelProperty("状态 1未开始 2活动中 3已结束 4 已失效")
    private Byte state;

    @Column(name = "cost",type = MySqlTypeConstant.INT,length = 12,comment = "预计成本")
    @ApiModelProperty("预计成本")
    private Integer cost;

    @Column(name = "activity_leador",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "活动负责人")
    @ApiModelProperty("活动负责人")
    private String activityLeador;

    @Column(name = "leador_phone",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "负责人电话")
    @ApiModelProperty("负责人电话")
    private String leadorPhone;

    @Column(name = "divide_department",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "分摊部门 [{divideDepartment:\"部门1\",“divideRatio”:\"10\"}]")
    @ApiModelProperty("分摊部门 [{divideDepartment:\"部门1\",“divideRatio”:\"10\"}]")
    private String divideDepartment;

    @Column(name = "divide_ratio",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "分摊比例")
    @ApiModelProperty("分摊比例")
    @Deprecated
    private String divide_ratio;

    @Column(name = "activity_count",type = MySqlTypeConstant.INT,length = 12,comment = "活动总量")
    @ApiModelProperty("活动总量")
    private Integer activityCount;

    @Column(name = "description",type = MySqlTypeConstant.TEXT, comment = "活动描述")
    @ApiModelProperty("活动描述")
    private String description;

    @Column(name = "activity_group",type = MySqlTypeConstant.VARCHAR, comment = "客群")
    @ApiModelProperty("客群")
    private String activityGroup;

    @Column(name = "generalize_img",type = MySqlTypeConstant.VARCHAR, comment = "推广图")
    @ApiModelProperty("推广图")
    private String generalizeImg;

    @Column(name = "edit_step",type = MySqlTypeConstant.TINYINT, comment = "编辑步骤")
    @ApiModelProperty("编辑步骤")
    private Byte editStep;

    @Column(name = "scenic_area_id",type = MySqlTypeConstant.BIGINT, comment = "景区id")
    @ApiModelProperty("景区id")
    private Long scenicAreaId;
}
