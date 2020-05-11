package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.util.GeneConstant;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bj
 * @Description
 * @Date19-5-30 上午10:54
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicePlaceApiTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;


     String tmpToken = "tmptoken";


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 巴松措PC正常登录
     *
     * @throws Exception
     */
    @Test
    public void test1Login() throws Exception {

        String result = this.mockMvc.perform(post("/login")
                .param("userName", "bscadmin")
                .param("userPwd", "123456"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(result);
        JSONObject resObj = JSONObject.parseObject(result);
        long resultCode = resObj.getLongValue("result");
        redisTemplate.opsForValue().set(tmpToken,resObj.getJSONObject("value").getString("token"),300,TimeUnit.SECONDS);
        Assert.assertEquals(resultCode, GeneConstant.SUCCESS_CODE);
    }

   @Test
   public void test2getServicePlace() throws Exception {
       String token = redisTemplate.opsForValue().get(tmpToken);
       String resultStr = this.mockMvc.perform(get("/serviceplace/get")
               .header("token", token))
               .andExpect(status().isOk())
               .andReturn()
               .getResponse()
               .getContentAsString();
       Assert.assertNotNull(resultStr);

       JSONObject jsonObject = JSONObject.parseObject(resultStr);
       Integer result = jsonObject.getInteger("result");
       System.out.println(jsonObject.getString("message"));
       if(result!=1){
           throw new RuntimeException("test2getServicePlace接口访问错误");
       }else {
           System.out.println(jsonObject.getJSONObject("value"));
       }


   }






}
