package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 抽奖资格枚举
 */
@BusinessEnum
@Getter
public enum DrawManConditionEnum {

    NUMBER_OF_PEOPLE("drawManCondition", new Byte("1"),"人数"),
    SUM_OF_MONEY("drawManCondition", new Byte("2"),"订单金额"),
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
    DrawManConditionEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
