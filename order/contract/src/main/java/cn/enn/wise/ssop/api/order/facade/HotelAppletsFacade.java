package cn.enn.wise.ssop.api.order.facade;


import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 小程序酒店订单API
 * @author jiabaiye
 * @date 2020-04-24
 */
@FeignClient(value = "order-service")
public interface HotelAppletsFacade {

    @ApiOperation(value = "订单未支付主动取消订单",notes = "订单未支付主动取消订单")
    @GetMapping("/applets/hotel/cancel")
    R<Boolean> cancel(Long orderId);

    /**
     * 游客支付订单，等待运营方房源二次确认
     * @param orderId
     * @return
     */
    @ApiOperation(value = "游客支付订单，等待运营方房源二次确认",notes = "游客支付订单，等待运营方房源二次确认")
    @GetMapping("/applets/hotel/confirm")
    R<Boolean> sendConfirm(Long orderId);

    /**
     * 房源二次确认成功
     * @param orderId
     * @return
     */
    @ApiOperation(value = "房源二次确认成功",notes = "房源二次确认成功")
    @GetMapping("/applets/hotel/confirmSuccess")
    R<Boolean> sendConfirmSuccess(Long orderId);

    /**
     * 房源二次确认失败，运营方发起退款申请
     * @param orderId
     * @return
     */
    @ApiOperation(value = "房源二次确认失败，运营方发起退款申请",notes = "房源二次确认失败，运营方发起退款申请")
    @GetMapping("/applets/hotel/sendConfirmFailure")
    R<Boolean> sendConfirmFailure(Long orderId);

    /**
     * 财务审核通过退款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务审核通过退款",notes = "财务审核通过退款")
    @GetMapping("/applets/hotel/checkPassRefund")
    R<Boolean> checkPassRefund(Long orderId);

    /**
     * 财务审核拒绝退款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务审核拒绝退款",notes = "财务审核拒绝退款")
    @GetMapping("/applets/hotel/checkRejectRefund")
    R<Boolean> checkRejectRefund(Long orderId);

    /**
     *  运营方退订审核成功，等待财务确认拨款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "运营方退订审核成功，等待财务确认拨款",notes = "运营方退订审核成功，等待财务确认拨款")
    @GetMapping("/applets/hotel/cancelAndCheck")
    R<Boolean> cancelAndCheck(Long orderId);

    /**
     *  财务确认拨款
     * @param orderId
     * @return
     */
    @ApiOperation(value = "财务确认拨款",notes = "财务确认拨款")
    @GetMapping("/applets/hotel/confirmWithdraw")
    R<Boolean> confirmWithdraw(Long orderId);

    /*  *//**
     *  财务拒绝拨款
     * @param orderId
     * @return
     *//*
    @GetMapping("/rejectWithdraw")
    public R<Boolean> rejectWithdraw(Long orderId);*/

    /**
     *  运营方退订审核失败
     * @param orderId
     * @return
     */
    @ApiOperation(value = "运营方退订审核失败",notes = "运营方退订审核失败")
    @GetMapping("/applets/hotel/backAndCheckFailure")
    R<Boolean> backAndCheckFailure(Long orderId);

    /**
     *  游客前往酒店办理入住，进行入住核验
     * @param orderId
     * @return
     */
    @ApiOperation(value = "游客前往酒店办理入住，进行入住核验",notes = "游客前往酒店办理入住，进行入住核验")
    @GetMapping("/applets/hotel/ruZhuCheck")
    R<Boolean> ruZhuCheck(Long orderId);
}
