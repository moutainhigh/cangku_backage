package cn.enn.wise.ssop.service.order;

import cn.enn.wise.ssop.service.order.mapper.OrderChangeRecordMapper;
import cn.enn.wise.ssop.service.order.mapper.OrderGoodsMapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceOrderApplication.class})
public class OrderGoodsServiceTest {

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Test
    public void testSaveOrderGoods(){
        List<OrderGoods> orderGoodsMapperList = new ArrayList<>(10);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoodsId(1L);
        orderGoods.setGoodsName("ceshi1");
        orderGoods.setGoodsType((byte)1);
        orderGoods.setAmount(2);
        orderGoods.setGoodsPrice(23);
        orderGoods.setOrderId(2L);
        orderGoods.setOrderNo("sfsdfsdfdsfdsfds");
        orderGoods.setOrderStatus(3);
        orderGoods.setPayStatus(4);
        orderGoods.setTransactionStatus(5);
        orderGoodsMapperList.add(orderGoods);

        OrderGoods orderGoods1 = new OrderGoods();
        orderGoods1.setGoodsId(2L);
        orderGoods1.setGoodsName("ceshi2");
        orderGoods1.setGoodsType((byte)3);
        orderGoods1.setAmount(53);
        orderGoods1.setGoodsPrice(32);
        orderGoods1.setSkuId(5L);
        orderGoods1.setOrderId(13212321L);
        orderGoods1.setOrderNo("aaaaaaaaaaaaaaaaaaaaaaaa");
        orderGoods1.setOrderStatus(4);
        orderGoods1.setPayStatus(8);
        orderGoods1.setTransactionStatus(8);
        orderGoods1.setExtraInformation("2423weruweootuoewuroewurewrew");
        orderGoods1.setParentOrderId(20L);
        orderGoodsMapperList.add(orderGoods1);
        orderGoodsMapper.batchInsert(orderGoodsMapperList);
    }
}
