package cn.enn.wise.ssop.service.order.service;


import cn.enn.wise.ssop.service.order.model.OrderRelatePeople;

import java.util.List;

/**
 * 订单联系人服务
 */
public interface OrderRelatePeopleService {

    /**
     * 根据订单id获取联系人列表
     * @param orderId
     * @return
     */
    List<OrderRelatePeople> getOrderRelatePeopleListByOrderId(Long orderId);

    /**
     * 获取主订单联系人列表
     * @param orderId
     * @return
     */
    List<OrderRelatePeople> getOrderRelatePeopleListByParentOrderId(Long orderId);

    /**
     * 批量保存订单联系人
     * @param orderRelatePeopleList
     */
    void batchSaveOrderRelatePeople(List<OrderRelatePeople> orderRelatePeopleList);

    /**
     * 根据订单id获取联系人明细
     * @param orderId
     * @return
     */
    OrderRelatePeople getOrderRelatePeopleInfo(Long orderId);


    /**
     * 获取票的数量
     * @param phone
     * @param userCard
     * @param start
     * @param end
     * @return
     */
    Integer getTicketNumber(String phone, String userCard, String start, String end);

    /**
     * 获取订单的数量
     * @param phone
     * @param userCard
     * @param start
     * @param end
     * @return
     */
    Integer getOrderNumberByMember(String phone, String userCard, String start, String end);


}
