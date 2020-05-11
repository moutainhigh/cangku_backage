package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.bo.autotable.DistributeBindUser;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrder;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface OrderDao extends BaseMapper<Orders> {

    List<Order> findBystate(@Param("state")int state ,@Param("orderType")int orderType);

    Order findOrderInfo2(@Param("id")Long id);

    List<Order> findAllOrderList(@Param("orderBean") OrderBean orderBean);

    List<Order> findOrderInfo(@Param("oderCode") String[] oderCode);

    Order findGoodsId(@Param("goodsId") Integer goodsId);

    List<OrderTickets> findOrderTicket(@Param("orderCode") String[] orderCode);

    OrderTickets findOrderTicketByBBDTicketNum(@Param("ticketNum")String ticketNum);

    OrderTickets findOrderTicketsById(@Param("id")Long id);

    int refundByOrderId(@Param("oderCode") String[] oderCode);

    List<AppOrderVo> findAppAllOrderList(@Param("appOrderBean") AppOrderBean appOrderBean);

    AppOrdersVo findAppAllNoUseOrder(@Param("appOrderBean") AppOrderBean appOrderBean);

    long cancelAppOrder(@Param("orderCode") String orderCode);

    long retreatAppOrder(@Param("orderCode") String orderCode);

    long retreatAppOrders(@Param("orderCode") String orderCode);

    int updateOrderTicket(@Param("id") Long id);

    List<Long> findOrderTicketIdByOrderCode(@Param("orderCode") String orderCode);

    void updateOrderTicketBBD(@Param("id")Long id,@Param("orderSerial")String orderSerial,@Param("ticketSerial")String ticketSerial,@Param("ticketId") String ticketId,@Param("ticketStatus")int ticketStatus,@Param("qrCode") String qrCode);

    void updateOrderTicketStateBBD(@Param("id")Long id,@Param("state")int state);

    void updateOrderTicketSerialBBD(@Param("id")Long id,@Param("ticketSerialBbd") String ticketSerialBbd,@Param("qrCode") String qrCode);

    List<Order> findCancelOrder();

    List<Order> findCancelWZDOrder();

    void updateOrderState(@Param("id") Long id);

    void updateOrderStateById(@Param("id") Long id,@Param("state") Integer state);

    void updateOrderTicketState(@Param("orderCode") String orderCode,@Param("snapshot") String snapshot);

    List<Order> queryRefundOrder();

    List<Order> queryTicketUnusedOrder();

    List<Order> queryTicketUnusedBBDOrder();

    List<Order> queryTicketPayedOrder();

    void updateOrderPayStatus(@Param("orderNum") String orderNum,@Param("actualPay") BigDecimal actualPay);

    /** 根据ID更新订单状态 **/
    void updateOrderStateById(@Param("state")int state, @Param("id")long id);

    /** 根据id更新支付状态 **/
    void updateOrderPayStateById(@Param("state")int state,@Param("payState")int payState, @Param("id")long id);

    void updateOrderStateByOrderCode(@Param("state")int state, @Param("orderCode")String orderCode);

    /** 设置（船票）订单票明细，已使用 **/
    void setOrderTicketStatus(@Param("ticketId")String ticketId, @Param("status")Integer status);

    /** 设置（船票）订单票明细，已退款 **/
    void setOrderTicketRefund(@Param("ticketId")String ticketId,@Param("refund")Double refund,@Param("refundRatio")String refundRatio);

    List<Map<String,Object>> listReport(@Param("startDate") String startDate,@Param("endDate") String endDate);

    List<GoodsProject> findProductName(@Param("projectId") Integer projectId);

    Order findDistributorPhone(@Param("id") Long id);

    GoodsProject queryByStaffIdAndProjectId(@Param("staffId")Long id, @Param("projectId")Long projectId);

    GroupOrderItem findGroupOrderItemByOrderId(@Param("orderId") Long orderId);

    GroupOrder findGroupOrderByGroupOrderId(@Param("groupOrderId") Long groupOrderId);

    GroupItemVo findGroupOrderInfo(@Param("id") Long id);

    List<H5OrderVo> findH5AllOrderList(@Param("orderQueryBean") OrderQueryBean orderQueryBean);

    long deleteH5Order(@Param("orderCode") String orderCode);

    long updateProdCommSts(@Param("orderCode") String orderCode);

    List<RefundApply> findRefundOrderByOrderCode(@Param("orderCodeList") List<String> orderCodeList);

    List<OrderTicketVo> findComposeOrderInfo(@Param("orderCode") String orderCode);

    OrderTicketVo findLaiU8OrderDetail(@Param("orderCode") String orderCode);

    long updateOrderUseSts(@Param("orderCode") String orderCode);

    Order findComposeOrderDetail(@Param("orderCode") String orderCode);

    Order findComposePftOrderDetail (@Param("wxCode") String wxCode);

    List<BoatOrderVo> findAllBoatOrderList(@Param("boatPcOrderBean") BoatPcOrderBean boatPcOrderBean);

    long updateLaiU8OrderSts(@Param("ticketId") String ticketId);

    long updateLaiU8PrintedSts(@Param("orderPrintedParams") OrderPrintedParams orderPrintedParams);

    long distributeBindUser(@Param("distributeBindUserParam") DistributeBindUserParam distributeBindUserParam);

    DistributeBindUser findIsDisBindUser(@Param("phone") String phone, @Param("userId") Long userId);

    long updateOrderFailureRefund(@Param("orderCode") String orderCode);

    long updateBBDOrderRefundSts(@Param("ticketSerialBbd") String ticketSerialBbd);

    long updateOrderSts(@Param("orderCode") String orderCode,@Param("status") Integer status);

    long updateLaiU8OrderCheckSts(@Param("ticketId") String ticketId);

    List<Long> findValidOrder(@Param("batCode") String batCode);

    long updateOrderTicketStatus(@Param("ids") Long ids);

    long updateOrderTicketStatusByOrderId(@Param("orderId") Long orderId);

    long updateUserCouId(@Param("id") Long id,@Param("state") Integer state,@Param("businessId")Long businessId,@Param("couPrice") Double couPrice);

    String findOrderCodeByBatCode(@Param("batCode") String batCode);

    List<OrderCouVo> listCou(@Param("appCouParam")AppCouParam appCouParam);

    OrderCouInfo getOrderCouInfo(@Param("orderCode") String orderCode);

    List<OrderCouInfo> getOrderCouInfoList(@Param("businessId") Long businessId);

 }
