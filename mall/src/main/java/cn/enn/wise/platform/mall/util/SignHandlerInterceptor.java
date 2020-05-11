package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class SignHandlerInterceptor implements HandlerInterceptor{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SignHandlerInterceptor.class);

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
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,X-Token,X-App-Code,companyid,openId,appId,token,scenicid,sign");
        }

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            SignRequired signRequired = ((HandlerMethod) handler).getMethodAnnotation(SignRequired.class);
            // 如果不需要签名
            if (signRequired == null) {
                return true;
            }else {
                //签名校验
                HashMap<String, String> parameterMap = new HashMap<>();
                        String header = httpServletRequest.getHeader("Content-Type");
                if(StringUtils.isNotEmpty(header)){
                    //formdata方式提交
                    if(header.contains("x-www-form-urlencoded")){

                        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
                        if(requestParameterMap != null){
                            Set<Map.Entry<String, String[]>> entries = requestParameterMap.entrySet();
                            Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
                            while (iterator.hasNext()){
                                Map.Entry<String, String[]> next = iterator.next();

                                parameterMap.put(next.getKey(),next.getValue()[0]);
                            }
                        }
                    }else if(header.contains("application/json")){

                       try {
                           //读取json数据
                           BufferedReader streamReader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
                           StringBuilder responseStrBuilder = new StringBuilder();
                           String inputStr;
                           while ((inputStr = streamReader.readLine()) != null)  {
                               responseStrBuilder.append(inputStr);
                           }
                           String jsonString = responseStrBuilder.toString();
                           if(StringUtils.isNotEmpty(jsonString)) {
                               parameterMap.put("json",jsonString);
                           }else {
                               throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"body不能为空");

                           }
                       }catch (Exception e){
                           e.printStackTrace();
                           throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"json格式错误");
                       }


                    }else {
                        throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"content-type不正确");
                    }



                }else {
                    throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"header参数content-type不能为空");
                }



                String signature = SignatureUtil.checkSignature(parameterMap);

                String sign = httpServletRequest.getHeader("sign");
                if(StringUtils.isEmpty(sign)){

                    throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"签名失败！");
                }

                if(!sign.equals(signature)){

                    throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"签名失败！");
                }

                return true;

            }


        }else{
            return true;
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
