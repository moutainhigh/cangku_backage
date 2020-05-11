package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.SystemStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.TransactionStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.*;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderCancelService;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.ssop.service.order.utils.OrderCancelRecordUtils;
import cn.enn.wise.uncs.base.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderCancelServiceImpl implements OrderCancelService {

    private static final int CHECK_STATUS = 1;

    private static final int CHECK_STATUS_SUCCESS = 2;

    private static final int CHECK_STATUS_REFUSE = 3;
    @Autowired
    private OrderCancelRecordMapper orderCancelRecordMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private OrderChangeRecordMapper orderChangeRecordMapper;

    @Autowired
    private OrderRelatePeopleMapper orderRelatePeopleMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrderCancelRecord(OrderChangeRecord orderChangeRecord ,List<OrderCancelRecord> orderCancelRecordList, @Nullable List<OrderGoods> orderGoodsList){
        try {
            /*orderGoodsList.forEach(c->{
                c.setSystemStatus(SystemStatusEnum.TICKET_CHECK.getValue());
                c.setRefundStatus(0);
                c.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            });*/
            //修改订单商品为退单状态
            orderGoodsMapper.batchUpdate(orderGoodsList);
            //保存总订单状态变化记录
            /*orderChangeRecord.setRefundStatus(0);
            orderChangeRecord.setSystemStatus(SystemStatusEnum.TICKET_CHECK.getValue());
            orderChangeRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            orderChangeRecord.setPayStatus(PayStatusEnum.PAYED.getValue());*/
            //orderChangeRecordMapper.insert(orderChangeRecord);
//            //保存总订单状态变化记录
//            orderChangeRecord.setRefundStatus(0);
//            orderChangeRecord.setSystemStatus(SystemStatusEnum.TICKET_CHECK.getValue());
//            orderChangeRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//            orderChangeRecord.setPayStatus(PayStatusEnum.PAYED.getValue());
            orderChangeRecordMapper.insert(orderChangeRecord);
            //保存订单取消记录
           /* orderCancelRecordList.forEach(c->{
                c.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                c.setCreateTime(new Timestamp(System.currentTimeMillis()));
            });*/
            orderCancelRecordMapper.batchInsert(orderCancelRecordList);
        }catch(BusinessException e){
            log.error("退单异常：{}",e);
            OrderExceptionAssert.BACK_TICKET_EXCEPTION.assertFail();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmOrderCancelRecord(Orders orderInfo, List<OrderGoods> orderGoodsList,int checkStatus) {
        try {
            BigDecimal refundPrice = BigDecimal.ZERO;
            BigDecimal youhuiPrice = BigDecimal.ZERO;
            for (OrderGoods orderGoods : orderGoodsList) {
                if (checkStatus == 1) {
                    //todo 审核通过
                    refundPrice.add(orderGoods.getRefundPrice());
                    youhuiPrice.add(orderGoods.getDecreasePrice());
                    orderGoods.setTransactionStatus(TransactionStatusEnum.TICKET_BACK.getValue());
                } else {
                    orderGoods.setDecreasePrice(BigDecimal.ZERO);
                    orderGoods.setRefundPrice(BigDecimal.ZERO);
                    orderGoods.setTransactionStatus(TransactionStatusEnum.TICKET_CHECK_UN_PASS.getValue());
                    //todo 审核拒绝
                }
            }
            //修改订单商品状态，优惠金额为0，退款金额为0
            orderGoodsMapper.batchUpdate(orderGoodsList);
            Orders target = new Orders();
            target.setOrderId(orderInfo.getOrderId());

            target.setDecreasePrice(youhuiPrice.add(orderInfo.getDecreasePrice()!=null?orderInfo.getDecreasePrice():BigDecimal.ZERO));
            target.setRefundPrice(refundPrice.add(orderInfo.getRefundPrice()!=null?orderInfo.getRefundPrice():BigDecimal.ZERO));
            if (checkStatus == 1) {
                //审核通过，修改总订单优惠金额和退款金额
                orderService.updateOrder(target);
            }
            //记录总订单状态变更记录
            OrderChangeRecord orderChangeRecord = OrderChangeRecordUtils.buildOrderChangeRecord(orderInfo, target, "退票审核");
            orderChangeRecordMapper.insert(orderChangeRecord);
        }catch(Exception e){
            log.error("退单审核确认异常：{}",e);
            OrderExceptionAssert.BACK_TICKET_CHECK_EXCEPTION.assertFail();
        }
    }

    @Override
    public List<OrderCancelListDTO> orderCancelList(Long orderId) {
        QueryWrapper<OrderRelatePeople> orderRelatePeopleQueryWrapper = new QueryWrapper<>();
        orderRelatePeopleQueryWrapper.eq("parent_order_id",orderId);
        List<OrderRelatePeople> orderRelatePeople = orderRelatePeopleMapper.selectList(orderRelatePeopleQueryWrapper);
        List<Long> orderIds=  orderRelatePeople.stream().map(OrderRelatePeople::getOrderId).collect(Collectors.toList());
        List<OrderCancelListDTO> orderCancelListDTOS = orderCancelRecordMapper.orderCancelList(orderIds);
        orderCancelListDTOS.forEach(c->{
            String extraInformation = c.getExtraInformation();
            Map<String, Object> map = (Map<String, Object>) JSONObject.parse(extraInformation);
            if( map!=null && map.size()!=0) {
                c.setEffectiveTime((String) map.get("effectiveTime"));
            }
        });
        return orderCancelListDTOS;

    }
}
