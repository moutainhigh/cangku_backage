package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.vo.ThirdOrderContext;

/**
 * 第三方订单下单服务
 *
 * @author baijie
 * @date 2020-02-13
 */
public interface ThirdOrderService {


    /**
     * 保存订单
     * @param thirdOrderContext
     * @throws Exception
     * @return
     */
    Object saveOrder(ThirdOrderContext thirdOrderContext) throws Exception;

    /**
     * 获取商品信息
     * @param orderContext
     * @throws Exception
     * @return
     */
    ThirdOrderContext getGoodsInfo(ThirdOrderContext orderContext) throws Exception;

    /**
     * 预下单
     * @param orderContext
     * @throws Exception
     * @return
     */
    ThirdOrderContext preSaveOrder(ThirdOrderContext orderContext) throws Exception;

    /**
     * 构建订单信息
     * @param orderContext
     * @throws Exception
     * @return
     */
    ThirdOrderContext buildOrderInfo(ThirdOrderContext orderContext) throws Exception;

    /**
     * 构建子订单信息
     * @param orderContext
     * @throws Exception
     * @return
     */
    ThirdOrderContext buildOrderTicketInfo(ThirdOrderContext orderContext) throws Exception;

    /**
     * 获取预下单信息
     * @param thirdOrderContext
     * @throws Exception
     * @return
     */
    ThirdOrderContext getPrePayInfo(ThirdOrderContext thirdOrderContext) throws Exception;

    /**
     * 出来支付成功的订单
     * @param orderCode
     * @throws Exception
     */
    void processPaySuccessOrder(String orderCode) throws Exception;
}
