package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.service.order.model.OrderRefundRecord;


/**
 *退款服务
 */
public interface OrderRefundService {

    /**
     * 获取退款明细
     * @param orderRefundRecord
     * @return
     */
    OrderRefundRecord getOrderRefund(OrderRefundRecord orderRefundRecord);
}
