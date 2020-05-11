package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 抽奖方式枚举
 */
@BusinessEnum
@Getter
public enum DrawTypeEnum {

    SPEED_DIAL("drawType", new Byte("1"),"九宫格"),
    TURNTABLE("drawType", new Byte("2"),"转盘"),
    SHAKE("drawType", new Byte("3"),"摇一摇");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    DrawTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
