package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@BusinessEnum
@Getter
public enum FrontPayStatusEnum {

    ALL_PAY(ConstantValue.TICKET_PAY_STATUS,(byte)-1,"全部"),
    WAIT_PAY(ConstantValue.TICKET_PAY_STATUS,(byte)1,"未支付"),
    HAVE_PAY(ConstantValue.TICKET_PAY_STATUS,(byte)2,"已支付"),
    REFUND_WAIT_CHECK(ConstantValue.TICKET_PAY_STATUS,(byte)2,"退款待审核"),
    REFUND_SUCCESS(ConstantValue.TICKET_PAY_STATUS,(byte)2,"退款成功"),
    REFUND_TIMEOUT(ConstantValue.TICKET_PAY_STATUS,(byte)2,"退款超时");

    private String type;

    private byte value;

    private String name;
}
