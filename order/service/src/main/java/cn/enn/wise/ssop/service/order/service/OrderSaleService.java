package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.service.order.model.OrderSale;

import java.util.List;

/**
 * 订单营销策略
 */
public interface OrderSaleService {

    /**
     * 检验销售策略是否可用
     * @return
     */
    boolean checkSaleStratogyAvilable();

    /**
     * 使用营销策略
     * @param orderSale
     */
    void useSaleStratogy(OrderSale orderSale);

    /**
     * 获取营销策略列表
     * @param orderId
     * @return
     */
    List<OrderSale> getOrderSaleList(Long orderId);

    /**
     * 批量保存订单营销策略
     * @param orderSaleList
     */
    void batchSaveOrderSale(List<OrderSale> orderSaleList);
}
