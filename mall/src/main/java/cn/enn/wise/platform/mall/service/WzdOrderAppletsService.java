package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.bean.vo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单相关业务逻辑
 */
public interface WzdOrderAppletsService {

    /**
     * 获取支付信息
     * @return
     * @param payParam
     */
    Object saveOrder(PayParam payParam) throws Exception ;

    /**
     * 保存拼团订单
     * @param payParam 拼团订单
     * @return 支付参数
     * @throws Exception 业务异常
     */
    Object savePackageOrder(PayParam payParam) throws Exception;


    BigDecimal calculatePrice(CouponInfoVo couponInfo, BigDecimal goodsPrice, BigDecimal orderPrice);

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
     *
     * @param orders 订单信息
     * @return 修改groupOrderId数据条数
     * @throws Exception
     */
    int updateOrder (Orders orders) throws Exception;
    /**
     * 订单支付成功修改支付状态
     * @param batCode
     * @param wxPayCode
     * @return
     * @throws Exception
     */
    int complateOrder(String batCode, String wxPayCode) throws Exception;

    /**
     * 取消未支付的过期订单
     */
    void cancelExpireOrder();

    /**
     * 支付完成后客户端主动查询微信订单接口
     *
     * @param companyId
     *          景区Id
     * @param orderCode
     *          订单号
     * @param user
     *          用户
     * @return 订单支付信息
     * @throws Exception 业务异常
     */
    OrderQueryVo orderQuery(Long companyId, String orderCode, User user)throws Exception;


    /**
     * 计算优惠订单价格
     * @param userId 用户Id
     * @param userOfCouponId 用户领取优惠券的记录Id
     * @param goodsPrice 商品单价
     * @param amount 商品购买数量
     * @return 订单价格实体
     */
    OrderPriceVo getOrderPrice(Long userId, Long userOfCouponId, BigDecimal goodsPrice, Integer amount);


    /**
     * 保存船票订单
     * @param payParam 支付参数
     * @throws Exception 业务异常
     * @return
     */
    Object saveShipOrder(PayParam payParam) throws Exception;

    /**
     * 船票下单发送验证码
     * @param phone
     */
    void sendVerificationCode(String phone);


    void synchronizeOrderToBBD(Orders orderInfoByOrderCode);


    /**
     * 补录船票订单
     * @param orderCode 订单号
     * @return 补录订单详细信息
     */
    Object makeUpOrder(String orderCode) throws Exception;
}
