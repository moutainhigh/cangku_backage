package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分销商app接口测试
 */
public class DistributorAppTest extends BaseIntegratedTest {

    //App端分销商注册
    @Test
    public void registerDistribution() throws Exception {
        //设置参数
        DistributorRigisterParam distributorRigisterParam = new DistributorRigisterParam();
        distributorRigisterParam.setDistributorName("测试App注册分销商001");
        distributorRigisterParam.setPhone("15110016067");
        distributorRigisterParam.setAccountPassword("123456");
        distributorRigisterParam.setAreaId(0L);
        distributorRigisterParam.setAreaName("区域");
        distributorRigisterParam.setChannelId(1L);
        distributorRigisterParam.setChannelName("渠道1");
        distributorRigisterParam.setDistributorType((byte) 1);
    /*    distributorRigisterParam.setBusinessLicense1("");
        distributorRigisterParam.setBusinessLicense2("");
        distributorRigisterParam.setIdCardPage1("");
        distributorRigisterParam.setIdCardPage2("");
        distributorRigisterParam.setDriverCardPage1("");
        distributorRigisterParam.setDriverCardPage2("");
        distributorRigisterParam.setGuideCardPage1("");
        distributorRigisterParam.setGuideCardPage2("");*/
        ArrayList<DistributorRigisterParam.DistribytorAddInfo> distribytorAddInfoList = new ArrayList<>();
        DistributorRigisterParam.DistribytorAddInfo distribytorAddInfo = distributorRigisterParam.getDistribytorAddInfo();
        distribytorAddInfo.type = 2;
        distribytorAddInfo.page1 = "idcard1";
        distribytorAddInfo.page2= "idcard2";
        distribytorAddInfoList.add(distribytorAddInfo);
        distributorRigisterParam.setDistribytorAddInfoList(distribytorAddInfoList);
        distributorRigisterParam.setBusinessScope("合作范围");
        distributorRigisterParam.setBusinessCounterpart("业务对接人");


        //发送请求
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("company_id","1");
        MvcResult mvcResult = postToResult("/app/distributor/registerDistribution", distributorRigisterParam, httpHeaders);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }


    //根据分销商id获取补充信息
    @Test
    public void getDistributionAddByDistribuBaseId() throws Exception {
        //设置参数
        //发送请求
        MvcResult mvcResult = getToResult("/app/distributor/getDistributionAddByDistribuBaseId?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }

    //修改分销商补充信息
    @Test
    public void updateDistributionAdd() throws Exception {
        //设置参数
        DistributorAddParam distributorAddParam = new DistributorAddParam();
        distributorAddParam.setId(6L);
        distributorAddParam.setDistributorBaseId(6L);
        distributorAddParam.setBusinessLicense1("123");
        distributorAddParam.setBusinessLicense2("123");
        distributorAddParam.setIdCardPage1("123");
        distributorAddParam.setIdCardPage2("123");
        distributorAddParam.setDriverCardPage1("123");
        distributorAddParam.setDriverCardPage2("123");
        distributorAddParam.setGuideCardPage1("123");
        distributorAddParam.setGuideCardPage2("123");
        distributorAddParam.setWithdrawAccountType("123");
      /*  distributorAddParam.setBankName("123");
        distributorAddParam.setBankAccountName("123");
        distributorAddParam.setBankCardNumber("123");
        distributorAddParam.setBankCode("123");*/
        distributorAddParam.setWechatNickname("123");
        distributorAddParam.setVerifyState((byte)0);
        distributorAddParam.setElectronicContract("123");
        distributorAddParam.setContractSacnFile("123");
        distributorAddParam.setDistributorBankId(1L);
        distributorAddParam.setBankCardImg("123");
        distributorAddParam.setReason("123");
        distributorAddParam.setOpenId("123");



        //发送请求
        MvcResult mvcResult = postToResult("/app/distributor/updateDistributionAdd", distributorAddParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }



    //通过用户id（即分销商账号id）判断用户是否是分销商账号
    @Test
    public void getUserIsDistributorByUserId() throws Exception {
        //设置参数
        //发送请求
        MvcResult mvcResult = getToResult("/app/distributor/getUserIsDistributorByUserId?userId=8", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //根据分销商id查询分销政策
    @Test
    public void getDistributionChannelRuleByDistribuBaseId() throws Exception {
        //设置参数
        //发送请求
        MvcResult mvcResult = getToResult("/app/distributor/getDistributionChannelRuleByDistribuBaseId?distribuBaseId=1&goodsId=1&ruleDay=2020/04/10", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //获取分销商App首页上半部分信息
    @Test
    public void getDistributorAppFirstPageHead() throws Exception {

        //发送请求
        MvcResult mvcResult = getToResult("/app/distributor/getDistributorAppFirstPageHead", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }

    //获取分销商App首页上下部分信息
    @Test
    public void getDistributorAppFirstPageBottom() throws Exception {

        //发送请求
        MvcResult mvcResult = getToResult("/app/distributor/getDistributorAppFirstPageBottom", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


}
