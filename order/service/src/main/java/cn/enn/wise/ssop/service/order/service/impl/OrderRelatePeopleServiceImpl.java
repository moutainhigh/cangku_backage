package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.OrderRelatePeopleMapper;
import cn.enn.wise.ssop.service.order.model.OrderRelatePeople;
import cn.enn.wise.ssop.service.order.service.OrderRelatePeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderRelatePeopleServiceImpl implements OrderRelatePeopleService {

    @Autowired
    private OrderRelatePeopleMapper orderRelatePeopleMapper;

    /**
     * 查看订单联系人列表
     * @param orderId
     * @return
     */
    @Override
    public List<OrderRelatePeople> getOrderRelatePeopleListByParentOrderId(Long orderId) {
        OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
        orderRelatePeople.setParentOrderId(orderId);
        QueryWrapper<OrderRelatePeople> orderChildWrapper = new QueryWrapper<>(orderRelatePeople);
        return orderRelatePeopleMapper.selectList(orderChildWrapper);
    }

    @Override
    public OrderRelatePeople getOrderRelatePeopleInfo(Long orderId){
        OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
        orderRelatePeople.setOrderId(orderId);
        QueryWrapper<OrderRelatePeople> orderChildWrapper = new QueryWrapper<>(orderRelatePeople);
        return orderRelatePeopleMapper.selectOne(orderChildWrapper);
    }

    @Override
    public List<OrderRelatePeople> getOrderRelatePeopleListByOrderId(Long orderId) {
        return orderRelatePeopleMapper.getRelatePeopleListByOrderId(orderId);
    }

    @Override
    public void batchSaveOrderRelatePeople(List<OrderRelatePeople> orderRelatePeopleList){
        orderRelatePeopleMapper.batchInsert(orderRelatePeopleList);
    }


    @Override
    public Integer getTicketNumber(String phone, String userCard, String start, String end) {
        return orderRelatePeopleMapper.getTicketNumber(phone, userCard, start, end);
    }

    @Override
    public Integer getOrderNumberByMember(String phone, String userCard, String start, String end) {
        return null;
    }
}
