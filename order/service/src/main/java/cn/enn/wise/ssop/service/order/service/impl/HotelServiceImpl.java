package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.service.order.handler.HotelOrderHandler;
import cn.enn.wise.ssop.service.order.mapper.OrderHotelMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.uncs.base.pojo.TableBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class HotelServiceImpl extends BaseOrderServiceImpl{

    @Autowired
    private HotelOrderHandler handler;

    @Autowired
    private OrderHotelMapper orderHotelMapper;

//    @Autowired
//    private OrderService orderService;

//    public Long saveOrder(BaseOrderParam orderParam) {
//
//        return orderService.saveOrderWapper(handler.initOrder(orderParam));
//    }

    @Override
    public void saveTableOrder(TableBase tableBase) {
        orderHotelMapper.insert((OrderHotel)tableBase);
    }

    @Override
    public Long saveOrder(BaseOrderParam baseOrderParam) {
        return null;
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
