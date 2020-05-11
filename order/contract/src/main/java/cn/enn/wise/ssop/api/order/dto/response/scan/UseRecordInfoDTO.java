package cn.enn.wise.ssop.api.order.dto.response.scan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("订单用户记录列表")
@Data
public class UseRecordInfoDTO {


    @ApiModelProperty("待使用数量")
    private int toBeUsedCount;//待使用数量

    @ApiModelProperty("已核验数量")
    private int usedCount;//已核验数量

    @ApiModelProperty("已退款数量")
    private int refundCount;//已退款数量

    @ApiModelProperty("使用记录列表")
    private List<UseRecordDTO> useRecordList;//使用记录列表

}
