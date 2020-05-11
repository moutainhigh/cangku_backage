package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.OrderDetailListParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.WeChatTicketParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderOperatorsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.app.OrderAppSearchDTO;
import cn.enn.wise.ssop.api.order.dto.response.app.OrderAppSearchListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderDetailListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderListDTO;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.config.enums.ChannelEnum;
import cn.enn.wise.ssop.service.order.config.enums.FrontPayStatusEnum;
import cn.enn.wise.ssop.service.order.config.enums.FrontTransactionStatusEnum;
import cn.enn.wise.ssop.service.order.config.enums.OrderSourceEnum;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderGoodsMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {

    @Resource
    OrderGoodsMapper orderGoodsMapper;

    /**
     * 查看订单商品信息
     * @param orderIds
     * @return
     */
    @Override
    public List<OrderGoods> getOrderGoodsList(List<Long> orderIds) {
        return orderGoodsMapper.getOrderGoodsList(orderIds);
    }

    @Override
    public List<OrderGoods> getOrderGoodsList(OrderGoods orderGoods) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(orderGoods);
        return orderGoodsMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrderGoods> getOrderGoodsListWithPage(WeChatTicketParam weChatTicketParam) {
        weChatTicketParam.setPageNo(weChatTicketParam.getPageNo().intValue()-1);
        return orderGoodsMapper.getOrderGoodsListWithPage(weChatTicketParam);
    }

    @Override
    public Integer getOrderGoodsCountWithPage(WeChatTicketParam weChatTicketParam) {
        return orderGoodsMapper.getOrderGoodsCountWithPage(weChatTicketParam);
    }

    @Override
    public OrderGoods getOrderGoodsInfo(Long orderId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        OrderGoods orderGoods = orderGoodsMapper.selectOne(queryWrapper);
        if(orderGoods==null){
            OrderExceptionAssert.ORDER_NOT_FOUND.assertFail();
        }
        return orderGoods;
    }

    @Override
    public void batchUpdateOrderGoods(List<OrderGoods> orderGoodsList) {
        orderGoodsMapper.batchUpdate(orderGoodsList);
    }

    @Override
    public List<OrderGoods> getOrderGoodsListByOrderId(Long orderId) {
        return orderGoodsMapper.getOrderGoodsListByOrderId(orderId);
    }

    /**
     * 获取订单列表
     * @param ticketSearchParam
     * @return List<Orders>
     */

    @Override
    public Map getOrderList(TicketSearchParam ticketSearchParam) {
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(ticketSearchParam.getGoodsName())){
            map.put("goodsName",ticketSearchParam.getGoodsName());
        }
        if(StringUtils.isNotEmpty(ticketSearchParam.getOrderNo())){
            map.put("orderNo",ticketSearchParam.getOrderNo());
        }
        if(ticketSearchParam.getOrderSource()!=null && ticketSearchParam.getOrderSource()!= OrderSourceEnum.TICKET_SOURCE_ALL.getValue()
        ){
            map.put("orderSource",ticketSearchParam.getOrderSource());
        }
        if(ticketSearchParam.getPayStatus()!=null && ticketSearchParam.getPayStatus()!= FrontPayStatusEnum.ALL_PAY.getValue()){
            map.put("payStatus",ticketSearchParam.getPayStatus());
        }
        if(StringUtils.isNotEmpty(ticketSearchParam.getPhone())){
            map.put("phone",ticketSearchParam.getPhone());
        }
        if(ticketSearchParam.getChannelId()!=null && ticketSearchParam.getChannelId()!= ChannelEnum.TICKET_ALL.getValue()){
            map.put("channelId",ticketSearchParam.getChannelId());
        }
        if(ticketSearchParam.getTransactionStatus()!=null && ticketSearchParam.getTransactionStatus()!=FrontTransactionStatusEnum.TICKET_ALL.getValue()){
            map.put("transactionStatus",ticketSearchParam.getTransactionStatus());
        }
        if(StringUtils.isNotEmpty(ticketSearchParam.getStartTime())){
            map.put("startTime",ticketSearchParam.getStartTime());
        }
        if(StringUtils.isNotEmpty(ticketSearchParam.getEndTime())){
            map.put("endTime",ticketSearchParam.getEndTime());
        }
        map.put("page",ticketSearchParam.getPageNo()-1);
        map.put("pageSize",ticketSearchParam.getPageSize());
        List<Map> getOrderList = orderGoodsMapper.getOrderList(map);
        int total = orderGoodsMapper.getOrderCount(map);
        map = new HashMap();
        map.put("result",getOrderList);
        map.put("total",total);
        return map;
    }

    @Override
    public OrderAppSearchDTO getOrderSearchList(OrderSearchParam orderSearchParam) {
        Page<OrderAppSearchListDTO> page = new Page<>(orderSearchParam.getPageNo(), orderSearchParam.getPageSize());
        IPage<OrderAppSearchListDTO> orderAppSearchListDTOIPage = orderGoodsMapper.orderGoodsSearch(page, orderSearchParam);
        List<OrderAppSearchListDTO> records = orderAppSearchListDTOIPage.getRecords();
        records.forEach(c->{
            String extraInformation = c.getExtraInformation();
            if(extraInformation!=null&&extraInformation!=""){
                Map<String, Object> map = (Map<String, Object>) JSONObject.parse(extraInformation);
                if( map!=null && map.size()!=0) {
                    if(map.containsKey("scenicName")){
                        c.setScenicName(map.get("scenicName").toString());
                    }
                    if(map.containsKey("isCanRefund")){
                        c.setIsCanRefund(Integer.parseInt(map.get("isCanRefund").toString()));
                    }
                    if(map.containsKey("discriminateBBDSts")){
                        c.setDiscriminateBBDSts(Integer.parseInt( map.get("discriminateBBDSts").toString()));
                    }
                    if(map.containsKey("timespan")){
                        c.setTimespan(map.get("timespan").toString());
                    }
                }
            }

        });
        Integer integer = orderGoodsMapper.searchOrderCount(orderSearchParam);
        orderAppSearchListDTOIPage.setRecords(records);
        OrderAppSearchDTO orderAppSearchDTO = new OrderAppSearchDTO();
        orderAppSearchDTO.setPeopleNumBer(integer);
        orderAppSearchDTO.setOrderAppletSearchListDTOS(records);
        return orderAppSearchDTO;
    }

    @Override
    public OrderListDTO getOrderGoodsList(OrderSearchParam orderSearchParam) {
        if(orderSearchParam.getTimeCriteria()!=null&&orderSearchParam.getTimeCriteria()==1){
            orderSearchParam.setCheckStartTime(orderSearchParam.getStartTime());
            orderSearchParam.setCheckEndTime(orderSearchParam.getEndTime());
            orderSearchParam.setStartTime("");
            orderSearchParam.setEndTime("");
        }
        if(orderSearchParam.getSearchCriteria()!=null&&orderSearchParam.getSearchCriteria()==1){
            orderSearchParam.setOrderNo(orderSearchParam.getSearch());
            orderSearchParam.setSearch("");
        }
        if(orderSearchParam.getSearchCriteria()!=null&&orderSearchParam.getSearchCriteria()==2){
            orderSearchParam.setCustomerName(orderSearchParam.getSearch());
            orderSearchParam.setSearch("");
        }
        if(orderSearchParam.getSearchCriteria()!=null&&orderSearchParam.getSearchCriteria()==3){
            orderSearchParam.setCertificateNo(orderSearchParam.getSearch());
            orderSearchParam.setSearch("");
        }
        if(orderSearchParam.getSearchCriteria()!=null&&orderSearchParam.getSearchCriteria()==4){
            orderSearchParam.setPhone(orderSearchParam.getSearch());
            orderSearchParam.setSearch("");
        }
        List<OrderGoodsListDTO> orderGoodsListDTOs = orderGoodsMapper.searchOrderGoodsList(orderSearchParam);
        Integer orderCount = orderGoodsMapper.searchOrderCount(orderSearchParam);
        orderSearchParam.setOrderStatus((byte)OrderStatusEnum.TICKET_WAIT_USE.getValue());
        Integer waitOrderCount = orderGoodsMapper.searchOrderCount(orderSearchParam);
        orderGoodsListDTOs.forEach(c->{
            if(c.getExtraInformation()!=null&&c.getExtraInformation()!="") {
                String extraInformation = c.getExtraInformation();
                Map<String, Object> map = (Map<String, Object>) JSONObject.parse(extraInformation);
                c.setExtraInformations(map);
            }
        });
        OrderListDTO orderListDTO = new OrderListDTO();
        orderListDTO.setPeopleNumBer(orderCount);
        orderListDTO.setWaitCount(waitOrderCount);
        orderListDTO.setOrderGoodsListDTOS(orderGoodsListDTOs);
        return orderListDTO;
    }
    @Override
    public List<OrderDetailListDTO> getOrderDetailList(OrderDetailListParam orderDetailListParam) {
        List<OrderDetailListDTO> orderDetailList = orderGoodsMapper.getOrderDetailList(orderDetailListParam);
        return orderDetailList;
    }

    @Override
    public List<OrderGoods> getOrderGoodsDetails(Long orderId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("order_id",orderId);
        return  orderGoodsMapper.selectList(orderGoodsQueryWrapper);
    }

    @Override
    public  List<OrderGoods>  getOrderGoodsModel(Long orderId) {
        return  orderGoodsMapper.getOrderGoodsModel(orderId);
    }

    @Override
    public OrderRelatePeople getOrderGoodsUser(Long orderId) {
        return orderGoodsMapper.getOrderGoodsUser(orderId);
    }

    @Override
    public Orders getParentOrders(Long parentOrderId) {
        return orderGoodsMapper.getParentOrders(parentOrderId);
    }

    @Override
    public List<OrderCancelRecord> getCanRefundOrder(Long orderId) {
        return orderGoodsMapper.getCanRefundOrder(orderId);
    }

    @Override
    public OrderRefundRecord getOrderGoodsRefund(Long orderId) {
        return orderGoodsMapper.getOrderGoodsRefund(orderId);
    }

    @Override
    public Integer selectAmount(Long skuId) {
        return orderGoodsMapper.selectSkuIdAmount(skuId);
    }

}
