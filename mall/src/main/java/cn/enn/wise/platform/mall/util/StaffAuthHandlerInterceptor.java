package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaffAuthHandlerInterceptor implements HandlerInterceptor{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(StaffAuthHandlerInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {


        //处理跨域请求
        String []  allowDomain= {"http://genius.enn.cn","http://travel.enn.cn","http://tx.enn.cn","http://10.4.137.197","http://127.0.0.1","http://localhost:8080,,http://localhost:9527"};
        Set<String> allowedOrigins= new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader=((HttpServletRequest) httpServletRequest).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {

            httpServletResponse.addHeader("Access-Control-Allow-Origin",originHeader);
            httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,X-Token,X-App-Code,companyid,openId,appId");
        }

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            StaffAuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(StaffAuthRequired.class);
            // 如果不需要权限检验
            if (authRequired == null) {
                return true;
            }

            //获取token
            String token = httpServletRequest.getHeader("X-Token");
            String companyId = httpServletRequest.getHeader("companyId");
            //调用根据token获取用户信息
            try {
                if(StringUtils.isEmpty(companyId)){
                    throw new RuntimeException("companyId不能为空!");
                }

                User userInfo = getUserByToken(token,companyId);
                logger.info("===userInfo==="+userInfo);
                if(userInfo ==null){
                    throw new RuntimeException("===用户身份已经过期===");
                }
                httpServletRequest.setAttribute("currentUser", userInfo);
                return true;

            } catch (Exception e) {

                e.printStackTrace();
                httpServletRequest.getRequestDispatcher("/error/tokenExpire").forward(httpServletRequest,httpServletResponse);
                return false;

            }
        }else{

            return true;
        }
    }
    /**
     * 通过HTTPClient调用根据用户token获取用户信息的接口
     * @param token
     * @return
     */
    private User getUserByToken(String token,String companyId){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token",token);

            Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));
            String url = urlConfig.get(AppConstants.STAFF_TOKEN_URL);

            String post = HttpClientUtil.post(url, paramMap);
            ResponseEntity resultUtil = JSONObject.parseObject(post, ResponseEntity.class);

            if(resultUtil!=null){
                int result = resultUtil.getResult();
                if(result == 1){
                    //获取用户信息成功
                    String userStr = JSONObject.toJSONString(resultUtil.getValue());
                    User userInfo = JSONObject.parseObject(userStr, User.class);
                    return userInfo;
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            logger.info("===请求获取用户信息接口异常===");
            return null;
        }
    }


    /**
     * 调用完controller之后，视图渲染之前
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 整个完成之后，通常用于资源清理
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
