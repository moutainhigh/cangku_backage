package cn.enn.wise.platform.mall.config.pay;

import lombok.Data;

/**
 * 微信支付配置实体
 *
 * @author baijie
 * @date 2019-07-29
 */
@Data
public class WxPayConfigBean {


    /**
     * companyId
     */
    private Long companyId;

    /**
     * 小程序appid
     */
    private String appId;

    /**
     * 微信支付的商户id
     */
    private String mchId;
    /**
     * 微信支付的商户密钥
     */
    private String key;
    /**
     * 支付成功后的服务器回调url
     */
    private String notifyUrl ;

    /**
     * 默认参数AA
     */
    private String grantType = "authorization_code";
    /**
     * 签名方式，固定值
     */
    private String signType = "MD5";
    /**
     * 交易类型，小程序支付的固定值为JSAPI
     */
    private String tradeType ;
    /**
     * 微信统一下单接口地址
     */
    private String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信订单主动查询地址
     */
    private String orderQueryUrl="https://api.mch.weixin.qq.com/pay/orderquery";


}
