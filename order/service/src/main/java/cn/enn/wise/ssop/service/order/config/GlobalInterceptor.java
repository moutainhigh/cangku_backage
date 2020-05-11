package cn.enn.wise.ssop.service.order.config;

import cn.enn.wise.ssop.service.order.config.constants.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;



@Controller
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {


    private List<String> includePathPatterns = new ArrayList(){{
        add("/**");
    }};
    private List<String> excludePathPatterns = new ArrayList();



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 将HEADER中的值设置到AppGlobalData的线程变量
        String strCreatorId = request.getHeader(Consts.CREATOR_ID);
        if(!StringUtils.isEmpty(strCreatorId)){
            strCreatorId = strCreatorId.trim();
            Long creatorId = Long.valueOf(strCreatorId);
            AppGlobalData.localCreatorId.set(creatorId);
        }else{
            log.warn("===+! HTTP请求头中不存在CreatorID");
        }
        String strCreatorName = request.getHeader(Consts.CREATOR_NAME);
        if(!StringUtils.isEmpty(strCreatorName)){
            strCreatorName = strCreatorName.trim();
            AppGlobalData.localCreatorName.set(strCreatorName);
        }else{
            log.warn("===+! HTTP请求头中不存在CreatorName");
        }
        String strCompanyId = request.getHeader(Consts.COMPANY_ID);
        if(!StringUtils.isEmpty(strCompanyId)){
            strCompanyId = strCompanyId.trim();
            Long companyId = Long.valueOf(strCompanyId);
            AppGlobalData.localCompanyId.set(companyId);
        }else{
            log.warn("===+! HTTP请求头中不存在CompanyID");
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)throws Exception{
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception{
    }


    /**
     * 将要拦截的路径
     * @return
     */
    public List<String> getIncludePathPatterns() {
        return includePathPatterns;
    }

    /**
     * 排除拦截的路径设置
     * @return
     */
    public List<String> getExcludePathPatterns() {
        return excludePathPatterns;
    }


}