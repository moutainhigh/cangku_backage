package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动类型枚举
 */
@BusinessEnum
@Getter
public enum StarTypeEnum {

    TWO("starType", new Byte("2"),"二星"),
    THREE("starType", new Byte("3"),"三星"),
    FOUR("starType", new Byte("4"),"四星")
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
    StarTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
