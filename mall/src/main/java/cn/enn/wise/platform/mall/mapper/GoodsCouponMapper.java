package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.vo.CouponCountVo;
import cn.enn.wise.platform.mall.bean.vo.CouponItemCountVo;
import cn.enn.wise.platform.mall.bean.vo.CouponPriceVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * GoodsCoupon Mapper 接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-12-12
 */
public interface GoodsCouponMapper extends BaseMapper<GoodsCouponBo> {

    /**
     * 获取优惠券消费数量统计结果
     * @param startDate
     * @param endDate
     * @param status
     * @param kind
     * @param couponNo
     * @param userId
     * @param couponPrice
     * @param businessName
     * @return
     */
    List<CouponCountVo> getCouponCountList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status, @Param("kind") String kind, @Param("couponNo") String couponNo, @Param("userId") String userId, @Param("couponPrice") Integer couponPrice, @Param("businessName") String businessName);

    /**
     * 获取优惠券消费金额统计结果
     * @param startDate
     * @param endDate
     * @param status
     * @param kind
     * @param couponNo
     * @param userId
     * @param couponPrice
     * @param businessName
     * @return
     */
    List<CouponPriceVo> getCouponPriceList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status, @Param("kind") String kind, @Param("couponNo") String couponNo, @Param("userId") String userId, @Param("couponPrice") Integer couponPrice, @Param("businessName") String businessName);

    /**
     * 获取优惠券消费明细统计结果
     * @param startDate
     * @param endDate
     * @param couponNo
     * @param userId
     * @param businessName
     * @param couponPrice
     * @return
     */
    List<CouponItemCountVo> getCouponCountItemList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status, @Param("kind") String kind, @Param("couponNo") String couponNo, @Param("userId") String userId, @Param("businessName") String businessName, @Param("couponPrice") Integer couponPrice);


    void updateGetedSize(@Param("couponIdList") List<Long> couponIdList);
}
