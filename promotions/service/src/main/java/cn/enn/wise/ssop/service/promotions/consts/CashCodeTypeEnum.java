package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 兑奖表示枚举
 */
@BusinessEnum
@Getter
public enum CashCodeTypeEnum {

    PHONE("cashCode", new Byte("1"),"手机号"),
    WE_CHAT_NUMBER("cashCode", new Byte("2"),"微信号"),
    ID_CARD_NUMBER("cashCode", new Byte("3"),"身份证"),
    ADDRESS("cashCode", new Byte("4"),"地址");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    CashCodeTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
