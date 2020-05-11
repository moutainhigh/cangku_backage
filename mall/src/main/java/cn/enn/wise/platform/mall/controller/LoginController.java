package cn.enn.wise.platform.mall.controller;


import cn.enn.wise.platform.mall.service.LoginService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login API
 *
 * @author caiyt
 * @since 2019-05-23
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    /**
     * 后台用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "后台用户登录", notes = "后台用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userName", value = "用户登录名", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userPwd", value = "用户登录密码", paramType = "query")
    })
    public ResponseEntity login(@RequestParam("userName") String userName, @RequestParam("userPwd") String userPwd) {
        return loginService.login(userName, userPwd);
    }
}