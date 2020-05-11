package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.util.GeneConstant;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile({"integrated", "local", "test"})
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 密码错误
     *
     * @throws Exception
     */
    @Test
    public void testLoginInvalidate() throws Exception {
        String result = this.mockMvc.perform(post("/login")
                .param("userName", "admin")
                .param("userPwd", "admin_error"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(result);
        JSONObject resObj = JSONObject.parseObject(result);
        long resultCode = resObj.getLongValue("result");
        Assert.assertEquals(resultCode, GeneConstant.LOGIN_TIMEOUT);
    }

    /**
     * 正常登录
     *
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception {
        String result = this.mockMvc.perform(post("/login")
                .param("userName", "admin")
                .param("userPwd", "123456"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(result);
        JSONObject resObj = JSONObject.parseObject(result);
        long resultCode = resObj.getLongValue("result");
        Assert.assertEquals(resultCode, GeneConstant.SUCCESS_CODE);
    }
}
