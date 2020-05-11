package cn.enn.wise.platform.mall.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author bj
 * @Description 获取当前运行环境工具类
 * @Date19-5-26 下午12:56
 * @Version V1.0
 **/
@Component
public class ProfileUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static String getProfile(){
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if(activeProfiles.length >0){
            return activeProfiles[0];
        }
        return null;
    }
}
