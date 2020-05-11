package cn.enn.wise.ssop.api.order.facade;


import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsResponseDto;
import cn.enn.wise.ssop.api.order.dto.response.TradeLogDTO;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 小程序订单中心API
 * @author jiabaiye
 * @date 2020-04-24
 */
@FeignClient(value = "order-service")
public interface OrderAppletsFacade {

    /**
     * 获取订单列表
     * @param orderStatus
     * @return
     */
    @ApiOperation(value = "订单列表",notes="订单列表")
    @GetMapping("/applet/order/list/All")
    R<List<OrderGoodsResponseDto>> getOrderList(Integer orderStatus);

    @ApiOperation(value = "根据用户id获取用户订单交易记录",notes = "根据用户id获取用户订单交易记录")
    @GetMapping("/applet/user/order")
    R<QueryData<TradeLogDTO>> getUserOrder(@RequestParam("userId") Long userId,@RequestParam("pageSize") Long pageSize,@RequestParam("pageNo") Long pageNo);


}
