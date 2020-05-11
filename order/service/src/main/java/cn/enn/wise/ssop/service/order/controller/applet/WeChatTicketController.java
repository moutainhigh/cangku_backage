package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.api.order.dto.request.OrderCancelParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderEvaluationParam;
import cn.enn.wise.ssop.api.order.dto.request.WeChatTicketParam;
import cn.enn.wise.ssop.api.order.dto.response.*;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.enums.FrontOrderStatusEnum;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.*;
import cn.enn.wise.ssop.service.order.utils.StatusUtils;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(value = "小程序订单中心API", tags = {"小程序订单中心API"})
@RestController
@RequestMapping("/xiao/ticket")
public class WeChatTicketController {

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRelatePeopleService orderRelatePeopleService;

    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderEvaluationService orderEvaluationService;


    /**
     * @method 去支付
     *
     */
    public void goPay(){

    }

    /**
     * 获取订单商品列表
     * @param weChatTicketParam  订单状态+分页
     * @return orderGoodsResponseDto 订单商品信息
     */
    @ApiOperation(value = "订单列表",notes="订单列表")
    @GetMapping("/list")
    public R<QueryData<OrderGoodsResponseDto>> getOrderList(WeChatTicketParam weChatTicketParam){

        QueryData<OrderGoodsResponseDto> queryData = new QueryData<>();

        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        if(StringUtils.isNotEmpty(memberId)){
            weChatTicketParam.setMemberId(Integer.valueOf(memberId));
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsListWithPage(weChatTicketParam);
        Integer count = orderGoodsService.getOrderGoodsCountWithPage(weChatTicketParam);
        List<OrderGoodsResponseDto> orderGoodsListDTOS = new ArrayList<>();


        if(!CollectionUtils.isEmpty(orderGoodsList)){
            for(OrderGoods orderGood:orderGoodsList){
                OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();

                if(orderGood.getOrderId().equals(orderGood.getParentOrderId())){
                    Orders orderInfo = orderService.getOrderInfo(orderGood.getOrderId());
                    if(orderInfo!=null){
                        orderGoodsResponseDto.setPrepayId(orderInfo.getPrepayId());
                    }
                }
                BeanUtils.copyProperties(orderGood,orderGoodsResponseDto);
                String extraInformation = orderGood.getExtraInformation();
                if(!StringUtils.isEmpty(extraInformation)){
                    Map<String,Object> extraInfo = JSONObject.parseObject(orderGood.getExtraInformation(),Map.class);
                    orderGoodsResponseDto.setExtraInfo(extraInfo);
                    orderGoodsResponseDto.setExtraInformation(null);

                }
                Map<String,String> map = StatusUtils.getTicketShowStatus(orderGood.getOrderStatus(),orderGood.getPayStatus(),orderGood.getTransactionStatus(),orderGood.getSystemStatus(),orderGood.getRefundStatus());
                orderGoodsResponseDto.setPcStatus(map.get(StatusUtils.PC_STATUS));
                orderGoodsResponseDto.setPcStatusValue(map.get(StatusUtils.PC_STATUS_VALUE));
                orderGoodsResponseDto.setAppStatus(map.get(StatusUtils.APP_STATUS));
                orderGoodsResponseDto.setAppStatusValue(map.get(StatusUtils.APP_STATUS_VALUE));
                orderGoodsResponseDto.setWechatStatus(map.get(StatusUtils.WECHAT_STATUS));
                orderGoodsResponseDto.setWechatStatusValue(map.get(StatusUtils.WECHAT_STATUS_VALUE));
                orderGoodsListDTOS.add(orderGoodsResponseDto);
            }
        }
        queryData.setRecords(orderGoodsListDTOS);
        queryData.setTotalCount(count);
        queryData.setPageNo(weChatTicketParam.getPageNo());
        queryData.setPageSize(weChatTicketParam.getPageSize());
        return R.success(queryData);
    }




    /**
     * 订单详细信息
     * @param orderId  订单id
     * @return  OrderDetailDTO 订单详情 （包含多个信息集合）
     */
    @GetMapping("/detail")
    @ApiOperation(value = "订单详细信息",notes="订单详细信息")
    public R<OrderDetailDTO> orderDetail(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsDetails(orderId);
        List<OrderGoodsResponseDto> orderGoodsResponseDtoList = new ArrayList<>();
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderGoodsList.forEach(c->{
            if(c.getOrderId().equals(c.getParentOrderId())){
                Orders orderInfo = orderService.getOrderInfo(c.getOrderId());
                if(orderInfo!=null)
                BeanUtils.copyProperties(orderInfo, orderResponseDto);
                BeanUtils.copyProperties(c, orderResponseDto);
            }
            OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
            BeanUtils.copyProperties(c, orderGoodsResponseDto);
            Map<String,Object> extraInfo = JSONObject.parseObject(c.getExtraInformation(),Map.class);
            orderGoodsResponseDto.setExtraInfo(extraInfo);
            orderGoodsResponseDto.setExtraInformation(null);
            Map<String,String> map = StatusUtils.getTicketShowStatus(c.getOrderStatus(),c.getPayStatus(),c.getTransactionStatus(),c.getSystemStatus(),c.getRefundStatus());
            orderGoodsResponseDto.setPcStatus(map.get(StatusUtils.PC_STATUS));
            orderGoodsResponseDto.setPcStatusValue(map.get(StatusUtils.PC_STATUS_VALUE));
            orderGoodsResponseDto.setAppStatus(map.get(StatusUtils.APP_STATUS));
            orderGoodsResponseDto.setAppStatusValue(map.get(StatusUtils.APP_STATUS_VALUE));
            orderGoodsResponseDto.setWechatStatus(map.get(StatusUtils.WECHAT_STATUS));
            orderGoodsResponseDto.setWechatStatusValue(map.get(StatusUtils.WECHAT_STATUS_VALUE));
            orderGoodsResponseDtoList.add(orderGoodsResponseDto);

        });

        List<OrderRelatePeopleResponseDto> orderRelatePeopleResponseDtoList = new ArrayList<>();
        List<OrderRelatePeople> orderRelatePeopleList = orderRelatePeopleService.getOrderRelatePeopleListByOrderId(orderId);

        orderRelatePeopleList.forEach(a->{
            if(a.getOrderId().equals(a.getParentOrderId())){
                BeanUtils.copyProperties(a, orderResponseDto);
            }else {
                OrderRelatePeopleResponseDto orderRelatePeopleResponseDto = new OrderRelatePeopleResponseDto();
                BeanUtils.copyProperties(a, orderRelatePeopleResponseDto);
                orderRelatePeopleResponseDtoList.add(orderRelatePeopleResponseDto);
            }
        });

        List<OrderChangeRecord> orderChangeRecordList = orderChangeService.getOrderChangeRecordList(orderId);
        List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList = new ArrayList<>();
        orderChangeRecordList.forEach(c->{
            OrderChangeRecordResponseDto orderChangeRecordResponseDto = new OrderChangeRecordResponseDto();
            BeanUtils.copyProperties(c, orderChangeRecordResponseDto);
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
     * 取消订单pc
     * @param orderCancelParam 订单取消参数
     * @return boolean
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "取消订单pc",notes = "更新订单状态为已取消pc")
    public R<Boolean> cancelOrder(@RequestBody OrderCancelParam orderCancelParam){
        OrderGoods cancelOrderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderCancelParam.getOrderId());
        if(cancelOrderGoodsInfo==null){
            return R.error(OrderExceptionAssert.ORDER_NOT_FOUND.getCode(),OrderExceptionAssert.ORDER_NOT_FOUND.getMessage());
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(OrderGoods.builder().parentOrderId(cancelOrderGoodsInfo.getParentOrderId()).build());

        Orders orders = null;
        boolean updateOrders = false;
        for(OrderGoods orderGoods:orderGoodsList){
            if(!orderGoods.getOrderId().equals(cancelOrderGoodsInfo.getParentOrderId()) && orderGoods.getOrderStatus().equals(FrontOrderStatusEnum.TUIPIAO_INITIAL.getValue())){
                updateOrders = true;
            }
        }
        if(updateOrders){
            orders = orderService.getOrderInfo(cancelOrderGoodsInfo.getParentOrderId());
        }
        boolean cancelResult = orderService.cancelOrder(cancelOrderGoodsInfo,orderCancelParam,orders);
        return R.success(cancelResult);
    }

    /**
     * 删除订单
     * @param id  主键id
     * @return boolean
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除订单",notes = "删除订单/更新订单状态为已删除")
    public R<Boolean> delete(Long id){
        boolean res = orderService.deleteOrder(id);
        return R.success(res);
    }

    /**
     * 保存评价
     *@param orderEvaluationParam  传入评论参数
     *@return boolean
     */
    @ApiOperation(value = "保存订单评价",notes = "保存订单评价")
    @PostMapping("/evaluate")
    public R<Boolean> evaluate(@RequestBody OrderEvaluationParam orderEvaluationParam){
        OrderEvaluate orderEvaluate = new OrderEvaluate();
        BeanUtils.copyProperties(orderEvaluationParam, orderEvaluate);
        boolean evaluateRes = orderEvaluationService.saveOrderEvaluation(orderEvaluate);
        return R.success(evaluateRes);
    }




    /**
     * 订单退款详情
     * @param memberId 会员id
     * @param orderId  订单id
     * @return OrderRefundRecordResponseDto 退款详情
     */
    @ApiOperation(value = "订单退款详情",notes="订单退款详情")
    @GetMapping("/refund/detail")
    public R<OrderRefundRecordResponseDto> getOrderRefundRecord(Long memberId, Long orderId){
        OrderRefundRecordResponseDto refundRecordResponseDto = null;
        OrderRefundRecord orderRefundRecord = new OrderRefundRecord();
        orderRefundRecord.setMemberId(memberId);
        orderRefundRecord.setOrderId(orderId);

        orderRefundRecord = orderRefundService.getOrderRefund(orderRefundRecord);
        if(orderRefundRecord!=null){
            refundRecordResponseDto = new OrderRefundRecordResponseDto();
            BeanUtils.copyProperties(orderRefundRecord,refundRecordResponseDto);
        }
        return R.success(refundRecordResponseDto);
    }


    /*
     *查看订单评价
     *@param orderId  订单id
     *@return OrderEvaluateDTO  订单评论
     */
    @ApiOperation(value = "查询订单评价",notes = "查询订单评价")
    @GetMapping("/see/evaluate")
    public R<OrderEvaluateDTO> seeEvaluate(long orderId){
        OrderEvaluateDTO orderEvaluateDTO=new OrderEvaluateDTO();
        OrderEvaluate orderEvaluate = orderEvaluationService.selectOne(orderId);
        if(orderEvaluate!=null)
        BeanUtils.copyProperties(orderEvaluate, orderEvaluateDTO);
        return R.success(orderEvaluateDTO);
    }




}
