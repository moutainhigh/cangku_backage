package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.GoodsPackageItemBo;
import cn.enn.wise.platform.mall.bean.param.GoodsPackageItemsParams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Goods Mapper 接口
 * </p>
 *
 * @author 安辉
 * @since 2019-05-22
 */
public interface GoodsPackageItemMapper extends BaseMapper<GoodsPackageItemBo> {

    List<GoodsPackageItemsParams> selectGoodsPackageItemByGoodsId(Long goodsId);

    List<GoodsPackageItemsParams> selectItemByGoodsId(Long goodsId);

    List<GoodsPackageItemsParams> selectGoodsPackageItemsByGoodsExtendId(@Param("goodsExtendId") Long goodsExtendId);
}
