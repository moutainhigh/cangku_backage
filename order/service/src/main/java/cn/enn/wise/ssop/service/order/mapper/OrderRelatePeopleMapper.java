
package cn.enn.wise.ssop.service.order.mapper;


import cn.enn.wise.ssop.service.order.model.OrderRelatePeople;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 订单数据库处理层
 * @author mrwhite
 * @date 2019-12-11
 */
@Mapper
@Repository
public interface OrderRelatePeopleMapper extends BaseMapper<OrderRelatePeople> {


    /**
     * 批量添加子订单数据
     * @param orderChildren
     */
    void batchInsert(List<OrderRelatePeople> orderChildren);

    List<OrderRelatePeople> getRelatePeopleListByOrderId(Long orderId);

    Integer getTicketNumber(@Param("phone")String phone,
                                   @Param("userCard")String userCard,
                                   @Param("start")String start,
                                   @Param("end") String end);

    Integer getOrderNumber(@Param("phone")String phone,
                                  @Param("userCard")String userCard,
                                  @Param("start")String start,
                                  @Param("end") String end);

}

