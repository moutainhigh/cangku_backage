package cn.enn.wise.ssop.service.order.handler;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.uncs.base.pojo.TableBase;
import org.springframework.stereotype.Component;

/**
 * 套餐订单
 *
 * @author baijie
 * @date 2020-05-03
 */
@Component
public class PackageOrderHandler extends OrderHandler {
    @Override
    public String generatorOrderNo() {


        return "PACKAGE" + System.currentTimeMillis() + (random.nextInt(900) + 100);
    }

    @Override
    public Integer saveOrder(BaseOrderParam baseOrder) {

        return null;
    }

    @Override
    public TableBase handle(BaseOrderParam defaultOrderSaveParam, Orders orders, OrderGoods orderGoods) {
        orders.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orders.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        orderGoods.setOrderStatus(OrderStatusEnum.TICKET_WAIT_PAY.getValue());
        orderGoods.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        return null;
    }


}
