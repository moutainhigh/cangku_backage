package cn.enn.wise.ssop.service.order.config.status;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    TICKET_WAIT_PAY(1, "待支付"),
    TICKET_TIMEOUT_CANCEL(2, "超时取消"),
    TICKET_CANCEL(3, "已取消"),
    TICKET_WAIT_USE(4, "待使用"),
    TICKET_USED(5, "已使用"),
    TICKET_CLOSE(6, "已关闭"),
    TICKET_FINISH(7, "已完成"),
    TICKET_LOCK(8, "锁定"),



    HOTEL_WAIT_USE(1,"待使用"),
    HOTEL_USED(2,"已使用"),
    HOTEL_CANCEL(3,"已取消"),
    HOTEL_FINISH(4,"已完成"),
    HOTEL_TIMEOUT_CANCEL(5,"超时未支付已取消"),
    HOTEL_WAIT_PAY(6, "待支付");

    private int value;

    private String name;

}