package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.autotable.DistributeBindUser;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrder;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 18:34
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public interface OrderService {

    PageInfo<Order> findAllOrderList(OrderBean orderBean);

    PageInfo<BoatOrderVo> findAllBoatOrderList(BoatPcOrderBean boatPcOrderBean);

    Boolean refundByOrderId(String orderCodes);

    List<Order> findOrderInfo(String orderCodes);

    GoodsProject  queryByStaffIdAndProjectId(Long userId, Long projectId);

    List<AppOrderVo> findAppAllOrderList(AppOrderBean appOrderBean);

    AppOrdersVo findAppAllOrderListV2(AppOrderBean appOrderBean);

    long closeAppOrder(Integer type,String orderCode,User user,String roleName);

    long confirmAppOrder(String orderCode,User user,Integer tactics,String roleName);

    void cancelOrder();

    void cancelWZDOrder();

    void handleCheckOrder();

    List<Order> queryRefundOrder();

    List<Order> queryTicketUnusedOrder();

    List<Order> queryTicketPayedOrder();

    void updateOrderPayStatus(String orderNum, BigDecimal actualPay);

    void updateOrderStateById(Integer state,Long id);

    void setOrderTicketStatus(String ticketId, Integer status);

    void setOrderTicketRefund(String ticketId,Double refund,String refundRatio);

    List<Map<String,Object>> listReport(String startDate,String endDate);

    GroupOrderItem findGroupOrderItemByOrderId(Long orderId);

    GroupOrder findGroupOrderByGroupOrderId(Long groupOrderId);

    List<H5OrderVo> findH5AllOrderList(OrderQueryBean orderQueryBean);

    long deleteH5Order(String orderCode);

    long updateProdCommSts(String orderCode);

    List<H5OrderVo> findH5OrderInfo(String orderCode,Long userId);

    ComposeOrderVo findComposeOrderDetail(String orderCode);

    BoatPcOrderDetailVo findBoatOrderDetailPc(String orderCode);

    List<OrderTicketVo> findTicketOrderDetail(String orderCode);

    OrderTicketVo findLaiU8OrderDetail(String orderCode);

    long updateLaiU8PrintedSts(OrderPrintedParams orderPrintedParams,String orderCode);

    long distributeBindUser(String phone,Long userId);

    DistributeBindUser findIsDisBindUser(String phone, Long userId);

    /**
     * 同步船票
     */
    void shipTicketSync();

    long updateOrderFailureRefund(String orderCode);

    /**
     * 同步订单状态
     */
    void updateBaBangDaOrderStatus();

    long updateBBDOrderRefundSts(String ticketSerialBbd);

    /**
     * 根据票付通的票票号找有效的订单
     * @param order16U
     * @return
     */
    List<Long> findValidOrder(String order16U);

    /**
     * 批量进行核销
     * @param ids
     * @return
     */
    void updateOrderTicketStatus(List<Long> ids, int num);

    /**
     * 根据票号查询出订单号
     * @param order16U
     * @return
     */
    String findOrderCodeByBatCode(String order16U);

    /**
     * 根据订单号修改订单状态
     * @param orderCode
     */
    void updateOrderStateByOrderCode(String orderCode);

    /***
     * 订单回调核销
     * @param orderStateCallBack
     */
    void orderStateCallBack(PFTOrderCallBack orderStateCallBack);

    void handlePftRefundOrder(PFTOrderCallBack pftOrderCallBack);
}
