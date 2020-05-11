package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.GoodsRelatedBBDBo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 优惠券
 * @author 安辉
 * @since 2019-09-12
 */
public interface GoodsRelatedBBDService extends IService<GoodsRelatedBBDBo> {

    /**
     * 查询商品与佰邦达关联信息
     * @param goodsExtendId 商品extendid
     * @param goodsDay 售卖日期（格式：2019-01-11）
     * @return
     */
    List<GoodsRelatedBBDBo> getGoodsInfo(Long goodsExtendId,String goodsDay);

}
