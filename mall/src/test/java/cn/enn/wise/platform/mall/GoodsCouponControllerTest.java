package cn.enn.wise.platform.mall;

import cn.enn.wise.platform.mall.bean.param.AddGoodsCouponParam;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
@Slf4j
public class GoodsCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;


    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void save() throws Exception {

        AddGoodsCouponParam param = new AddGoodsCouponParam();

        param.setCouponType(1);
        param.setGetLimit(2);
        param.setGoodsCouponRuleId(3L);
        param.setInitSize(4);
        param.setName("name");
//        param.setOrgName("orgname");
//        param.setOrgScale(90);
        param.setPrice(20);
        param.setRemark("remark");
        param.setTag("tag");
        param.setUsePlatform(1L);
//        param.setValidityTime(1);
        param.setValidityType(1);



        String url = "pc/coupon/save";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(param)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }

}
