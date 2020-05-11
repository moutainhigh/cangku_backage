package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.testng.Assert;

import java.util.Arrays;

/**
 * 渠道销售价格相关接口测试
 */
public class ChannelPriceTest extends BaseIntegratedTest {


    //获取商品渠道价格
    @Test
    public void getChannelPrice() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getChannelPrice?goodsId=1&sellPrice=20&costPrice=15&distributorId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //获取商品结算价格
    @Test
    public void getSettlementPrice() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getSettlementPrice?goodsId=1&sellPrice=20&costPrice=15&distributorId=1&ruleDay=2020/04/10", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }




}
