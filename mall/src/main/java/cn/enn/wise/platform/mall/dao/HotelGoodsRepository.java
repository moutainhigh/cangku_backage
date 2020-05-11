package cn.enn.wise.platform.mall.dao;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * 商品属性持久化层
 *
 * @author baijie
 * @date 2019-09-20
 */
public interface HotelGoodsRepository extends MongoRepository<HotelGoods,String > {


    /**
     * 获取商品列表
     * @param status
     * @return
     */
    List<HotelGoods>  getAllByStatus(Integer status);


}
