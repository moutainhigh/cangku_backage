package cn.enn.wise.ssop.api.order.dto.response.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("票务信息")
public class TicketInfoBean {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("0待出票 -1 出票失败/已取消 1出票成功 100已检票 2230退成功已结款 410已改签")
    private Integer ticketStateBbd;

    @ApiModelProperty("票号")
    private String ticketSerialBbd;
}
