package cn.enn.wise.ssop.service.order;


import cn.enn.wise.ssop.service.order.utils.HttpClient;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

@Slf4j
public class WithdrawControllerTest {

    private final String baseURL = "http://localhost:9030";

    @Test
    void getWithdrawInfo() {
        String path = baseURL + "/info/distribute";
        log.info("====> 查询指定时间段的分销信息");
        Map<String,String> param = new HashedMap();
        param.put("distributorId","1");
        param.put("startDate","2019-01-01");
        param.put("endDate","2020-12-31");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);
    }

    @Test
    void sendVerifyCode() {
        String path = baseURL + "/verifyCode";
        log.info("====> 发送短信验证码");
        Map<String,String> param = new HashedMap();
        param.put("cellphone","15653125630");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);
    }

    @Test
    void checkVerifyCode() {
        String path = baseURL + "/checkVerifyCode";
        log.info("====> 验证短信验证码");
        Map<String,String> param = new HashedMap();
        param.put("phone","15653125630");
        param.put("code","586214");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);
    }

    @Test
    void submitApply() {
        String path = baseURL + "/submitApply";
        log.info("====> 提交提现申请");
        Map<String,String> param = new HashedMap();
        param.put("distributorId","15653125630");
        param.put("cellphone","586214");
        param.put("verifyCode","586214");
        param.put("accountSign","1002");
        param.put("accountUser","高广林");
        param.put("accountType","1");
        param.put("accountNum","586214");
        param.put("orderId","{10006,10007}");
        param.put("startDate","2020-01-01");
        param.put("endDate","2020-05-30");

        String resp = HttpClient.post(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);
    }

    @Test
    void listInDate() {

        String path = baseURL + "/listByDate";
        log.info("====> 按照日期查询提现单");
        Map<String,String> param = new HashedMap();
        param.put("distributorId","10007");
        param.put("startDate","2020-01-01");
        param.put("endDate","2020-05-30");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);

    }

    @Test
    void detail() {

        String path = baseURL + "/detail";
        log.info("====> 根据序列号查询提现单详情");
        Map<String,String> param = new HashedMap();
        param.put("withdrawSerial","20190205053");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);

    }

    @Test
    void sendComplement() {

        String path = baseURL + "/sendComplement";
        log.info("====> 根据序列号查询提现单详情");
        Map<String,String> param = new HashedMap();
        param.put("withdrawSerials","20190205053,20190205053");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);

    }

    @Test
    void putOut() {
        String path = baseURL + "/putOut";
        log.info("====> 批量设置状态为发放");
        Map<String,String> param = new HashedMap();
        param.put("withdrawSerials","20190205053,20190205053");

        String resp = HttpClient.get(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);
    }

    @Test
    void listByParam() {

        String path = baseURL + "/page/list";
        log.info("====> 后台数据列表：根据参数查询");
        Map<String,String> param = new HashedMap();
        param.put("pageNum","1");
        param.put("pageSize","30");
        param.put("cellphone","15653125630");
        param.put("applyDate","2016-01-01/2016-01-02");
        param.put("distributor","高广林");
        param.put("putOut","-2");
        param.put("serial","20190205053");
        param.put("auditDate","2016-01-01");
        param.put("permit","-2");
        param.put("auditor","高广林");

        String resp = HttpClient.post(path,param);
        log.info("==>RESULT:{}",resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        Object code = jsonObject.get("code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS);

    }
}