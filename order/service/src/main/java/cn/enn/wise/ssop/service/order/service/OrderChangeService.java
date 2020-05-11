package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;

import java.util.List;

public interface OrderChangeService {

    /**
     * 查看操作记录结果
     * @param orderId
     * @return List<OrderChangeRecord>
     */
    List<OrderChangeRecord> getOrderChangeRecordList(Long orderId);
}
