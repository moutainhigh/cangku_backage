package cn.enn.wise.ssop.service.promotions;

import cn.enn.wise.ssop.api.promotions.dto.request.PartnerSaveParam;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicePromotionsApplication.class)
@WebAppConfiguration
@Slf4j
public class ChannelTest extends BaseIntegratedTest {

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        //initSql("/data/partnerTest.sql");
    }

    //查询合作伙伴列表
    //@Test
    public void getPartnerList() {

        //get("/common/getEnum/"+"channel",null);
    }



}
