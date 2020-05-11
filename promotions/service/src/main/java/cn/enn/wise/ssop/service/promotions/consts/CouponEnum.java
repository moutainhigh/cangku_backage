package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

@BusinessEnum
@Getter
public enum CouponEnum {

    GOODS_SPECIAL_YES("couponGoodsSpecialEnum",new Byte("1"), "是"),
    GOODS_SPECIAL_NO("couponGoodsSpecialEnum",new Byte("2"), "不是"),

    STATE_YES("couponStateEnum",new Byte("1"), "有效"),
    STATE_NO("couponStateEnum",new Byte("2"), "无效"),

    EXPERIENCE("couponTypeEnum", new Byte("1"), "体验券"),
    FULL_REDUCE( "couponTypeEnum", new Byte("2"), "满减券"),
    CASH("couponTypeEnum", new Byte("3"), "代金券"),

    USER_COUPON_STATE_SEND("userOfCouponsState",new Byte("1"),"未领取"),
    USER_COUPON_STATE_RECEIVED("userOfCouponsState",new Byte("2"),"领取未使用"),
    USER_COUPON_STATE_USE("userOfCouponsState",new Byte("3"),"已使用"),
    USER_COUPON_STATE_OVERDUE("userOfCouponsState",new Byte("4"),"已过期"),
    USER_COUPON_STATE_TRANSFER_ING("userOfCouponsState",new Byte("5"),"转让中"),
    USER_COUPON_STATE_TRANSFER_ED("userOfCouponsState",new Byte("6"),"已转让"),

    FULL_REDUCE_REBATE_MONEY("fullReduceRebate",new Byte("1"),"金额"),
    FULL_REDUCE_REBATE_DISCOUNT("fullReduceRebate",new Byte("2"),"折扣"),

    EXPERIENCE_NUMBER_SHARE("experienceNumber",new Byte("1"),"共享次数"),
    EXPERIENCE_NUMBER_PER("experienceNumber",new Byte("2"),"各产品次数"),

    EXPERIENCE_INTERVAL_MORNING("experienceInterval",new Byte("1"),"上午08:00-13:00"),
    EXPERIENCE_INTERVAL_AFTERNOON("experienceInterval",new Byte("2"),"下午13:00-18:00"),
    EXPERIENCE_INTERVAL_ALL_DAY("experienceInterval",new Byte("3"),"全天08:00-18:00"),

    COUPON_IS_RANDOM_YES("couponIsRandom",new Byte("1"),"是"),
    COUPON_IS_RANDOM_NO("couponIsRandom",new Byte("2"),"不是"),

    COUPON_GOODS_SPECIAL_YES("couponGoodsSpecial",new Byte("1"),"是"),
    COUPON_GOODS_SPECIAL_NO("couponGoodsSpecial",new Byte("2"),"不是"),

    COUPON_IS_SEND_YES("couponIsSend",new Byte("1"),"是"),
    COUPON_IS_SEND_NO("couponIsSend",new Byte("2"),"不是"),


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
    CouponEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
