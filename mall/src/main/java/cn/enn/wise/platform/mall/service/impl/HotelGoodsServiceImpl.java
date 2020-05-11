package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;
import cn.enn.wise.platform.mall.dao.HotelGoodsRepository;
import cn.enn.wise.platform.mall.service.HotelGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 商品业务实现
 *
 * @author baijie
 * @date 2019-09-20
 */
@Service
public class HotelGoodsServiceImpl implements HotelGoodsService {


    @Autowired
    private HotelGoodsRepository goodsRepository;

    @Override
    public void saveGoods(HotelGoods goods) throws Exception {
        System.out.println(goods);

        goodsRepository.save(goods);
//        goodsRepository.saveAll()
    }

    @Override
    public void updateGoods(HotelGoods goods) throws Exception {
        goodsRepository.findById(goods.getId()).ifPresent( g -> {
            g.setDescribe(goods.getDescribe());
            g.setHotelName(g.getHotelName());
            g.setHouseName(g.getHouseName());
            g.setPresent(goods.getPresent());
            g.setRule(goods.getRule());
            g.setStatus(goods.getStatus());
            g.setPrice(goods.getPrice());
            goodsRepository.save(g);
        });

    }

    @Override
    public HotelGoods getById(String  id) {
        Optional<HotelGoods> byId = goodsRepository.findById(id);

        return byId.orElse(null);
    }

    @Override
    public void deleteById(String  id) throws Exception {

        goodsRepository.findById(id).ifPresent(g ->{
            g.setStatus(3);
            goodsRepository.save(g);
        });
    }

    @Override
    public List<HotelGoods> goodsList() {

        return goodsRepository.getAllByStatus(1);
    }


}
