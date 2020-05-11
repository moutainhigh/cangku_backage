package cn.enn.wise.ssop.api.order.facade;



import cn.enn.wise.ssop.api.order.dto.request.DefaultOrderSaveParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderDetailListParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderDetailDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderDetailListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderSaveDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单服务接口暴露
 * @author mrwhite
 * @date 2019-12-12
 */
@FeignClient(value = "order-service")
public interface OrderFacade {

    /**
     * 创建订单
     * @param defaultOrderSaveParam 订单创建参数
     * @return
     */
    @PostMapping("/order/save")
    @ApiOperation(value = "创建待支付订单",notes = "该接口为使用UNCS系统创建待支付订单,在已经接入UNCS商品系统的应用端，可以使用此接口创建订单",position = 1)
    R<OrderSaveDTO> saveOrder(@RequestBody DefaultOrderSaveParam defaultOrderSaveParam);

    /**
     * 查看订单详情
     * @param orderNo 订单号
     * @param partnerId 合作伙伴Id
     * @return
     */
    @GetMapping("/order/detail")
    @ApiOperation(value = "获取订单详情",notes="该接口可以获取从UNCS下单的订单详情信息，包含订单的状态，订单的联系人信息、以及订单的支付时间等详情信息",position = 3)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderNo",value = "订单号")
    })
    R<OrderDetailDTO> orderDetail(@RequestParam("orderNo") String orderNo, @RequestHeader("uncs_partner_id") Long partnerId);

    /**
     * 更新订单为已支付状态
     * @param orderNo 订单号
     * @param payOrderNo 订单支付流水
     * @return
     */
    @PostMapping("/order/pay")
    @ApiOperation(value = "更新订单为已支付状态",notes = "该接口在支付成功后，使用订单时，更新订单状态为待使用")
    R<Boolean> orderToBeUsed(@RequestParam("orderNo") String orderNo, @RequestParam("payOrderNo") String payOrderNo);

    /**
     * 更新订单为已使用状态
     * @param orderNo
     * @param partnerId
     * @return
     */
    @PostMapping("/order/check")
    @ApiOperation(value = "更新订单为已使用状态",notes = "合作伙伴更新订单为已使用状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderNo",value = "订单号",required = true),
            @ApiImplicitParam(name = "partnerId",value = "合作伙伴Id",required = true)
    })
     R<Boolean> orderUsed(@RequestParam("orderNo") Long orderNo, @RequestHeader("uncs_partner_id") Long partnerId);

    /**
     * 用户取消订单
     * @param orderNo  订单号
     * @param partnerId 合作伙伴ID
     * @return
     */
    @PostMapping("/order/cancel")
    @ApiOperation(value = "合作伙伴取消订单",notes = "合作伙伴需要更新订单状态为已取消",position = 9)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderNo",value = "订单号",required = true),
            @ApiImplicitParam(name = "partnerId",value = "合作伙伴Id",required = true)
    })
    R<Boolean> orderPartnerCancel(@RequestParam("orderNo") String orderNo, @RequestHeader("uncs_partner_id") Long partnerId);

    /**
     * 查询简单订单信息列表
     * @param orderDetailListParam
     */
    @PostMapping("/order/getOrderDetailList")
    @ApiOperation(value = "查询简单订单信息列表",notes = "查询简单订单信息列表")
    public R<List<OrderDetailListDTO>> getOrderDetailList(@RequestBody OrderDetailListParam orderDetailListParam);
}
