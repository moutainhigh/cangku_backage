package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.GoodsCouponPromotionBo;
import cn.enn.wise.platform.mall.bean.param.AddGoodsCouponPromotionParam;
import cn.enn.wise.platform.mall.bean.param.PromotionInvalidParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsCouponPromotionVo;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
 *
 * 活动
 * @author 安辉
 * @since 2019-09-12
 */
public interface GoodsCouponPromotionService extends IService<GoodsCouponPromotionBo> {




    ResponseEntity<GoodsCouponPromotionVo> updateCouponPromotion(AddGoodsCouponPromotionParam param) throws ParseException;

    ResponseEntity<GoodsCouponPromotionBo> saveGoodsCouponPromotion(AddGoodsCouponPromotionParam param);

    ResponseEntity<GoodsCouponPromotionVo> getCouponPromotionDetail(Long id);

    /**
     * 添加活动
     * @param param
     * @return
     */
    ResponseEntity<GoodsCouponPromotionVo> addPromotion(AddGoodsCouponPromotionParam param) throws ParseException;

    /**
     * 获取详情
     * @param id
     * @return
     */
    ResponseEntity<GoodsCouponPromotionVo> getPromotionDetailById(String id);

    /**
     * 失效
     * @param param
     * @return
     */
    ResponseEntity invalid(PromotionInvalidParam param);

}
