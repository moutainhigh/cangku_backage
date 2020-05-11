package cn.enn.wise.ssop.service.order.utils;

import cn.enn.wise.ssop.service.order.config.status.*;
import cn.enn.wise.ssop.service.order.model.OrderGoods;

public class StatusCheck {

    public static boolean checkOrderInfoStatus(OrderGoods orderGoods, OrderStatusEnum goodsOrderStatusEnum, PayStatusEnum goodsPayStatusEnum, TransactionStatusEnum goodsTransactionStatusEnum,
                                        RefundStatusEnum goodsRefundStatusEnum, SystemStatusEnum goodSystemStatusEnum){
        boolean check = false;
        if(goodsOrderStatusEnum!=null){
            if(orderGoods.getOrderStatus().equals(goodsOrderStatusEnum.getValue())){
                check = true;
            }else{
                return false;
            }
        }
        if(goodsPayStatusEnum!=null){
            if(orderGoods.getPayStatus().equals(goodsPayStatusEnum.getValue())){
                check = true;
            }else{
                return false;
            }
        }
        if(goodsTransactionStatusEnum!=null){
            if(orderGoods.getTransactionStatus().equals(goodsTransactionStatusEnum.getValue())){
                check = true;
            }else{
                return false;
            }
        }
        if(goodsRefundStatusEnum!=null){
            if(orderGoods.getRefundStatus().equals(goodsRefundStatusEnum.getValue())){
                check = true;
            }else{
                return false;
            }
        }
        if(goodSystemStatusEnum!=null){
            if(orderGoods.getSystemStatus().equals(goodSystemStatusEnum.getValue())){
                check = true;
            }else{
                return false;
            }
        }
        return check;
    }
}
