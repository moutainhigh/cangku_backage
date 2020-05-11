package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.HotelOrder;
import cn.enn.wise.platform.mall.bean.param.HotelOrderParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderAdminVo;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;

import java.util.List;

/**
 * 酒店订单接口
 *
 * @author baijie
 * @date 2019-09-20
 */

public interface HotelOrderService {

    /**
     * 保存订单
     * @param hotelOrderVo
     *         创建订单参数
     * @return 微信支付参数
     * @throws Exception
     *          业务异常
     */
    Object saveOrder(HotelOrderVo hotelOrderVo) throws Exception;

    /**
     * 获取用户订单列表
     * @param userId
     *          用户id
     * @return
     *          用户订单列表
     */
    List<HotelOrder> getUserOrderList(Long userId);

    /**
     * 获取用户订单详情
     * @param orderCode
     *      订单号
     * @param userId
     *      用户id
     * @return
     *      订单详情
     */
    HotelOrder getOrderInfo(String orderCode,Long userId);

    /**
     * 用户取消订单
     * @param orderId
     *          订单Id
     * @param userId
     *          用户id
     * @exception
     *      cn.enn.wise.platform.mall.util.exception.BusinessException
     *          业务异常 订单状态不正确,无法取消
     */
    void userCancelOrder(String orderId,Long userId) throws Exception;


    /**
     * 再次支付订单
     * @param hotelOrderVo
     *          再次支付订单参数
     * @return
     *          微信支付参数
     * @exception Exception
     *          业务异常
     */
    Object payOldOrder(HotelOrderVo hotelOrderVo) throws Exception;

    /**
     * 修改订单状态至支付成功
     * @param batCode
     *          微信订单号
     */
    void updateOrderStatusToSuccess(String batCode);

    /**
     * 取消过期的订单
     */
    void cancelExpireOrder();


    /**
     * PC管理端订单列表
     * @param orderParams
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<HotelOrderAdminVo>>> listHotelOrder(ReqPageInfoQry<HotelOrderParams> orderParams);

    /**
     * 退单
     * @param orderCode
     * @return
     */
    ResponseEntity refundOrderById(String orderCode);

    /**
     * 入住
     * @param orderCode
     * @return
     */
    ResponseEntity checkIn(String orderCode);
}
