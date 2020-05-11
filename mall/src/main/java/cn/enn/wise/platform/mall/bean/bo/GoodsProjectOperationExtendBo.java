package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsProjectOperationExtendBo extends  GoodsProjectOperationBo {

    @ApiModelProperty(value = "项目Id：1 帆船 2 游艇")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
}
