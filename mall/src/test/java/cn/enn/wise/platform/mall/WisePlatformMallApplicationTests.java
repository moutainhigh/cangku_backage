package cn.enn.wise.platform.mall;

import cn.enn.wise.platform.mall.util.GeneUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WisePlatformMallApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * web项目上下文
	 */
	@Autowired
	private WebApplicationContext webApplicationContext;


	/**
	 * 所有测试方法执行之前执行该方法
	 */
	@Before
	public void before() {
		//获取mockmvc对象实例
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void contextLoads() {
	}

	/**
	 * 测试商品列表
	 * @throws Exception
	 */
	@Test
	public void testGoodsList() throws Exception {
		String result = mockMvc.perform(get("/good/searchbydate").param("timeFrame","1"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		System.out.println(result);
	}

	@Test
    public void testformat(){
        BigDecimal bigDecimal1 = new BigDecimal("0.01");
        BigDecimal bigDecimal2 = new BigDecimal("590.00");
        System.out.println(GeneUtil.formatBigDecimal(bigDecimal1));
        System.out.println(GeneUtil.formatBigDecimal(bigDecimal2));
    }
}
