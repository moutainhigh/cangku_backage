package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.autotable.OrderStateHistory;
import cn.enn.wise.platform.mall.bean.param.OrderAllAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-01-15 17:23
 **/
public interface OrderStateHistoryMapper extends BaseMapper<OrderStateHistory> {

    /**
     * 根据ID 去订单表查询
     * @param id
     * @return
     */
    OrderAllAttribute selectOrdersById(@Param("id")Long id);
}
