package cn.enn.wise.ssop.service.order.controller;

import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.*;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.ssop.service.order.service.impl.HotelServiceImpl;
import cn.enn.wise.ssop.service.order.utils.StatusCheck;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hotel")
@Api(value = "酒店订单API", tags = {"酒店订单API"})
public class HotelController {


    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private HotelServiceImpl hotelServiceImpl;

    /**
     * 订单未支付主动取消订单
     * @param orderId
     * @return
     */
    @ApiOperation(value = "订单未支付主动取消订单",notes = "订单未支付主动取消订单")
    @GetMapping("/cancel")
    public R<Boolean> cancel(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, null, PayStatusEnum.UN_PAY, null, null, null);
        if(b){
            boolean b1 = hotelServiceImpl.updateOrderStatus(orderGoodsInfo.getOrderId(), OrderStatusEnum.HOTEL_CANCEL,PayStatusEnum.UN_PAY,
                    null,null,null,
                    null,null,null,null,
                    null,"订单未支付主动取消订单",true);
            return R.success(b1);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     * 游客支付订单，等待运营方房源二次确认
     * @param orderId
     * @return
     */
    @ApiOperation(value = "游客支付订单，等待运营方房源二次确认",notes = "游客支付订单，等待运营方房源二次确认")
    @GetMapping("/confirm")
    public R<Boolean> sendConfirm(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.HOTEL_WAIT_PAY, PayStatusEnum.UN_PAY,null,null,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_WAIT_USE,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_WAIT_CONFIRM,null,null,
                    null,null,null,
                    null,null,
                    "游客支付订单，等待运营方房源二次确认",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     * 房源二次确认成功
     * @param orderId
     * @return
     */
    @ApiOperation(value = "房源二次确认成功",notes = "房源二次确认成功")
    @GetMapping("/confirmSuccess")
    public R<Boolean> sendConfirmSuccess(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED, TransactionStatusEnum.HOTEL_WAIT_CONFIRM,null,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_WAIT_USE,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_WAIT_ZHU,null,null,
                    null,null,null,
                    null,null,
                    "房源二次确认成功",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     * 房源二次确认失败，运营方发起退款申请
     * @param orderId
     * @return
     */
    @ApiOperation(value = "房源二次确认失败，运营方发起退款申请",notes = "房源二次确认失败，运营方发起退款申请")
    @GetMapping("/sendConfirmFailure")
    public R<Boolean> sendConfirmFailure(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED, TransactionStatusEnum.HOTEL_WAIT_CONFIRM,null,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_WAIT_USE,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_CONFIRM_FAILURE,RefundStatusEnum.HOTEL_WAIT_CHECK,SystemStatusEnum.HOTEL_CONFIRM,
                    null,null,null,
                    null,null,
                    "房源二次确认失败，运营方发起退款申请",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     * 财务审核通过退款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务审核通过退款",notes = "财务审核通过退款")
    @GetMapping("/checkPassRefund")
    public R<Boolean> checkPassRefund(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,TransactionStatusEnum.HOTEL_CONFIRM_FAILURE, RefundStatusEnum.HOTEL_CONFIRM,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_CANCEL,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_CANCEL,RefundStatusEnum.HOTEL_CONFIRM,SystemStatusEnum.HOTEL_CONFIRM,
                    null,null,null,
                    null,null,
                    "财务审核通过退款",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     * 财务审核拒绝退款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务审核拒绝退款",notes = "财务审核拒绝退款")
    @GetMapping("/checkRejectRefund")
    public R<Boolean> checkRejectRefund(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,TransactionStatusEnum.HOTEL_CONFIRM_FAILURE, RefundStatusEnum.HOTEL_CONFIRM,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "财务审核拒绝退款",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     *  运营方退订审核成功，等待财务确认拨款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "运营方退订审核成功，等待财务确认拨款",notes = "运营方退订审核成功，等待财务确认拨款")
    @GetMapping("/cancelAndCheck")
    public R<Boolean> cancelAndCheck(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,TransactionStatusEnum.HOTEL_WAIT_ZHU,null,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_WAIT_USE,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_ZHU,RefundStatusEnum.HOTEL_WAIT_CHECK,SystemStatusEnum.HOTEL_CONFIRM,
                    null,null,null,
                    null,null,
                    "运营方退订审核成功，等待财务确认拨款",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     *  财务确认拨款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务确认拨款",notes = "财务确认拨款")
    @GetMapping("/confirmWithdraw")
    public R<Boolean> confirmWithdraw(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED,TransactionStatusEnum.HOTEL_WAIT_ZHU,RefundStatusEnum.HOTEL_WAIT_CHECK, SystemStatusEnum.HOTEL_WAIT_CHECK);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_CANCEL,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_CANCEL,RefundStatusEnum.HOTEL_CONFIRM,SystemStatusEnum.HOTEL_CONFIRM,
                    null,null,null,
                    null,null,
                    "财务确认拨款",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

  /*  *//**
     *  财务拒绝拨款
     * @param orderId
     * @return
     *//*
    @GetMapping("/rejectWithdraw")
    public R<Boolean> rejectWithdraw(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED,null,null,null);
        if(b){
            boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "财务拒绝拨款",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }*/

    /**
     *  运营方退订审核失败
     * @param orderId
     * @return
     */
    @ApiOperation(value = "运营方退订审核失败",notes = "运营方退订审核失败")
    @GetMapping("/backAndCheckFailure")
    public R<Boolean> backAndCheckFailure(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,
                TransactionStatusEnum.HOTEL_WAIT_ZHU,null,SystemStatusEnum.HOTEL_WAIT_CHECK);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_WAIT_ZHU,null,SystemStatusEnum.HOTEL_REFUSE,
                    null,null,null,
                    null,null,
                    "运营方退订审核失败",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    /**
     *  游客前往酒店办理入住，进行入住核验
     * @param orderId
     * @return
     */
    @ApiOperation(value = "游客前往酒店办理入住，进行入住核验",notes = "游客前往酒店办理入住，进行入住核验")
    @GetMapping("/ruZhuCheck")
    public R<Boolean> ruZhuCheck(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.HOTEL_WAIT_USE, PayStatusEnum.PAYED,
                TransactionStatusEnum.HOTEL_WAIT_ZHU,null,null);
        if(b){
            boolean u = hotelServiceImpl.updateOrderStatus(orderId, OrderStatusEnum.HOTEL_USED,PayStatusEnum.PAYED,
                    TransactionStatusEnum.HOTEL_ZHU,null,null,
                    null,null,null,
                    null,null,
                    "游客前往酒店办理入住，进行入住核验",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

}
