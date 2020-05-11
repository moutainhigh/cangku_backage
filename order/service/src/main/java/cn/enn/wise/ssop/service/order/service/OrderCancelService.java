package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.service.order.model.OrderCancelRecord;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.Orders;

import java.util.List;

/**
 * 订单取消服务
 */
public interface OrderCancelService {

    /**
     * 保存取消记录
     * @param orderChangeRecord
     * @param orderCancelRecordList
     * @param orderGoodsList
     */
    void saveOrderCancelRecord(OrderChangeRecord orderChangeRecord , List<OrderCancelRecord> orderCancelRecordList, List<OrderGoods> orderGoodsList);

    /**
     * 确认取消
     * @param orders
     * @param orderGoodsList
     * @param checkStatus
     */
    void confirmOrderCancelRecord(Orders orders, List<OrderGoods> orderGoodsList,int checkStatus);

    /**
     * 获取取消记录列表
     * @param orderId
     * @return
     */
    List<OrderCancelListDTO> orderCancelList(Long orderId);
}
