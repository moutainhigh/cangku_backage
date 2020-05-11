package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum  SearchCriteriaEnum {
    SEARCH_ORDERNO(ConstantValue.SEARCH_CRITERIA,(byte)1,"订单编号"),
    SEARCH_NAME(ConstantValue.SEARCH_CRITERIA,(byte)2,"游客名称"),
    SEARCH_CERTIFICATENO(ConstantValue.SEARCH_CRITERIA,(byte)3,"游客身份证号"),
    SEARCH_PHONE(ConstantValue.SEARCH_CRITERIA,(byte)4,"联系人手机号");

    private String type;

    private byte value;

    private String name;
}
