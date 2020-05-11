package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.service.order.ServiceOrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceOrderApplication.class)
@WebAppConfiguration
public class OrderHotelControllerTest {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    // 酒店++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //酒店保存 可以变换里面字段进行不同的测试
    @Test
    public void testSaveHoteltOrder() throws Exception {
        String result="{\n" +
                "\t\"certificateNo\": \"\",\n" +
                "\t\"certificateType\": 1,\n" +
                "\t\"channelId\": 1,\n" +
                "\t\"channelName\": \"123\",\n" +
                "\t\"customerName\": \"孙\",\n" +
                "\t\"distributorMobile\": \"\",\n" +
                "\t\"extraInformation\": {\n" +
                "\t\t\"inTime\": \"2020-04-30\",\n" +
                "\t\t\"outTime\": \"2020-05-01\"\n" +
                "\t},\n" +
                "\t\"inTime\": \"2020-04-30\",\n" +
                "\t\"outTime\": \"2020-05-01\",\n" +
                "\t\"goodsInfoParamList\": [{\n" +
                "\t\t\"customerId\": \"\",\n" +
                "\t\t\"saleId\": \"\",\n" +
                "\t\t\"amount\": 3,\n" +
                "\t\t\"skuId\": 341\n" +
                "\t}],\n" +
                "\t\"0\": {\n" +
                "\t\t\"customerId\": \"\",\n" +
                "\t\t\"saleId\": \"\",\n" +
                "\t\t\"amount\": 3,\n" +
                "\t\t\"skuId\": 341\n" +
                "\t},\n" +
                "\t\"memberId\": 1,\n" +
                "\t\"orderSource\": 1,\n" +
                "\t\"phone\": \"18201156460\",\n" +
                "\t\"threeOrderNo\": \"\"\n" +
                "}";
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(result)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //酒店订单全部详情信息
    @Test
    public void testHotelDetailsTicketOrder() throws Exception {
        String orderId="457266458624";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/totaldetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //酒店商品详情
    @Test
    public void testDetailsHotelOrder() throws Exception {
        String orderId="457266458624";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //门票退票详情
    @Test
    public void testCancelDetailsHotelOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/canceldetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //门票规则明细
    @Test
    public void testruleDetailsHotelOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/ruledetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //门票退款信息
    @Test
    public void testRefundDetailsHotelOrder() throws Exception {
        String orderId="369371144192";
        System.out.println(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/refunddetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderId)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //酒店列表展示
    @Test
    public void testListHotelOrder() throws Exception {
        String result="";//没用到
        System.out.println(result);
        mockMvc.perform(MockMvcRequestBuilders.post("/order/hotel/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-TOKEN", "666")
                .header("member_id",7))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
