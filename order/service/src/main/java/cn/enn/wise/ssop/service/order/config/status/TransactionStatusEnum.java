package cn.enn.wise.ssop.service.order.config.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionStatusEnum {

    TICKET_CHECK(1, "核销中"),
    TICKET_CHECK_UN_PASS(2, "已核销"),
    TICKET_CHECK_PASS(3, "部分核销"),
    TICKET_CHECK_LOCK(4, "已核销(锁定)"),
    TICKET_GET_LOCK(5, "已取票(锁定)"),
    TICKET_BACK(6, "已退票"),
    TICKET_GET(7, "已取票"),
    TICKET_EXPIRED(8, "已过期"),
    TICKET_LOCK(9, "锁定"),


    HOTEL_WAIT_CONFIRM(1, "待确认"),
    HOTEL_WAIT_ZHU(2, "待入住"),
    HOTEL_CONFIRM_FAILURE(3, "确认失败"),
    HOTEL_CANCEL(4, "已取消"),
    HOTEL_REFUSE(5, "已拒绝"),
    HOTEL_ZHU(6, "已入住"),
    HOTEL_BACK_ZHU(7, "已退房"),
    HOTEL_ZHU_TIMEOUT(8, "超时入住"),
    HOTEL_BACK_ZHU_TIMEOUT(9, "超时已退房");


    private int value;

    private String name;
}
