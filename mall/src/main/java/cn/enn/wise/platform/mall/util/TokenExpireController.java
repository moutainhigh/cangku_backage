/**
 * Copyright (C), 2018-2019
 * FileName: TokenExpireController
 * Author:   Administrator
 * Date:     2019-04-22 9:39
 * Description:
 */
package cn.enn.wise.platform.mall.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class TokenExpireController {

    @RequestMapping("/tokenExpire")
    public ResultUtil tokenExpire(){
        return new ResultUtil(-1,"token已过期",null);
    }
}