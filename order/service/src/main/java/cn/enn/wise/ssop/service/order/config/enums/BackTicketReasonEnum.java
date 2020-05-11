package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum BackTicketReasonEnum {

    BACK_TICKET_REASON_ALL(ConstantValue.BACK_TICKET_REASON,(byte)-1,"请选择"),
    BACK_TICKET_REASON_WEATHER(ConstantValue.BACK_TICKET_REASON,(byte)1,"天气原因"),
    BACK_TICKET_REASON_EVENT(ConstantValue.BACK_TICKET_REASON,(byte)2,"突发事件"),
    BACK_TICKET_REASON_SELF(ConstantValue.BACK_TICKET_REASON,(byte)3,"自定义原因");

    private String type;

    private byte value;

    private String name;
}
