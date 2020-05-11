package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对接佰邦达商品服务
 * @author jiabaiye
 * @since 2019/9/12
 */
@Service
public class GoodsRelatedBBDServiceImpl extends ServiceImpl<GoodsRelatedBBDMapper, GoodsRelatedBBDBo> implements GoodsRelatedBBDService {

    @Autowired
    private GoodsRelatedBBDMapper goodsRelatedBBDMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 查询与佰邦达商品关联信息
     * @param goodsExtendId 商品extendid
     * @param goodsDay 售卖日期（格式：2019-01-11）
     * @return
     */
    @Override
    public List<GoodsRelatedBBDBo> getGoodsInfo(Long goodsExtendId,String goodsDay){

        Goods goods = goodsMapper.getGoodsByExtendId(goodsExtendId);
        if(goods.getIsByPeriodOperation()==1){
            List<GoodsRelatedBBDBo> list = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("goods_id",goods.getId()));
            return list;
        }else{
            List<GoodsRelatedBBDBo> list = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("goods_id",goodsExtendId).eq("goods_day",goodsDay));
            return list;
        }

//        if(goods.getIsByPeriodOperation()==2){
//            List<GoodsRelatedBBDBo> list = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("goods_id",goodsExtendId));
//            return list;
//        }else{
//            List<GoodsRelatedBBDBo> list = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("goods_id",goodsExtendId).eq("goods_day",goodsDay));
//            return list;
//        }
    }
}
