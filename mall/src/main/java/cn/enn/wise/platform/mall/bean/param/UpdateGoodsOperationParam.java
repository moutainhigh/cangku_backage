package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class UpdateGoodsOperationParam {

    @ApiModelProperty(value = "时间集合 格式:yyyy-MM-dd")
    private List<Date> dateList;

    @ApiModelProperty(value = "状态")
    private int status = -1;

    @ApiModelProperty(value = "项目编号")
    private int projectId = -1;

    @ApiModelProperty(value = "地点")
    private int servicePlaceId = -1;

    @ApiModelProperty(value = "原因")
    private String remark = "";

    @ApiModelProperty(value = "概率")
    private int probability = -1;

    @ApiModelProperty(value = "影响")
    private String degreeOfInfluence = "";
}
