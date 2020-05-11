package cn.enn.wise.ssop.service.order.controller.app;

import cn.enn.wise.ssop.api.order.dto.request.OrderDetailListParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.response.*;
import cn.enn.wise.ssop.api.order.dto.response.app.*;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.TransactionStatusEnum;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.*;
import cn.enn.wise.ssop.service.order.utils.OrderCancelRecordUtils;
import cn.enn.wise.ssop.service.order.utils.OrderChangeRecordUtils;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@Api(value = "APP门票订单公共API", tags = {"APP门票订单公共API"})
@RestController
@RequestMapping("/app/ticket")
public class AppTicketController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderGoodsService orderGoodsService;


    @Autowired
    private OrderRelatePeopleService orderRelatePeopleService;


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
    public R<OrderDetailBean> orderDetail(Long orderId){
        OrderDetailBean orderDetailBean = new OrderDetailBean();
        //查询出所有的订单 商品
        List<OrderGoods> orderGoodsModel = orderGoodsService.getOrderGoodsModel(orderId);
        OrderGoods orderGoods1 = null;
        for (OrderGoods orderGood : orderGoodsModel) {
            if (orderGood.getOrderId().equals(orderGood.getParentOrderId())) {
                orderGoods1=orderGood;//有一个主订单
            }
        }
        String extraInformation = orderGoods1.getExtraInformation();//没有的字段都在orderGoods里面
        JSONObject jsonObject = JSON.parseObject(extraInformation);
        if(orderGoods1!=null){
            orderDetailBean.setAmount(orderGoods1.getAmount());//商品数量
            orderDetailBean.setGoodsName(orderGoods1.getGoodsName());//商品名称
            orderDetailBean.setSiglePrice(orderGoods1.getGoodsPrice());//商品单价
            orderDetailBean.setExperienceTime(orderGoods1.getUseTime());//体验时间set
            orderDetailBean.setOrderCode(orderGoods1.getOrderNo());//订单编号

        }
        //查询联系人
        OrderRelatePeople orderRelatePeople= orderGoodsService.getOrderGoodsUser(orderId);
        if(orderRelatePeople!=null){
            orderDetailBean.setName(orderRelatePeople.getCustomerName());//联系人姓名
            orderDetailBean.setPhone(orderRelatePeople.getPhone());
        }
        //查询订单信息
        Orders orderInfo=orderGoodsService.getParentOrders(orderId);
        if(orderInfo!=null){
            orderDetailBean.setGoodsPrice(Double.valueOf(orderGoods1.getShouldPayPrice().toString()));//订单金额
            orderDetailBean.setShouldPay(Double.valueOf(orderGoods1.getActualPayPrice().toString()));
            orderDetailBean.setCouponPrice(Double.valueOf(orderGoods1.getDecreasePrice().toString()));
            orderDetailBean.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH/ss/mm").format(orderInfo.getPayTime()));
        }
        //分销商信息
        String isDistributor = jsonObject.getString("isDistributor");
        if(isDistributor.equals("2")){//说明是分销商订单
            orderDetailBean.setIsDistributeOrder(Integer.valueOf(isDistributor));
            Object distributorName = jsonObject.get("distributorName");
            Object distributorPhone = jsonObject.get("distributorPhone");
            orderDetailBean.setDistributorName(distributorName.toString());
            orderDetailBean.setDistributorPhone(distributorPhone.toString());
        }
        //核销信息
        String writeoffName = jsonObject.getString("writeoffName");
        String writeoffTime = jsonObject.getString("writeoffTime");
        orderDetailBean.setCheckName(writeoffName);
        orderDetailBean.setCheckInTime(writeoffTime);
        //判断拼团ID
        String isAssemble = jsonObject.getString("isAssemble");
        if(isAssemble.equals("3")){ //拼团订单 是否拼团
            orderDetailBean.setOrderType(Integer.valueOf(isAssemble));
            String assembleModel = jsonObject.getString("assembleModel");
            JSONObject jsonObject1 = JSON.parseObject(assembleModel);
            List<GroupOrderInfoBean> objects = new ArrayList<>();
            GroupOrderInfoBean groupOrderInfoBean = new GroupOrderInfoBean();
            groupOrderInfoBean.setId(Integer.valueOf(jsonObject1.getString("id").toString()));//ID
            groupOrderInfoBean.setTimespan(jsonObject1.getString("timespan"));
            groupOrderInfoBean.setGoodsNum(jsonObject1.getString("goodsNum"));
            groupOrderInfoBean.setGroupOrderCode(jsonObject1.getString("groupOrderCode"));
            groupOrderInfoBean.setStatus(Integer.valueOf(jsonObject1.getString("status")));
            groupOrderInfoBean.setState(Integer.valueOf(jsonObject1.getString("state")));
            objects.add(groupOrderInfoBean);
            orderDetailBean.setGroupOrderVoList(objects);
            //得到一个拼团数组
        }
        //判断票务
        String isTicketing = jsonObject.getString("isTicketing");//判断票型
        if(isTicketing.equals("1")){
            orderDetailBean.setIsTicketing(1);//1有 2 没有
        }
        //判断退单 根据订单号查询是否有没有退单的
        List<OrderCancelRecord> list= orderGoodsService.getCanRefundOrder(orderId);
        if(list.size()>0){
            orderDetailBean.setIsRefund(2);
            OrderCancelRecord orderRefundRecord = list.get(0);
            int platform = orderRefundRecord.getPlatform();
            if(platform==2){//app端
                orderDetailBean.setPlatform(2);
            }

        }
        //1 不退单 2  退单
       /* if(orderId==null){
            return R.error(OrderExceptionAssert.ORDER_ID_REQUIRED.getCode(),OrderExceptionAssert.ORDER_ID_REQUIRED.getMessage());
        }
        //获取订单信息
        Orders orderInfo = orderService.getOrderInfo(orderId);
        if(orderInfo==null){
            return R.error(OrderExceptionAssert.ORDER_NOT_FOUND.getCode(),OrderExceptionAssert.ORDER_NOT_FOUND.getMessage());
        }
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd HH/mm/ss" );
        orderDetailBean.setCreateTime(sdf.format(orderInfo.getPayTime()));//下单时间
        OrderResponseDto orderResponseDto = new OrderResponseDto();//返回的信息
        BeanUtils.copyProperties(orderInfo,orderResponseDto);
        //获取商品
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
        orderDetailDTO.setOrderChangeRecordResponseDtoList(orderChangeRecordResponseDtoList);*/
        return R.success(orderDetailBean);
    }

    //高智杰+++++++++++++++++++++++++++++++++++++
    @GetMapping("/ticketingdetail")
    @ApiOperation(value = "票务信息",notes="票务信息")
    public R<List<TicketInfoBean>> tickeTingdetail(Long orderId){
        //查询出所有的订单 商品 因为信息在infor里面
        List<OrderGoods> orderGoodsModel = orderGoodsService.getOrderGoodsModel(orderId);
        String extraInformation = orderGoodsModel.get(0).getExtraInformation();
        JSONObject jsonObject = JSON.parseObject(extraInformation);
        String ticketInfoList = jsonObject.getString("ticketInfoList");
        List<TicketInfoBean> result = JSON.parseArray(ticketInfoList, TicketInfoBean.class);//时间段数据
        return R.success(result);
    }

    //高智杰+++++++++++++++++++++++++++++++++++++
    @GetMapping("/retreatorderdetail")
    @ApiOperation(value = "退票详情",notes="退票详情")
    public R<List<RefundDetailBean>> retreatOrderdetail(Long orderId){
        ArrayList<RefundDetailBean> objects = new ArrayList<>();
        //查询出所有的订单 商品 因为信息在infor里面
        //OrderGoods orderGoods= orderGoodsService.getOrderGoodsModel(orderId);
        //更具单号查取详情
        List<OrderCancelRecord> list= orderGoodsService.getCanRefundOrder(orderId);//退单的list循环给出信息是吧
        //因为涉及到多次兑票所有得那个判断下是哪次退票的
        if(list.size()>0){

        }
        return R.success(objects);
    }


    //高智杰+++++++++++++++++++++++++++++++++++++
    @GetMapping("/canceldetail")
    @ApiOperation(value = "需要退单的详情",notes="需要退单的详情")
    public R<RefundOrderDetailBean> getCancelDetail(Long orderId){
        RefundOrderDetailBean refundOrderDetailBean = new RefundOrderDetailBean();//返回的退单详情
        //在orderGoods查询出主订单的信息  判断是哪种类型的  订票 ,住宿，体验项目
        //查询订单信息
        Orders orderInfo=orderGoodsService.getParentOrders(orderId);
        //判断商品类型
        List<OrderGoods> orderGoods= orderGoodsService.getOrderGoodsModel(orderId);
        if(orderGoods.size()>0){
            Byte goodsType = orderGoods.get(0).getGoodsType();
         // if(goodsType==1 || goodsType==2){ //说明是门票
              OrderGoods orderGoods1 = null;
              for (OrderGoods orderGood : orderGoods) {
                  if (orderGood.getOrderId().equals(orderGood.getParentOrderId())) {
                      orderGoods1=orderGood;//有一个主订单
                  }
              }
              if(goodsType!=1 && goodsType!=2){
                  refundOrderDetailBean.setPackageName(orderGoods1.getGoodsName());
              }
                refundOrderDetailBean.setOrderSts(orderGoods1.getOrderStatus());//订单状态
                refundOrderDetailBean.setOrderCode(orderGoods1.getOrderNo());//订单编号
                refundOrderDetailBean.setPrice(orderGoods1.getGoodsPrice().toString());//单价
                refundOrderDetailBean.setAmount(orderGoods1.getAmount());//人数
                refundOrderDetailBean.setActualPay(orderGoods1.getActualPayPrice().toString());//实付金额
                if(orderInfo.getDecreasePrice()!=null && !orderInfo.getDecreasePrice().toString().equals("0")){
                    refundOrderDetailBean.setCouponTotalPrice(orderInfo.getDecreasePrice().toString());//优惠金额
                    refundOrderDetailBean.setIsJoinCoupon(2);
                }else{
                    refundOrderDetailBean.setCouponTotalPrice("0");//优惠金额
                    refundOrderDetailBean.setIsJoinCoupon(1);
                }
                refundOrderDetailBean.setOrderTotalPrice(orderInfo.getActualPayPrice().toString());//实付出总金额
                refundOrderDetailBean.setDiscriminateBBDSts(1);
                //未体验商品明细，循环判断 orderStatus=1为待使用
                List<RefundOrderDetailBean.NoPlayVoListBean> objects = new ArrayList<>();
                for (OrderGoods orderGood : orderGoods) {
                    if(orderGood.getOrderStatus()== OrderStatusEnum.HOTEL_WAIT_USE.getValue()){//说明是待使用的 套餐也试用
                        RefundOrderDetailBean.NoPlayVoListBean noPlayVoListBean = new RefundOrderDetailBean.NoPlayVoListBean();
                        noPlayVoListBean.setGoodsId(Integer.valueOf(orderGood.getGoodsId().toString()));//商品ID
                        noPlayVoListBean.setId(Integer.valueOf(orderGood.getId().toString()));//主键ID
                        noPlayVoListBean.setGoodsName(orderGood.getGoodsName());//名称
                        if(orderGood.getTransactionStatus()==TransactionStatusEnum.TICKET_GET.getValue()){
                            noPlayVoListBean.setStatus("已取票");
                        }
                        noPlayVoListBean.setAmount(orderGood.getAmount());//数量
                        noPlayVoListBean.setPrice(orderGood.getActualPayPrice().toString());//单价
                        noPlayVoListBean.setCouponPrice(orderGood.getDecreasePrice().toString());//优惠分摊金额
                        String extraInformation = orderGood.getExtraInformation();
                        JSONObject jsonObject = JSON.parseObject(extraInformation);
                        String ticketSerialBbd = jsonObject.getString("ticketSerialBbd");
                        noPlayVoListBean.setTicketSerialBbd(ticketSerialBbd);//百邦达票号
                        String ticketStateBbd = jsonObject.getString("ticketStateBbd");
                        noPlayVoListBean.setTicketStateBbd(Integer.valueOf(ticketStateBbd));
                        objects.add(noPlayVoListBean);
                    }
                }
                refundOrderDetailBean.setNoPlayVoList(objects);
                //已退商品明细 查询取消票号的表
                List<OrderCancelRecord> list= orderGoodsService.getCanRefundOrder(orderId);//退单的list循环给出信息是吧
                List<RefundOrderDetailBean.RefundDetailVoListBean> objects1 = new ArrayList<>();
                List<RefundOrderDetailBean.RefundDetailVoListBean.RefundVoListBean> info=  new ArrayList<>();
                if(list.size()>0){ //说明有退票的
                    for (OrderCancelRecord orderCancelRecord : list) {
                        //去退款表里面查询退款金额跟时间
                        OrderRefundRecord result=orderGoodsService.getOrderGoodsRefund(orderCancelRecord.getOrderId());
                        RefundOrderDetailBean.RefundDetailVoListBean refundDetailVoListBean = new RefundOrderDetailBean.RefundDetailVoListBean();
                        refundDetailVoListBean.setTotalPrice(result.getRefundPrice().toString());//待确定
                        refundDetailVoListBean.setRefundTime(new SimpleDateFormat("yyyy/MM/dd HH:ss:mm").format(orderCancelRecord.getCancelTime()));
                        //
                        RefundOrderDetailBean.RefundDetailVoListBean.RefundVoListBean refundVoListBean = new RefundOrderDetailBean.RefundDetailVoListBean.RefundVoListBean();//待确定
                        refundVoListBean.setPrice(result.getRefundPrice().toString());//待确定
                        refundVoListBean.setAmount(1);//待确定
                        info.add(refundVoListBean);//待确定
                        refundDetailVoListBean.setRefundVoList(info);
                        objects1.add(refundDetailVoListBean);
                    }
                }
                refundOrderDetailBean.setRefundDetailVoList(objects1);
            }
     //  }

        return R.success(refundOrderDetailBean);
    }

    //高智杰+++++++++++++++++++++++++++++++++++++
    @PostMapping("/cancel/save")
    @ApiOperation(value = "退票记录保存",notes="退票记录保存")
    public R<Boolean> saveCancelSave(@RequestBody RefundPostBean refundPostBean){// RefundPostBean refundPostBean
        //RefundPostBean 参数封印到 ticketCancelParam 就好

        TicketCancelParam ticketCancelParam = new TicketCancelParam();
        ticketCancelParam.setOrderId(Long.valueOf(refundPostBean.getOrderId()));
        List<RefundPostBean.RefundApplyDetailedParamListBean> refundApplyDetailedParamList = refundPostBean.getRefundApplyDetailedParamList();
        ArrayList<Long> objects = new ArrayList<>();//获取一个子订单编号的集合
        for (RefundPostBean.RefundApplyDetailedParamListBean refundApplyDetailedParamListBean : refundApplyDetailedParamList) {
            objects.add(Long.valueOf(refundApplyDetailedParamListBean.getOrderCode()));
        }
        ticketCancelParam.setOrderIdList(objects);
        ticketCancelParam.setBenefitOption(new Byte(String.valueOf(refundPostBean.getBuyerMsgType())));//退款原因类型
        ticketCancelParam.setRefundType(new Byte("1"));
        ticketCancelParam.setRefundReasonType(new Byte("1"));
        ticketCancelParam.setRefundReasonDesc(refundPostBean.getBuyerMsg());//退款原因
        //因为具体流程不清楚 目前存放到 order_cancel_record 表
        Orders orderInfo = orderService.getOrderInfo(ticketCancelParam.getOrderId());
        if(orderInfo==null){
            log.error("数据有误,找不到订单号:{}，无法退票",ticketCancelParam.getOrderId());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(ticketCancelParam.getOrderIdList());// 通过子订单ID查询商品
        if(CollectionUtils.isEmpty(orderGoodsList)){
            log.error("请确定要退的订单号：{}是否正确",ticketCancelParam.getOrderIdList());
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        try{
            List<OrderCancelRecord> orderCancelRecordList = new ArrayList<>();//取消票号
            //生成指定长度的随机字符串
            String str= RandomStringUtils.randomAlphanumeric(10);
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
                    orderCancelRecord.setJudgeRecord(str);
                    orderCancelRecordList.add(orderCancelRecord);
                }
            }
            Orders target = new Orders();//创建新的订单
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
     * 订单详细信息
     * @param orderId
     * @return
     */
    @GetMapping("/detailInfo")
    @ApiOperation(value = "订单详细信息( 订单商品，联系人信息）",notes="订单详细信息( 订单商品，联系人信息）")
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

    /**
     * 查询简单订单信息列表
     * @param orderDetailListParam
     */
    @PostMapping("/getOrderDetailList")
    @ApiOperation(value = "查询简单订单信息列表",notes = "查询简单订单信息列表")
    public R<List<OrderDetailListDTO>> getOrderDetailList(@RequestBody OrderDetailListParam orderDetailListParam){

        return R.success(orderGoodsService.getOrderDetailList(orderDetailListParam));
    }
    /**
     * 搜索酒店订单列表
     * @param orderSearchParam
     * @return
     */
    @PostMapping("/search/list")
    @ApiOperation(value = "订单展示筛选列表",notes="订单展示筛选列表")
    public R<OrderAppSearchDTO> getOrderGoods(@RequestBody OrderSearchParam orderSearchParam){
        return R.success(orderGoodsService.getOrderSearchList(orderSearchParam));
    }


}
