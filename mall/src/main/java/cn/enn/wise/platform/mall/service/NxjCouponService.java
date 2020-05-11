package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.AppCouParam;
import cn.enn.wise.platform.mall.bean.vo.CouCountInfo;
import cn.enn.wise.platform.mall.bean.vo.CouInfo;
import cn.enn.wise.platform.mall.bean.vo.OrderCouInfo;
import cn.enn.wise.platform.mall.bean.vo.OrderCouVo;
import com.github.pagehelper.PageInfo;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 10:16
 **/
public interface NxjCouponService {

    /***
     * 订单详情
     * @param orderCode
     * @return
     */
    OrderCouInfo getOrderCouInfo(String orderCode);

    Boolean orderCan(String orderCode,Long businessId);

    Boolean couCan(Long businessId,Long id,Double couponPrice,String name);

    CouInfo getCouInfo(Long id,Long userId);

    PageInfo<OrderCouVo> list(AppCouParam appCouParam, Integer pageNum, Integer pageSize);

    PageInfo<OrderCouInfo> couList(Long businessId,Integer pageNum, Integer pageSize);

    PageInfo<CouInfo> downList(Long businessId,Integer pageNum, Integer pageSize);

    CouCountInfo count(Long businessId,Integer pageNum, Integer pageSize,String time);
}
