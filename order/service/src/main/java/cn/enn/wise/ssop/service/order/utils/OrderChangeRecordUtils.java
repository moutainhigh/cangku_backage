package cn.enn.wise.ssop.service.order.utils;

import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.service.order.config.enums.BackTicketBenefitEnum;
import cn.enn.wise.ssop.service.order.config.enums.BackTicketSettingEnum;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.model.Orders;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderChangeRecordUtils {

    public static final String YOUHUI_MONEY = "decrease_money";

    public static final String REFUND_MONEY = "refund_money";

    public static Map<String,BigDecimal> calCureatePrice(TicketCancelParam ticketCancelParam,BigDecimal payPrice, BigDecimal decreasePrice){
        BigDecimal refundPrice = BigDecimal.ZERO;
        Byte isCancleYouhui = ticketCancelParam.getBenefitOption();
        BigDecimal youhuiPrice = BigDecimal.ZERO;
        if(isCancleYouhui== BackTicketBenefitEnum.BACK_TICKET_BENEFIT_CANCEL.getValue()){
            youhuiPrice = decreasePrice;
            payPrice = payPrice.subtract(youhuiPrice);
        }
        if(ticketCancelParam.getRefundType()!=null){
            if(ticketCancelParam.getRefundType().byteValue()== BackTicketSettingEnum.BACK_TICKET_SETTINTG_ALL.getValue()){
                refundPrice = payPrice;
            }else if(ticketCancelParam.getRefundType().byteValue()==BackTicketSettingEnum.BACK_TICKET_SETTINTG_RATE.getValue()){
                if(ticketCancelParam.getRefundRate().compareTo(BigDecimal.ZERO)<0 || ticketCancelParam.getRefundRate().compareTo(BigDecimal.valueOf(100.00))>0){
                    throw new RuntimeException("比例输入有误");
                }
                refundPrice = ticketCancelParam.getRefundRate().divide(BigDecimal.valueOf(100)).multiply(payPrice);
            }else if(ticketCancelParam.getRefundType().byteValue()==BackTicketSettingEnum.BACK_TICKET_SETTINTG_NUMBER.getValue()){
                if(payPrice.compareTo(ticketCancelParam.getRefundMoney())<0){
                    throw new RuntimeException("退款金额输入有误");
                }
                refundPrice = payPrice.subtract(ticketCancelParam.getRefundMoney());
            }else{
                throw new RuntimeException("退款有误");
            }
        }
        Map<String,BigDecimal> map = new HashMap<>();
        map.put(YOUHUI_MONEY,youhuiPrice);
        map.put(REFUND_MONEY,refundPrice);
        return map;
    }

    public static OrderChangeRecord buildOrderChangeRecord(Orders source, Orders target, String changeReason){
        OrderChangeRecord orderChangeRecord = new OrderChangeRecord();
        orderChangeRecord.setOrderId(source.getOrderId());
        orderChangeRecord.setOrderStatus(source.getOrderStatus());
        orderChangeRecord.setTransactionStatus(source.getTransactionStatus());
        orderChangeRecord.setPayStatus(source.getPayStatus());
        orderChangeRecord.setSystemStatus(source.getSystemStatus());
        orderChangeRecord.setRefundStatus(source.getRefundStatus());
        orderChangeRecord.setNewOrderStatus(target.getOrderStatus());
        orderChangeRecord.setNewTransactionStatus(target.getTransactionStatus());
        orderChangeRecord.setNewPayStatus(target.getPayStatus());
        orderChangeRecord.setNewSystemStatus(target.getSystemStatus());
        orderChangeRecord.setNewRefundStatus(target.getRefundStatus());
        orderChangeRecord.setChangeReason(changeReason);
        orderChangeRecord.setChangeTime(new Date());
        return orderChangeRecord;
    }
}
