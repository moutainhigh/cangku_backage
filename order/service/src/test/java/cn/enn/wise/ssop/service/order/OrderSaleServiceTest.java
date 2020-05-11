package cn.enn.wise.ssop.service.order;

import cn.enn.wise.ssop.service.order.mapper.OrderSaleMapper;
import cn.enn.wise.ssop.service.order.model.OrderSale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceOrderApplication.class})
public class OrderSaleServiceTest {

    @Autowired
    private OrderSaleMapper orderSaleMapper;

    @Test
    public void testSaveOrderChangeRecord(){
        List<OrderSale> orderSaleList = new ArrayList<>();
        OrderSale orderSale = new OrderSale();
        orderSale.setOrderId(1L);
        orderSale.setOrderNo("sfdsfsd");
        orderSale.setSaleId(2l);
        orderSale.setSaleName("测试");
        orderSale.setSaleType((byte)3);
        orderSale.setRuleId(4l);
        orderSale.setRuleName("规则1");
        orderSaleList.add(orderSale);
        orderSaleMapper.batchInsert(orderSaleList);
    }
}
