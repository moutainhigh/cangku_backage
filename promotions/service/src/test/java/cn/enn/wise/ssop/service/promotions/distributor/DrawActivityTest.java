package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.ssop.api.promotions.dto.request.SysRoleParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserParam;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.Assert;

import java.util.ArrayList;

/**
 * 抽奖用户活动次数查询
 */
public class DrawActivityTest extends BaseIntegratedTest {



    @Test
    public void selectDrawDetail() throws Exception {

        UserParam userParam = new UserParam();
        userParam.setId(2L);
        userParam.setIdCard("162136455465465433");
        userParam.setName("卡能2");
        userParam.setNickName("六子");
        userParam.setHeadImg("a.PNG");
        userParam.setGender(1);

        ArrayList<SysRoleParam> list =new ArrayList();
        SysRoleParam sysRoleParam = new SysRoleParam();
        sysRoleParam.setCompanyId(-1L);
        sysRoleParam.setRoleName("用户");
        list.add(sysRoleParam);

        userParam.setSysRoles(list);

        MvcResult mvcResult = postToResult("/applets/draw/activity/user/details", userParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());


    }




}
