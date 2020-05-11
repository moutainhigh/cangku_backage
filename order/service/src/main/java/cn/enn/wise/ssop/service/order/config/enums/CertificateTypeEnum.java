package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum CertificateTypeEnum {
    CERTIFICATE_TYPE_SFZ(ConstantValue.CERTIFICATE_TYPE,(byte)1,"身份证"),
    CERTIFICATE_TYPE_HZ(ConstantValue.CERTIFICATE_TYPE,(byte)2,"护照 "),
    CERTIFICATE_TYPE_XS(ConstantValue.CERTIFICATE_TYPE,(byte)3,"学生");

    private String type;

    private byte value;

    private String name;
}
