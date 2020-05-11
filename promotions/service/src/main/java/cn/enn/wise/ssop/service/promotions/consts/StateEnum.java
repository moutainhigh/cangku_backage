package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动是否有效类型枚举
 */
@BusinessEnum
@Getter
public enum StateEnum {

    ONGOING("stateType", new Byte("1"),"活动中"),
    NOT_START("stateType", new Byte("2"),"未开始"),
    FINISH("stateType", new Byte("3"),"结束"),
    ENABLE("stateType", new Byte("4"),"已失效");



    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    StateEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
