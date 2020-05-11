package cn.enn.wise.platform.mall.config.url;

import lombok.Data;

import java.util.HashMap;

/**
 * http调用配置类
 * @author baijie
 * @date 2019-07-27
 */
@Data
public class UrlConfigBean {

    /**
     * 景区Id
     */
    private Long companyId;

    /**
     * 路径配置map
     */
    private HashMap<String,String> urlMap;
}
