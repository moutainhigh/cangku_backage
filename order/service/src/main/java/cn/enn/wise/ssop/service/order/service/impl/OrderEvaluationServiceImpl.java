package cn.enn.wise.ssop.service.order.service.impl;
import cn.enn.wise.ssop.service.order.mapper.OrderEvaluationMapper;
import cn.enn.wise.ssop.service.order.model.OrderEvaluate;
import cn.enn.wise.ssop.service.order.service.OrderEvaluationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderEvaluationServiceImpl implements OrderEvaluationService {

    @Autowired
    private OrderEvaluationMapper orderEvaluationMapper;

    @Override
    public boolean saveOrderEvaluation(OrderEvaluate orderEvaluate){
        int insert = orderEvaluationMapper.insert(orderEvaluate);
        return insert==1?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public OrderEvaluate selectOne(long orderId) {
        QueryWrapper<OrderEvaluate> orderEvaluateQueryWrapper = new QueryWrapper<>();
        orderEvaluateQueryWrapper.eq("order_id",orderId);
        return orderEvaluationMapper.selectOne(orderEvaluateQueryWrapper);
    }
}
