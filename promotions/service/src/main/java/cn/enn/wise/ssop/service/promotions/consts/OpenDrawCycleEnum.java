package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 开奖频率枚举
 */
@BusinessEnum
@Getter
public enum OpenDrawCycleEnum {

    ONCE_PEER_DAY("openDrawCycle", new Byte("1"),"每天（一次）"),
    ONCE_PEER_ACTIVITY("openDrawCycle", new Byte("2"),"活动终结（一次）");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    OpenDrawCycleEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
