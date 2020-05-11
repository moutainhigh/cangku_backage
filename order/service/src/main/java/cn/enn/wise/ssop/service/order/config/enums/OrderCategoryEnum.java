package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum OrderCategoryEnum {
    //订单类型 1 门票 2 体验项目 3 住宿 4 套餐 5 导游 6 热气球 7 缆车 8 租车 9 餐饮 10 体验券
    TICKET(ConstantValue.ORDER_CATEGORY,(byte)1,"门票"),
    TIYAN(ConstantValue.ORDER_CATEGORY,(byte)2,"体验项目"),
    HOTEL(ConstantValue.ORDER_CATEGORY,(byte)3,"酒店"),
    TAOCAN(ConstantValue.ORDER_CATEGORY,(byte)4,"套餐"),
    DAOYOU(ConstantValue.ORDER_CATEGORY,(byte)5,"导游"),
    REQIQIU(ConstantValue.ORDER_CATEGORY,(byte)6,"热气球"),
    LANCHE(ConstantValue.ORDER_CATEGORY,(byte)7,"缆车"),
    ZUCHE(ConstantValue.ORDER_CATEGORY,(byte)8,"租车"),
    CANYIN(ConstantValue.ORDER_CATEGORY,(byte)9,"餐饮"),
    TIYAN_TICKET(ConstantValue.ORDER_CATEGORY,(byte)10,"体验券"),
    ;

    private String type;

    private byte value;

    private String name;

}
