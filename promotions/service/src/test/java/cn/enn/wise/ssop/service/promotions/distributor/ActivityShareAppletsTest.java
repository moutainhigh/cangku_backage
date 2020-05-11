package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.Assert;

/**
 * 小程序端促销活动相关接口测试
 */
public class ActivityShareAppletsTest extends BaseIntegratedTest {


    //根据促销活动id获取分享页面相关信息
    @Test
    public void getDistributionAddByDistribuBaseId() throws Exception {
        //发送请求
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("company_id","1");
        MvcResult mvcResult = getToResult("/applets/activityShareApplets/getActivitySharePageInfoByActivityBaseId?activityBaseId=1", null,httpHeaders);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());

    }


}
