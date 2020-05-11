package cn.enn.wise.ssop.service.promotions.controller.pc;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBaseListParam;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.Assert;

import static org.junit.Assert.*;

public class ActivityControllerTest extends BaseIntegratedTest {

    @Test
    public void list() throws Exception {

        ActivityBaseListParam param = new ActivityBaseListParam();
        param.setPageNo(1);
        param.setPageSize(10);
        param.setState(new Byte("2"));
        param.setActivityType(new Byte("1"));
        MvcResult mvcResult = getToResult("/activity/list", param, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }
}