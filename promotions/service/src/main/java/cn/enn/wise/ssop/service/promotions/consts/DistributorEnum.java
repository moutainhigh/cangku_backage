package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 耿小洋
 * 分销商枚举
 */
@BusinessEnum
@Getter
public enum DistributorEnum {

    /*
     *分销商基础信息
     */
    //分销商类型
    GROUP_ENTERPRISE("distributorType", new Byte("1"), "集团企业"),
    INDIVIDUAL_ENTERPRISE("distributorType", new Byte("2"), "个人企业"),
    INDIVIDUAL("distributorType", new Byte("3"), "个人"),

    // 分销身份审核状态
    BASE_TO_BE_AUDITED("verifyState", new Byte("1"), "待审核"),
    BASE_ADD_AUDIT_PASS("verifyState", new Byte("2"), "审核通过"),
    BASE_ADD_NO_AUDIT_PASS("verifyState", new Byte("3"), "审核未通过"),

    // 分销商状态
    EFFECTIVE("state", new Byte("1"), "有效"),
    INVALID("state", new Byte("2"), "无效"),

    // 分销商状态
    NOT_AVAILABLE_DISTRIBUTOR("verifyType", new Byte("1"), "草稿"),
    AVAILABLE_DISTRIBUTOR("verifyType", new Byte("2"), "保存成功的可用数据"),


    /**
     * 分销商业务信息
     */

    // 综合等级
    SUPER_LEVEL("level", new Byte("3"), "高级"),
    FIRST_LEVEL("level", new Byte("1"), "初级"),
    SECOND_LEVEL("level", new Byte("2"), "中级"),

    // 渠道类型
    RUN_DIRECTLY_BY_MANUFACTURER("channelType", new Byte("1"), "直营"),
    DISTRIBUTION("channelType", new Byte("2"), "分销"),

    // 资源类型
    HOTEL("resourceType", new Byte("1"), "酒店"),
    GUIDE("resourceType", new Byte("2"), "导游"),
    RENT_CAR("resourceType", new Byte("3"), "租车"),

    // 业务范围
    BUSINESS_HOTEL("businessScope", new Byte("1"), "酒店"),
    BUSINESS_SPECIALTY("businessScope", new Byte("2"), "特产"),

    // 合作方式
    ONLINE_OPERATION("cooperationMethod", new Byte("1"), "线上运营"),
    OFFLINE_OPERATION("cooperationMethod", new Byte("2"), "线下运营"),

    // 结算方式
    BASE_PRICE_SETTLEMENT("settlementMethod", new Byte("1"), "底价结算"),
    REBATE_SETTLEMENT("settlementMethod", new Byte("2"), "返利结算"),

    // 结算周期
    MONTHLY_KNOTS("settlementType", new Byte("1"), "月结"),
    DAILY_KNOTS("settlementType", new Byte("2"), "日结"),


    /**
     * 补充信息
     */

    // 提现账户类型
    WEICHAT("withdrawAccountType", new Byte("1"), "微信"),
    BANKCARD("withdrawAccountType", new Byte("2"), "银行卡"),


    // 证件类型
    BUSINESS_LICENSE("documentType", new Byte("1"), "营业执照"),
    ID_CARD("documentType", new Byte("2"), "身份证"),
    DRIVER_CARD("documentType", new Byte("3"), "驾驶证"),
    GUIDE_CARD("documentType", new Byte("4"), "导游证"),
    ELECTRONICCONTRACT("documentType", new Byte("5"), "电子合同"),
    CONTRACTSACNFILE("documentType", new Byte("6"), "电子合同扫描件"),


    /**
     * 分销商账号信息
     */

    // 是否默认密码
    IS_DEFAULT_PASSWORD("isdefaultPassword", new Byte("1"), "默认"),
    IS_NOT_DEFAULT_PASSWORD("isdefaultPassword", new Byte("2"), "不默认"),


    /**
     * 分销商保存轨迹
     */

    // 是否默认密码
    EDITSTEP1("editStep", new Byte("1"), "基础信息"),
    EDITSTEP2("editStep", new Byte("2"), "联系人信息"),
    EDITSTEP3("editStep", new Byte("3"), "财务信息"),
    EDITSTEP4("editStep", new Byte("4"), "业务信息"),
    EDITSTEP5("editStep", new Byte("5"), "补充信息"),


    ;

    private String name;
    private Byte value;
    private String type;

    /**
     * @param type
     * @param value
     * @param name
     */
    DistributorEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
