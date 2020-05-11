package cn.enn.wise.platform.mall;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.mapper.OrderAppletsMapper;
import cn.enn.wise.platform.mall.service.impl.OrderAppletsServiceImpl;
import cn.enn.wise.platform.mall.util.DateUtil;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.ResultUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
public class AppletsApiTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;


    String token = "tmp_token";

    String openId = "o09qu4j_QcifF_BichyUrqD-09Gc";

    String appId = "1";

    String goodsId = "tmp_goodsId";
    String periodId = "tmp_periodId";
    String timeFrame = "2";

    String orderCode = "tmp_orderCode";

    @Value("${TOKEN_SERVICE_URL}")
    private String TOKEN_SERVICE_URL;

    @Value("${GET_TOKEN_URL}")
    private String GET_TOKEN_URL;

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OrderAppletsMapper orderAppletsMapper;

    @Autowired
    private OrderAppletsServiceImpl orderAppletsService;

    /**
     * 所有测试方法执行之前执行该方法
     * 该方法为使在执行每一个测试用例前都让用户登录
     */
    @Test
    public void test1Login() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //登录获取token和OpenId
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        String post = HttpClientUtil.post(TOKEN_SERVICE_URL, paramMap);
        ResultUtil resultUtil = JSONObject.parseObject(post, ResultUtil.class);
        Integer result = resultUtil.getResult();
        if (result != 1) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("openId", openId);
            httpHeaders.add("appId", appId);

            HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(GET_TOKEN_URL, requestEntity, String.class);

            String body = stringResponseEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            Integer result1 = jsonObject.getInteger("result");
            if (result1 == 1) {
                String message = jsonObject.getString("message");
                System.out.println(message);
                JSONObject value = jsonObject.getJSONObject("value");
                System.out.println(value);
                redisTemplate.opsForValue().set(token, value.getString("token"), 300, TimeUnit.SECONDS);

            }
        }
    }


    /**
     * 测试获取商品列表
     *
     * @throws Exception
     */
    @Test
    public void test2GetGoods() throws Exception {
        String getGoodsResult = mockMvc.perform(
                get("/good/searchbydate")
                        .param("timeFrame", timeFrame)
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HashMap<String, Object> hashMap = JSONObject.parseObject(getGoodsResult, HashMap.class);
        Integer result = (Integer) hashMap.get("result");
        Object message = hashMap.get("message");
        Object value = hashMap.get("value");
        System.out.println(result);
        System.out.println(message);
        if (result == 1) {
            System.out.println("===获取商品列表成功===");
            System.out.println(value.toString());

            JSONObject jsonObject1 = JSONObject.parseObject(getGoodsResult);
            JSONObject goodsValue = jsonObject1.getJSONObject("value");
            JSONArray packageTicketList = goodsValue.getJSONArray("packageTicketList");
            if (packageTicketList.size() == 0) {
                JSONArray singleTicketList = goodsValue.getJSONArray("singleTicketList");
                if (singleTicketList.size() != 0) {
                    JSONObject resVo = (JSONObject) packageTicketList.get(0);
                    redisTemplate.opsForValue().set(goodsId, resVo.getString("id"),300,TimeUnit.SECONDS);
                    redisTemplate.opsForValue().set(periodId, String.valueOf(((JSONObject) ((JSONObject) resVo.getJSONArray("skuInfo").get(0)).getJSONArray("skuInfo").get(0)).getString("periodId")),300,TimeUnit.SECONDS);
                }

            } else {

                JSONObject resVo = (JSONObject) packageTicketList.get(0);
                redisTemplate.opsForValue().set(goodsId, resVo.getString("id"),300,TimeUnit.SECONDS);
                redisTemplate.opsForValue().set(periodId, String.valueOf(((JSONObject) ((JSONObject) resVo.getJSONArray("skuInfo").get(0)).getJSONArray("skuInfo").get(0)).getString("periodId")),300,TimeUnit.SECONDS);
            }
        } else {
            throw new RuntimeException("===获取商品列表失败===");
        }


    }

    /**
     * 测试保存订单
     *
     * @throws Exception
     */
    @Test
    public void test4SaveOrders() throws Exception {
        PayParam payParam = new PayParam();
        payParam.setPhone("15303786335");
        payParam.setIdNumber("41138119970328391X");
        payParam.setAmount(1);
        //payParam.setPeriodId(Integer.valueOf(redisTemplate.opsForValue().get(periodId)));

        payParam.setPeriodId(12);

        payParam.setName("baijie");
        payParam.setScenicId(5L);
        //payParam.setGoodsId(Long.valueOf(redisTemplate.opsForValue().get(goodsId)));

        payParam.setGoodsId(1L);
        payParam.setPayType("weixin");
        payParam.setTotalPrice("100");
        payParam.setTimeFrame(2);

        String result = mockMvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSONObject.toJSONString(payParam))
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResultUtil resultUtil = JSONObject.parseObject(result, ResultUtil.class);
        Integer result1 = resultUtil.getResult();
        String message = resultUtil.getMessage();
        Object value = resultUtil.getValue();
        System.out.println(message);
        if (result1 == 1) {
            System.out.println(value);
            redisTemplate.opsForValue().set(orderCode, ((JSONObject) resultUtil.getValue()).getString("orderCode"),300,TimeUnit.SECONDS);
        } else {
            throw new RuntimeException("===测试下单接口异常===");
        }
    }


    /**
     * 测试获取预定票信息
     *
     * @throws Exception
     */
    @Test
    public void test3Predestinate() throws Exception {
        GoodsReqParam goodsReqParam = new GoodsReqParam();
        goodsReqParam.setGoodsId(Long.valueOf(redisTemplate.opsForValue().get(goodsId)));
        goodsReqParam.setPeriodId(Integer.valueOf(redisTemplate.opsForValue().get(periodId)));
        goodsReqParam.setTimeFrame(2);
        String result = mockMvc.perform(
                post("/orders/getpredestinateinfo")
                        .content(JSONObject.toJSONString(goodsReqParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResultUtil resultUtil = JSONObject.parseObject(result, ResultUtil.class);

        System.out.println(resultUtil.getMessage());
        if (resultUtil.getResult() == 1) {
            System.out.println(resultUtil.getValue());
        } else {
            throw new RuntimeException("===调用获取预定票信息接口失败===");
        }
    }

    /**
     * 待支付订单支付
     *
     * @throws Exception
     */
    @Test
    public void test5Payoriginal() throws Exception {
        String result = mockMvc.perform(
                post("/orders/payoriginal")
                        .param("scenicId", "5")
                        .param("orderCode", redisTemplate.opsForValue().get(orderCode))
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer result1 = jsonObject.getInteger("result");
        String message = jsonObject.getString("message");
        System.out.println(message);
        String value = jsonObject.getString("value");
        if (result1 == 1) {
            System.out.println(value);

        } else {
            throw new RuntimeException("===测试下单接口异常===");
        }

    }

    /**
     * 根据用户Id和订单号查询订单详情
     *
     * @throws Exception
     */
    @Test
    public void test6GetOrderByIdAndUserId() throws Exception {
        String result = mockMvc.perform(
                get("/orders/detail")
                        .param("orderCode", redisTemplate.opsForValue().get(orderCode))
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer result1 = jsonObject.getInteger("result");
        String message = jsonObject.getString("message");
        System.out.println(message);
        String value = jsonObject.getString("value");
        if(result1 == 1){
            System.out.println(value);
        }else {
            throw new RuntimeException("===查看订单详情接口错误===");
        }

    }

    /**
     * 测试用户查询所有订单
     *
     * @throws Exception
     */
    @Test
    public void test7listOrderByUserId() throws Exception {
        PayParam payParam = new PayParam();
        payParam.setScenicId(5L);
//        payParam.setState(5);
        String result = mockMvc.perform(
                post("/orders/userorder")
                        .content(JSONObject.toJSONString(payParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);

        Integer result1 = jsonObject.getInteger("result");
        String message = jsonObject.getString("message");
        System.out.println(message);
        String value = jsonObject.getString("value");
        if(result1 == 1){
            System.out.println(value);
            JSONArray jsonObject1 = JSONObject.parseArray(value);
            if(jsonObject1.size() <= 0 ){
                throw  new RuntimeException("===用户订单列表不能为空===");
            }
        }else {
            throw new RuntimeException("===查看订单列表接口错误===");
        }
    }

    /**
     * 测试订单完结接口
     */
    @Test
    public void test8OrderComplate() throws Exception{
        String result = mockMvc.perform(
                post("/orders/complate")
                        .param("orderCode", redisTemplate.opsForValue().get(orderCode))
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);

        Integer result1 = jsonObject.getInteger("result");
        String message = jsonObject.getString("message");
        System.out.println(message);
        String value = jsonObject.getString("value");
        if(result1 == 1){
            System.out.println(value);
        }else {
            throw new RuntimeException("===订单完结接口调用错误===");
        }
    }


    /**
     * 取消订单
     *
     * @throws Exception
     */
    @Test
    public void test9refundOrder() throws Exception {
        String orderCodes = redisTemplate.opsForValue().get(orderCode);
        String result = mockMvc.perform(
                post("/orders/refundorder")
                        .param("orderCode", orderCodes)
                        .header("X-Token", redisTemplate.opsForValue().get(token))
                        .header("openId", openId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer result1 = jsonObject.getInteger("result");
        String message = jsonObject.getString("message");
        System.out.println(message);
        String value = jsonObject.getString("value");
        if(result1 == 1){
            System.out.println(value);
            Orders orders = new Orders();
            orders.setState(5);
            orders.setActualPay(new BigDecimal(0));
            orders.setPayStatus(3);
            orders.setOrderCode(orderCodes);
            orderAppletsMapper.updateOrderByOrderCode(orders);
        }else {
            throw new RuntimeException("===订单取消接口调用错误===");
        }

    }



    @Test
    public void testtime(){
           Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.now());
           Timestamp timestamp2 = new Timestamp(DateUtil.getNextTime(14).getTime());
        System.out.println(DateUtil.differentMunitess(timestamp1,timestamp2));
    }



}
