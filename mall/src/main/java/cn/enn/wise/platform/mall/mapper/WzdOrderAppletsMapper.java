package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.OrderTicket;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.util.order.OrderState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WzdOrderAppletsMapper {

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
     * 更新订单操作
     * @param orders
     * @return
     * @throws Exception
     */
    @OrderState(operate = "更新票付通订单号和二维码地址",clazz =Orders.class,value = "2")
    int updateOrderNew(Orders orders) throws Exception;


    /**
     * 根据订单号查询订单信息
     * @return
     */
    Orders getOrderInfoByOrderCode(Orders orders);

    /**
     * 根据微信订单号查询订单信息
     * @return
     */
    Orders getOrderInfoByBatCode(@Param("batCode") String batCode);

    /**
     * 根据订单号查询订单信息
     * @param orderCode
     * @return
     */
    Orders getOrderInfoByOrderCode2(@Param("orderCode") String orderCode);

    /**
     * 根据订单Id查询订单信息
     * @param id
     * @return
     */
    Orders getOrderById(@Param("id") Long id);

    /**
     * 根据订单百邦达订单号查询订单信息
     * @param batCodeOther
     * @return
     */
    Orders getOrderByBatCodeOther(@Param("batCodeOther") String batCodeOther);

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
    void updateOrdersProfitStatus(@Param("profitStatus") Integer profitStatus, @Param("orderId") Long orderId);

    /**
     * 查询最大的订单号
     * @param code
     * @return
     */
    String getMaxOrderCode(@Param("code")String code);

    /**
     * 查询半个小时的体验人数
     */
    List<Map<String,Object>> selectProjectOrder(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 更新订单二维码和凭证码
     * @param qrCode
     * @param proof
     */
    void updateQrCode(@Param("qrCode")String qrCode,@Param("proof") String proof,@Param("id") Long id);

    /**
     * 更新groupOrderId
     * @param orderId 订单Id
     * @param groupOrderId  团单ID
     */
    void updateGroupOrderId(@Param("orderId") Long orderId,@Param("groupOrderId") Long groupOrderId);


    /**
     * 未支付状态取消订单
     * @param state 订单状态 5 已取消
     * @param id 订单id
     */
    void updateOrderStatusToCancel(@Param("state")Integer state,
                                   @Param("id")Long id);

    /**
     * 更新订单使用优惠券Id为null
     * @param id
     * @param userOfCouponId
     */
    void updateUserOfCouponId(@Param("id")Long id,
                              @Param("userOfCouponId")Long userOfCouponId);


    /**
     * 更新电子登船单
     * @param id
     * @param ticketsUrl
     */
    void updateOrderTicketUrl(@Param("id") Long id,@Param("batCodeOther") String ticketsUrl);

    /**
     * 批量更新用户Id
     * @param userId 用户Id
     * @param orderIds 订单Id集合
     */
    void batchUpdateUserId(@Param("orderIds") List<Long> orderIds,@Param("userId") Long userId);


    /**
     * 查询未成功提交的订单
     * @return
     */
    List<Orders> selectFailedOrder();
}
