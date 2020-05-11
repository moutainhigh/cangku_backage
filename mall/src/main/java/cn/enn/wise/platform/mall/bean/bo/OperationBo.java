package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationBo {
    @ApiModelProperty(value = "时段")
    private String title;
    @ApiModelProperty(value = "预订数")
    private String bookingCount;
    @ApiModelProperty(value = "完成数量")
    private String completeCount;
    @ApiModelProperty(value = "可承载的数量")
    private String fullCount;
    @ApiModelProperty(value = "概率")
    private String probability;
    @ApiModelProperty(value = "概率")
    private Byte status;
    @ApiModelProperty(value = "服务地点")
    private Long servicePlaceId;
    @ApiModelProperty(value = "id")
    private Long periodId;
    @ApiModelProperty(value = "影响级别")
    private String degreeOfInfluence;
}
