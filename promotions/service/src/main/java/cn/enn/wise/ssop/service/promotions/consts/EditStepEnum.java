package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动类型枚举
 */
@BusinessEnum
@Getter
public enum EditStepEnum {

    One("editStep", new Byte("1"),"基础信息"),
    TWO("editStep", new Byte("2"),"规则"),
    THREE("editStep", new Byte("3"),"分享设置"),
    FOUR("editStep", new Byte("4"),"跟踪设置");


    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    EditStepEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
