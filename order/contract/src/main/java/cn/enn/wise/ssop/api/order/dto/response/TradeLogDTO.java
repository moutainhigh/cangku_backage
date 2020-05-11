package cn.enn.wise.ssop.api.order.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


@ApiModel("交易记录列表条目返回数据")
@Data
public class TradeLogDTO {

    @ApiModelProperty("总交易金额")
    private String allAmount;

    @ApiModelProperty("订单数")
    private Long tradeSize;

    @ApiModelProperty("交易列表")
    private List<TradeDTO> tradeDTOList;


}