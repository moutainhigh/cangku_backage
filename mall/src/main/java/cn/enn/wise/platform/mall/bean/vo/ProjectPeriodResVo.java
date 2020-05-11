package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 项目时段运营信息响应类
 *
 * @author caiyt
 * @since 2019-05-28
 */
@Data
@ApiModel(value = "项目时段运营信息响应类")
public class ProjectPeriodResVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "所属项目ID")
    private Long projectId;

    @ApiModelProperty(value = "运营时段[08:00-09:00]")
    private String title;

    @ApiModelProperty(value = "开始时间[08:00]")
    private String startTime;

    @ApiModelProperty(value = "结束时间[09:00]")
    private String endTime;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    @ApiModelProperty(value = "更新时间")
    private Integer updateTime;

    @ApiModelProperty(value = "更新人ID")
    private Long updateUserId;

    @ApiModelProperty(value = "修改人名称")
    private String updateUserName;

    @ApiModelProperty(value = "排序字段")
    private Integer orderby;

    @ApiModelProperty(value = "状态 1-启用 2-禁用")
    private byte status;

    @ApiModelProperty(value = "可编辑状态 true-可编辑 false-不可编辑")
    private boolean editable;
}