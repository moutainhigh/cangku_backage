package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 抽奖条件枚举
 */
@BusinessEnum
@Getter
public enum DrawOrderConditionEnum {

    SINGLETON("drawOrderCondition", new Byte("1"),"单笔订单"),
    MULTIPLE("drawOrderCondition", new Byte("2"),"多笔订单"),
    ;

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    DrawOrderConditionEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
