package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.OperationBo;
import cn.enn.wise.platform.mall.bean.bo.OperationPeriodBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class OperationResultVo {

    @ApiModelProperty(value = "预计流量")
    private Integer predictionCount;
    @ApiModelProperty(value = "已入园人数")
    private Integer enterCount;
    @ApiModelProperty(value = "客流预测")
    private List<OperationBo> operationBoList;
    @ApiModelProperty(value = "时段管理")
    private List<OperationPeriodBo> operationPeriodBoList;

    @ApiModelProperty(value = "是否分时段1分时段，2不分时段")
    private Integer isPeriod;

    @ApiModelProperty(value = "项目运营因素")
    private ProjectOperationStatusResponseAppVO operationStatus;
}
