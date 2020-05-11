package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 兑奖时间标识
 */
@BusinessEnum
@Getter
public enum CashTimeTypeEnum {

    AFTER_ACTIVITY("cashTimeType", new Byte("1"),"活动结束后"),
    AFTER_DRAW("cashTimeType", new Byte("2"),"中奖后");
    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    CashTimeTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
