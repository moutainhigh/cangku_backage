package cn.enn.wise.ssop.service.order.config.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SystemStatusEnum {

    TICKET_CHECK(10, "退票审核中"),
    TICKET_CHECK_UN_PASS(11, "退票审核未通过"),
    TICKET_CHECK_PASS(12, "退票审核通过"),


    HOTEL_CONFIRM(1, "已确认"),
    HOTEL_WAIT_CHECK(2, "待审核"),
    HOTEL_REFUSE(3, "已拒绝");


    private int value;

    private String name;
}
