package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动类型枚举
 */
@BusinessEnum
@Getter
public enum ActivityTypeEnum {

    DEDUCTION("activityType", new Byte("1"),"优惠活动"),
    GROUPON("activityType", new Byte("2"),"拼团活动"),
    DRAW("activityType", new Byte("3"),"抽奖活动"),

    //优惠类型
    RESERVATIONROLE("discountRoleType", new Byte("1"),"早定优惠"),
    SALEROLE("discountRoleType", new Byte("2"),"特价优惠"),
    MINUSRULE("discountRoleType", new Byte("3"),"满减优惠"),

    DISCOUNTMODE_COUPONS("discountMode", new Byte("1"),"优惠券"),
    DISCOUNTMODE_MINUS("discountMode", new Byte("2"),"减免"),

    SALEMODE_MONEY("saleMode", new Byte("1"),"金额"),
    SALEMODE_SALE("saleMode", new Byte("2"),"折扣"),

    PREFERENTIALTERMS_MONEY("preferentialTerms", new Byte("1"),"订单金额（元）"),
    PREFERENTIALTERMS_PERSON("preferentialTerms", new Byte("2"),"订单人头（位）"),

    ;

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    ActivityTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
