package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.util.ResponseEntity;

/**
 * Login服务类
 *
 * @author caiyt
 * @since 2019-05-23
 */
public interface LoginService {
    /**
     * 后台用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    ResponseEntity login(String userName, String userPwd);
}
