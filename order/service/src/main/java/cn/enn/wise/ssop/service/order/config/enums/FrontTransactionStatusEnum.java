package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@BusinessEnum
@Getter
public enum FrontTransactionStatusEnum {

    TICKET_ALL(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)-1,"全部"),
    TICKET_WAITPAY(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)2,"待付款"),
    TICKET_HAVEPAY(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)3,"买家已付款"),
    TICKET_CONFIRM(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)4,"系统已确认"),
    TICKET_CHUPIAO(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)5,"已出票"),


    TICKET_FINISH(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)1,"交易完成"),
    TICKET_CLOSE(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)2,"交易关闭"),
    TICKET_WAITCHECK(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)3,"退票待审核"),
    TICKET_DOING(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)4,"退票处理中"),
    TICKET_CONFIRM_TIMEOUT(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)5,"退票完成"),


    TICKET_TUIKUAN_TIMEOUT(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)4,"退款超时"),
    TICKET_CHUPIAO_TIMEOUT(ConstantValue.TICKET_TRANSACTION_STATUS,(byte)5,"出票超时");
    private String type;

    private byte value;

    private String name;
}
