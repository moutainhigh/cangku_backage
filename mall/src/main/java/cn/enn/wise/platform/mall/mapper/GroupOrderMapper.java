package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.GroupOrderBo;
import cn.enn.wise.platform.mall.bean.param.GroupOrderParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsAndGroupPromotionInfoVo;
import cn.enn.wise.platform.mall.bean.vo.GroupOrderInfoVo;
import cn.enn.wise.platform.mall.bean.vo.GroupOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 * <p>
 * GroupOrder Mapper 接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-09-12
 */
public interface GroupOrderMapper extends BaseMapper<GroupOrderBo> {



    /**
     * 根据id和有效时间获取拼团信息
     * @param id
     * @return
     */
    GroupOrderVo getGroupOrderByIdAndAvailableTime(@Param("id") Long id);
    /**
     * 根据id获取拼团信息
     * @param id
     * @return
     */
    GroupOrderBo getGroupOrderById(@Param("id") Long id);

    /**
     * 根据商品id查询团中正在拼团的信息
     * @param goodsId
     * @return 开团信息
     */
    List<GroupOrderBo> getGroupOrderByGoodsId(@Param("goodsId") Long goodsId);


    List<GroupOrderVo> getGroupOrderForList(@Param("goodsId") Long goodsId);

    /**
     * 根据拼团到期时间以及拼团状态查询到期未成团的拼团信息
     * @return 团信息
     */
    List<GroupOrderBo> getGroupOrderListByTime();

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<GroupOrderVo> getGroupOrderListByPage(@Param("params") GroupOrderParam params);


    /**
     * 查询数量
     * @param param
     * @return
     */
    Map<String,Object> getGroupOrderListCounts(@Param("params") GroupOrderParam param);

    /**
     * 获取小程序端拼团详情数据
     * @param id
     *          团单Id
     * @return
     *          拼团详情VO
     */
    GroupOrderInfoVo getAppletsGroupOrderInfoVo(@Param("id") Long id);


    /**
     * 查询当前用户是否存在一个团中
     * @param userId
     *          用户id
     * @param groupPromotionId
     *          当前拼团活动Id
     * @param goodsId
     *          商品Id
     * @return
     *          如果已经在团中,返回团单Id,userId
     *          如果不在团中,返回null
     */
    Map<String,String> getMemberIfInTheGroup(@Param("userId")Long userId,
                                             @Param("groupPromotionId") Long groupPromotionId,
                                             @Param("goodsId") Long goodsId);

    /**
     * 根据商品时段Id查询商品信息,活动信息,规则信息
     * @param extendId
     *         时段Id
     * @param promotionId
     *          活动Id
     * @param type
     *          1 拼团规则  2 优惠规则
     * @return
     */
    GoodsAndGroupPromotionInfoVo getGoodsAndGroupPromotionInfoByExtendId(@Param("extendId")Long extendId,
                                                                         @Param("promotionId") Long promotionId,
                                                                         @Param("type") Integer type);

    /**
     * 保存拼团订单
     * @param groupOrderBo
     *          拼团订单实体
     * @throws Exception
     * @return 受影响的行数
     */
    int saveGroupOrder(GroupOrderBo groupOrderBo) throws Exception;

    Long getGroupLimitById(@Param("promotionId") Long promotionId);

    /**
     * 查询用户购买的已经拼团成功的数量和限制购买的次数
     * @param promotionId 活动ID
     * @param userId 用户Id
     * @return  Map<String,Integer></>
     *          key amount 已经购买数量
     *          key groupLimit 限制购买的次数
     */
    Map<String,Object> getBuyAmount(@Param("promotionId") Long promotionId,@Param("userId") Long userId);

    /**
     * 修改拼团状态为失败
     * @param id
     * @param status
     */
    void updateStatusById(@Param("id") String id,@Param("status") Integer status);
}
