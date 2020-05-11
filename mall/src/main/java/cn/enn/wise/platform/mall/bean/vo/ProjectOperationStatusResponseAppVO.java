package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 项目运营因素APP返回VO
 *
 * @author baijie
 * @date 2019-08-27
 */
@Data
@ApiModel("运营状态APP返回Vo")
public class ProjectOperationStatusResponseAppVO {

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty("因素类型 1 运营时段因素 2 非时段因素")
    private Integer factorType;

    @ApiModelProperty("1 数值 2 文字标签")
    private Integer operationStatusType;

    @ApiModelProperty("文字标签值")
    private String label;

    @ApiModelProperty("状态 1 有效 2无效")
    private  Integer status;

    @ApiModelProperty("是否在小程序端展示 1是 2 否")
    private Integer isShowApplet;

    @ApiModelProperty("运营因素描述VO集合")
    private List<ProjectOperationStatusDescVo> descVos;
}
