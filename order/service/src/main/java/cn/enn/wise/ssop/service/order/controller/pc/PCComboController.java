package cn.enn.wise.ssop.service.order.controller.pc;



import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.PcOrderGoodsParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.*;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.TransactionStatusEnum;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.*;
import cn.enn.wise.ssop.service.order.utils.OrderCancelRecordUtils;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.ssop.service.order.utils.StatusUtils;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author lishuiquan
 * @date 2020-04-02
 */
@Slf4j
@RestController
@RequestMapping("/pc/combo")
@Api(value = "PC套餐订单API", tags = {"PC套餐订单API"})
public class PCComboController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRelatePeopleService orderRelatePeopleService;
    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private OrderSaleService orderSaleService;
    @Autowired
    private OrderCancelService orderCancelService;

    /**
     * 订单列表
     * @param orderSearchParam
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "订单列表",notes="订单列表")
    public R<QueryData<OrderGoodsListDTO>> getOrderList(OrderSearchParam orderSearchParam){
        QueryData<OrderGoodsListDTO> queryData = new QueryData<>();
        orderSearchParam.setIsMaster(1);
        OrderListDTO orderGoodsList = orderGoodsService.getOrderGoodsList(orderSearchParam);
        queryData.setRecords(orderGoodsList.getOrderGoodsListDTOS());
        queryData.setTotalCount(orderGoodsList.getPeopleNumBer());
        queryData.setPageNo(orderSearchParam.getPageNo());
        queryData.setPageSize(orderSearchParam.getPageSize());
        return R.success(queryData);
    }


    /**
     * 订单详细信息
     * @param orderId  订单id
     * @return  OrderDetailDTO 订单详情 （包含多个信息集合）
     */
    @GetMapping("/detail")
    @ApiOperation(value = "订单详细信息( 订单信息，商品信息，联系人信息，订单状态变更记录信息）",notes="( 订单信息，商品信息，联系人信息，订单状态变更记录信息）")
    public R<OrderDetailDTO> orderDetail(Long orderId){


        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage()+orderId);
        }
        Orders orderInfo = orderService.getOrderInfo(orderId);
        if(orderInfo==null){
            return R.error(OrderExceptionAssert.ORDER_NOT_FOUND.getCode(),OrderExceptionAssert.ORDER_NOT_FOUND.getMessage());
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        BeanUtils.copyProperties(orderInfo,orderResponseDto);
        Map<String,Object> extraInfoOrder = JSONObject.parseObject(orderInfo.getExtraInformation(),Map.class);
        orderResponseDto.setVipLevel(String.valueOf(extraInfoOrder.get("vipLevel")));
        orderResponseDto.setScenicName(String.valueOf(extraInfoOrder.get("scenicName")));

        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsListByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderGoodsList)){
            R.error(OrderExceptionAssert.GOOD_NOT_FOUND.getCode(),OrderExceptionAssert.GOOD_NOT_FOUND.getMessage()+orderId);
        }
        List<OrderGoodsResponseDto> orderGoodsResponseDtoList = new ArrayList<>();
        orderGoodsList.forEach(c->{
            if(c.getOrderId().equals(orderInfo.getOrderId())){
                BeanUtils.copyProperties(c, orderResponseDto);
            }else {
                OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
                orderGoodsResponseDto.setThreeOrderNo(orderInfo.getThreeOrderNo());
                BeanUtils.copyProperties(c, orderGoodsResponseDto);
                String extraInformation = c.getExtraInformation();
                if(!StringUtils.isEmpty(extraInformation)){
                    Map<String,Object> extraInfo = JSONObject.parseObject(c.getExtraInformation(),Map.class);
                    orderGoodsResponseDto.setExtraInfo(extraInfo);
                    orderGoodsResponseDto.setExtraInformation(null);
                    orderGoodsResponseDto.setWechatStatus((String)extraInfo.get("wechatStatus"));
                    orderGoodsResponseDto.setAppStatus((String)extraInfo.get("appStatus"));
                    orderGoodsResponseDto.setPcStatus((String)extraInfo.get("pcStatus"));
                    orderGoodsResponseDto.setEffectiveTime((String)extraInfo.get("effectiveTime"));
                    orderGoodsResponseDto.setDaysNum((Integer)extraInfo.get("daysNum"));
                    orderGoodsResponseDto.setStartTime((String)extraInfo.get("startTime"));
                    orderGoodsResponseDto.setOutTime((String)extraInfo.get("outTime"));
                    orderGoodsResponseDto.setVipLevel((String)extraInfo.get("vipLevel"));
                    orderGoodsResponseDto.setPrepayId((String)extraInfo.get("prepayId"));
                    orderGoodsResponseDto.setCustomerName((String)extraInfo.get("customerName"));
                    orderGoodsResponseDto.setPhone((String)extraInfo.get("phone"));

                    orderGoodsResponseDto.setCertificateNo((String)extraInfo.get("certificateNo"));
                    orderGoodsResponseDto.setCertificateType(Byte.valueOf(String.valueOf(extraInfo.get("certificateType"))));

                }


                orderGoodsResponseDtoList.add(orderGoodsResponseDto);
            }
        });

        List<OrderRelatePeopleResponseDto> orderRelatePeopleResponseDtoList = new ArrayList<>();
        List<OrderRelatePeople> orderRelatePeopleList = orderRelatePeopleService.getOrderRelatePeopleListByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderRelatePeopleList)){
            R.error(OrderExceptionAssert.ORDER_OF_USER_NOT_ONLINE.getCode(),OrderExceptionAssert.ORDER_OF_USER_NOT_ONLINE.getMessage()+orderId);
        }
        orderRelatePeopleList.forEach(a->{
            if(a.getOrderId().equals(orderInfo.getOrderId())){
                BeanUtils.copyProperties(a, orderResponseDto);
            }else {
                OrderRelatePeopleResponseDto orderRelatePeopleResponseDto = new OrderRelatePeopleResponseDto();
                BeanUtils.copyProperties(a, orderRelatePeopleResponseDto);
                orderRelatePeopleResponseDtoList.add(orderRelatePeopleResponseDto);
            }
        });

        List<OrderChangeRecord> orderChangeRecordList = orderChangeService.getOrderChangeRecordList(orderId);
        if(CollectionUtils.isEmpty(orderChangeRecordList)){
            R.error(OrderExceptionAssert.ORDER_STATUS_UPDATE_EXCEPTION.getCode(),OrderExceptionAssert.ORDER_STATUS_UPDATE_EXCEPTION.getMessage()+orderId);
        }
        List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList = new ArrayList<>();
        orderChangeRecordList.forEach(c->{
            OrderChangeRecordResponseDto orderChangeRecordResponseDto = new OrderChangeRecordResponseDto();
            BeanUtils.copyProperties(c, orderChangeRecordResponseDto);
            Map<String,String> map = StatusUtils.getTicketShowStatus(c.getNewOrderStatus(),c.getNewPayStatus(),c.getNewTransactionStatus(),c.getNewSystemStatus(),c.getNewRefundStatus());
            orderChangeRecordResponseDto.setPcStatusName(map.get(StatusUtils.PC_STATUS));
            orderChangeRecordResponseDto.setAppStatusName(map.get(StatusUtils.APP_STATUS));
            orderChangeRecordResponseDto.setYouStatusName(map.get(StatusUtils.WECHAT_STATUS));
            orderChangeRecordResponseDtoList.add(orderChangeRecordResponseDto);
        });
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderResponseDto(orderResponseDto);
        orderDetailDTO.setOrderGoodsResponseDtoList(orderGoodsResponseDtoList);
        orderDetailDTO.setOrderRelatePeopleResponseDtoList(orderRelatePeopleResponseDtoList);
        orderDetailDTO.setOrderChangeRecordResponseDtoList(orderChangeRecordResponseDtoList);
        return R.success(orderDetailDTO);
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
        if(CollectionUtils.isEmpty(orderChangeRecordList)){
            R.error(OrderExceptionAssert.ORDER_STATUS_UPDATE_EXCEPTION.getCode(),OrderExceptionAssert.ORDER_STATUS_UPDATE_EXCEPTION.getMessage()+orderId);
        }
        List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList = new ArrayList<>();
        orderChangeRecordList.forEach(c->{
            log.info("订单状态值：status:"+""+c.getNewOrderStatus()+c.getNewPayStatus()+c.getNewTransactionStatus()+c.getNewSystemStatus()+c.getNewRefundStatus());
            OrderChangeRecordResponseDto orderChangeRecordResponseDto = new OrderChangeRecordResponseDto();
            Map<String,String> map = StatusUtils.getTicketShowStatus(c.getNewOrderStatus(),c.getNewPayStatus(),c.getNewTransactionStatus(),c.getNewSystemStatus(),c.getNewRefundStatus());
            orderChangeRecordResponseDto.setPcStatusName(map.get(StatusUtils.PC_STATUS));
            orderChangeRecordResponseDto.setAppStatusName(map.get(StatusUtils.APP_STATUS));
            orderChangeRecordResponseDto.setYouStatusName(map.get(StatusUtils.WECHAT_STATUS));
            orderChangeRecordResponseDto.setChangeReason(c.getChangeReason());
            orderChangeRecordResponseDto.setChangeTime(c.getChangeTime());
            orderChangeRecordResponseDto.setOrderId(c.getOrderId());
            orderChangeRecordResponseDtoList.add(orderChangeRecordResponseDto);
        });
        return R.success(orderChangeRecordResponseDtoList);
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

    @PostMapping("/cancel/list")
    @ApiOperation(value = "退票审核列表",notes = "退票审核列表")
    public R<List<OrderCancelListDTO>> ticketList(@RequestBody TicketCancelParam ticketCancelParam){

        return R.success(orderCancelService.orderCancelList(ticketCancelParam.getOrderId()));
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
     * 获取订单营销策略
     * @param orderId
     * @return
     */
    @GetMapping("/sale/info")
    @ApiOperation(value = "订单营销策略信息",notes="订单营销策略信息")
    public R<List<OrderSaleResponseDto>> orderSaleList(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<OrderSale> orderSaleList = orderSaleService.getOrderSaleList(orderId);
        List<OrderSaleResponseDto> orderSaleResponseDtoList = new ArrayList<>();
        orderSaleList.forEach(c->{
            OrderSaleResponseDto orderSaleResponseDto = new OrderSaleResponseDto();
            String extraInformation = c.getExtraInformation();
            Integer salePrice = 0;
            if(extraInformation!=null&&extraInformation!=""){
                Map<String, Object> map = (Map<String, Object>) JSONObject.parse(extraInformation);
                if(map.containsKey("salePrice")){
                    salePrice = (Integer) map.get("salePrice");
                }
            }
            orderSaleResponseDto.setSalePrice(salePrice);
            BeanUtils.copyProperties(c,orderSaleResponseDto);
            orderSaleResponseDtoList.add(orderSaleResponseDto);
        });
        return R.success(orderSaleResponseDtoList);
    }

}