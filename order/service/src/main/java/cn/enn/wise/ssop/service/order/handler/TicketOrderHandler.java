package cn.enn.wise.ssop.service.order.handler;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderTicketMapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.OrderTicket;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.uncs.base.pojo.TableBase;
import javafx.scene.control.Tab;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketOrderHandler extends OrderHandler {

    @Autowired
    private OrderTicketMapper orderTicketMapper;

    @Override
    public String generatorOrderNo() {
        return "TICKET" + System.currentTimeMillis() + (random.nextInt(900) + 100);
    }

    @Override
    public Integer saveOrder(BaseOrderParam orderParam) {
        //TODO:在这里保存业务订单特有的扩展表的信息
        OrderTicket orderTicket = new OrderTicket();
        BeanUtils.copyProperties(orderParam,orderTicket);
        orderTicket.setTouristPhone(orderParam.getPhone());
        orderTicket.setTouristName(orderParam.getCustomerName());
        orderTicket.setTouristCard(orderParam.getCertificateNo());
        return orderTicketMapper.insert(orderTicket);
    }

    @Override
    public TableBase handle(BaseOrderParam baseOrderParam, Orders orders, OrderGoods orderGoods) {
        OrderTicket orderTicket = new OrderTicket();
        orders.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orders.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        orderGoods.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orderGoods.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        BeanUtils.copyProperties(orderGoods,orderTicket);
        BeanUtils.copyProperties(baseOrderParam,orderTicket);
        BeanUtils.copyProperties(orders,orderTicket);
        orderTicket.setTouristPhone(baseOrderParam.getPhone());
        orderTicket.setTouristName(baseOrderParam.getCustomerName());
        orderTicket.setTouristCard(baseOrderParam.getCertificateNo());
        return orderTicket;
    }

}
