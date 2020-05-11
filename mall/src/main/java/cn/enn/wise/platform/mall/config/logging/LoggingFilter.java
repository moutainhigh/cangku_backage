package cn.enn.wise.platform.mall.config.logging;

import cn.enn.wise.platform.mall.constants.LoginConstants;
import cn.enn.wise.platform.mall.service.LogRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 日志收集器
 *
 * @author caiyt
 */
public class LoggingFilter implements Filter {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Logger log = getLogger(getClass());
    private Set<String> excludedPaths = emptySet();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("LoggingFilter just supports HTTP requests");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        for (String excludedPath : excludedPaths) {
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.startsWith(excludedPath)) {
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            }
        }

        LoggingHttpServletRequestWrapper requestWrapper = new LoggingHttpServletRequestWrapper(httpRequest);
        LoggingHttpServletResponseWrapper responseWrapper = new LoggingHttpServletResponseWrapper(httpResponse);

        LogModel logModel = new LogModel();
        logModel.setMethod(httpRequest.getRequestURI());
        getRequestDescription(requestWrapper, logModel);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            getResponseDescription(responseWrapper, logModel);
        }

        String token = httpRequest.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            // 后台管理token
            token = String.format(LoginConstants.MANAGEMENT_SYSTEM_TOKEN, token);
        } else {
            // APP、小程序token
            token = httpRequest.getHeader("X-Token");
        }
        if (!StringUtils.isEmpty(token)) {
            String userInfoStr = redisTemplate.opsForValue().get(token);
            logModel.setUserInfo(userInfoStr);
        }

        log.info(JSONObject.toJSONString(logModel, true));
        try {
            logRepository.save(logModel);
        } catch (Exception e) {
            log.error("save es error:{}", e.getMessage());
        }

        httpResponse.getOutputStream().write(responseWrapper.getContentAsBytes());
    }

    @Override
    public void destroy() {
    }

    protected String getRequestDescription(LoggingHttpServletRequestWrapper requestWrapper, LogModel logModel) {
        LoggingRequest loggingRequest = new LoggingRequest();
        loggingRequest.setSender(requestWrapper.getLocalAddr());
        loggingRequest.setMethod(requestWrapper.getMethod());
        loggingRequest.setPath(requestWrapper.getRequestURI());
        loggingRequest.setParams(requestWrapper.isFormPost() ? null : requestWrapper.getParameters());
        loggingRequest.setHeaders(requestWrapper.getHeaders());
        String content = requestWrapper.getContent();
        if (log.isTraceEnabled()) {
            loggingRequest.setBody(content);
        } else {
            loggingRequest.setBody(content.substring(0, Math.min(content.length(), 2 * 1024)));
        }
        logModel.setRequest(loggingRequest);
        return JSON.toJSONString(loggingRequest, true);
    }

    protected String getResponseDescription(LoggingHttpServletResponseWrapper responseWrapper, LogModel logModel) {
        LoggingResponse loggingResponse = new LoggingResponse();
        loggingResponse.setStatus(responseWrapper.getStatus());
        loggingResponse.setHeaders(responseWrapper.getHeaders());
        String content = responseWrapper.getContent();
        if (log.isTraceEnabled()) {
            loggingResponse.setBody(content);
        } else {
            loggingResponse.setBody(content.substring(0, Math.min(content.length(), 3 * 1024)));
        }
        logModel.setResponse(loggingResponse);
        return JSON.toJSONString(loggingResponse, true);
    }
}