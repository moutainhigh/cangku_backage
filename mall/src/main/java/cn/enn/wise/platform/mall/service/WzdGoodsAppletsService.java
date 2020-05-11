package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.TicketInfoQueryParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsApiResVO;
import cn.enn.wise.platform.mall.bean.vo.GoodsProjectVo;
import cn.enn.wise.platform.mall.bean.vo.ShipTicketInfo;
import cn.enn.wise.platform.mall.bean.vo.TicketResVo;

import java.util.List;
import java.util.Map;

/**
 * 涠洲岛商品服务
 *
 * @author baijie
 * @date 2019-07-31
 */
public interface WzdGoodsAppletsService {
    /**
     * 小程序获取热气球票列表
     *
     * @param goodsReqQry 具体筛选数据的条件
     * @return
     */
    Map<String, Object> getGoodsList(GoodsReqParam goodsReqQry);

    /**
     * 获取商品详情
     * @param goodsReqParam
     * @return
     */
    TicketResVo getGoodInfoById(GoodsReqParam goodsReqParam);


    /**
     * 获取拼团预定信息
     * @param goodsReqParam
     * @return
     */
    TicketResVo getGroupInfoById(GoodsReqParam goodsReqParam);
    /**
     * 获取体验时间
     * @param goodsReqParam 查询参数
     * @return 体验时间
     */
    GoodsApiResVO getExperienceTime(GoodsReqParam goodsReqParam);

    /**
     * 根据商品Id获取商品详情
     * @param goodsId
     *          商品Id
     * @return
     *          商品实体
     */
    Goods getGoodsInfoById(Long goodsId);

    /**
     * 小程序首页根据tagId获取商品和项目列表
     * @param goodsProjectParam 参数列表Id
     * @return
     */
    List<GoodsProjectVo> listByTag(GoodsProjectParam goodsProjectParam);

    /**
     * 小程序首页根据tag获取项目列表
     * @param goodsProjectParam
     * @return
     */
    List<GoodsProjectVo> projectListByTag(GoodsProjectParam goodsProjectParam);

    /**
     * 初始化项目入口
     * @param goodsProjectParam
     */
    void initEntrance(GoodsProjectParam goodsProjectParam);

    /**
     * 获取项目体验类型
     * @param projectId 项目ID
     * @param distributePhone 分销商手机号
     * @return 体验类型列表
     */
    List<GoodsApiResVO> getExperienceType(Long projectId,String distributePhone);

    /**
     * 预下单获取票信息
     * @param ticketInfoQueryParam 船票查询参数
     * @return 预下单票信息
     */
    ShipTicketInfo getShipTicketInfo(TicketInfoQueryParam ticketInfoQueryParam);

}
