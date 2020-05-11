package cn.enn.wise.platform.mall;

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

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/28 12:46
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:订单单元测试
 ******************************************/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile("contact")
@Slf4j
public class OrderTest {

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
    public void testOrderList() throws Exception {
        String url = "/order/query/all";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }

    @Test
    public void testAppAllOrderList() throws Exception {
        String url = "/order/app/query/all";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("offset","1")
                .param("limit","10")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }


    @Test
    public void testCloseAppOrder() throws Exception {
        String url = "/order/app/refund/";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("type","1")
                .param("orderCode","TODO-20190601")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }


    @Test
    public void testOrderDetail() throws Exception {
        String url = "/order/detail";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("orderCodes","TODO-20190606")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }

    @Test
    public void testRefundOrder() throws Exception {
        String url = "/order/refund";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("orderCodes","TODO-20190606")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        int status = response.getStatus();

        String contentAsString = response.getContentAsString();

        log.info(""+status);
        log.info(contentAsString);

    }



}
