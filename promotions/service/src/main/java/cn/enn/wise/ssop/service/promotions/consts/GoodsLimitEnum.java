package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 产品范围枚举
 */
@BusinessEnum
@Getter
public enum GoodsLimitEnum {

//    ALL("goodsLimitType", new Byte("1"),"全部商品"),
    ALLOCATE("goodsLimitType", new Byte("2"),"指定商品");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    GoodsLimitEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
