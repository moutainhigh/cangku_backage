package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationPeriodBo {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "时段")
    private String title;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "状态-文本")
    private String statusTxt;
    @ApiModelProperty(value = "体验概率")
    private String probability;
    @ApiModelProperty(value = "影响范围")
    private String degreeOfInfluence;
}
