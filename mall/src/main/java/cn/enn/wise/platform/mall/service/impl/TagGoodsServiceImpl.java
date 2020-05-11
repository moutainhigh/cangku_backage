package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.TagGoodsBo;
import cn.enn.wise.platform.mall.bean.param.TagGoodsParam;
import cn.enn.wise.platform.mall.mapper.TagGoodsMapper;
import cn.enn.wise.platform.mall.service.TagGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/19
 */
@Service("tagGoodsService")
public class TagGoodsServiceImpl extends ServiceImpl<TagGoodsMapper, TagGoodsBo> implements TagGoodsService {
    @Override
    public void updateGoodsTagList(List<TagGoodsParam> param) {
        for(TagGoodsParam tagGoods:param){

            // 选中状态
            if(tagGoods.getIsChecked()==1){
                //TODO id为0 新增，不为0 不处理
                if(tagGoods.getCheckId() ==0){
                    //id为0 新增
                    TagGoodsBo goodsBo = new TagGoodsBo();
                    goodsBo.setTagId(tagGoods.getTagId());
                    goodsBo.setGoodsId(tagGoods.getBusinessId());
                    goodsBo.setCreateBy(-1L);
                    goodsBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    goodsBo.setUpdateBy(-1L);
                    goodsBo.setCreateTime(new Timestamp(System.currentTimeMillis()));

                    this.saveOrUpdate(goodsBo);
                }
                if(tagGoods.getCheckId() !=0){
                    //不为0 不处理
                }
            }

            // 未选中状态
            if(tagGoods.getIsChecked()==2){

                // TODO id为0 不处理，id不为0 ，删除
                if(tagGoods.getCheckId() ==0){
                    // id为0 不处理
                }
                if(tagGoods.getCheckId() !=0){
                    // id不为0 ，删除
                    this.baseMapper.deleteById(tagGoods.getCheckId() );
                }
            }
        }
    }
}
