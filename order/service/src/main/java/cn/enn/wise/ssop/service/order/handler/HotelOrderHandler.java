package cn.enn.wise.ssop.service.order.handler;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderHotelMapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.OrderHotel;
import cn.enn.wise.ssop.service.order.model.Orders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelOrderHandler extends OrderHandler{

    @Autowired
    private OrderHotelMapper orderHotelMapper;

    @Override
    public String generatorOrderNo() {
        return "HOTEL" + System.currentTimeMillis() + (random.nextInt(900) + 100);
    }

    @Override
    public Integer saveOrder(BaseOrderParam orderParam) {
        //TODO:在这里保存业务订单特有的扩展表的信息
        OrderHotel orderHoTel = new OrderHotel();
        BeanUtils.copyProperties(orderParam,orderHoTel);
        return orderHotelMapper.insert(orderHoTel);
    }

    @Override
    public OrderHotel handle(BaseOrderParam orderParam, Orders orders, OrderGoods orderGoods) {
        orders.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orders.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        orderGoods.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orderGoods.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        OrderHotel orderHoTel = new OrderHotel();
        BeanUtils.copyProperties(orderGoods,orderHoTel);
        BeanUtils.copyProperties(orderParam,orderHoTel);
        return orderHoTel;
    }
}
