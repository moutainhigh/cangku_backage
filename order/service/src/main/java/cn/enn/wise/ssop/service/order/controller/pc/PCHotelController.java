package cn.enn.wise.ssop.service.order.controller.pc;

import cn.enn.wise.ssop.api.order.dto.request.HotelOrderParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.response.*;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.mapper.OrderHotelMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.*;
import cn.enn.wise.ssop.service.order.service.impl.HotelServiceImpl;
import cn.enn.wise.ssop.service.order.utils.StatusUtils;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lishuiquan
 * @date 2020-04-02
 */
@Slf4j
@RestController
@RequestMapping("/pc/hotel")
@Api(value = "PC酒店订单API", tags = {"PC酒店订单API"})
public class PCHotelController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderRelatePeopleService orderRelatePeopleService;

    @Autowired
    private HotelServiceImpl hotelService;

    @Autowired
    private OrderHotelMapper orderHotelMapper;

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
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "订单详细信息( 订单信息，商品信息，联系人信息，订单状态变更记录信息）",notes="( 订单信息，商品信息，联系人信息，订单状态变更记录信息）")
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
            String extraInformation = c.getExtraInformation();
            Map<String, Object> map = new HashMap<>();
            if(extraInformation!=null&&extraInformation!=""){
                map = (Map<String, Object>) JSONObject.parse(extraInformation);
            }
            if(c.getOrderId().equals(orderInfo.getOrderId())){
                String vipLevel = null;
                if(map.containsKey("vipLevel")){
                    vipLevel = (String) map.get("vipLevel");
                }
                orderResponseDto.setVipLevel(vipLevel);
                OrderOperatorsListDTO orderOperatorsListDTO = new OrderOperatorsListDTO();
                if(map.containsKey("operator")){
                    JSONObject operator = JSONObject.parseObject(map.get("operator").toString());
                    orderOperatorsListDTO = JSONArray.toJavaObject(operator, OrderOperatorsListDTO.class);
                }
                BeanUtils.copyProperties(c, orderResponseDto);
                orderResponseDto.setOrderOperatorsListDTO(orderOperatorsListDTO);
            }else {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("order_id",orderId);
                OrderHotel orderHotel = orderHotelMapper.selectOne(queryWrapper);
                OrderGoodsResponseDto orderGoodsResponseDto = new OrderGoodsResponseDto();
               /* Integer roomNum = (Integer) map.get("roomNum");
                String startTime = (String) map.get("startTime");
                String outTime = (String) map.get("outTime");
                Integer daysNum = (Integer) map.get("daysNum");*/
                Integer roomNum = Integer.valueOf(orderHotel.getHomeNo());
                String startTime = orderHotel.getComeDate();
                String outTime = orderHotel.getLeaveDate();
                //TODO 临时注释
                Integer daysNum = 1;
                        OrderOperatorsListDTO orderOperatorsListDTO = new OrderOperatorsListDTO();
                if(map.containsKey("operator")){
                    JSONObject operator = JSONObject.parseObject(map.get("operator").toString());
                    orderOperatorsListDTO = JSONArray.toJavaObject(operator, OrderOperatorsListDTO.class);
                }
                BeanUtils.copyProperties(c, orderGoodsResponseDto);
                orderGoodsResponseDto.setRoomNum(roomNum);
                orderGoodsResponseDto.setStartTime(startTime);
                orderGoodsResponseDto.setOutTime(outTime);
                orderGoodsResponseDto.setDaysNum(daysNum);
                orderGoodsResponseDto.setOrderOperatorsListDTO(orderOperatorsListDTO);
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
            Map<String,String> map = StatusUtils.getHotelShowStatus(c.getOrderStatus(),c.getPayStatus(),c.getTransactionStatus(),c.getSystemStatus(),c.getRefundStatus());
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
        List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList = new ArrayList<>();
        orderChangeRecordList.forEach(c->{
            log.info("订单状态值：status:"+""+c.getNewOrderStatus()+c.getNewPayStatus()+c.getNewTransactionStatus()+c.getNewSystemStatus()+c.getNewRefundStatus());
            OrderChangeRecordResponseDto orderChangeRecordResponseDto = new OrderChangeRecordResponseDto();
            Map<String,String> map = StatusUtils.getHotelShowStatus(c.getNewOrderStatus(),c.getNewPayStatus(),c.getNewTransactionStatus(),c.getNewSystemStatus(),c.getNewRefundStatus());
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


    @PostMapping("/save")
    @ApiOperation(value = "酒店统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveHotelOrder(@RequestBody @Valid HotelOrderParam hotelOrderParam){

        Long orderId= hotelService.saveOrder(hotelOrderParam);

        return R.success(orderId);
    }
}

