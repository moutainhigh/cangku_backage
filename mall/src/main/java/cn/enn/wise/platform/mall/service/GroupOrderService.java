package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GroupOrderBo;
import cn.enn.wise.platform.mall.bean.param.GroupOrderParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * GroupOrder服务类
 *
 * @author jiabaiye
 * @since 2019-09-12
 */
public interface GroupOrderService extends IService<GroupOrderBo> {

    //TODO 接口参数说明
    /**
     * 验证是否可以参加拼团(groupOrderId=0 创建团的判断，groupOrderId ！=0参加拼团判断)
     * @param groupOrderId
     * @return 验证后的编码和信息
     */
    ResponseEntity<Object> isIntoGroupOrder(Long groupOrderId,Long groupPromotionId,Long userId);


    /**
     * 查询是否已经拼团成功
     * @param groupOrderId
     * @return 拼团状态编码和拼团信息
     */
    ResponseEntity<Object> isGroupOrderSuccess(Long groupOrderId);

    /**
     * 根据商品查询正在拼的团信息
     * @param goodsId 商品Id
     * @param companyId 景区Id
     * @return 拼团进行中的列表
     */
    ResponseEntity getGroupOrderList(Long goodsId,Long companyId);

    /**
     * 根据商品查询正在拼的团详细信息
     * @param goodsId
     * @return 拼团进行中的列表
     */
    ResponseEntity getGroupOrderListDetail(Long goodsId);

    /**
     * 参与拼团
     * @param  groupId, openId, orderCode
     * @return
     * @throws Exception
     */
    ResponseEntity insertGroupOrder(final Long groupId,  Long openId,String orderCode)  throws Exception ;
    /**
     * 获取订单列表
     * @param id
     * @return
     */
    GroupOrderDetailVo getGroupOrderById(Long id);

    /**
     * 拼团列表
     * @param param
     * @return
     */
    ResPageInfoVO<List<GroupOrderVo>> listByPage(ReqPageInfoQry<GroupOrderParam> param);

    /**
     * 拼团详情查询
     * @param id
     *      团单id
     * @return
     *      团单详情
     */
    GroupOrderInfoVo groupOrderDetail(Long id);

    /**
     * 查询当前用户是否存在一个团中
     * @param userId 用户id
     * @param goodsId  商品Id
     * @param promotionId  拼团活动Id
     * @exception cn.enn.wise.platform.mall.util.exception.BusinessException
     *               code 3   当前无拼团活动
     * @return
     *          如果已经在团中,返回团单Id id,用户Id userId
     *          如果不在团中,返回null
     */
    Map<String,String> getMemberIfInTheGroup(Long userId,Long goodsId,Long promotionId);


    /**
     * 创建拼团订单
     * @param userId
     *          用户Id
     * @param orderCode
     *          系统订单号
     * @param promotionId
     *          拼团活动Id
     * @return
     * @throws Exception
     */
    OrderQueryVo createGroupOrder(Long userId,String orderCode,Long promotionId) throws Exception;


    /**
     * 当退款成功后，根据支付单号，查询订单id和userId，更新groupOrderItem的 status=2 已退款
     * @param orderNum
     */
    void updateItemStatusById(String orderNum);

}
