package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * Goods Mapper 接口
 * </p>
 *
 * @author caiyt
 * @since 2019-05-22
 */
public interface GoodsMapper extends BaseMapper<Goods> {


    /**
     * 西藏小程序获取商品列表
     * @param reqQry
     * @return
     */
    List<GoodsApiResVO> getGoodsList (GoodsReqParam reqQry);

    /**
     * 根据条件获取商品信息
     * @param reqQry
     * @return
     */
    GoodsApiResVO getGoodsById(GoodsReqParam reqQry);


    /**
     * 根据项目获取绑定的商品
     * @param id
     * @return
     */
    List<Goods> getGoodsByGoodsProject(@Param("id") Long id);

    /**
     * 根据项目获取绑定的商品
     * @param projectId
     * @return
     */
    List<GoodsApiResVO> getGoodsByProject(@Param("projectId")Long projectId);

    /**
     * 根据商品Id和运营时间获取商品时段信息
     * @return
     */
    List<GoodsExtendVo> getGoodsPeriodIdByGoodsIdAndOperationDate(@Param("goodsId")Long goodsId,@Param("operationDate") String operationDate,@Param("servicePlaceId") Integer servicePlaceId);


    /**
     * 根据项目id获取服务地点和服务线路
     * @param id
     * @return
     */
    List<GoodsProjectResVo> getLineAndServicePlaceByProjectId(@Param("id") Long id);

    /**
     * 根据Id和状态查询项目编号
     * @param id
     * @return
     */
    List<Long> selectProjectIdByIdAndStatus(List<Long> id);


    /**
     * 查询拼团商品信息
     * @param goodId 商品Id
     * @return 套装商品信息
     */
    GroupGoodsInfoVo getGroupInfoVoByGoodId(@Param("goodId")Long goodId);

    /**
     * 根据商品id查询商品基础价格的总和
     * @param list
     * @return
     */
    Map<String,Object> getSumBasePrice(List<Long> list);

    /**
     * 根据商品skuId获取商品信息
     * @param goodsExtendId
     * @return
     */
    GoodsExtendInfoVO getGoodsExtendInfoById(@Param("id") Long goodsExtendId);


    Goods getGoodsByExtendId(@Param("id") Long goodsExtendId);

    /**
     * 更新商品的orderby字段
     * @param id
     * @param orderBy
     */
    void updateGoodsOrderBy(@Param("id") Long id,@Param("orderBy")Integer orderBy);

}
