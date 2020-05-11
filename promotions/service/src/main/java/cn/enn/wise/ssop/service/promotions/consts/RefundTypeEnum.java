package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 退款方式枚举
 */
@BusinessEnum
@Getter
public enum RefundTypeEnum {

    NORMAL("refundType", new Byte("1"),"常规退款"),
    REFUSE("refundType", new Byte("2"),"不予退款");

    private String name;
    private Byte value;
    private String type;

    /**
     * @param type
     * @param value
     * @param name
     */
    RefundTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
