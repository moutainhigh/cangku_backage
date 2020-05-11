package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.api.order.dto.request.OrderDetailListParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.WeChatTicketParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderDetailListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderListDTO;
import cn.enn.wise.ssop.api.order.dto.response.app.OrderAppSearchDTO;
import cn.enn.wise.ssop.service.order.model.*;

import java.util.List;
import java.util.Map;

/**
 * 订单商品服务
 */
public interface OrderGoodsService {

    /**
     * 查看订单商品列表信息
     * @param orderIds
     * @return
     */
    List<OrderGoods> getOrderGoodsList(List<Long> orderIds);

    /**
     * 获取订单商品列表
     * @param orderGoods
     * @return
     */
    List<OrderGoods> getOrderGoodsList(OrderGoods orderGoods);

    /**
     * 带分页获取订单商品列表
     * @param weChatTicketParam
     * @return
     */
    List<OrderGoods> getOrderGoodsListWithPage(WeChatTicketParam weChatTicketParam);

    /**
     * 带分页获取订单商品数量
     * @param weChatTicketParam
     * @return
     */
    Integer getOrderGoodsCountWithPage(WeChatTicketParam weChatTicketParam);

    /**
     * 获取订单商品详细信息
     * @param orderId
     * @return
     */
    OrderGoods getOrderGoodsInfo(Long orderId);

    /**
     * 批量更新订单商品
     * @param orderGoodsList
     */
    void batchUpdateOrderGoods(List<OrderGoods> orderGoodsList);

    /**
     * 获取主订单下的商品列表
     * @param orderId
     * @return
     */
    List<OrderGoods> getOrderGoodsListByOrderId(Long orderId);

    /**
     * 商品列表查询
     * @param ticketSearchParam
     * @return
     */
    Map getOrderList(TicketSearchParam ticketSearchParam);

    /**
     * APP商品列表查询
     * @param orderSearchParam
     * @return
     */
    OrderAppSearchDTO getOrderSearchList(OrderSearchParam orderSearchParam);

    /**
     * 订单搜索列表
     * @param orderSearchParam
     * @return
     */
    OrderListDTO getOrderGoodsList(OrderSearchParam orderSearchParam);

    /**
     * 订单详细信息列表
     * @param orderDetailListParam
     * @return
     */
    List<OrderDetailListDTO> getOrderDetailList(OrderDetailListParam orderDetailListParam);

    /**
     * 获取订单商品列表
     * @param orderId
     * @return
     */
    List<OrderGoods> getOrderGoodsDetails(Long orderId);//新加的

    /**
     * 获取订单商品列表
     * @param orderId
     * @return
     */
    List<OrderGoods>  getOrderGoodsModel(Long orderId);

    /**
     * 获取订单联系人
     * @param orderId
     * @return
     */
    OrderRelatePeople getOrderGoodsUser(Long orderId);

    /**
     * 获取订单信息
     * @param orderId
     * @return
     */
    Orders getParentOrders(Long orderId);

    /**
     * 获取订单取消记录
     * @param orderId
     * @return
     */
    List<OrderCancelRecord> getCanRefundOrder(Long orderId);

    /**
     * 获取订单退款记录
     * @param orderId
     * @return
     */
    OrderRefundRecord getOrderGoodsRefund(Long orderId);

    /**
     * 获取订单数量
     * @param skuId
     * @return
     */
    Integer selectAmount(Long skuId);
}
