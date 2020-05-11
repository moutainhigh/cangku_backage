package cn.enn.wise.ssop.service.order.utils;

import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.service.order.config.enums.FrontOrderStatusEnum;
import cn.enn.wise.ssop.service.order.model.*;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class OrderCancelRecordUtils {

    public static OrderCancelRecord buildOrderCancelRecord(TicketCancelParam ticketCancelParam, OrderGoods orderGoods){
        OrderCancelRecord orderCancelRecord = new OrderCancelRecord();
        orderCancelRecord.setOrderId(orderGoods.getOrderId());
        orderCancelRecord.setOrderNo(orderGoods.getOrderNo());
        orderCancelRecord.setCancelType(ticketCancelParam.getRefundType());
        orderCancelRecord.setCancelTime(new Date());
        orderCancelRecord.setParentOrderId(ticketCancelParam.getOrderId());
        orderCancelRecord.setCancelReasonType(ticketCancelParam.getRefundReasonType());
        orderCancelRecord.setCancelReason(ticketCancelParam.getRefundReasonDesc());
        return orderCancelRecord;
    }

    public static OrderRefundRecord buildOrderRefundRecord(TicketCancelParam ticketCancelParam, OrderGoods orderGoods){
        OrderRefundRecord orderRefundRecord = new OrderRefundRecord();
        orderRefundRecord.setOrderId(orderGoods.getOrderId());
        orderRefundRecord.setRefundPrice(ticketCancelParam.getRefundMoney());
        orderRefundRecord.setParentOrderId(ticketCancelParam.getOrderId());
        orderRefundRecord.setRefundType(ticketCancelParam.getRefundType());
        orderRefundRecord.setRefundReasonType(ticketCancelParam.getRefundReasonType());
        orderRefundRecord.setRefundReasonDesc(ticketCancelParam.getRefundReasonDesc());
        orderRefundRecord.setRefundStatus(FrontOrderStatusEnum.TUIPIAO_CHECK.getValue());
        orderRefundRecord.setBenefitOption(ticketCancelParam.getBenefitOption());
        return orderRefundRecord;
    }


}
