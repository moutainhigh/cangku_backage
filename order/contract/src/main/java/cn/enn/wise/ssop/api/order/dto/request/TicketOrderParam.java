package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 票类商品订单参数，通常指门票；
 * 票类订单的流程特征：
 * 1、需要对应闸机核销；
 * 2、通常有纸质票；
 * 3、一人一票等？
 */
@Data
public class TicketOrderParam extends BaseOrderParam{

    /**
     * 第三方票号
     */
    @ApiModelProperty(value = "第三方票号")
    private String ticketNo;

    /**
     * 第三方票务系统标识
     */
    @ApiModelProperty(value = "第三方票务系统标识")
    private String ticketSystemCode;

    /**
     * 第三方订单编号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;
}
