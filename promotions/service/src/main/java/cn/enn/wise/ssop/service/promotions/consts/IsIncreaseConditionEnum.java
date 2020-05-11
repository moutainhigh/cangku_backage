package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 抽奖次数是否递增
 */
@BusinessEnum
@Getter
public enum IsIncreaseConditionEnum {

    YES("isIncreaseCondition", new Byte("1"),"是"),
    NOT("isIncreaseCondition", new Byte("2"),"否"),
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
    IsIncreaseConditionEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
