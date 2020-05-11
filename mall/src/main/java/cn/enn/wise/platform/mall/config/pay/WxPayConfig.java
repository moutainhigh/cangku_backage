package cn.enn.wise.platform.mall.config.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 微信支付配置
 * @author baijie
 * @date 2019-07-29
 */
@Data
@Component
@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfig {

    List<WxPayConfigBean> wxPayConfig;
}
