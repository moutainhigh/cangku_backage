package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author anhui@jytrip.net
 */
@AllArgsConstructor
@Getter
@BusinessEnum
public enum OrderSalesTypeEnum {

    PROMOTION("orderSalesType",(byte)1,"优惠"),
    GROUP_BUY("orderSalesType",(byte)2,"拼团"),
    DRAW("orderSalesType",(byte)3,"抽奖");

    private String type;
    private byte value;
    private String name;
}
