
package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.api.order.dto.request.*;
import cn.enn.wise.ssop.api.order.dto.response.*;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.enums.FrontOrderStatusEnum;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.service.*;
import cn.enn.wise.ssop.service.order.service.impl.HotelServiceImpl;
import cn.enn.wise.ssop.service.order.utils.ExportUtil;
import cn.enn.wise.uncs.base.constant.BusinessEnumInit;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.alibaba.csp.sentinel.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;



/**
 * @author lishuiquan
 * @date 2020-04-02
 */

@Slf4j
@RestController
@RequestMapping("/order")
@Api(value = "订单公共API", tags = {"订单公共API"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderSaleService orderSaleService;

    @Autowired
    private OrderRelatePeopleService orderRelatePeopleService;

    /*@Autowired
    private ProcessEngineer processEngineer;*/

    @Autowired
    private OrderEvaluationService orderEvaluationService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PayService payService;

    @Autowired
    private HotelServiceImpl hotelService;


    /**
     * 订单列表
     *
     * @param orderSearchParam
     * @return
     */

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", notes = "订单列表")
    public R<QueryData<OrderGoodsListDTO>> postOrderList(@RequestBody OrderSearchParam orderSearchParam) {
        QueryData<OrderGoodsListDTO> queryData = new QueryData<>();

        OrderListDTO orderGoodsList = orderGoodsService.getOrderGoodsList(orderSearchParam);

        queryData.setRecords(orderGoodsList.getOrderGoodsListDTOS());
        queryData.setTotalCount(orderGoodsList.getPeopleNumBer());
        queryData.setPageNo(orderSearchParam.getPageNo());
        queryData.setPageSize(orderSearchParam.getPageSize());

        return R.success(queryData);
    }

    /**
     * 订单列表
     *
     * @param orderSearchParam
     * @return
     */

    @GetMapping(value = "/list")
    @ApiOperation(value = "订单列表", notes = "订单列表")
    public R<QueryData<OrderGoodsListDTO>> getOrderList(OrderSearchParam orderSearchParam) {
        QueryData<OrderGoodsListDTO> queryData = new QueryData<>();
        OrderListDTO orderGoodsList = orderGoodsService.getOrderGoodsList(orderSearchParam);
        queryData.setRecords(orderGoodsList.getOrderGoodsListDTOS());
        queryData.setTotalCount(orderGoodsList.getPeopleNumBer());
        queryData.setPageNo(orderSearchParam.getPageNo());
        queryData.setPageSize(orderSearchParam.getPageSize());
        return R.success(queryData);
    }


    @GetMapping("/condition")
    @ApiOperation(value = "搜索条件", notes = "搜索条件")
    public R<Map<String, List<SelectData>>> getQueryCondition() {
        return R.success(BusinessEnumInit.enumMap);
    }

/*
    @PostMapping("/addCart")
    @ApiOperation(value = "加入购物车",notes = "加入购物车")
    public void addCart(@RequestBody List<CartParam> cartParamList){
        redisTemplate.opsForValue().set("user_id"+1,cartParamList);

        redisTemplate.opsForList().leftPush("sss1",cartParamList);
    }*/

/*

*/
/**
     * 快捷支付
     * @param defaultOrderSaveParam
     * @return R<Boolean>
     *//*


    @PostMapping("/quickOrder")
    @ApiOperation(value = "快捷支付",notes = "快捷支付")
    public R quickOrder(@RequestBody @Valid DefaultOrderSaveParam defaultOrderSaveParam){
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        log.info("会员id:{}",memberId);
        if(memberId!=null){
            defaultOrderSaveParam.setMemberId(Long.valueOf(memberId));
        }
        OrderWrapper process = processEngineer.process(defaultOrderSaveParam);
        Long orderId = orderService.saveOrder(process);
        try {
            String openId = HttpContextUtils.GetHttpHeader("open_id");
            if(StringUtil.isEmpty(openId)){
                R.error("openId不存在");
            }
            Map data = payService.unifiedOrder(String.valueOf(orderId), "测试数据", String.valueOf(process.getOrders().getShouldPayPrice()), openId);
            log.info("统一微信订单id:{}",data);
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setMemberId(Long.parseLong(memberId));
            orders.setPrepayId(String.valueOf(data.get("prepay_id")));
            orderService.updateOrder(orders);
            R r = payService.pay(String.valueOf(data.get("prepay_id")));
            Map<String,String> map = (Map)r.getData();
            map.put("orderId",String.valueOf(orderId));
            map.put("memberId",String.valueOf(memberId));
            r.setData(map);
            return r;
        }catch (BaseException e){
            return R.error(e.getResponseEnum().getCode(),e.getResponseEnum().getMessage());
        }catch (Exception e){
            log.error("快捷支付异常：{}",e);
            return R.error(OrderExceptionAssert.ORDER_SAVE_EXCEPTION.getCode(),OrderExceptionAssert.ORDER_SAVE_EXCEPTION.getMessage());
        }
    }


*/

@PostMapping("/pay")
@ApiOperation(value = "快捷支付",notes = "快捷支付")
public R quickOrder(@RequestBody @Valid DefaultOrderSaveParam defaultOrderSaveParam) {
    String memberId = HttpContextUtils.GetHttpHeader("member_id");
    log.info("会员id:{}", memberId);
    if (memberId != null) {
        defaultOrderSaveParam.setMemberId(Long.valueOf(memberId));
    }
    Long orderId = defaultOrderSaveParam.getOrderId();
    try {
        String openId = HttpContextUtils.GetHttpHeader("open_id");
        if (StringUtil.isEmpty(openId)) {
            R.error("openId不存在");
        }

        Orders orderInfo = hotelService.getOrderInfo(orderId);
        if (orderInfo == null) {
            R.error("订单号不存在");

        }

        Map data = payService.unifiedOrder(String.valueOf(orderId), "测试数据", String.valueOf(orderInfo.getShouldPayPrice()), openId);
        log.info("统一微信订单id:{}", data);
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setMemberId(Long.parseLong(memberId));
        orders.setPrepayId(String.valueOf(data.get("prepay_id")));
        orderService.updateOrder(orders);
        R r = payService.pay(String.valueOf(data.get("prepay_id")));
        Map<String, String> map = (Map) r.getData();
        map.put("orderId", String.valueOf(orderId));
        map.put("memberId", String.valueOf(memberId));
        r.setData(map);
        return r;
    } catch (BaseException e) {
        return R.error(e.getResponseEnum().getCode(), e.getResponseEnum().getMessage());
    } catch (Exception e) {
        log.error("快捷支付异常：{}", e);
        return R.error(OrderExceptionAssert.ORDER_SAVE_EXCEPTION.getCode(), OrderExceptionAssert.ORDER_SAVE_EXCEPTION.getMessage());
    }

 }
}


/**
     * 保存订单
     * @param defaultOrderSaveParam
     * @return R<Boolean>
     *//*


    @PostMapping("/save")
    @ApiOperation(value = "统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveOrder(@RequestBody @Valid DefaultOrderSaveParam defaultOrderSaveParam){
        OrderWrapper process = processEngineer.process(defaultOrderSaveParam);
        Long orderId = orderService.saveOrder(process);
        return R.success(orderId);
    }


*/
/**
     * 保存门票订单
     * @param ticketOrderParam
     * @return R<Boolean>
     *//*


    @PostMapping("/ticket/save")
    @ApiOperation(value = "统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveTicketOrder(@RequestBody @Valid TicketOrderParam ticketOrderParam){

        TicketOrderService ticketService=new TicketOrderService();


        Long orderId= ticketService.saveOrder(ticketOrderParam);


//        OrderWrapper process = processEngineer.process(defaultOrderSaveParam);
//        Long orderId = orderService.saveOrder(process);
        return R.success(orderId);
    }
*/


/** 保存门票订单
     * @param hotelOrderParam
     * @return R<Boolean>
     *//*

    @PostMapping("/hotel/save")
    @ApiOperation(value = "统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveHotelOrder(@RequestBody @Valid HotelOrderParam hotelOrderParam){

        Long orderId= hotelService.saveOrder(hotelOrderParam);

        return R.success(orderId);
    }

    */
/**
     * 订单详细信息
     * @param orderId
     * @return
     *//*

    @GetMapping("/detail")
    @ApiOperation(value = "订单详细信息( 扫码）",notes="订单详细信息( 扫码）")
    public R<OrderDetailDTO> orderDetail(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        Orders orderInfo = orderService.getOrderInfo(orderId);
        if(orderInfo==null){
            return R.error(OrderExceptionAssert.ORDER_NOT_FOUND.getCode(),OrderExceptionAssert.ORDER_NOT_FOUND.getMessage());
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        BeanUtils.copyProperties(orderInfo,orderResponseDto);
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsListByOrderId(orderId);
        List<OrderGoodsResponseDto> orderGoodsResponseDtoList = new ArrayList<>();
        orderGoodsList.forEach(c->{
            if(c.getOrderId().equals(orderInfo.getOrderId())){
                BeanUtils.copyProperties(c, orderResponseDto);
            }else {
                OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
                BeanUtils.copyProperties(c, orderGoodsResponseDto);
                orderGoodsResponseDtoList.add(orderGoodsResponseDto);
            }
        });

        List<OrderRelatePeopleResponseDto> orderRelatePeopleResponseDtoList = new ArrayList<>();
        List<OrderRelatePeople> orderRelatePeopleList = orderRelatePeopleService.getOrderRelatePeopleListByOrderId(orderId);

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


    */
/**
     * 订单详细信息
     * @param orderId
     * @return
     *//*

    @GetMapping("/detailInfo")
    @ApiOperation(value = "订单详细信息（列表的一条）",notes="订单详细信息（列表的一条）")
    public R<OrderDetailResponseDTO> orderDetailInfo(Long orderId){
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);

        OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
        BeanUtils.copyProperties(orderGoodsInfo, orderGoodsResponseDto);


        OrderRelatePeople orderRelatePeopleInfo = orderRelatePeopleService.getOrderRelatePeopleInfo(orderId);
        OrderRelatePeopleResponseDto orderRelatePeopleResponseDto = new OrderRelatePeopleResponseDto();
        BeanUtils.copyProperties(orderRelatePeopleInfo, orderRelatePeopleResponseDto);
        OrderDetailResponseDTO orderDetailResponseDTO = new OrderDetailResponseDTO();
        orderDetailResponseDTO.setOrderGoodsResponseDto(orderGoodsResponseDto);
        orderDetailResponseDTO.setOrderRelatePeopleResponseDto(orderRelatePeopleResponseDto);
        return R.success(orderDetailResponseDTO);
    }

    */
/**
     * 获取订单商品信息
     * @param orderId
     * @return
     *//*

    @GetMapping("/goods")
    @ApiOperation(value = "订单商品列表（一个或多个商品）",notes="订单商品列表（一个或多个商品）")
    public R<List<OrderGoodsResponseDto>> orderGoodsList(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<Long> orderIds  = new ArrayList<>();
        orderIds.add(orderId);
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(orderIds);
        List<OrderGoodsResponseDto> orderGoodsResponseDtoList = new ArrayList<OrderGoodsResponseDto>();
        orderGoodsList.forEach(c->{
            OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
            BeanUtils.copyProperties(c,orderGoodsResponseDto);
            orderGoodsResponseDtoList.add(orderGoodsResponseDto);
        });
        return R.success(orderGoodsResponseDtoList);
    }

    */
/**
     * 获取订单联系人信息
     * @param orderId
     * @return
     *//*

    @GetMapping("/people")
    @ApiOperation(value = "订单联系人信息",notes="订单联系人信息")
    public R<List<OrderRelatePeopleResponseDto>> orderRelatePeopleList(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<OrderRelatePeople> orderRelatePeopleList = orderRelatePeopleService.getOrderRelatePeopleListByParentOrderId(orderId);
        List<OrderRelatePeopleResponseDto> orderRelatePeopleResponseDtoList = new LinkedList<>();
        orderRelatePeopleList.forEach(c->{
            OrderRelatePeopleResponseDto orderRelatePeopleResponseDto = new OrderRelatePeopleResponseDto();
            BeanUtils.copyProperties(c,orderRelatePeopleResponseDto);
            orderRelatePeopleResponseDtoList.add(orderRelatePeopleResponseDto);
        });
        return R.success(orderRelatePeopleResponseDtoList);
    }

    */
/**
     * 获取订单营销策略
     * @param orderId
     * @return
     *//*

    @GetMapping("/sale")
    @ApiOperation(value = "订单营销策略信息",notes="订单营销策略信息")
    public R<List<OrderSaleResponseDto>> orderSaleList(Long orderId){
        if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        List<OrderSale> orderSaleList = orderSaleService.getOrderSaleList(orderId);
        List<OrderSaleResponseDto> orderSaleResponseDtoList = new ArrayList<>();
        orderSaleList.forEach(c->{
            OrderSaleResponseDto orderSaleResponseDto = new OrderSaleResponseDto();
            BeanUtils.copyProperties(c,orderSaleResponseDto);
            orderSaleResponseDtoList.add(orderSaleResponseDto);
        });
        return R.success(orderSaleResponseDtoList);
    }

    @PostMapping("/confirm")
    @ApiOperation(value = "确认订单",notes = "确认订单")
    public R<Boolean> confirmOrder(String orderNo,Long orderId){
        // todo 校验优惠券是否可用 orderCheck.checkSale();
        Orders orders = orderService.getOrderInfo(orderId);
        Boolean result = orderService.confirmOrder(orders);
        return R.success(result);
    }

    */
/**
     * 参数中的orderId 必须为orderGoods订单商品中的orderId
     * 取消订单 orderGoods 表中的parentOrderId为order表中的order_id；
     * orderGoods表中的orderId和parentOrderId相同时，orderGoods表中的记录为父订单商品信息
     * orderGoods表中的orderId和parentOrderId不同时，orderGoods表中的记录为子订单商品信息
     * @param orderCancelParam
     * @return
     *//*

    @PostMapping("/cancel")
    @ApiOperation(value = "取消订单",notes = "更新订单状态为已取消")
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




    */
/**
     * 删除订单
     * @param id
     * @return
     *//*

    @GetMapping("/delete")
    @ApiOperation(value = "删除订单",notes = "删除订单/更新订单状态为已删除")
    public R<Boolean> delete(Long id){
        boolean res = orderService.deleteOrder(id);
        return R.success(res);
    }

    */
/**
     * 评价
     *//*

    @ApiOperation(value = "评价订单",notes = "评价订单")
    @PostMapping("/evaluate")
    public R<Boolean> evaluate(@RequestBody OrderEvaluationParam orderEvaluationParam){
        OrderEvaluate orderEvaluate = new OrderEvaluate();
        BeanUtils.copyProperties(orderEvaluationParam, orderEvaluate);
        boolean evaluateRes = orderEvaluationService.saveOrderEvaluation(orderEvaluate);
        return R.success(evaluateRes);
    }
    */
/**
     * 查询简单订单信息列表
     * @param orderDetailListParam
     *//*

    @PostMapping("/getOrderDetailList")
    @ApiOperation(value = "查询简单订单信息列表",notes = "查询简单订单信息列表")
    public R<List<OrderDetailListDTO>> getOrderDetailList(@RequestBody OrderDetailListParam orderDetailListParam){

        return R.success(orderGoodsService.getOrderDetailList(orderDetailListParam));
    }

}

*/
