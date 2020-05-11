package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.constants.LoginConstants;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

/**
 * 公共controller类
 *
 * @author caiyt
 */
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 业务异常统一封装处理
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity handleException(Exception ex) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseEntity.setResult(businessException.getCode());
            responseEntity.setMessage(businessException.getMessage());
            responseEntity.setValue(businessException.getData());
        } else {
            responseEntity.setResult(GeneConstant.SYSTEM_ERROR);
            responseEntity.setMessage(ex.getMessage());
//            ex.printStackTrace();
//            logger.error(ex.getMessage()+"--ex.getStackTrace：" +ex.getStackTrace().toString());
            StringWriter stringWriter=new StringWriter();
            PrintWriter printWriter=new PrintWriter(stringWriter);

            try {
                ex.printStackTrace(printWriter);
                logger.error(stringWriter.toString());

            }finally {
                printWriter.close();
            }
        }
        return responseEntity;
    }


    /**
     * 根据token获取登录管理员信息
     *
     * @param token
     * @return
     */
    protected SystemStaffVo getUserByToken(String token) {
        String cacheKey = String.format(LoginConstants.MANAGEMENT_SYSTEM_TOKEN, token);
        // 登录已失效，请重新登录
        if (!redisTemplate.hasKey(cacheKey)) {
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT, LoginConstants.LoginEnum.USER_LOGIN_EXPIRED.getMessage());
        }
        String cacheTokenStr = redisTemplate.opsForValue().get(cacheKey);
        // 登录已失效，请重新登录
        if (StringUtils.isEmpty(cacheTokenStr)) {
            logger.info("1.2.2版登录失效异常token:[{}]",token);
            redisTemplate.delete(cacheKey);
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT, LoginConstants.LoginEnum.USER_LOGIN_EXPIRED.getMessage());
        }
        redisTemplate.expire(cacheKey, LoginConstants.TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);
        SystemStaffVo systemStaffVo = JSONObject.parseObject(cacheTokenStr, SystemStaffVo.class);
        if (systemStaffVo == null || systemStaffVo.getId() == null) {
            // 登录已失效，请重新登录
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT, LoginConstants.LoginEnum.USER_LOGIN_EXPIRED.getMessage());
        }
        return systemStaffVo;
    }
}
