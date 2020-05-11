package cn.enn.wise.platform.mall.config;

import cn.enn.wise.platform.mall.config.pay.WxPayConfigBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统适配器
 *
 * @author baijie
 * @date 2019-07-29
 */
public class SystemAdapter {
    /**
     * 针对不同景区接口之间调用路径的不同
     */
    public static final Map<Long, Map<String,String>> URL_MAP  = new HashMap<>();

    /**
     * 支付适配
     */
    public static final Map<Long, WxPayConfigBean> PAY_MAP = new HashMap<>(16);

    public static final Map<String,String> QUEUE_MAP = new HashMap<>(16);

}
