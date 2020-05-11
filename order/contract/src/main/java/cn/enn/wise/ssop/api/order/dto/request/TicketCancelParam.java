package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TicketCancelParam {

    @ApiModelProperty("订单Id")
    private Long orderId;

    @ApiModelProperty("订单标号")
    private String orderNo;

    @ApiModelProperty("子订单Id")
    private List<Long> orderIdList;

    @ApiModelProperty("退款方式 1 全额退款 2 按比例退款 3 扣除固定金额 ")
    private Byte refundType;

    @ApiModelProperty("退款比例/扣除金额")
    private BigDecimal refundRate;

    @ApiModelProperty("退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty("退款原因种类")
    private Byte refundReasonType;

    @ApiModelProperty("退款原因详细")
    private String refundReasonDesc;

    @ApiModelProperty("是否保留优惠  1不保留 2 保留")
    private Byte benefitOption;

    private Integer checkStatus;
}
