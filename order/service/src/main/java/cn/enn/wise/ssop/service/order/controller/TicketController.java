package cn.enn.wise.ssop.service.order.controller;

import cn.enn.wise.ssop.api.goods.dto.request.OrderStockUpdateParam;
import cn.enn.wise.ssop.api.goods.facade.GoodsExtendFacade;
import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketSearchParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderChangeRecordResponseDto;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityPriceUtilDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.CouponOrderDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityPriceUtilFacade;
import cn.enn.wise.ssop.api.promotions.facade.CouponFacade;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.TransactionStatusEnum;
import cn.enn.wise.ssop.service.order.model.OrderCancelRecord;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.ssop.service.order.service.OrderCancelService;
import cn.enn.wise.ssop.service.order.service.OrderChangeService;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.utils.OrderCancelRecordUtils;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.ssop.service.order.utils.StatusCheck;
import cn.enn.wise.ssop.service.order.utils.StatusUtils;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/ticket")
@Api(value = "门票订单API", tags = {"门票订单API"})
public class TicketController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponFacade couponFacade;

    @Autowired
    private ActivityPriceUtilFacade activityPriceUtilFacade;

    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private GoodsExtendFacade goodsExtendFacade;

    @ApiOperation(value = "测试更新库存",notes = "测试更新库存")
    @PostMapping("/testUpdateStock")
    public void test(@RequestBody List<OrderStockUpdateParam> orderStockUpdateParams){
        log.info("更新库存:{}",orderStockUpdateParams);
        R<Boolean> booleanR = goodsExtendFacade.updateGoodsExtendStock(orderStockUpdateParams);
        log.info("更新库存response:{}",booleanR);
    }
    /**
     * 测试couponOrder微服务
     * @param couponOrderParamList
     */
    @PostMapping("/testCouponOrder")
    @ApiOperation(value = "测试couponOrder）",notes = "测试couponOrder）")
    public R<ActivityPriceUtilDTO> couponOrderTest(@RequestBody List<CouponOrderParam> couponOrderParamList){
        /**
        List<CouponOrderParam> couponOrderParamList = new ArrayList<>();
        couponOrderParamList.add(couponOrderParams);
        */
        log.info("调用couponOrder参数couponOrderParams:{}",couponOrderParamList);
        R<List<CouponOrderDTO>> listR = couponFacade.couponOrder(couponOrderParamList);
        log.info("调用couponOrder返回值couponOrderDTOS:{}",listR);
        List<CouponOrderDTO> couponOrderDTOS = listR.getData();


        ActivityPriceUtilParam activityPriceUtilParam = new ActivityPriceUtilParam();

        List<ActivityPriceParam> activityPriceParams = new ArrayList<>();
        ActivityPriceParam activityPriceParam = new ActivityPriceParam();
        activityPriceParam.setActivityBaseId(0L);
        activityPriceParam.setActivityRuleId(0L);
        activityPriceParam.setSaleType((byte)0);
        activityPriceParam.setAlgorithms((byte)0);
        activityPriceParams.add(activityPriceParam);
        activityPriceUtilParam.setActivityPriceParams(activityPriceParams);



        List<CouponPriceParam> couponPriceParams = new ArrayList<>();
        CouponPriceParam couponPriceParam = new CouponPriceParam();
        couponPriceParam.setCouponType((byte)2);
        couponPriceParam.setCouponId(16L);
        couponPriceParams.add(couponPriceParam);
        activityPriceUtilParam.setCouponPriceParams(couponPriceParams);

        activityPriceUtilParam.setActivityType((byte)4);
        activityPriceUtilParam.setIsDistribute((byte)1);
        activityPriceUtilParam.setUserId(2l);

        List<GoodsPriceParam> goodsPriceParams = new ArrayList<>();
        GoodsPriceParam goodsPriceParam = new GoodsPriceParam();
        goodsPriceParam.setGoodsId(264l);
        goodsPriceParam.setGoodsNum(10);
        goodsPriceParam.setGoodsName("门票");
        goodsPriceParam.setGoodsPrice(20);
        goodsPriceParam.setDistributePrice(15);
        goodsPriceParams.add(goodsPriceParam);
        activityPriceUtilParam.setGoodsPriceParams(goodsPriceParams);

        R<ActivityPriceUtilDTO> activityPriceUtilDTOR = activityPriceUtilFacade.getCouponPrice(activityPriceUtilParam);
        ActivityPriceUtilDTO activityPriceUtilDTO = activityPriceUtilDTOR.getData();
        log.info("调用getCouponPrice参数activityPriceUtilParam"+activityPriceUtilParam);
        log.info("调用getCouponPrice返回值activityPriceUtilDTO"+activityPriceUtilDTO);

        return R.success(activityPriceUtilDTO);
    }
    /**
     * 搜索酒店订单列表
     * @param ticketSearchParam
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "门票订单搜索列表",notes="门票订单搜索列表")
    public R<Map> orderList(TicketSearchParam ticketSearchParam){
        Map orderList = orderGoodsService.getOrderList(ticketSearchParam);
        return R.success(orderList);
    }

    @ApiOperation(value = "一捡多次",notes = "一捡多次")
    @GetMapping("/oneCheck")
    public R<Boolean>  oneCheck(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED,null,null,null);
        if(b){
            //更新状态
            boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "一捡多次",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }


    @ApiOperation(value = "部分多捡多次",notes = "部分多捡多次")
    @GetMapping("/moreCheckPart")
    public R<Boolean>  moreCheckPart(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED, null, null, null);
        boolean b1 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_USED, PayStatusEnum.PAYED,null,null,null);
        boolean b2 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,null, PayStatusEnum.PAYED,TransactionStatusEnum.TICKET_GET,null,null);
        if(b || b1 || b2){
            boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "部分多捡多次",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "全部多捡多次",notes = "全部多捡多次")
    @GetMapping("/moreCheckAll")
    public R<Boolean>  moreCheckAll(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED, null, null, null);
        boolean b1 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_USED, PayStatusEnum.PAYED,null,null,null);
        boolean b2 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,null, PayStatusEnum.PAYED,TransactionStatusEnum.TICKET_GET,null,null);
        if(b || b1 || b2){
            boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "全部多捡多次",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "发起退票",notes = "发起退票")
    @GetMapping("/tuiPiao")
    public R<Boolean>  tuiPiao(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo,OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED,null,null,null);
        if(b){
            boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "发起退票",false);
            return R.success(u);
        }else{
            return R.error(OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getCode(),OrderExceptionAssert.ORDER_STATUS_CHECK_FAILURE.getMessage());
        }
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "退票",notes = "退票")
    public R<Boolean> backTicket(@RequestBody TicketCancelParam ticketCancelParam){
        Orders orderInfo = orderService.getOrderInfo(ticketCancelParam.getOrderId());
        if(orderInfo==null){
            log.error("数据有误,找不到订单号:{}，无法退票",ticketCancelParam.getOrderId());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(ticketCancelParam.getOrderIdList());
        if(CollectionUtils.isEmpty(orderGoodsList)){
            log.error("请确定要退的订单号：{}是否正确",ticketCancelParam.getOrderIdList());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        try{
            List<OrderCancelRecord> orderCancelRecordList = new ArrayList<>();
            BigDecimal refundPrice = BigDecimal.ZERO;
            BigDecimal decreasePrice = BigDecimal.ZERO;

            if (!CollectionUtils.isEmpty(orderGoodsList)) {
                for (OrderGoods orderGoods : orderGoodsList) {
                    Map<String, BigDecimal> refundMap = OrderChangeRecordUtils.calCureatePrice(ticketCancelParam, orderGoods.getShouldPayPrice(), orderGoods.getDecreasePrice());
                    orderGoods.setRefundPrice(refundMap.get(OrderChangeRecordUtils.REFUND_MONEY));
                    orderGoods.setDecreasePrice(refundMap.get(OrderChangeRecordUtils.YOUHUI_MONEY));
                    orderGoods.setTransactionStatus(TransactionStatusEnum.TICKET_BACK.getValue());

                    refundPrice = refundPrice.add(orderGoods.getRefundPrice());
                    decreasePrice = decreasePrice.add(orderGoods.getDecreasePrice());

                    OrderCancelRecord orderCancelRecord = OrderCancelRecordUtils.buildOrderCancelRecord(ticketCancelParam, orderGoods);
                    orderCancelRecordList.add(orderCancelRecord);
                }
            }
            Orders target = new Orders();
            target.setRefundPrice(refundPrice);
            target.setDecreasePrice(decreasePrice);
            OrderChangeRecord orderChangeRecord = OrderChangeRecordUtils.buildOrderChangeRecord(orderInfo, target, "退票");
            orderCancelService.saveOrderCancelRecord(orderChangeRecord,orderCancelRecordList,orderGoodsList);

        }catch (BaseException e){
            return R.error(e.getResponseEnum().getCode(),e.getResponseEnum().getMessage());
        }
        return R.success(Boolean.TRUE);
    }

    @PostMapping("/checkBack")
    @ApiOperation(value = "退票审核",notes = "退票审核，成功，checkStatus=1")
    public R<Boolean> checkBackTicketPass(@RequestBody TicketCancelParam ticketCancelParam){

        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(ticketCancelParam.getOrderIdList());
        if(CollectionUtils.isEmpty(orderGoodsList)){
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }

        Long parentOrderId = orderGoodsList.get(0).getParentOrderId();
        Orders orderInfo = orderService.getOrderInfo(parentOrderId);
        try{
            orderCancelService.confirmOrderCancelRecord(orderInfo,orderGoodsList,ticketCancelParam.getCheckStatus());
        }catch (BaseException e){
            return R.error(e.getResponseEnum().getCode(),e.getResponseEnum().getMessage());
        }

        return R.success(Boolean.TRUE);
    }


    /**
     * 全部当日体验完毕（体验日期3天后【失效日期】）
     * @param orderId
     */
    @GetMapping("/experienceFinishAfterThreeDays")
    @ApiOperation(value = "全部当日体验完毕（体验日期3天后【失效日期】）",notes = "全部当日体验完毕（体验日期3天后【失效日期】）")
    public R<Boolean> experienceFinishAfterThreeDays(Long orderId){
        boolean u = orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                null,null,null,
                null,null,null,
                null,null,
                "全部当日体验完毕（体验日期3天后【失效日期】）",false);
        return R.success(u);
    }

    /**
     * 获取订单状态变更记录/交易进度记录
     * @param orderId
     * @return
     */
    @GetMapping("/change/list")
    @ApiOperation(value = "订单(状态)变更记录",notes = "获取订单状态变更记录/获取订单交易进度记录")
    public R<List<OrderChangeRecordResponseDto>> getChangeRecordList(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<OrderChangeRecord> orderChangeRecordList = orderChangeService.getOrderChangeRecordList(orderId);
        List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList = new ArrayList<>();
        orderChangeRecordList.forEach(c->{
            log.info("订单状态值：status:"+""+c.getNewOrderStatus()+c.getNewPayStatus()+c.getNewTransactionStatus()+c.getNewSystemStatus()+c.getNewRefundStatus());
            OrderChangeRecordResponseDto orderChangeRecordResponseDto = new OrderChangeRecordResponseDto();
            Map<String,String> map = StatusUtils.getTicketShowStatus(c.getNewOrderStatus(),c.getNewPayStatus(),c.getNewTransactionStatus(),c.getNewSystemStatus(),c.getNewRefundStatus());
            orderChangeRecordResponseDto.setPcStatusName(map.get(StatusUtils.PC_STATUS));
            orderChangeRecordResponseDto.setAppStatusName(map.get(StatusUtils.APP_STATUS));
            orderChangeRecordResponseDto.setYouStatusName(map.get(StatusUtils.WECHAT_STATUS));
            orderChangeRecordResponseDtoList.add(orderChangeRecordResponseDto);
        });
        return R.success(orderChangeRecordResponseDtoList);
    }


}
