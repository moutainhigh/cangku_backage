package cn.enn.wise.ssop.service.order.service.impl;
import cn.enn.wise.ssop.service.order.mapper.OrderRefundRecordMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderRefundService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class OrderRefundServiceImpl implements OrderRefundService {

    @Autowired
    private OrderRefundRecordMapper orderRefundRecordMapper;

    @Override
    public OrderRefundRecord getOrderRefund(OrderRefundRecord orderRefundRecord) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(orderRefundRecord);
        return orderRefundRecordMapper.selectOne(queryWrapper);
    }
}
