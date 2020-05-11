package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.OrderSaleMapper;
import cn.enn.wise.ssop.service.order.model.OrderSale;
import cn.enn.wise.ssop.service.order.service.OrderSaleService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSaleServiceImpl implements OrderSaleService {

    @Autowired
    private OrderSaleMapper orderSaleMapper;

    @Override
    public boolean checkSaleStratogyAvilable() {
        //todo 校验用户的优惠券信息是否可用
        return false;
    }

    @Override
    public void useSaleStratogy(OrderSale orderSale) {

    }

    @Override
    public List<OrderSale> getOrderSaleList(Long orderId){
        QueryWrapper<OrderSale> orderSaleQueryWrapper = new QueryWrapper<>();
        OrderSale orderSale = new OrderSale();
        orderSale.setOrderId(orderId);
        orderSaleQueryWrapper.setEntity(orderSale);
        return orderSaleMapper.selectList(orderSaleQueryWrapper);
    }



    @Override
    public void batchSaveOrderSale(List<OrderSale> orderSaleList){
        if(orderSaleList!=null && orderSaleList.size()>0) {
            orderSaleMapper.batchInsert(orderSaleList);
        }
    }
}
