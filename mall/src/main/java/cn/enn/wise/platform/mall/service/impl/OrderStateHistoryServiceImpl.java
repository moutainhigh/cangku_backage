package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.autotable.OrderStateHistory;
import cn.enn.wise.platform.mall.bean.param.OrderAllAttribute;
import cn.enn.wise.platform.mall.mapper.OrderStateHistoryMapper;
import cn.enn.wise.platform.mall.service.OrderStateHistoryService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 订单流转记录实现
 * @program: mall
 * @author: zsj
 * @create: 2020-01-15 17:26
 **/
@Service
public class OrderStateHistoryServiceImpl implements OrderStateHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(OrderStateHistoryServiceImpl.class);

    @Autowired
    private OrderStateHistoryMapper orderStateHistoryMapper;

    @Override
    public void saveOrderStateHistory(Long id, String operate) {
        logger.info("=======定单id" + id + "==========");
        OrderAllAttribute orderAllAttribute = orderStateHistoryMapper.selectOrdersById(id);
        if(orderAllAttribute != null){
            OrderStateHistory orderStateHistory = new OrderStateHistory();
            orderStateHistory.setOrderId(orderAllAttribute.getId());
            orderStateHistory.setOrderCode(orderAllAttribute.getOrderCode());
            orderStateHistory.setState(orderAllAttribute.getState());
            orderStateHistory.setPayStatus(orderAllAttribute.getPayStatus());
            orderStateHistory.setHistoryTime(new Timestamp(System.currentTimeMillis()));
            orderStateHistory.setOperate(operate);
            orderStateHistory.setOrderJson(JSONObject.toJSONString(orderAllAttribute));
            orderStateHistoryMapper.insert(orderStateHistory);
            logger.info("=======定单id" + id + "流转记录成功==========");
        }else {
            logger.info("=======定单id" + id + "不存在==========");
        }
    }
}
