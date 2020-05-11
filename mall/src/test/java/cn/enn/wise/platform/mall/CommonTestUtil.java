package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.util.GeneConstant;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试工具类
 *
 * @author caiyt
 */
public class CommonTestUtil {
    /**
     * 登录获取token
     *
     * @param mockMvc
     * @return
     */
    public static String getLoginToken(MockMvc mockMvc) throws Exception {
        String resultStr = mockMvc.perform(post("/login")
                .param("userName", "admin")
                .param("userPwd", "123456"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(resultStr);
        JSONObject resObj = JSONObject.parseObject(resultStr);
        long result = resObj.getLongValue("result");
        Assert.assertEquals(result, GeneConstant.SUCCESS_CODE);
        JSONObject valueObj = resObj.getJSONObject("value");
        String token = valueObj.getString("token");
        Assert.assertNotNull(token);
        return token;
    }
}
