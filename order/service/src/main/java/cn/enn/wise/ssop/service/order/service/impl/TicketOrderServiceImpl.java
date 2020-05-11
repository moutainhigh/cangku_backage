package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.service.order.handler.TicketOrderHandler;
import cn.enn.wise.ssop.service.order.mapper.OrderTicketMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.service.impl.BaseOrderServiceImpl;
import cn.enn.wise.uncs.base.pojo.TableBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiabaiye
 */
@Service
public class TicketOrderServiceImpl  extends BaseOrderServiceImpl{

    @Autowired
    private TicketOrderHandler handler;
    @Autowired
    private OrderTicketMapper orderTicketMapper;
    @Resource
    private OrderService orderService;

    @Override
    public void saveTableOrder(TableBase tableBase) {
        orderTicketMapper.insert((OrderTicket) tableBase);
    }

    @Override
    public Long saveOrder(BaseOrderParam orderParam) {

        return orderService.saveOrderWapper(handler.initOrder(orderParam));
    }

    @Override
    public void saveOrderCancelRecord(OrderChangeRecord orderChangeRecord, List<OrderCancelRecord> orderCancelRecordList, List<OrderGoods> orderGoodsList) {

    }

    @Override
    public void confirmOrderCancelRecord(Orders orders, List<OrderGoods> orderGoodsList, int checkStatus) {

    }

    @Override
    public List<OrderCancelListDTO> orderCancelList(Long orderId) {
        return null;
    }
}
