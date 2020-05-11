package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.PromotionAndGoodsBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.PromotionAndGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/18
 */
public interface PromotionAndGoodsMapper  extends BaseMapper<PromotionAndGoodsBo> {


    /**
     * 根据活动ID获取绑定的所有商品
     * @param ids
     * @return
     */
    List<PromotionAndGoods> getPromotionListByPromotionId(List<Long> ids);

}
