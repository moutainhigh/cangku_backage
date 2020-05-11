package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.bean.vo.TicketResVo;

import java.util.List;

/**
 * 订单相关业务逻辑
 */
public interface OrderAppletsService {

    /**
     * 获取支付信息
     * @return
     * @param payParam
     */
    Object saveOrder(PayParam payParam) throws Exception ;

    /**
     * 立即预定信息
     * @return
     */
    TicketResVo predestinate(GoodsReqParam goodsReqParam) throws Exception;


    /**
     * 原有订单支付
     * @param payParam
     * @return
     */
     Object payOriginalOrder(PayParam payParam) throws Exception;

    /**
     * 获取用户订单
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
    int refundOrder(Orders orders) throws Exception;

    /**
     * 订单支付成功修改支付状态
     * @param orders
     * @return
     * @throws Exception
     */
    int complateOrder(Orders orders) throws Exception;

    /**
     * 取消未支付的过期订单
     */
    void cancelExpireOrder();


}
