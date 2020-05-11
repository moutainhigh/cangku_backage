
package cn.enn.wise.ssop.service.promotions.mapper;


import cn.enn.wise.ssop.api.promotions.dto.request.GroupOrderListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.GroupGoodsListDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.GroupOrderDTO;
import cn.enn.wise.ssop.service.promotions.model.GroupOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author jiabaiye
 * 拼团订单操作
 */
@Mapper
@Repository
public interface GroupOrderMapper extends BaseMapper<GroupOrder> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<GroupOrderDTO> getGroupOrderListByPage(@Param("params") GroupOrderListParam params);


    /**
     * 查询数量
     * @param param
     * @return
     */
    Map<String,Object> getGroupOrderListCounts(@Param("params") GroupOrderListParam param);

    /**
     * 保存拼团订单
     * @param groupOrder
     *          拼团订单实体
     * @throws Exception
     * @return 受影响的行数
     */
    int saveGroupOrder(@Param("groupOrder")GroupOrder groupOrder);

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
     * 根据商品id查询团中正在拼团的信息
     * @param goodsId
     * @return 开团信息
     */
   // List<GroupOrderBo> getGroupOrderByGoodsId(@Param("goodsId") Long goodsId);

    List<GroupOrderDTO> getGroupOrderForList(@Param("goodsId") Long goodsId);

    /**
     * 根据id和有效时间获取拼团信息
     * @param id
     * @return
     */
    GroupOrderDTO getGroupOrderByIdAndAvailableTime(@Param("id") Long id);

    Long getGroupLimitById(@Param("groupActivityId") Long groupActivityId);



    /**
     * 展示拼团活动的商品
     * @return
     */
    List<GroupGoodsListDTO> getGroupGoodsList();


    List<Map> getGroupGoodsHotList();

}

