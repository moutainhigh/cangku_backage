package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum PayTypeEnum {
    PAY_TYPE_WX(ConstantValue.PAY_TYPE,(byte)1,"微信"),
    PAY_TYPE_ZFB(ConstantValue.PAY_TYPE,(byte)2,"支付宝 "),
    PAY_TYPE_YSF(ConstantValue.PAY_TYPE,(byte)3,"云闪付");

    private String type;

    private byte value;

    private String name;
}
