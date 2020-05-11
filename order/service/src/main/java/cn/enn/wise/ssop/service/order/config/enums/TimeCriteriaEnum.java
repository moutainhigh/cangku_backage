package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum TimeCriteriaEnum {
    TIME_IN(ConstantValue.TIME_CRITERIA,(byte)1,"入住时间"),
    TIME_ORDER(ConstantValue.TIME_CRITERIA,(byte)2,"下单时间");

    private String type;

    private byte value;

    private String name;
}
