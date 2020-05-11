package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Mapper
@Repository
public interface CouponMapper extends BaseMapper<Coupon> {
}
