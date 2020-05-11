package cn.enn.wise.ssop.service.order;

import cn.enn.wise.ssop.service.order.mapper.OrderChangeRecordMapper;
import cn.enn.wise.ssop.service.order.service.OrderChangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceOrderApplication.class})
public class OrderChangeServiceTest {

    @Autowired
    private OrderChangeRecordMapper orderChangeRecordMapper;

    @Test
    public void testSaveOrderChangeRecord(){
        //orderChangeRecordMapper.batchInsertOrderChangeRecord();
    }
}
