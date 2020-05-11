package cn.enn.wise.ssop.service.promotions.controller;

import cn.enn.wise.ssop.api.promotions.dto.request.CouponSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.request.CouponUpdateParam;
import cn.enn.wise.ssop.service.promotions.consts.CouponEnum;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CouponTest extends BaseIntegratedTest {

    Integer id = 0;

    @Test
    public void save() throws Exception {
        CouponSaveParam couponSaveParam = new CouponSaveParam();
        couponSaveParam.setName("春季券");
        couponSaveParam.setRemark("春季券备注");
        couponSaveParam.setState(CouponEnum.STATE_YES.getValue());
        couponSaveParam.setCouponType(CouponEnum.CASH.getValue());
        couponSaveParam.setRebateMethod(CouponEnum.FULL_REDUCE_REBATE_MONEY.getValue());
        MvcResult mvcResult = postToResult("/goods/save/base", couponSaveParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }

    @Test
    public void list() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        MvcResult mvcResult = getToResult("/coupon/list", paramMap, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }

    @Test
    public void detail() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        MvcResult mvcResult = getToResult("/coupon/detail?id="+id, paramMap,null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }

    @Test
    public void update() throws Exception {
        CouponUpdateParam couponUpdateParam = new CouponUpdateParam();
        couponUpdateParam.setId(id.longValue());
        couponUpdateParam.setName("楠溪江");
        MvcResult mvcResult = putToResult("/coupon/update",couponUpdateParam);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }

    @Test
    public void updateStatus() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        MvcResult mvcResult = putToResult("/coupon/update/status?id="+id, paramMap);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }

    @Test
    public void consumptionList() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        MvcResult mvcResult = getToResult("/coupon/consumption/list", paramMap, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }


}
