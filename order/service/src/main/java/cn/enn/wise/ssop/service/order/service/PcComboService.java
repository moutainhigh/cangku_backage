package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.api.order.dto.request.PcOrderGoodsParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderListDTO;
import cn.enn.wise.ssop.service.order.model.OrderGoods;

import java.util.List;

public interface PcComboService {


    OrderListDTO getOrderGoodsList(PcOrderGoodsParam pcOrderGoodsParam);

    List<OrderGoods> getPcOrderGoodsDetails(Long orderId);


}
