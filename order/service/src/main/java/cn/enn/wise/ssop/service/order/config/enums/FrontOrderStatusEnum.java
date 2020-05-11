package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@BusinessEnum
public enum FrontOrderStatusEnum {

    TUIPIAO_INITIAL(ConstantValue.BACK_TICKET_STATUS,(byte)0,"正常"),
    TUIPIAO_CHECK(ConstantValue.BACK_TICKET_STATUS,(byte)1,"退票申请"),
    TUIPIAO_PART(ConstantValue.BACK_TICKET_STATUS,(byte)2,"部分退票"),
    TUIPIAO_CHECK_SUCCESS(ConstantValue.BACK_TICKET_STATUS,(byte)3,"退票审核通过"),
    TUIPIAO_CHECK_FAILED(ConstantValue.BACK_TICKET_STATUS,(byte)4,"退票审核未通过");

    private String type;

    private byte value;

    private String name;
}
