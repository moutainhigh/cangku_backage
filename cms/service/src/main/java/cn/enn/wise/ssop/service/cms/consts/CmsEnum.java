package cn.enn.wise.ssop.service.cms.consts;


import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * 内容服务枚举
 */
@BusinessEnum
@Getter
public enum CmsEnum{
    CMS_TYPE_COMMODITY("cmsType", new Byte("1"), "商品"),
    CMS_TYPE_STRATEGY("cmsType", new Byte("2"), "攻略"),
    CMS_TYPE_SCENICRES("cmsType", new Byte("3"), "景点"),
    CMS_TYPE_KNOWLEDGE("cmsType", new Byte("4"), "知识"),
    CMS_TYPE_ACTIVITY("cmsType", new Byte("5"), "活动"),



    ADVERTISE_TARGET_PROMOTE("advertiseTarget", new Byte("1"), "活动推广"),
    ADVERTISE_TARGET_DOWNLOAD("advertiseTarget", new Byte("2"), "下载引流"),
    ADVERTISE_TARGET_NEWMEMBER("advertiseTarget", new Byte("3"), "会员拉新"),
    ADVERTISE_TARGET_OTHER("advertiseTarget", new Byte("4"), "其他"),

    ADVERTISE_LOCATION_BANNER("advertiseLocation", new Byte("1"), "Banner"),
    ADVERTISE_LOCATION_LINE("advertiseLocation", new Byte("2"), "首页线路"),

    ADVERTISE_CLICK("advertiseClickLoad", new Byte("1"), "点击量"),
    ADVERTISE_LOAD("advertiseClickLoad", new Byte("2"), "加载量"),


    ADVERTISE_LEFT_TO_RIGHT("advertisePlayMode", new Byte("1"), "从左到右"),
    ADVERTISE_RIGHT_TO_LEFT("advertisePlayMode", new Byte("2"), "从右到左"),
    ADVERTISE_FADE_OUT("advertisePlayMode", new Byte("3"), "淡入淡出"),


    ENSHRINE_TYPE_SCENICRES("enshrineType", new Byte("1"), "景点"),
    ENSHRINE_TYPE_JOURNEY("enshrineType", new Byte("2"), "路线"),



    ;
    // 成员变量
    private String name;
    private Byte value;
    private String type;

    // 构造方法
    CmsEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
