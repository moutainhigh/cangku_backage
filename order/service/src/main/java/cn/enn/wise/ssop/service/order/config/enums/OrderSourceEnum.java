package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum OrderSourceEnum {

    TICKET_SOURCE_ALL(ConstantValue.TICKET_ORDER_SOURCE,(byte)1,"全部来源"),
    TICKET_GONGZHONG(ConstantValue.TICKET_ORDER_SOURCE,(byte)2,"大峡谷公众号"),
    TICKET_FRONT(ConstantValue.TICKET_ORDER_SOURCE,(byte)3,"酒店前台推荐（场景）"),
    TICKET_CHUANGTOU(ConstantValue.TICKET_ORDER_SOURCE,(byte)4,"酒店床头推荐（场景）");

    private String type;

    private byte value;

    private String name;
}