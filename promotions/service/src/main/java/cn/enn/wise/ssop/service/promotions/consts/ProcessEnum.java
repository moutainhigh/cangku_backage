package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动类型枚举
 */
@BusinessEnum
@Getter
public enum ProcessEnum {

    NOT_START("processType", new Byte("1"),"未处理"),
    DOING("processType", new Byte("2"),"处理中"),
    DONE("processType", new Byte("3"),"完成");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    ProcessEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
