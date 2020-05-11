package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.autotable.GoodsPackageItem;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author baijie
 * @date 2019-07-31
 */
public interface WzdGoodsAppletsMapper  {

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
     * 不分时段商品信息查询
     * @param goodsId
     * @return
     */
    GoodsApiResVO getGoodsInfoById(@Param("goodsId") Long goodsId);

    /**
     * 不分时段商品列表查询
     * @return
     */
    List<GoodsApiResVO> getGoodsListIsByPeriodOperation(@Param("projectId") Long projectId,@Param("goodsId") Long goodsId);


    /**
     * 根据tag获取商品，项目的信息
     * @param tagIdList 标签Id
     * @return 商品基本信息集合
     */
    List<GoodsProjectVo> getByTag(List<Long> tagIdList);


    /**
     * 套餐商品获取商品详情
     * @param goodsId 商品Id
     * @return 套餐商品详情
     */
    PackageGoodVO getPackageGoodsInfoById(@Param("goodsId") Long goodsId);

    /**
     * 查询商品分时段售卖的最低价
     * @param goodsId
     * @return
     */
    MinPriceVo getMinPriceByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * 根据套装Id查询套装商品详情
     * @param packageId 套装Id
     * @return 套装商品列表
     */
    List<GoodsPackageItem> getPackageGoodsByPackageId(@Param("packageId")Long packageId);


    /**
     * 根据skuId 获取商品信息
     * @param extendId skuId
     * @return 商品和项目的名称
     */
    Map<String, Object > getGoodsAndProjectByExtendId(@Param("extendId")Long extendId);


    /**
     * 查询船票信息
     * @param lineFrom 出发点
     * @param lineTo 到达点
     * @param lineDate 出发日期
     * @param cabinName 船舱名称
     * @param timespan 开船时间
     * @param shipName 开船时间
     * @return
     */
    ShipTicketInfo getShipTicketInfo(@Param("lineFrom") String lineFrom,
                                     @Param("lineTo") String lineTo,
                                     @Param("lineDate") String lineDate,
                                     @Param("cabinName") String cabinName,
                                     @Param("timespan") String timespan,
                                     @Param("shipName") String shipName);
}
