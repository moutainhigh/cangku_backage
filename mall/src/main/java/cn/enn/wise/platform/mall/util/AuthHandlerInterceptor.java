package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.vo.User;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthHandlerInterceptor implements HandlerInterceptor{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(AuthHandlerInterceptor.class);

    @Value("${TOKEN_SERVICE_URL}")
    private String TOKEN_SERVICE_URL;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
            logger.info("===进入springmvc拦截器===");

        //处理跨域请求
        String []  allowDomain= {"http://genius.enn.cn","http://travel.enn.cn","http://tgx.enn.cn","http://tx.enn.cn","http://10.4.137.197","http://127.0.0.1","http://localhost:8080,http://localhost:9527"};
        Set<String> allowedOrigins= new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader=((HttpServletRequest) httpServletRequest).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {

            httpServletResponse.addHeader("Access-Control-Allow-Origin",originHeader);
            httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,X-Token,X-App-Code,companyid,openId,appId,token,scenicid");
        }

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(AuthRequired.class);
            // 如果不需要权限检验
            if (authRequired == null) {
                return true;
            }

            //获取token
            String token = httpServletRequest.getHeader("X-Token");
            //调用根据token获取用户信息
            try {
                User userInfo = getUserByToken(token);
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
    private User getUserByToken(String token){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token",token);
            String post = HttpClientUtil.post(TOKEN_SERVICE_URL, paramMap);
            ResultUtil resultUtil = JSONObject.parseObject(post, ResultUtil.class);
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
