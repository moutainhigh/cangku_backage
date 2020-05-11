package cn.enn.wise.ssop.service.order.config.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RefundStatusEnum {

    TICKET_WAIT_REFUND(1, "待退款"),
    TICKET_DOING_REFUND(2, "退款中"),
    TICKET_SUCCESS_REFUND(3, "退款成功"),
    TICKET_FAILURE_REFUND(4, "退款失败"),
    TICKET_FINISH_REFUND(5, "已退款"),

    HOTEL_WAIT_CHECK(1, "待审核"),
    HOTEL_CONFIRM(2, "已确认"),
    HOTEL_UN_REFUND(3, "不退款"),
    HOTEL_SUCCESS_REFUND(4, "退款成功"),
    HOTEL_FAILURE_REFUND(5, "退款失败");


    private int value;

    private String name;

}
