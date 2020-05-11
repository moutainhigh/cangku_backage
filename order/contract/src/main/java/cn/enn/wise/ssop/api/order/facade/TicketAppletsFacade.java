package cn.enn.wise.ssop.api.order.facade;


import cn.enn.wise.ssop.api.order.dto.request.TicketCancelParam;
import cn.enn.wise.ssop.api.order.dto.request.TicketSearchParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderChangeRecordResponseDto;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.uncs.base.exception.BaseException;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 小程序门票订单API
 * @author jiabaiye
 * @date 2020-04-24
 */
@FeignClient(value = "order-service")
public interface TicketAppletsFacade {


    /**
     * 搜索酒店订单列表
     * @param ticketSearchParam
     * @return
     */
    @GetMapping("/applets/ticket/list")
    @ApiOperation(value = "门票订单搜索列表",notes="门票订单搜索列表")
    R<Map> orderList(TicketSearchParam ticketSearchParam);

    @ApiOperation(value = "一捡多次",notes = "一捡多次")
    @GetMapping("/applets/ticket/oneCheck")
    R<Boolean>  oneCheck(Long orderId);


    @ApiOperation(value = "部分多捡多次",notes = "部分多捡多次")
    @GetMapping("/applets/ticket/moreCheckPart")
    R<Boolean>  moreCheckPart(Long orderId);

    @ApiOperation(value = "全部多捡多次",notes = "全部多捡多次")
    @GetMapping("/applets/ticket/moreCheckAll")
    R<Boolean>  moreCheckAll(Long orderId);

    @ApiOperation(value = "发起退票",notes = "发起退票")
    @GetMapping("/applets/ticket/tuiPiao")
    R<Boolean>  tuiPiao(Long orderId);

    @PostMapping("/applets/ticket/cancel")
    @ApiOperation(value = "退票",notes = "退票")
    R<Boolean> backTicket(@RequestBody TicketCancelParam ticketCancelParam);

    @PostMapping("/applets/ticket/checkBack")
    @ApiOperation(value = "退票审核",notes = "退票审核，成功，checkStatus=1")
    R<Boolean> checkBackTicketPass(@RequestBody TicketCancelParam ticketCancelParam);


    /**
     * 全部当日体验完毕（体验日期3天后【失效日期】）
     * @param orderId
     */
    @GetMapping("/applets/ticket/experienceFinishAfterThreeDays")
    @ApiOperation(value = "全部当日体验完毕（体验日期3天后【失效日期】）",notes = "全部当日体验完毕（体验日期3天后【失效日期】）")
    R<Boolean> experienceFinishAfterThreeDays(Long orderId);

    /**
     * 获取订单状态变更记录/交易进度记录
     * @param orderId
     * @return
     */
    @GetMapping("/applets/ticket/change/list")
    @ApiOperation(value = "订单(状态)变更记录",notes = "获取订单状态变更记录/获取订单交易进度记录")
    R<List<OrderChangeRecordResponseDto>> getChangeRecordList(Long orderId);
}
