package cn.enn.wise.platform.mall.bean.bo;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * @author Administrator
 */
@Data
public class GoodsProjectOperationProjectBo extends  GoodsProjectOperationBo {
    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
}
