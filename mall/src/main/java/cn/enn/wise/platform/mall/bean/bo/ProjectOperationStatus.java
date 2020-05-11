package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目运营因素实体
 *
 * @author baijie
 * @date 2019-08-26
 */
@Data
@ApiModel(value = "项目运营因素实体")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_operation_factors")
public class ProjectOperationStatus {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty("因素类型 1 运营时段因素 2 非时段因素")
    private Integer factorType;

    @ApiModelProperty("运营状态1 正常 2 停止")
    private Integer operationStatus;

    @ApiModelProperty("1 数值 2 文字标签")
    private Integer operationStatusType;

    @ApiModelProperty("文字标签值")
    private String label;

    @ApiModelProperty("状态 1 有效 2无效")
    private  Integer status;

    @ApiModelProperty("状态文字描述")
    private String descValue;

    @ApiModelProperty("原因,多个原因用逗号分割")
    private String reason;

    @ApiModelProperty("影响范围程度")
    private String degreeOfInfluence;

    @ApiModelProperty("是否在小程序端展示 1是 2 否")
    private Integer isShowApplet;


}
