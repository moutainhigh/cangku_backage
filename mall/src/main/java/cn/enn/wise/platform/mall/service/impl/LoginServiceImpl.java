package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.constants.LoginConstants;
import cn.enn.wise.platform.mall.service.LoginService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 登录服务实现类
 *
 * @author caiyt
 * @since 2019-05-22
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 后台登录用户列表
     */
    private volatile Map<String, SystemStaffVo> msUserMap = null;

    @Profile({"local", "integrated", "test"})
    @Component
    class TestInit {
        @PostConstruct
        private void initTest() {
            init("ms_user_test.json");
        }
    }

    @Profile("prod")
    @Component
    class ProdInit {
        @PostConstruct
        private void initProd() {
            init("ms_user_prod.json");
        }
    }

    /**
     * 初始化后台用户列表，从文件中加载，保证只被加载一次
     */
    private void init(String fileName) {
        if (msUserMap == null) {
            synchronized (LoginServiceImpl.class) {
                if (msUserMap == null) {
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream("conf/" + fileName);
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        List<SystemStaffVo> staffVoList = JSONArray.parseArray(stringBuilder.toString(), SystemStaffVo.class);
                        msUserMap = staffVoList.stream().collect(Collectors.toMap(SystemStaffVo::getName, Function.identity()));
                        log.info("加载后台管理用户文件[{}]完成！", fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public ResponseEntity login(String userName, String userPwd) {
        if(msUserMap == null) {
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT, "系统数据还未初始完成，请稍后再试！");
        }
        SystemStaffVo systemStaffVo = msUserMap.get(userName);
        // 用户名不存在或密码错误
        if (systemStaffVo == null || !systemStaffVo.getPasswd().equals(userPwd)) {
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT, LoginConstants.LoginEnum.USER_NOT_EXIT_OR_PWD_ERROR.getMessage());
        }
        // 保存在redis中的内容
        JSONObject redisStoreInfo = new JSONObject();
        redisStoreInfo.put("id", systemStaffVo.getId());
        redisStoreInfo.put("name", systemStaffVo.getName());
        redisStoreInfo.put("companyId", systemStaffVo.getCompanyId());
        redisStoreInfo.put("companyName", systemStaffVo.getCompanyName());
        //生成token
        String token = UUID.randomUUID().toString();
        redisStoreInfo.put("token", token);
        // redis 缓存时间30分钟
        String cacheKey = String.format(LoginConstants.MANAGEMENT_SYSTEM_TOKEN, token);
        redisTemplate.opsForValue().set(cacheKey, redisStoreInfo.toJSONString());
        redisTemplate.expire(cacheKey, LoginConstants.TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);
        return new ResponseEntity(redisStoreInfo);
    }
}
