package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安辉
 * 分摊部门
 */
@Data
@ApiModel("分摊部门")
public class ActivityBaseDivideDepartmentParam {

    @ApiModelProperty("分摊部门")
    private String divideDepartment;

    @ApiModelProperty("分摊比例")
    private String divideRatio;

}
