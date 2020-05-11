package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.handler.PackageOrderHandler;
import cn.enn.wise.ssop.service.order.mapper.OrderPackageMapper;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.service.OrderPackageService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 套餐订单业务逻辑层
 *
 * @author baijie
 * @date 2020-05-03
 */
@Service("packageOrderService")
@Slf4j
public class PackageOrderServiceImpl  {


    @Autowired
    private PackageOrderHandler handler;

    @Autowired
    private OrderPackageService orderPackageService;

    @Autowired
    private HotelServiceImpl hotelService;

//    @Override
    public Long saveOrder(BaseOrderParam baseOrderParam) {

        OrderWrapper orderWrapper = handler.initOrder(baseOrderParam);

        Long orderId = hotelService.saveOrderWapper(orderWrapper);

//        List<OrderGoods> orderGoodsList = orderWrapper.getOrderGoodsList();
//        List<OrderPackage> packageInfoList = new ArrayList<>();
//        if(CollectionUtils.isNotEmpty(orderGoodsList)){
//            log.info("构建套餐订单子表信息");
//            for (OrderGoods orderGoods : orderGoodsList) {
//
//                packageInfoList.addAll(buildOrderPackage(orderGoods));
//
//            }
//            if(CollectionUtils.isNotEmpty(packageInfoList)){
//                orderPackageService.saveBatch(packageInfoList);
//            }
//        }

        return orderId;
    }


    /**
     * 转换extInfo信息为套餐订单子表信息
     */

    private List<OrderPackage> buildOrderPackage(OrderGoods orderGoods){

        List<OrderPackage> orderPackages = new ArrayList<>();
        if(orderGoods == null){
            return orderPackages;
        }

        String extraInformation = orderGoods.getExtraInformation();
        PackageExtInfo packageExtInfo = JSONObject.parseObject(extraInformation, PackageExtInfo.class);
        List<PackageExtInfo.TicketInfoBean> ticketInfo = packageExtInfo.getTicketInfo();
        ticketInfo.forEach( info ->{

            List<PackageExtInfo.TicketInfoBean.TicketMsgBean> ticketMsg = info.getTicketMsg();
            //套餐下单以每张票保存一条数据
            ticketMsg.forEach( ticketMsgBean -> {
                OrderPackage orderPackage = new OrderPackage();

                orderPackage.setOrderId(orderGoods.getOrderId());
                orderPackage.setOrderNo(orderGoods.getOrderNo());
                orderPackage.setGoodsId(orderGoods.getGoodsId());
                orderPackage.setGoodsType(orderGoods.getGoodsType());

                orderPackage.setUseStartDate(packageExtInfo.getStartTime());
                orderPackage.setUseEndDate(packageExtInfo.getOutTime());
                orderPackage.setPackageGoodsName(packageExtInfo.getScenicName());
                orderPackage.setGoodsName(info.getGoodsName());
                orderPackage.setGoodsSku(JSONObject.toJSONString(packageExtInfo.getSku()));
                orderPackage.setTicketName(ticketMsgBean.getTicketName());
                orderPackage.setTicketCode(ticketMsgBean.getTicketName());
                orderPackage.setTicketStatus(ticketMsgBean.getTicketStatus());
                orderPackages.add(orderPackage);
            });

        });

        return orderPackages;
    }

//    @Override
//    public void saveOrderCancelRecord(OrderChangeRecord orderChangeRecord, List<OrderCancelRecord> orderCancelRecordList, List<OrderGoods> orderGoodsList) {
//
//    }
//
//    @Override
//    public void confirmOrderCancelRecord(Orders orders, List<OrderGoods> orderGoodsList, int checkStatus) {
//
//    }
//
//    @Override
//    public List<OrderCancelListDTO> orderCancelList(Long orderId) {
//        return null;
//    }
}
