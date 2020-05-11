package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenIdAuthHandlerInterceptor implements HandlerInterceptor{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(OpenIdAuthHandlerInterceptor.class);



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
            OpenIdAuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(OpenIdAuthRequired.class);
            // 如果不需要权限检验
            if (authRequired == null) {
                return true;
            }

            //获取token
            String companyId = httpServletRequest.getHeader("companyId");
            String appId = httpServletRequest.getHeader("appId");
            String openId = httpServletRequest.getHeader("openId");
            //调用根据token获取用户信息
            try {
                if(StringUtils.isEmpty(companyId)){

                    throw new RuntimeException("companyId不能为空");
                }
                if(StringUtils.isEmpty(appId)){

                    throw new RuntimeException("appId不能为空");
                }
                if(StringUtils.isEmpty(openId)){

                    throw new RuntimeException("openId不能为空");
                }

                User userInfo = getUserByToken(appId,companyId,openId);

                if(userInfo ==null || userInfo.getId() == null){
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
     * @param
     * @return
     */
    private User getUserByToken(String appId,String companyId,String openId){
        try {

            Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));
            String url = urlConfig.get(AppConstants.OPENID_SERVICE_URL);



            List<org.apache.http.NameValuePair> headers = new ArrayList<>();
            headers.add(new BasicNameValuePair("appId",appId));
            headers.add(new BasicNameValuePair("companyId",companyId));
            headers.add(new BasicNameValuePair("openId",openId));

            String post = HttpClientUtil.post(url,headers,new ArrayList<>());

            ResultUtil resultUtil = JSONObject.parseObject(post, ResultUtil.class);
            if(resultUtil!=null){

                int result = resultUtil.getResult();
                if(result == 1){
                    //获取用户信息成功
                    String userStr = JSONObject.toJSONString(resultUtil.getValue());
                    User userInfo = JSONObject.parseObject(userStr, User.class);
                    return userInfo;

                }else {
                    throw new RuntimeException("获取用户信息异常:"+resultUtil);
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
