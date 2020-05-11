package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安辉
 * 分摊部门
 */
@Data
@ApiModel("分摊部门")
public class ActivityBaseDivideDepartmentDTO {

    @ApiModelProperty("分摊部门")
    private String divideDepartment;

    @ApiModelProperty("分摊比例")
    private String divideRatio;

}
