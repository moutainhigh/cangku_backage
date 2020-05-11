package cn.enn.wise.platform.mall.config.url;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * http调用接口配置
 *
 * @author baijie
 * @date 2019-07-27
 */
@Component
@Data
@ConfigurationProperties(prefix = "urlconfig")
public class UrlConfig {

    private List<UrlConfigBean> urlConfigBeans;
}
