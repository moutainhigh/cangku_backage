package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分销商测试
 */
public class distributorTest extends BaseIntegratedTest {

    //获取分销商列表
    @Test
    public void getDistributorList() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getDistributorList", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }

    //获取分销商id和名称列表
    @Test
    public void getDistributorIdAndNameList() throws Exception {

        MvcResult mvcResult = getToResult("/distributor/getDistributorIdAndNameList", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }



    //保存分销商基础信息
    @Test
    public void saveDistributionBase() throws Exception {
        //设置参数
        DistributorBaseParam couponSaveParam = new DistributorBaseParam();
        couponSaveParam.setDistributorName("测试分销商006");
        couponSaveParam.setScenicId(123L);
        couponSaveParam.setScenicName("景区名称0011");
        couponSaveParam.setCityId(123L);
        couponSaveParam.setCityName("城市名称001");
        couponSaveParam.setAreaId(123L);
        couponSaveParam.setAreaName("区域名称001");
        couponSaveParam.setChannelId(123L);
        couponSaveParam.setChannelName("渠道名称001");
        couponSaveParam.setDistributorType((byte) 1);
//        couponSaveParam.setParentId(0L);
        couponSaveParam.setState((byte) 1);
        couponSaveParam.setRemark("备注001");
        couponSaveParam.setVerifyState((byte) 0);

        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveDistributionBase", couponSaveParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //修改分销商基础信息
    @Test
    public void updateDistributionBase() throws Exception {
        //设置参数
        DistributorBaseParam couponSaveParam = new DistributorBaseParam();
        couponSaveParam.setId(1L);
        couponSaveParam.setDistributorName("测试分销商005");
        couponSaveParam.setScenicId(123L);
        couponSaveParam.setScenicName("景区名称002");
        couponSaveParam.setCityId(123L);
        couponSaveParam.setCityName("城市名称002");
        couponSaveParam.setAreaId(123L);
        couponSaveParam.setAreaName("区域名称002");
        couponSaveParam.setChannelId(123L);
        couponSaveParam.setChannelName("渠道名称002");
        couponSaveParam.setDistributorType((byte) 1);
//        couponSaveParam.setParentId(0L);
        couponSaveParam.setState((byte) 1);
        couponSaveParam.setRemark("备注002");
        couponSaveParam.setVerifyState((byte) 0);

        //发送请求
//        MvcResult mvcResult = postToResult("/distributor/updateDistributionBase", couponSaveParam, null);
        MvcResult mvcResult = putToResult("/distributor/updateDistributionBase", couponSaveParam);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }

    //根据分销商id获取基础信息
    @Test
    public void getDistributionBaseByDistribuBaseId() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getDistributionBaseByDistribuBaseId?distribuBaseId=43", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //保存分销商联系人
    @Test
    public void saveDistributionContact() throws Exception {
        //设置参数
        List<DistributorContactParam> DistributorContactParams = new ArrayList<>();

        DistributorContactParam distributorContactParam1 = new DistributorContactParam();
//        distributorContactParam1.setDistributorBaseId(1L);
        distributorContactParam1.setContactName("测试分销商联系人1");
        distributorContactParam1.setPhone("13519031245");
        distributorContactParam1.setQq("1045327166");
        distributorContactParam1.setEmail("13213165@qq.com");
        distributorContactParam1.setWechat("adasdfas");
        distributorContactParam1.setPosition("经理1");
        distributorContactParam1.setProvinceId(123L);
        distributorContactParam1.setProvinceName("甘肃省1");
        distributorContactParam1.setCityId(123L);
        distributorContactParam1.setCityName("兰州市1");
        distributorContactParam1.setAreaId(123L);
        distributorContactParam1.setAreaName("城关区1");
        distributorContactParam1.setContactAddress("详细地址001");

        DistributorContactParam distributorContactParam2 = new DistributorContactParam();
//        distributorContactParam2.setDistributorBaseId(1L);
        distributorContactParam2.setContactName("测试分销商联系人2");
        distributorContactParam2.setPhone("13519031245");
        distributorContactParam2.setQq("1045327166");
        distributorContactParam2.setEmail("13213165@qq.com");
        distributorContactParam2.setWechat("adasdfas");
        distributorContactParam2.setPosition("经理2");
        distributorContactParam2.setProvinceId(123L);
        distributorContactParam2.setProvinceName("甘肃省2");
        distributorContactParam2.setCityId(123L);
        distributorContactParam2.setCityName("兰州市2");
        distributorContactParam2.setAreaId(123L);
        distributorContactParam2.setAreaName("城关区2");
        distributorContactParam2.setContactAddress("详细地址002");

        DistributorContactParams.add(distributorContactParam1);
        DistributorContactParams.add(distributorContactParam2);


        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveDistributionContact", DistributorContactParams, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //修改分销商联系人
    @Test
    public void updateDistributionContact() throws Exception {
        //设置参数
        DistributorContactUpdateParam distributorContactUpdateParam = new DistributorContactUpdateParam();

        List<DistributorContactParam> DistributorContactParams = new ArrayList<>();
        DistributorContactParam distributorContactParam1 = new DistributorContactParam();
        distributorContactParam1.setId(1L);
        distributorContactParam1.setDistributorBaseId(1L);
        distributorContactParam1.setContactName("测试分销商联系人3");
        distributorContactParam1.setPhone("13519031245");
        distributorContactParam1.setQq("1045327166");
        distributorContactParam1.setEmail("13213165@qq.com");
        distributorContactParam1.setWechat("adasdfas");
        distributorContactParam1.setPosition("经理3");
        distributorContactParam1.setProvinceId(123L);
        distributorContactParam1.setProvinceName("甘肃省3");
        distributorContactParam1.setCityId(123L);
        distributorContactParam1.setCityName("兰州市3");
        distributorContactParam1.setAreaId(123L);
        distributorContactParam1.setAreaName("城关区3");
        distributorContactParam1.setContactAddress("详细地址001");

        DistributorContactParam distributorContactParam2 = new DistributorContactParam();
        distributorContactParam2.setId(2L);
        distributorContactParam2.setContactName("测试分销商联系人4");
        distributorContactParam2.setPhone("13519031245");
        distributorContactParam2.setQq("1045327166");
        distributorContactParam2.setEmail("13213165@qq.com");
        distributorContactParam2.setWechat("adasdfas");
        distributorContactParam2.setPosition("经理4");
        distributorContactParam2.setProvinceId(123L);
        distributorContactParam2.setProvinceName("甘肃省4");
        distributorContactParam2.setCityId(123L);
        distributorContactParam2.setCityName("兰州市4");
        distributorContactParam2.setAreaId(123L);
        distributorContactParam2.setAreaName("城关区4");
        distributorContactParam2.setContactAddress("详细地址004");

        DistributorContactParams.add(distributorContactParam1);
        DistributorContactParams.add(distributorContactParam2);
        distributorContactUpdateParam.setDistributorContactParam(DistributorContactParams);

        //发送请求
        MvcResult mvcResult = postToResult("/distributor/updateDistributionContact", distributorContactUpdateParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }

    //根据分销商id获取联系人信息
    @Test
    public void getDistributionContactByDistribuBaseId() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        //发送请求
        MvcResult mvcResult = getToResult("/distributor/getDistributionContactByDistribuBaseId?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }



    //保存分销商财务信息
    @Test
    public void saveDistributorBank() throws Exception {
        //设置参数
        List<DistributorBankParam> Params = new ArrayList<>();

        DistributorBankParam distributorBankParam1 = new DistributorBankParam();
        distributorBankParam1.setDistributorBaseId(1L);
        distributorBankParam1.setBankName("银行名称1");
        distributorBankParam1.setBankCode("银行代码1");
        distributorBankParam1.setUserName("账户名1");
        distributorBankParam1.setCardNumber("银行卡号1");
        distributorBankParam1.setState((byte)1);
        distributorBankParam1.setBankAddress("详细地址1");
        distributorBankParam1.setRemark("备注1");

        DistributorBankParam distributorBankParam2 = new DistributorBankParam();
        distributorBankParam2.setDistributorBaseId(1L);
        distributorBankParam2.setBankName("银行名称2");
        distributorBankParam2.setBankCode("银行代码2");
        distributorBankParam2.setUserName("账户名2");
        distributorBankParam2.setCardNumber("银行卡号2");
        distributorBankParam2.setState((byte)2);
        distributorBankParam2.setBankAddress("详细地址2");
        distributorBankParam2.setRemark("备注2");



        Params.add(distributorBankParam1);
        Params.add(distributorBankParam2);


        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveDistributorBank", Params, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //修改分销商财务信息
    @Test
    public void updateDistributionBank() throws Exception {
        //设置参数

        DistributorBankUpdateParam distributorBankUpdateParam = new DistributorBankUpdateParam();

        List<DistributorBankParam> Params = new ArrayList<>();

        DistributorBankParam distributorBankParam1 = new DistributorBankParam();
        distributorBankParam1.setId(1L);
        distributorBankParam1.setDistributorBaseId(1L);
        distributorBankParam1.setBankName("银行名称3");
        distributorBankParam1.setBankCode("银行代码3");
        distributorBankParam1.setUserName("账户名3");
        distributorBankParam1.setCardNumber("银行卡号3");
        distributorBankParam1.setState((byte)1);
        distributorBankParam1.setBankAddress("详细地址3");
        distributorBankParam1.setRemark("备注3");

        DistributorBankParam distributorBankParam2 = new DistributorBankParam();
        distributorBankParam2.setId(2L);
        distributorBankParam2.setDistributorBaseId(1L);
        distributorBankParam2.setBankName("银行名称4");
        distributorBankParam2.setBankCode("银行代码4");
        distributorBankParam2.setUserName("账户名4");
        distributorBankParam2.setCardNumber("银行卡号4");
        distributorBankParam2.setState((byte)2);
        distributorBankParam2.setBankAddress("详细地址4");
        distributorBankParam2.setRemark("备注4");


        DistributorBankParam distributorBankParam3 = new DistributorBankParam();
        distributorBankParam3.setDistributorBaseId(1L);
        distributorBankParam3.setBankName("银行名称2");
        distributorBankParam3.setBankCode("银行代码2");
        distributorBankParam3.setUserName("账户名2");
        distributorBankParam3.setCardNumber("银行卡号2");
        distributorBankParam3.setState((byte)2);
        distributorBankParam3.setBankAddress("详细地址2");
        distributorBankParam3.setRemark("备注2");

        Params.add(distributorBankParam1);
        Params.add(distributorBankParam2);
        Params.add(distributorBankParam3);

        distributorBankUpdateParam.setDistributorBankParams(Params);

        ArrayList<Long>  deleteBankIds= new ArrayList<>();
        deleteBankIds.add(12L);
        deleteBankIds.add(13L);

        distributorBankUpdateParam.setDeleteBankIds(deleteBankIds);


        //发送请求
        MvcResult mvcResult = postToResult("/distributor/updateDistributionBank", distributorBankUpdateParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }

    //根据分销商id获取财务信息
    @Test
    public void getDistributionBankByDistribuBaseId() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        //发送请求
        MvcResult mvcResult = getToResult("/distributor/getDistributionBankByDistribuBaseId?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //保存分销商业务信息
    @Test
    public void saveDistributionBussiness() throws Exception {
        //设置参数
        DistributorBusinessParam distributorBusinessParam = new DistributorBusinessParam();
        distributorBusinessParam.setDistributorBaseId(1L);
        distributorBusinessParam.setLevel((byte)0);
        distributorBusinessParam.setChannelType((byte)1);
        distributorBusinessParam.setResourceType("1");
        distributorBusinessParam.setGrade(200L);
        distributorBusinessParam.setRemark("备注");
        distributorBusinessParam.setCooperationMethod((byte)1);
        distributorBusinessParam.setSettlementMethod((byte)1);
        distributorBusinessParam.setSettlementType((byte)1);
        distributorBusinessParam.setSettlementTime("2020-03-02");
        distributorBusinessParam.setEnjoyService("享受服务");
        distributorBusinessParam.setBusinessScope("业务范围");
        distributorBusinessParam.setBusinessCounterpart("业务对接人");
        distributorBusinessParam.setPreferentialStrength("优惠力度");


        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveDistributionBussiness", distributorBusinessParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //修改分销商业务信息
    @Test
    public void updateDistributionBussiness() throws Exception {
        //设置参数
        DistributorBusinessParam distributorBusinessParam = new DistributorBusinessParam();
        distributorBusinessParam.setId(1L);
        distributorBusinessParam.setDistributorBaseId(1L);
        distributorBusinessParam.setLevel((byte)0);
        distributorBusinessParam.setChannelType((byte)1);
        distributorBusinessParam.setResourceType("1");
        distributorBusinessParam.setGrade(200L);
        distributorBusinessParam.setRemark("备注");
        distributorBusinessParam.setCooperationMethod((byte)1);
        distributorBusinessParam.setSettlementMethod((byte)1);
        distributorBusinessParam.setSettlementType((byte)1);
        distributorBusinessParam.setSettlementTime("2020-03-02");
        distributorBusinessParam.setEnjoyService("享受服务001");
        distributorBusinessParam.setBusinessScope("业务范围001");
        distributorBusinessParam.setBusinessCounterpart("业务对接人001");
        distributorBusinessParam.setPreferentialStrength("优惠力度001");

        //发送请求
        MvcResult mvcResult = postToResult("/distributor/updateDistributionBussiness", distributorBusinessParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }

    //根据分销商id获取业务信息
    @Test
    public void getDistributionBussinessByDistribuBaseId() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getDistributionBussinessByDistribuBaseId?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //保存分销商补充信息
    @Test
    public void saveDistributionAdd() throws Exception {
        //设置参数
        DistributorAddParam distributorAddParam = new DistributorAddParam();
        distributorAddParam.setDistributorBaseId(1L);
        distributorAddParam.setBusinessLicense1("营业执照正面");
        distributorAddParam.setBusinessLicense2("营业执照反面");
        distributorAddParam.setIdCardPage1("身份证正面");
        distributorAddParam.setIdCardPage2("身份证反面");
        distributorAddParam.setDriverCardPage1("驾驶证正面");
        distributorAddParam.setDriverCardPage2("驾驶证反面");
        distributorAddParam.setGuideCardPage1("导游证正面");
        distributorAddParam.setGuideCardPage2("导游证反面");
        distributorAddParam.setWithdrawAccountType("1");
/*        distributorAddParam.setBankName("银行名");
        distributorAddParam.setBankAccountName("银行账户名");
        distributorAddParam.setBankCardNumber("银行卡号");
        distributorAddParam.setBankCode("银行代码");*/
        distributorAddParam.setWechatNickname("微信");
        distributorAddParam.setVerifyState((byte)0);

        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveDistributionAdd", distributorAddParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //修改分销商补充信息
    @Test
    public void updateDistributionAdd() throws Exception {
        //设置参数
        DistributorAddParam distributorAddParam = new DistributorAddParam();
        distributorAddParam.setId(13L);
        distributorAddParam.setDistributorBaseId(1L);
        distributorAddParam.setBusinessLicense1("营业执照正面1");
        distributorAddParam.setBusinessLicense2("营业执照反面1");
        distributorAddParam.setIdCardPage1("身份证正面1");
        distributorAddParam.setIdCardPage2("身份证反面1");
        distributorAddParam.setDriverCardPage1("驾驶证正面1");
        distributorAddParam.setDriverCardPage2("驾驶证反面1");
        distributorAddParam.setGuideCardPage1("导游证正面1");
        distributorAddParam.setGuideCardPage2("导游证反面1");
        distributorAddParam.setWithdrawAccountType("1");
/*        distributorAddParam.setBankName("银行名1");
        distributorAddParam.setBankAccountName("银行账户名1");
        distributorAddParam.setBankCardNumber("银行卡号1");
        distributorAddParam.setBankCode("银行代码1");*/
        distributorAddParam.setWechatNickname("微信1");
        distributorAddParam.setVerifyState((byte)0);

        //发送请求
        MvcResult mvcResult = postToResult("/distributor/updateDistributionAdd", distributorAddParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }

    //根据分销商id获取补充信息
    @Test
    public void getDistributionAddByDistribuBaseId() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getDistributionBaseByDistribuBaseId?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //根据分销商id验证该分销商是否可以提现
    @Test
    public void getDistrbutorIsCashWithdrawal() throws Exception {
        //设置参数
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("distribuBaseId", Arrays.asList("1"));
        MvcResult mvcResult = getToResult("/distributor/getDistrbutorIsCashWithdrawal?distribuBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //根据分销商id获取分销商账号信息
    @Test
    public void getDistrbutorAccoultListByDistribuBaseId() throws Exception {
        //设置参数
        MvcResult mvcResult = getToResult("/distributor/getDistrbutorAccoultListByDistribuBaseId?distributorBaseId=1", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


    //保存或修改分销商账号信息
    @Test
    public void saveOrDistributionAccount() throws Exception {
        //设置参数

        DistributorAccountParam distributorAccountParam = new DistributorAccountParam();
//        distributorAccountParam.setId(0L);
        distributorAccountParam.setDistributorBaseId(1L);
        distributorAccountParam.setAccountNumber("15110016362");
        distributorAccountParam.setAccountPassword("");
        distributorAccountParam.setState((byte)1);
        distributorAccountParam.setPhone("15111112221");
        distributorAccountParam.setSendMessage((byte)1);
        distributorAccountParam.setRemark("123");
        distributorAccountParam.setIsdefaultPassword((byte)1);
        //发送请求
        MvcResult mvcResult = postToResult("/distributor/saveOrDistributionAccount", distributorAccountParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }


    //根据分销商账号id账号详细信息
    @Test
    public void getDistributionAccountById() throws Exception {
        //设置参数
        MvcResult mvcResult = getToResult("/distributor/getDistributionAccountById?distributionAccountId=8", null, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }



}
