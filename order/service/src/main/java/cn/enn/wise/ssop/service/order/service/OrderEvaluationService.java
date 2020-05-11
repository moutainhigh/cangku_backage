package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.service.order.model.OrderEvaluate;

/**
 * 订单评价
 */
public interface OrderEvaluationService{

    /**
     * 保存订单评价
     * @param orderEvaluate
     * @return
     */
    boolean saveOrderEvaluation(OrderEvaluate orderEvaluate);

    /**
     * 获取订单评价信息
     * @param orderId
     * @return
     */
    OrderEvaluate selectOne(long orderId);

}
