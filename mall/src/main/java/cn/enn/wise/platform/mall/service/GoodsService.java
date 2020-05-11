package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Goods服务类
 *
 * @author caiyt
 * @since 2019-05-22
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 根据条件查询商品列表
     *
     * @param goodsPageQry 查询商品列表的分页条件，含当前页、每页的数据量、具体筛选数据的条件
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GoodsResVO>>> listGoods(ReqPageInfoQry<GoodsReqParam> goodsPageQry);

    /**
     * 根据商品ID查询商品详情
     *
     * @param id 商品ID
     * @return 商品详情信息
     * @throws Exception
     */
    ResponseEntity<GoodsResVO> getGoodById(long id);

    /**
     * 根据商品ID查询商品详情
     *
     * @param id 商品ID
     * @return 商品详情信息
     * @throws Exception
     */
    ResponseEntity<AddGoodsPackageParams> getGoodPackageById(long id);

    /**
     * 更新商品信息
     *
     * @param goodsReqParam 商品信息
     * @param staffVo       管理员信息
     * @return 更新结果
     * @throws Exception
     */
    ResponseEntity updateGoods(GoodsReqParam goodsReqParam, SystemStaffVo staffVo,String token);

    /**
     * 更新楠溪江价格
     * @param goodsTmp
     * @return
     */
    ResponseEntity updateGoodsPrice(Goods goodsTmp);


    /**
     * 判断商品名称是否已经存在
     *
     * @param goodsName 商品名称
     * @return true 商品已存在 false 商品名称不存在
     */
    boolean isGoodsNameExist(String goodsName);

    /**
     * 保存商品信息
     *
     * @param goodsReqParam
     * @param staffVo
     * @return
     */
    ResponseEntity saveGoods(GoodsReqParam goodsReqParam, SystemStaffVo staffVo);

    /**
     * 小程序获取热气球票列表
     *
     * @param goodsReqQry 具体筛选数据的条件
     * @return
     */
    Map<String, Object> getGoodsList(GoodsReqParam goodsReqQry);

    /**
     * 批量上下架商品
     *
     * @param goodsIds
     * @param status
     * @param staffVo
     * @return
     */
    ResponseEntity batchSwitchGoodsStatus(List<Long> goodsIds, byte status, SystemStaffVo staffVo);

    /**
     * 批量删除商品（逻辑删除）
     *
     * @param goodsIds
     * @param staffVo
     * @return
     */
    ResponseEntity batchDeleteGoods(List<Long> goodsIds, SystemStaffVo staffVo);


    /**
     * 设置时段运营
     *
     * @param goodsId
     * @param goodsExtendReqParamList
     * @param staffVo
     * @return
     */
    ResponseEntity goodsExtendOperation(long goodsId, List<GoodsExtendReqParam> goodsExtendReqParamList, SystemStaffVo staffVo);

    /**
     * 根据商品ID查询商品的运营时段信息
     *
     * @param goodsId
     * @return
     */
    ResponseEntity<List<GoodsExtendOperationResVo>> getGoodsExtendOperationInfo(long goodsId);

    /**
     * 根据项目获取商品
     * @param id
     * @return
     */
    List<Goods> getGoodsByGoodsProject(Long id);

    /**
     * 根据商品Id和运营时间查看所有时段
     * @param
     * @return
     */
    Map<String,Object>  getGoodsPeriodIdByGoodsIdAndOperationDate(Long goodsId,String date,Integer servicePlaceId);

    /**
     * 根据项目id获取服务路线和服务地点
     * @param id
     * @return
     */
    GoodsProjectResVo getServicePlaceAndLineById(Long id);

    /**
     * 根据项目获取商品和服务场地
     * @param projectId
     * @return
     */
    Map<String,Object>  getGoodsAndServicePlaceIdByProject(Long projectId);

    /**
     * 获取商品详情
     * @param param
     * @return
     */
    List<GoodsPackageItemVo> getGoodsProjectId(Long[] param);

    /**
     * 保存套装
     * @param addGoodsPackageParams
     * @param staffVo
     * @return
     */
    ResponseEntity savePackage(AddGoodsPackageParams addGoodsPackageParams, SystemStaffVo staffVo);

    /**
     * 更新套装
     * @param goodsReqParam
     * @param staffVo
     * @return
     */
    ResponseEntity updatePackage(AddGoodsPackageParams goodsReqParam, SystemStaffVo staffVo);

    void autoPiaoFuTongGoods();

    /**
     * 更新商品排序字段
     * @param id
     * @param orderBy
     */
    void updateGoodsOrderBy(Long id ,Integer orderBy);
}
