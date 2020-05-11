package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;

import java.util.List;

/**
 * 商品处理业务层
 *
 * @author baijie
 * @date 2019-09-20
 */
public interface HotelGoodsService {

    /**
     * 保存商品
     * @param goods
     *          商品实体
     * @throws Exception
     */
    void saveGoods(HotelGoods goods) throws Exception;

    /**
     * 更新商品
     * @param goods
     *         商品实体
     * @throws Exception
     */
    void updateGoods(HotelGoods goods) throws Exception;

    /**
     * 根据商品Id获取商品详情
     * @param id
     * @return
     */
    HotelGoods getById(String id);

    /**
     * 删除一个商品
     * @param id
     * @throws Exception
     */
    void deleteById(String id) throws Exception;

    /**
     * 获取商品列表
     * @return
     */
    List<HotelGoods> goodsList();
}
