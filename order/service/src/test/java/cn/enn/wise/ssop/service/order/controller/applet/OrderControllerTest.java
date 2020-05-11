package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.api.order.dto.request.DefaultOrderSaveParam;
import cn.enn.wise.ssop.service.order.ServiceOrderApplication;
import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceOrderApplication.class)
@WebAppConfiguration
public class OrderControllerTest extends TestCase {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        System.out.println("====>");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    private Result login() throws Exception {
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("realName", "孔兵"),
//                new BasicNameValuePair("userName", "whisky"),
//                new BasicNameValuePair("password", "123456789")), "utf-8");
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/passport/signin")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .content(EntityUtils.toString(entity)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String json = result.getResponse().getContentAsString();
//
//        return JSON.parseObject(json, Result.class);
//    }

//    private Result getToken() throws Exception {
//
//        //code memberID二选一 code优先
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/wechat/signIn?memberID=189&code=")
//                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String json = result.getResponse().getContentAsString();
//
//        return JSON.parseObject(json, Result.class);
//    }

    // 门票++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //门票保存第一种
    @Test
    public void testSaveTicketOrder() throws Exception {
        String result="{\n" +
                "\t\"certificateNo\": \"fhdnsgsahsn\",\n" +
                "\t\"certificateType\": 1,\n" +
                "\t\"channelId\": 1,\n" +
                "\t\"channelName\": \"123\",\n" +
                "\t\"customerName\": \"宋\",\n" +
                "\t\"distributorMobile\": \"18435106879\",\n" +
                "\t\"useDate\": \"2020-04-30\",\n" +
                "\t\"goodsInfoParamList\": [{\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t}],\n" +
                "\t\"0\": {\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t},\n" +
                "\t\"memberId\": 1,\n" +
                "\t\"orderSource\": 1,\n" +
                "\t\"phone\": \"18201156460\",\n" +
                "\t\"threeOrderNo\": \"fdhfgsksaksj\"\n" +
                "\n" +
                "}";
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(result)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票保存第二种 缺少渠道名称 channelName
    @Test
    public void testSaveTicketOrder1() throws Exception {
        String result="{\n" +
                "\t\"certificateNo\": \"fhdnsgsahsn\",\n" +
                "\t\"certificateType\": 1,\n" +
                "\t\"channelId\": 1,\n" +
                "\t\"channelName\": \"\",\n" +
                "\t\"customerName\": \"宋\",\n" +
                "\t\"distributorMobile\": \"18435106879\",\n" +
                "\t\"useDate\": \"2020-04-30\",\n" +
                "\t\"goodsInfoParamList\": [{\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t}],\n" +
                "\t\"0\": {\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t},\n" +
                "\t\"memberId\": 1,\n" +
                "\t\"orderSource\": 1,\n" +
                "\t\"phone\": \"18201156460\",\n" +
                "\t\"threeOrderNo\": \"fdhfgsksaksj\"\n" +
                "\n" +
                "}";
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(result)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //门票保存第三种 缺少用户ID memberId
    @Test
    public void testSaveTicketOrder2() throws Exception {
        String result="{\n" +
                "\t\"certificateNo\": \"fhdnsgsahsn\",\n" +
                "\t\"certificateType\": 1,\n" +
                "\t\"channelId\": 1,\n" +
                "\t\"channelName\": \"\",\n" +
                "\t\"customerName\": \"宋\",\n" +
                "\t\"distributorMobile\": \"18435106879\",\n" +
                "\t\"useDate\": \"2020-04-30\",\n" +
                "\t\"goodsInfoParamList\": [{\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t}],\n" +
                "\t\"0\": {\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t},\n" +
                "\t\"memberId\": \n" +
                "\t\"orderSource\": 1,\n" +
                "\t\"phone\": \"18201156460\",\n" +
                "\t\"threeOrderNo\": \"fdhfgsksaksj\"\n" +
                "\n" +
                "}";
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(result)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票保存第四种 缺少用户手机号 phone
    @Test
    public void testSaveTicketOrder3() throws Exception {
        String result="{\n" +
                "\t\"certificateNo\": \"fhdnsgsahsn\",\n" +
                "\t\"certificateType\": 1,\n" +
                "\t\"channelId\": 1,\n" +
                "\t\"channelName\": \"123\",\n" +
                "\t\"customerName\": \"宋\",\n" +
                "\t\"distributorMobile\": \"18435106879\",\n" +
                "\t\"useDate\": \"2020-04-30\",\n" +
                "\t\"goodsInfoParamList\": [{\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t}],\n" +
                "\t\"0\": {\n" +
                "\t\t\"customerId\": \"1\",\n" +
                "\t\t\"saleId\": \"1\",\n" +
                "\t\t\"amount\": 1,\n" +
                "\t\t\"skuId\": 264\n" +
                "\t},\n" +
                "\t\"memberId\": 1,\n" +
                "\t\"orderSource\": 1,\n" +
                "\t\"phone\": \"\",\n" +
                "\t\"threeOrderNo\": \"S\"\n" +
                "\n" +
                "}";
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(result)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());

    }
    //门票订单全部详情信息
    @Test
    public void testTotalDetailsTicketOrder() throws Exception {
        String orderId="120629260288";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/totaldetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票商品详情
    @Test
    public void testDetailsTicketOrder() throws Exception {
        String orderId="120629260288";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //门票退票详情
    @Test
    public void testCancelDetailsTicketOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/canceldetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //门票规则明细
    @Test
    public void testruleDetailsTicketOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/ruledetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票退款信息
    @Test
    public void testRefundDetailsTicketOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/refunddetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票列表展示
    @Test
    public void testListTicketOrder() throws Exception {
        String result="";//没用到
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/ticket/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

}