package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.ssop.service.promotions.model.Application;
import cn.enn.wise.ssop.service.promotions.model.Partner;

/**
 * 合作伙伴服务Redis Key 常量
 */
public interface RedisKey {


    /**
     * 合作伙伴详情
     * 参数1 客户端id
     * value
     * @see Partner
     */
    String PARTNER_DETAIL = "PARTNER:PARTNER:ID:%S";

    /**
     * 客户端详情
     * 参数1 客户端id
     * value
     * @see Application
     */
    String APP_DETAIL = "PARTNER:APP:ID:%S";
}
