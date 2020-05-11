package cn.enn.wise.platform.mall.mapper;


import cn.enn.wise.platform.mall.bean.bo.OrderTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * OrdersTicket Mapper 接口
 * </p>
 *
 * @author baijie
 * @since 2019-05-22
 */
public interface OrdersTicketMapper extends BaseMapper<OrderTicket> {


    /**
     * 更新票的改签状态和改签的票信息
     * @param orderTicket
     */
    void updateChangeTicketInfoById(OrderTicket orderTicket);


    /**
     * 获取需要同步状态的订单
     * @return
     */
    List<Map<String,Object>> getBaiBangDaOrderList();

}
