package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BusinessEnum
public enum HotelOrderStatusEnum {
    HOTEL_WAIT_USE(ConstantValue.HOTEL_ORDER_STATUS,1,"待使用"),
    HOTEL_USED(ConstantValue.HOTEL_ORDER_STATUS,2,"已使用"),
    HOTEL_CANCEL(ConstantValue.HOTEL_ORDER_STATUS,3,"已取消"),
    HOTEL_FINISH(ConstantValue.HOTEL_ORDER_STATUS,4,"已完成"),
    HOTEL_TIMEOUT_CANCEL(ConstantValue.HOTEL_ORDER_STATUS,5,"超时未支付已取消"),
    HOTEL_WAIT_PAY(ConstantValue.HOTEL_ORDER_STATUS,6, "待支付");

    private String type;

    private int value;

    private String name;

}