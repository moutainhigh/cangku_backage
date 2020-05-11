package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.UserOfCouponBo;
import cn.enn.wise.platform.mall.bean.param.UserOfCouponParam;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券
 * @author 安辉
 * @since 2019-09-12
 */
public interface UserOfCouponService extends IService<UserOfCouponBo> {


    /**
     * 保存
     * @param param
     * @return
     */
    ResponseEntity saveUserOfCoupon(UserOfCouponParam param,Long userId);

    ResponseEntity updateUserOfCoupon(UserOfCouponParam param);

    Integer countCoupon(Long userId,String phone);
}
