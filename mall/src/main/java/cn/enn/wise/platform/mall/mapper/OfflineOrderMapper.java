package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.vo.OrderResVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 离线订单持久化层
 *
 * @author baijie
 * @date 2019-08-06
 */
@Mapper
public interface OfflineOrderMapper {

    /**
     * 根据订单id查询离线订单详情
     * @param id
     * @return
     */
    OrderResVo selectOfflineOrderById(@Param("id")Long id);

    /**
     * 更新离线订单
     * @param orders
     * @return
     */
    int updateOfflineOrder(Orders orders);

    /**
     * 删除离线订单
     * @param id
     * @return
     */
    int deleteOfflineOrderById(Long id);

}
