package cn.enn.wise.ssop.api.order.dto.response.scan;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("流转记录")
public class UseRecordDTO {

    @ApiModelProperty("流转动作名称")
    private String operateDes;//订单动作名称 比如 出票 核验

    @ApiModelProperty("操作时间")
    private String operateTime;//操作时间 2020-02-20 10：00：00

    @ApiModelProperty("操作人姓名")
    private String operaterName;//操作人姓名

    @ApiModelProperty("操作数量")
    private int operateCount;//操作数量

}
