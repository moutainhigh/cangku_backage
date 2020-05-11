package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum BackTicketBenefitEnum {

    BACK_TICKET_BENEFIT_CANCEL(ConstantValue.BACK_TICKET_BENEFIT,(byte)1,"优惠撤销"),
    BACK_TICKET_BENEFIT_KEEP(ConstantValue.BACK_TICKET_BENEFIT,(byte)2,"优惠保留");

    private String type;

    private byte value;

    private String name;
}
