package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.api.order.dto.response.TradeLogDTO;
import cn.enn.wise.ssop.service.order.config.status.*;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.model.OrderCancelRecord;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.uncs.base.pojo.TableBase;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 订单服务
 * 1.保存订单
 * 2.根据订单id获取订单信息
 * 3.获取会员订单
 * 4.更新订单
 * 5.删除订单
 * 6.重复校验
 * 7.订单确认
 * 8.取消订单（订单的取消操作）
 * 9.订单核销
 * 10.退票
 * 11.修改联系人
 */
public interface OrderService {


    void saveTableOrder(TableBase tableBase);

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    Orders getOrderInfo(Long orderId);

    /**
     * 生成客户订单
     *
     * @param baseOrderParam 框架订单
     * @return
     */
     Long saveOrder(BaseOrderParam baseOrderParam);

    /**
     * 生成客户订单
     *
     * @param orderWrapper 框架订单
     * @return
     */
    Long saveOrderWapper(OrderWrapper orderWrapper);

    /**
     * 校验重复
     *
     * @param applyId
     * @param orderSource
     * @return
     */
    int checkDuplicate(String applyId, Byte orderSource);

    /**
     * 取消订单
     *
     * @param orderGoods
     * @param orderCancelParam
     * @param orders
     * @return
     */
    boolean cancelOrder(OrderGoods orderGoods, OrderCancelParam orderCancelParam, Orders orders);

    /**
     * 订单确认
     *
     * @param orders
     * @return
     */
    boolean confirmOrder(Orders orders);

    /**
     * 更新订单
     *
     * @param orders
     * @return
     */
    boolean updateOrder(Orders orders);

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    boolean deleteOrder(Long id);

    /**
     * 订单状态变更
     *
     * @param orderId
     * @param goodsOrderStatusEnum
     * @param goodsPayStatusEnum
     * @param goodsTransactionStatusEnum
     * @param goodsRefundStatusEnum
     * @param goodSystemStatusEnum
     * @param orderStatusEnum
     * @param payStatusEnum
     * @param transactionStatusEnum
     * @param refundStatusEnum
     * @param systemStatusEnum
     * @param changeReason
     * @param both
     * @return
     */
    boolean updateOrderStatus(Long orderId, OrderStatusEnum goodsOrderStatusEnum,
                              PayStatusEnum goodsPayStatusEnum, TransactionStatusEnum goodsTransactionStatusEnum,
                              RefundStatusEnum goodsRefundStatusEnum, SystemStatusEnum goodSystemStatusEnum,
                              OrderStatusEnum orderStatusEnum, PayStatusEnum payStatusEnum, TransactionStatusEnum transactionStatusEnum,
                              RefundStatusEnum refundStatusEnum, SystemStatusEnum systemStatusEnum, String changeReason, boolean both);

    /**
     * 获取用户订单
     *
     * @param userId
     * @param pageSize
     * @param pageNo
     * @return
     */
    QueryData<TradeLogDTO> getUserOrder(Long userId, Long pageSize, Long pageNo);

    /**
     * 保存取消记录
     *
     * @param orderChangeRecord
     * @param orderCancelRecordList
     * @param orderGoodsList
     */
    void saveOrderCancelRecord(OrderChangeRecord orderChangeRecord, List<OrderCancelRecord> orderCancelRecordList, List<OrderGoods> orderGoodsList);

    /**
     * 确认取消
     *
     * @param orders
     * @param orderGoodsList
     * @param checkStatus
     */
    void confirmOrderCancelRecord(Orders orders, List<OrderGoods> orderGoodsList, int checkStatus);

    /**
     * 获取取消记录列表
     *
     * @param orderId
     * @return
     */
    List<OrderCancelListDTO> orderCancelList(Long orderId);
}