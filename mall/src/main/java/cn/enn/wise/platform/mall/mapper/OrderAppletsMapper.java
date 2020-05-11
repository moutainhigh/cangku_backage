package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.OrderTicket;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderAppletsMapper {

    /**
     * 添加订单
     * @param order
     * @return
     * @throws Exception
     */
    int insertOrder(Orders order) throws Exception;

    /**
     * 更新订单
     * @param orders
     * @return
     * @throws Exception
     */
    int updateOrder(Orders orders) throws Exception;

    /**
     * 根据订单号查询订单信息
     * @return
     */
    Orders getOrderInfoByOrderCode(Orders orders);

    /**
     * 根据订单Id查询订单信息
     * @param id
     * @return
     */
    Orders getOrderById(@Param("id") Long id);

    /**
     * 根据用户Id获取用户所有订单
     * @param orders
     * @return
     */
    List<Orders> getUserOrder(Orders orders);


    /**
     * 根据订单号查询订单详情
     * @param orders
     * @return
     */
    Orders  getOrderByIdAndUserId(Orders orders);

    /**
     * 取消订单
     * @param orders
     * @return
     */
    int refundOrder(Orders orders);

    /**
     * 订单完成
     */
    int updateOrderByOrderCode(Orders orders);

    /**
     * 查询未在支付时间内支付的订单
     * @return
     */
    List<Orders> selectExpireOrder();

    /**
     * 批量插入订单子表
     * @param orderTickets
     */
    void insertOrdertTicket(List<OrderTicket> orderTickets);

    /**
     * 更新订单分润状态
     */
    void updateOrdersProfitStatus(@Param("profitStatus") Integer profitStatus, @Param("orderId") Long orderId,@Param("isDistributeOrder") Integer isDistributeOrder);

    /**
     * 更新第三方订单编号
     * @param batCode
     * @param orderId
     */
    void updateOrdersBatCode(@Param("batCode") String batCode,@Param("orderId") Long orderId);

    /**
     * 查询最大的订单号
     * @param
     * @return
     */
    Map<String,String> selectMaxOrderCode();

    List<Order> getOrderByUserIdAndState(@Param("userId") Long userId);


    /**
     * 根据orderID查询子订单
     * @param orderId 订单Id
     * @return
     */
    List<OrderTicket> getOrderTicketByOrderId(@Param("orderId")Long orderId);

    /**
     * 批量更新订单子表
     * @param orderTicket
     */
    void updateOrderTicket(OrderTicket orderTicket);

    /**
     * 批量修改船票订单状态
     * @param orderId 订单id
     * @param status 状态
     */
    void updateOrderTicketShipStatus(@Param("orderId")Long orderId,@Param("status")Integer status);

}
