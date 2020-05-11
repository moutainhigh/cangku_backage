
package cn.enn.wise.ssop.service.order.mapper;


import cn.enn.wise.ssop.api.order.dto.response.TradeDTO;
import cn.enn.wise.ssop.service.order.model.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 订单数据库处理层
 * @author mrwhite
 * @date 2019-12-11
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Orders> {


    /**
     * 更新订单
     * @param order 更新订单实体
     * @throws Exception 更新失败
     * @return 更新条数
     */
    int updateOrder(Orders order);

    int updateOrderStatus(Orders order);

    List<TradeDTO> getTradeDTO(@Param("userId") Long userId,@Param("pageSize") Long pageSize,@Param("offset") Long offset);

    Map getTotalCountInfo(@Param("userId") Long userId);
}

