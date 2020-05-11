package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum  OrderTypeEnum{

    DISTRIBUTOR("orderType",(byte)1,"分销订单"),
    GENERAL("orderType",(byte)2,"普通订单");

    private String type;

    private byte value;

    private String name;



}
