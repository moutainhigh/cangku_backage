package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.bean.vo.ProjectPeriodResVo;
import cn.enn.wise.platform.mall.service.GoodsProjectPeriodService;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile({"integrated", "test", "local"})
public class ProjectPeriodTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private GoodsProjectPeriodService periodService;

    @Autowired
    private GoodsProjectService projectService;

    private String token = null;

    @Before
    public void before() throws Exception {
        token = CommonTestUtil.getLoginToken(mockMvc);
    }

    @Test
    public void testList() throws Exception {
        String result = this.mockMvc.perform(get("/period/list/1")
                .header("token", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ResponseEntity<List<ProjectPeriodResVo>> responseEntity = gson.fromJson(result,
                new TypeToken<ResponseEntity<List<ProjectPeriodResVo>>>() {
                }.getType());
        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
        /*List<ProjectPeriodResVo> periodResVoList = responseEntity.getValue();
        Assert.assertTrue(periodResVoList.size() > 0);*/
    }


    @Test
    public void testGetGoodsProjectList() throws Exception {

        String contentAsString = this.mockMvc.perform(get("/period/project/list"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(contentAsString);
        Integer result = jsonObject.getInteger("result");

        if(result != 1){
            throw new RuntimeException("获取项目列表失败");
        }
        System.out.println(jsonObject.getJSONArray("value"));
    }
}
