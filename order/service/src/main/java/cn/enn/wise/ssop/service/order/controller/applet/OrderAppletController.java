package cn.enn.wise.ssop.service.order.controller.applet;


import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsResponseDto;
import cn.enn.wise.ssop.api.order.dto.response.TradeLogDTO;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.TransactionStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderChargeOffMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderCancelService;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.utils.OrderCancelRecordUtils;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.ssop.service.order.utils.QrGenerator;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert.USER_ID_NOT_NULL;
@Slf4j
@Api(value = "App订单中心API", tags = {"App订单中心API"})
@RestController
@RequestMapping("/applet")
public class OrderAppletController {

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private OrderChargeOffMapper orderChargeOffMapper;

    /**
     * 获取订单列表
     * @param orderStatus
     * @return
     */
    @ApiOperation(value = "订单列表",notes="订单列表")
    @GetMapping("/order/list/All")
    public R<List<OrderGoodsResponseDto>> getOrderList(Integer orderStatus){
        OrderGoods orderGoods = new OrderGoods();
        if(orderStatus!=null && orderStatus!=-1) {
            orderGoods.setOrderStatus(orderStatus);
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(orderGoods);
        List<OrderGoodsResponseDto> orderGoodsResponseDtoList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderGoodsList)){
            for(OrderGoods orders:orderGoodsList){
                OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
                BeanUtils.copyProperties(orders,orderGoodsResponseDto);
                Map maps = JSONObject.parseObject(orders.getExtraInformation(), Map.class);
                orderGoodsResponseDto.setExtraInfo(maps);
                orderGoodsResponseDto.setExtraInformation(null);
                orderGoodsResponseDtoList.add(orderGoodsResponseDto);
            }
        }
        return R.success(orderGoodsResponseDtoList);
    }


    @ApiOperation(value = "根据用户id获取用户订单",notes = "根据用户id获取用户订单")
    @GetMapping("/user/order")
    public R<QueryData<TradeLogDTO>> getUserOrder(@RequestParam("userId") Long userId, @RequestParam("pageSize") Long pageSize, @RequestParam("pageNo") Long pageNo){
        if(userId==null){
            USER_ID_NOT_NULL.assertFail();
        }
        return new R(orderService.getUserOrder(userId,pageSize, pageNo));
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "退票",notes = "退票")
    public R<Boolean> backTicket(@RequestBody TicketCancelParam ticketCancelParam){
        Orders orderInfo = orderService.getOrderInfo(ticketCancelParam.getOrderId());
        if(orderInfo==null){
            log.error("数据有误,找不到订单号:{}，无法退票",ticketCancelParam.getOrderId());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(ticketCancelParam.getOrderId());
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(orderIds);
        if(org.springframework.util.CollectionUtils.isEmpty(orderGoodsList)){
            log.error("请确定要退的订单号：{}是否正确",ticketCancelParam.getOrderIdList());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        try{
            List<OrderCancelRecord> orderCancelRecordList = new ArrayList<>();
            BigDecimal refundPrice = BigDecimal.ZERO;
            BigDecimal decreasePrice = BigDecimal.ZERO;
            if (!org.springframework.util.CollectionUtils.isEmpty(orderGoodsList)) {
                for (OrderGoods orderGoods : orderGoodsList) {
                    ticketCancelParam.setBenefitOption((byte)2);
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


    /**
     * 获取订单二维码
     *
     * @param orderId
     * @return
     */
    @GetMapping("/matrix")
    public R<String> getMatrix(Long orderId){

        QueryWrapper<OrderChargeOff> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        List<OrderChargeOff> list = orderChargeOffMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            OrderChargeOff orderChargeOff = list.get(0);
            if(orderChargeOff!=null && !StringUtils.isEmpty(orderChargeOff.getQrImgBase64())){
                return R.success(orderChargeOff.getQrImgBase64());
            }
        }

        List<OrderGoods> orderGoods = orderGoodsService.getOrderGoodsDetails(orderId);
        if(orderGoods==null || orderGoods.size()==0){
            return R.error("不存在订单记录");
        }
        OrderGoods currentOrderGoods = orderGoods.get(0);
        int goodsType = currentOrderGoods.getGoodsType().intValue();
        String content = String.valueOf(goodsType)+":"+String.valueOf(orderId);
        String base64Str = QrGenerator.getBase64Str(content);

        OrderChargeOff orderChargeOff = new OrderChargeOff();
        orderChargeOff.setOrderId(orderId);
        orderChargeOff.setQrImgBase64(base64Str);
        orderChargeOff.setStatus(-1);

        orderChargeOffMapper.insert(orderChargeOff);
        return R.success(base64Str);

    }




}
