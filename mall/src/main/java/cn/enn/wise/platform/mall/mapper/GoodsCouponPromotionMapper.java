package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponPromotionBo;
import cn.enn.wise.platform.mall.bean.param.CouponParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * GoodsCouponPromotionMapper接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-12-12
 */
public interface GoodsCouponPromotionMapper extends BaseMapper<GoodsCouponPromotionBo> {

    /**
     * 查询新人优惠券信息
     * @return
     */
    List<CouponParam> getNewPeopleCoupon(@Param("companyId") Long companyId);


    List<CouponParam> getNewPeopleCouponByUserId(@Param("companyId") Long companyId,@Param("userId") Long userId);

    List<CouponParam> getDistributorCouponByUserId(@Param("companyId") Long companyId,@Param("userId") Long userId,@Param("phone")String phone);

    /**
     * 查询用户优惠券,根据状态
     * @param companyId
     * @param userId
     * @param status
     * @return
     */
    List<CouponParam> getCouponByStatus(@Param("companyId") Long companyId, @Param("userId") Long userId,@Param("status") String status);

    List<CouponParam> getCouponByStatus1(@Param("companyId") Long companyId, @Param("userId") Long userId);

    List<CouponParam> getCouponByStatusAndPhone(@Param("companyId") Long companyId, @Param("userId") Long userId,@Param("status") String status,@Param("phone") String phone);

    List<Map<String,Object>> getGoods(@Param("promotionId") Long promotionId);

    List<CouponParam> getCouponInfo(@Param("id") Long id);

    List<CouponParam> selectCouponNotGeted(@Param("companyId") Long companyId, @Param("userId") Long userId);
}
