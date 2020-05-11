package cn.enn.wise.ssop.service.order.config.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    UN_PAY(1, "待支付"),
    PAYED(2, "已支付");

    private int value;

    private String name;
}