package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 活动接口
 *
 * @author anhui
 * @since 2019/9/12
 */
public interface GroupPromotionGoodsMapper extends BaseMapper<GroupPromotionGoodsBo> {

    /**
     * 获取活动商品
     * @param goodsId
     * @return
     */
    Map<String,Object> listPromotionGoodsByGoodsId(@Param("goodsId") Long goodsId,@Param("id")Long promotionId,@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 获取当前正常活动的商品信息
     * @return
     */
    List<GroupPromotionGoodsBo> getPromotionGoods();

    /**
     * 查询参加拼团活动的商品信息
     * @param promotionId 活动Id
     * @param goodId 商品Id
     * @return 拼团商品的信息
     */
    GroupPromotionGoodsBo getPromotionGoodByGoodsId(@Param("promotionId") Long promotionId,@Param("goodId") Long goodId);

    /**
     * 查询拼团项目中的最低拼团价
     * @param projectId 项目Id
     * @param promotionId 拼团活动Id
     * @return groupPrice 拼团价
     */
    Map<String,Object> getMinGroupPriceByProject(@Param("projectId") Long projectId,@Param("promotionId") Long promotionId);

    /**
     * 获取拼团商品信息
     * @param goodId 商品Id
     * @param promotionId 拼团活动Id
     * @return 商品信息
     */
    GroupPromotionGoodsBo getPromotionGoodInfo(@Param("goodId")Long goodId,@Param("promotionId") Long promotionId);


}
