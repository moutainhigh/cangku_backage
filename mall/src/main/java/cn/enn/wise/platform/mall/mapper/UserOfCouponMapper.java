package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.UserOfCouponBo;
import cn.enn.wise.platform.mall.bean.vo.CouInfo;
import cn.enn.wise.platform.mall.bean.vo.CouVo;
import cn.enn.wise.platform.mall.bean.vo.CouponInfoVo;
import cn.enn.wise.platform.mall.bean.vo.DrawCouponVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * UserOfCouponMapper 接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-12-13
 */
public interface UserOfCouponMapper extends BaseMapper<UserOfCouponBo> {


    /**
     * 获取优惠券的信息
     * @param userId 用户Id
     * @param id  用户领取的优惠券记录Id
     * @return
     */
    CouponInfoVo getCouponInfo(@Param("userId") Long userId,
                               @Param("id")Long id);


    /**
     * 更新优惠券为已使用状态
     * @param id 主键Id
     * @param status 状态1领取未使用 2已使用 3已过期 4装让中 5已转让
     */
    void updateCouponState(@Param("id")Long id,@Param("status") Integer status);

    /**
     * 更新优惠券为已使用状态
     * @param id 主键Id
     * @param status 状态1领取未使用 2已使用 3已过期 4装让中 5已转让
     */
    void updateCouponStateAndCouponPrice(@Param("id")Long id, @Param("status") Integer status, @Param("couponPrice")BigDecimal couponPrice);

    /**
     * 定时任务修改过期优惠券信息
     */
    void updateExpiredCoupon();

    void updateExpiredCouponByPromotion();

    void updateSendCoupon();

    Integer selectCouponSizeByUserId(@Param("userId")Long userId);

    Integer selectCouponSizeByUserIdAndPhone(@Param("userId")Long userId,@Param("phone")String phone);
    /**
     * 获取用户的优惠券状态
     * @param userId 用户id
     * @param id 用户领取优惠券记录的id
     * @return
     */
    Map<String,Object> getCouponStatus(@Param("userId") Long userId,
                                       @Param("id") Long id);

    Double getPrice(@Param("goodsCouponId")Long goodsCouponId);

    List<CouInfo> getCouInfoList(@Param("businessId")Long businessId);

    String count(@Param("time")String time,@Param("businessId")Long businessId);

    Double totalPrice(@Param("time")String time,@Param("businessId")Long businessId);

    Double upPrice(@Param("time")String time);

    Double downPrice(@Param("time")String time,@Param("businessId")Long businessId);

    List<CouVo> finPrice(@Param("time")String time,@Param("businessId")Long businessId);

    List<DrawCouponVo> findUserDrawCouponList(@Param("openId") String openId);
}
