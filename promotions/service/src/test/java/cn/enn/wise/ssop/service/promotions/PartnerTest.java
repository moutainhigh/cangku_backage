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



public class PartnerTest extends BaseIntegratedTest {



    //查询合作伙伴列表
    @Test
    public void getPartnerList() {

    }

    //获取合作伙伴详情
    @Test
    public void getPartnerDetail() {
        String partnerId="1";

        //get("/partner/detail/"+partnerId);
    }


    // 增加合作伙伴
    @Test
    public void addPartner() throws Exception {
        PartnerSaveParam partnerSaveParam = new PartnerSaveParam();

        partnerSaveParam.setAddress("北京市朝阳区摩托罗拉大厦");
        partnerSaveParam.setBankAccount("8654453353");
        partnerSaveParam.setBankAddress("朝阳区招商银行");
        partnerSaveParam.setBankName("张燕");
        partnerSaveParam.setContactEmail("4423@qq.com");

        partnerSaveParam.setContactName("张伟");
        partnerSaveParam.setContactPhone("1501111111");
        partnerSaveParam.setName("西藏旅游");
        partnerSaveParam.setDescs("西藏旅游售卖");

        //post("/partner/save",partnerSaveParam);
    }


    // 编辑合作伙伴
    @Test
    public void editPartner() throws Exception {
        PartnerSaveParam partnerSaveParam = new PartnerSaveParam();

        partnerSaveParam.setAddress("北京市朝阳区摩托罗拉大厦");
        partnerSaveParam.setBankAccount("8654453353");
        partnerSaveParam.setBankAddress("朝阳区招商银行");
        partnerSaveParam.setBankName("张燕");
        partnerSaveParam.setContactEmail("4423@qq.com");

        partnerSaveParam.setContactName("张伟");
        partnerSaveParam.setContactPhone("1501111111");
        partnerSaveParam.setName("西藏旅游2");
        partnerSaveParam.setDescs("西藏旅游售卖");
        partnerSaveParam.setId(1L);

        //post("/partner/save",partnerSaveParam);
    }



    //根据id删除合作伙伴
    @Test(dependsOnMethods = "editPartner")
    public void delPartner() {
        String partnerId="1";

        //delete("/partner/delete/"+partnerId);
    }


    //获取合作伙伴详情
    @Test
    public void getPartnerClientDetail() {
        String clientId="1";

        //get("/partner-clients/"+clientId);
    }



    //客户端
    //查询合作伙伴客户端列表
//    @Test
//    public void getPartnerClientList() {
//        String partnerId = "1";
//
//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//
//        paramMap.add("pageNo","1");
//        paramMap.add("pageSize","10");
//
//        get("/partner/"+partnerId+"/clients",paramMap);
//    }








}
