package cn.enn.wise.platform.mall.config;

import cn.enn.wise.platform.mall.config.pay.WxPayConfig;
import cn.enn.wise.platform.mall.config.pay.WxPayConfigBean;
import cn.enn.wise.platform.mall.config.url.UrlConfig;
import cn.enn.wise.platform.mall.config.url.UrlConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bj
 * @Description 系统适配器
 * @Date19-6-21 下午12:55
 * @Version V1.0
 **/
@Component
@Order(1)
public class SystemAdapterRegister implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SystemAdapterRegister.class);

    private static Map<String,String>  companyMap = new HashMap<>(8);
    {
        companyMap.put("5","大峡谷景区");
        companyMap.put("7","大峡谷景区");
        companyMap.put("10","巴松措景区");
        companyMap.put("11","涠洲岛景区");
        companyMap.put("13","楠溪江景区");
    }

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private WxPayConfig wxPayConfig;

    @Value("${companyId}")
    private String companyId;

    /**
     * 初始化景区对应的队列
     * @param args
     * @throws Exception
     */

    @Override
    public void run(String... args) throws Exception {

        //#region 初始化url配置 start
        List<UrlConfigBean> urlConfigBeans = urlConfig.getUrlConfigBeans();

        for (UrlConfigBean urlConfigBean : urlConfigBeans) {

            Long companyId = urlConfigBean.getCompanyId();
            HashMap<String, String> urlMap = urlConfigBean.getUrlMap();
            SystemAdapter.URL_MAP.put(companyId,urlMap);

        }

        logger.info("===初始化远程服务调用适配器==="+SystemAdapter.URL_MAP.size());
        //#region 初始化url配置 end
        //#region start 初始化支付配置
        List<WxPayConfigBean> wxPayConfig = this.wxPayConfig.getWxPayConfig();

        if(wxPayConfig == null){

            throw new RuntimeException("=====读取支付配置失败=====");

        }

        for (WxPayConfigBean wxPayConfigBean : wxPayConfig) {

            Long companyId = wxPayConfigBean.getCompanyId();
            SystemAdapter.PAY_MAP.put(companyId,wxPayConfigBean);
            logger.info("config pay in companyId:"+companyId);
        }

        logger.info("===初始化微信支付适配==="+SystemAdapter.PAY_MAP.size());


        logger.info("当前系统适用于景区: "+companyMap.get(companyId));



    }
}
