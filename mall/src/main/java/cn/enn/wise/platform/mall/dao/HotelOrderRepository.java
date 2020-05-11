package cn.enn.wise.platform.mall.dao;

import cn.enn.wise.platform.mall.bean.bo.HotelOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 酒店订单持久化层
 *
 * @author baijie
 * @date 2019-09-20
 */
public interface HotelOrderRepository extends MongoRepository<HotelOrder,String> {

    /**
     * 根据用户Id查询订单列表
     * @param userId
     *          用户Id
     * @return
     *      订单列表
     */
    List<HotelOrder> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 根据用户id和订单号查询订单详情
     * @param orderCode
     *          订单号
     * @param userId
     *          用户id
     * @return
     *      订单详情
     */
    HotelOrder findHotelOrderByOrderCodeAndUserId(String orderCode,Long userId);

    /**
     * 根据用户id和订单id查询订单详情
     * @param id
     *          订单id
     * @param userId
     *          用户id
     * @return
     *      订单详情
     */
    HotelOrder findHotelOrderByIdAndUserId(String id,Long userId);

    /**
     * 查询未支付订单
     * @param orderCode
     *          订单号
     * @param userId
     *          用户id
     * @param payStatus
     *          支付状态
     * @param state
     *          订单状态
     * @return
     */
    HotelOrder findHotelOrderByOrderCodeAndUserIdAndPayStatusAndState(String orderCode,Long userId,Integer payStatus,Integer state);

    /**
     * 根据微信订单号查询订单
     * @param batCode
     *          微信订单号
     * @return
     *          订单号
     */
    HotelOrder findByBatCode(String batCode);



}
