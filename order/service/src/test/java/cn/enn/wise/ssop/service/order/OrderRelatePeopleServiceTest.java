package cn.enn.wise.ssop.service.order;

import cn.enn.wise.ssop.service.order.mapper.OrderChangeRecordMapper;
import cn.enn.wise.ssop.service.order.mapper.OrderRelatePeopleMapper;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.model.OrderRelatePeople;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceOrderApplication.class})
public class OrderRelatePeopleServiceTest {

    @Autowired
    private OrderRelatePeopleMapper orderRelatePeopleMapper;

    @Test
    public void testSaveOrderChangeRecord(){
        List<OrderRelatePeople> orderRelatePeopleList = new LinkedList<>();
        OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
        orderRelatePeople.setMemberId(1L);
        orderRelatePeople.setCustomerName("sss");
        orderRelatePeople.setCertificateNo("sfddsfdfdsdfd");
        orderRelatePeople.setCertificateType((byte)1);
        orderRelatePeople.setOrderId(2l);
        orderRelatePeople.setPhone("15321705226");
        orderRelatePeopleList.add(orderRelatePeople);
        orderRelatePeopleMapper.batchInsert(orderRelatePeopleList);


    }
}
