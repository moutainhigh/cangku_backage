package cn.enn.wise.ssop.service.order.handler;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderHotelMapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.OrderHotel;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.uncs.base.pojo.TableBase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anhui257@63.com
 *
 */
@Component
public class ExperienceProjectHandler extends OrderHandler{

    @Autowired
    private OrderHotelMapper orderHotelMapper;

    @Override
    public String generatorOrderNo() {
        return "EP" + System.currentTimeMillis() + (random.nextInt(900) + 100);
    }

    @Override
    public Integer saveOrder(BaseOrderParam baseOrder) {
        return null;
    }

    @Override
    public TableBase handle(BaseOrderParam orderParam, Orders orders, OrderGoods orderGoods) {
         return null;
    }
}
