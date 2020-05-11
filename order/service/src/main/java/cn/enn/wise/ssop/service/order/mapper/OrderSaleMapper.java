
package cn.enn.wise.ssop.service.order.mapper;


import cn.enn.wise.ssop.service.order.model.OrderSale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 订单数据库处理层
 * @author mrwhite
 * @date 2019-12-11
 */
@Mapper
public interface OrderSaleMapper extends BaseMapper<OrderSale> {

    void batchInsert(List<OrderSale> orderSaleList);
}

