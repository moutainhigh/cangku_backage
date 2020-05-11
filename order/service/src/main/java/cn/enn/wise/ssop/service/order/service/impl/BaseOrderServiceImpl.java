package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.OrderCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.TradeDTO;
import cn.enn.wise.ssop.api.order.dto.response.TradeLogDTO;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.*;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.mapper.*;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.uncs.base.pojo.TableBase;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <strong>通用订单业务逻辑</strong>实现服务
 * 通用服务有：
 * 1、下单；
 * 2、支付
 * 3、...
 */

public abstract class BaseOrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);

    private static final String CHANGE_FEILD = "ORDER_STATUS";

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSaleMapper orderSaleMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private OrderCancelRecordMapper orderCancelRecordMapper;

    @Autowired
    private OrderChangeRecordMapper orderChangeRecordMapper;

    @Autowired
    private OrderRelatePeopleMapper orderRelatePeopleMapper;

/*    @Resource
    private OrderService orderService;*/

    @Override
    public boolean updateOrder(Orders orders) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orders.getOrderId());
        orders.setOrderId(null);
        int i = orderMapper.update(orders, queryWrapper);
        return i == 1 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(Long id) {
        try {
            orderMapper.deleteById(id);
            orderGoodsMapper.deleteById(id);
        } catch (Exception e) {
            log.error("订单id: {}删除异常：{}", id, e);
            OrderExceptionAssert.ORDER_DELETE_EXCEPTION.assertFail();
        }
        return true;
    }

    /**
     * 获取订单基础信息
     *
     * @param orderId
     * @return
     */
    @Override
    public Orders getOrderInfo(Long orderId) {
        Orders order = new Orders();
        order.setOrderId(orderId);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper(order);
        return orderMapper.selectOne(queryWrapper);
    }

    /**
     * TODO:在这里保存订单的主表信息
     *
     * @param orderWrapper
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrderWapper(OrderWrapper orderWrapper) {
        try {
            log.info("OrderWrapper:" + JSON.toJSONString(orderWrapper));
            //保存订单主表
            Orders orders = orderWrapper.getOrders();
            orderMapper.insert(orders);

            orderWrapper.getOrderGoodsList().forEach(c -> {
                orderGoodsMapper.insert(c);
            });

            orderWrapper.getOrderRelatePeopleList().forEach(c -> {
                orderRelatePeopleMapper.insert(c);
            });

            orderWrapper.getOrderSaleList().forEach(c -> {
                orderSaleMapper.insert(c);
            });
            TableBase tableBase = orderWrapper.getTableBase();
//            if(tableBase != null){
//                orderService.saveTableOrder(tableBase);
//            }

            //orderGoodsMapper.batchInsert(orderWrapper.getOrderGoodsList());
            //orderRelatePeopleMapper.batchInsert(orderWrapper.getOrderRelatePeopleList());

            /*if(orderWrapper.getOrderSaleList()!=null && orderWrapper.getOrderSaleList().size()>0) {
                orderSaleMapper.batchInsert(orderWrapper.getOrderSaleList());
            }*/

            // 下单成功，发送下单成功事件,用于生成订单核销码等
            //    OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(this,orderWrapper);
            //   applicationContext.publishEvent(orderCreatedEvent);

            return orders.getOrderId();
        } catch (Exception e) {
            log.error("保存订单数据异常:{}", e);
            OrderExceptionAssert.ORDER_INSERT_ERROR.assertFail();
        }
        return 0L;
    }

    @Override
    public boolean confirmOrder(Orders orders) {
        OrderSale orderSale = new OrderSale();
        orderSaleMapper.insert(orderSale);
        //todo 调用支付服务，支付成功，修改订单状态
        return false;
    }

    @Override
    public int checkDuplicate(String applyId, Byte orderSource) {
        Orders order = new Orders();
        order.setApplyId(applyId);
        order.setOrderSource(orderSource);
        Wrapper<Orders> orderWrapper = new LambdaQueryWrapper<>(order);
        return orderMapper.selectCount(orderWrapper);
    }

    @Override
    public boolean saveBatch(Collection<Orders> entityList) {
        return false;
    }

    /**
     * 更新订单状态变化记录，如果一个订单只有一个商品，主订单状态为子订单状态
     * 如果一个订单有多个商品（多个子订单），更改的不是最后一个更改太，主订单状态为传入的自定义
     * 状态，如果是最后一个更改的，主订单状态为子订单状态
     *
     * @param orderId
     * @param goodsOrderStatusEnum
     * @param goodsPayStatusEnum
     * @param goodsTransactionStatusEnum
     * @param orderStatusEnum
     * @param payStatusEnum
     * @param transactionStatusEnum
     * @param changeReason
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateOrderStatus(Long orderId, OrderStatusEnum goodsOrderStatusEnum, PayStatusEnum goodsPayStatusEnum, TransactionStatusEnum goodsTransactionStatusEnum,
                                     RefundStatusEnum goodsRefundStatusEnum, SystemStatusEnum goodSystemStatusEnum,
                                     OrderStatusEnum orderStatusEnum, PayStatusEnum payStatusEnum, TransactionStatusEnum transactionStatusEnum,
                                     RefundStatusEnum refundStatusEnum, SystemStatusEnum systemStatusEnum,
                                     String changeReason, boolean parentEqualsChild) {
        try {
            List<OrderGoods> updateGoodsList = new ArrayList<>();
            List<OrderGoods> orderGoodsList = orderGoodsMapper.getOrderGoodsListByOrderId(orderId);
            if (orderGoodsList == null || orderGoodsList.size() < 1) {
                OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
            }
            Orders temp = new Orders();


            if (orderGoodsList.size() == 1) {
                temp.setOrderId(orderId);
                //订单没有子订单
                if (goodsTransactionStatusEnum != null) {
                    temp.setTransactionStatus(goodsTransactionStatusEnum.getValue());
                }
                if (goodsPayStatusEnum != null) {
                    temp.setPayStatus(goodsPayStatusEnum.getValue());
                }
                if (goodsOrderStatusEnum != null) {
                    temp.setOrderStatus(goodsOrderStatusEnum.getValue());
                }
                if (goodsRefundStatusEnum != null) {
                    temp.setRefundStatus(goodsRefundStatusEnum.getValue());
                }
                if (goodSystemStatusEnum != null) {
                    temp.setSystemStatus(goodSystemStatusEnum.getValue());
                }
                updateGoodsList.add(orderGoodsList.get(0));
            } else {
                boolean autoUpdateOrderStatus = true;
                boolean autoUpdateTransactionStatus = true;
                boolean autoUpdatePayStatus = true;
                boolean autoUpdateRefundStatus = true;
                boolean autoUpdateSystemStatus = true;
                for (OrderGoods orderGoods : orderGoodsList) {
                    temp.setOrderId(orderGoods.getParentOrderId());
                    if (parentEqualsChild || orderGoods.getOrderId().equals(orderId)) {
                        if (goodsTransactionStatusEnum != null) {
                            orderGoods.setTransactionStatus(goodsTransactionStatusEnum.getValue());
                        }
                        if (goodsPayStatusEnum != null) {
                            orderGoods.setPayStatus(goodsPayStatusEnum.getValue());
                        }
                        if (goodsOrderStatusEnum != null) {
                            orderGoods.setOrderStatus(goodsOrderStatusEnum.getValue());
                        }
                        if (goodsRefundStatusEnum != null) {
                            orderGoods.setOrderStatus(goodsRefundStatusEnum.getValue());
                        }
                        if (goodSystemStatusEnum != null) {
                            orderGoods.setSystemStatus(goodSystemStatusEnum.getValue());
                        }
                        updateGoodsList.add(orderGoods);
                    } else {
                        if (orderGoods.getOrderStatus() == null || (goodsOrderStatusEnum != null && goodsOrderStatusEnum.getValue() != orderGoods.getOrderStatus())) {
                            autoUpdateOrderStatus = false;
                        }
                        if (orderGoods.getPayStatus() == null || (goodsPayStatusEnum != null && goodsPayStatusEnum.getValue() != orderGoods.getPayStatus())) {
                            autoUpdatePayStatus = false;
                        }
                        if (orderGoods.getTransactionStatus() == null || (goodsTransactionStatusEnum != null && goodsTransactionStatusEnum.getValue() != orderGoods.getTransactionStatus())) {
                            autoUpdateTransactionStatus = false;
                        }
                        if (orderGoods.getRefundStatus() == null || (goodsRefundStatusEnum != null && goodsRefundStatusEnum.getValue() != orderGoods.getRefundStatus())) {
                            autoUpdateRefundStatus = false;
                        }
                        if (orderGoods.getSystemStatus() == null || (goodSystemStatusEnum != null && goodSystemStatusEnum.getValue() != orderGoods.getSystemStatus())) {
                            autoUpdateSystemStatus = false;
                        }
                    }

                }
                if (parentEqualsChild || autoUpdateOrderStatus) {
                    if (goodsOrderStatusEnum != null) {
                        temp.setOrderStatus(goodsOrderStatusEnum.getValue());
                    } else if (orderStatusEnum != null) {
                        temp.setOrderStatus(orderStatusEnum.getValue());
                    }
                }
                if (parentEqualsChild || autoUpdateTransactionStatus) {
                    if (goodsTransactionStatusEnum != null) {
                        temp.setTransactionStatus(goodsTransactionStatusEnum.getValue());
                    } else if (transactionStatusEnum != null) {
                        temp.setTransactionStatus(transactionStatusEnum.getValue());
                    }
                }
                if (parentEqualsChild || autoUpdatePayStatus) {
                    if (goodsPayStatusEnum != null) {
                        temp.setPayStatus(goodsPayStatusEnum.getValue());
                    } else if (payStatusEnum != null) {
                        temp.setPayStatus(payStatusEnum.getValue());
                    }
                }
                if (parentEqualsChild || autoUpdateRefundStatus) {
                    if (goodsRefundStatusEnum != null) {
                        temp.setRefundStatus(goodsRefundStatusEnum.getValue());
                    } else if (refundStatusEnum != null) {
                        temp.setRefundStatus(refundStatusEnum.getValue());
                    }
                }
                if (parentEqualsChild || autoUpdateSystemStatus) {
                    if (goodSystemStatusEnum != null) {
                        temp.setSystemStatus(goodSystemStatusEnum.getValue());
                    } else if (systemStatusEnum != null) {
                        temp.setSystemStatus(systemStatusEnum.getValue());
                    }
                }
            }


            if (temp.getOrderStatus() != null || temp.getTransactionStatus() != null || temp.getPayStatus() != null || temp.getSystemStatus() != null || temp.getRefundStatus() != null) {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("order_id", temp.getOrderId());
                Orders source = orderMapper.selectOne(queryWrapper);
                orderMapper.updateOrderStatus(temp);
                OrderChangeRecord orderChangeRecord = OrderChangeRecordUtils.buildOrderChangeRecord(source, temp, changeReason);
                orderChangeRecordMapper.insert(orderChangeRecord);
            }
            for (OrderGoods orderGoods : updateGoodsList) {
                log.info("更新的状态是{}", orderGoods);
                orderGoodsMapper.updateById(orderGoods);
            }
            // orderGoodsMapper.batchUpdateOrderStatus(updateGoodsList);
            return true;
        } catch (Exception e) {
            log.error("更改订单状态异常：{}", e);
            OrderExceptionAssert.ORDER_STATUS_UPDATE_EXCEPTION.assertFail();
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public boolean cancelOrder(OrderGoods orderGoods, OrderCancelParam orderCancelParam, Orders orders) {
        try {
            updateOrderStatus(orderGoods.getOrderId(), OrderStatusEnum.TICKET_CLOSE, null,
                    null, null, null,
                    OrderStatusEnum.TICKET_CLOSE, null, null, null,
                    null, "取消订单", false);
            OrderCancelRecord orderCancelRecord = new OrderCancelRecord();
            BeanUtils.copyProperties(orderCancelParam, orderCancelRecord);
            orderCancelRecordMapper.insert(orderCancelRecord);
            return true;
        } catch (Exception e) {
            log.error("订单取消异常:{}", e);
            throw new RuntimeException("订单取消异常");
        }

    }

    @Override
    public void saveTableOrder(TableBase tableBase){

    }

    @Override
    public QueryData<TradeLogDTO> getUserOrder(Long userId, Long pageSize, Long pageNo) {
        Map map = orderMapper.getTotalCountInfo(userId);
        Long totalRecord = Long.parseLong(map.get("tradeSize").toString());
        Long totalPages = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        if ((pageNo - 1) < 1) {
            pageNo = 1L;
        }
        Long offset = (pageNo - 1) * pageSize;
        List<TradeDTO> tradeDTOS = orderMapper.getTradeDTO(userId, pageSize, offset);

        TradeLogDTO tradeLogDTO = new TradeLogDTO();
        tradeLogDTO.setTradeDTOList(tradeDTOS);
        tradeLogDTO.setTradeSize(totalRecord);
        tradeLogDTO.setAllAmount(map.get("allAmount").toString());
        List<TradeLogDTO> tradeLogDTOList = new ArrayList<>();
        tradeLogDTOList.add(tradeLogDTO);
        QueryData<TradeLogDTO> tradeLogDTOQueryData = new QueryData<>();
        tradeLogDTOQueryData.setPageNo(pageNo);
        tradeLogDTOQueryData.setPageSize(pageSize);
        tradeLogDTOQueryData.setTotalCount(totalPages);
        tradeLogDTOQueryData.setRecords(tradeLogDTOList);
        return tradeLogDTOQueryData;
    }
}
