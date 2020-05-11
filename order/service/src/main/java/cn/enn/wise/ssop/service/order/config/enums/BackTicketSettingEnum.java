package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum BackTicketSettingEnum {

    BACK_TICKET_SETTINTG_ALL(ConstantValue.BACK_TICKET_SETTING,(byte)1,"全额退款"),
    BACK_TICKET_SETTINTG_RATE(ConstantValue.BACK_TICKET_SETTING,(byte)2,"按比例退款"),
    BACK_TICKET_SETTINTG_NUMBER(ConstantValue.BACK_TICKET_SETTING,(byte)3,"扣除固定金额");

    private String type;

    private byte value;

    private String name;
}
