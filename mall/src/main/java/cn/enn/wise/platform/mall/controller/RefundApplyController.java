package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.RefundApply;
import cn.enn.wise.platform.mall.bean.param.BoatRefundApplyParam;
import cn.enn.wise.platform.mall.bean.param.RefundApplyParam;
import cn.enn.wise.platform.mall.bean.param.RefundReqParam;
import cn.enn.wise.platform.mall.bean.param.UpdateApprovalsParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.service.RefundApplyService;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.StaffAuthRequired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 13:57
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:申请Refund api
 ******************************************/
@RequestMapping(value = "/refund")
@RestController
@Api(value = "申请退款Controller", tags = "申请退款Controller")
@Slf4j
public class RefundApplyController {

    @Autowired
    private RefundApplyService refundApplyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WithdrawalService withdrawalService;


    @Autowired
    private OrderDao orderDao;

    @Value("${audit.phone}")
    String auditerPhone;


    @PostMapping(value = "/app/apply")
    @ApiOperation(value = "APP申请退款", notes = "APP申请退款")
    @StaffAuthRequired
    public ResponseEntity refundApply(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody RefundApplyParam refundApplyParam) {
        ResponseEntity<Long> resultVo = new ResponseEntity();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isEmpty(refundApplyParam.getOrderId())) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }
        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }
        if (refundApplyParam.getRefundAmount().compareTo(orderInfo.get(0).getShouldPay()) == 1) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("退款金额不能大于订单总金额");
            return resultVo;
        }

        long result = refundApplyService.refundApply(refundApplyParam, null);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }

        return resultVo;
    }

    @PostMapping(value = "/pc/apply")
    @ApiOperation(value = "PC申请退款", notes = "PC申请退款")
    public ResponseEntity refundApplyPC(@RequestBody RefundApplyParam refundApplyParam) {
        ResponseEntity<Long> resultVo = new ResponseEntity();

        if (Strings.isEmpty(refundApplyParam.getOrderId())) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }
        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }
        if (refundApplyParam.getRefundAmount().compareTo(orderInfo.get(0).getShouldPay()) == 1) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("退款金额不能大于订单总金额");
            return resultVo;
        }
        long result = refundApplyService.refundApply(refundApplyParam, null);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }

        return resultVo;
    }

    @PostMapping(value = "/apply")
    @ApiOperation(value = "小程序申请退款", notes = "小程序申请退款")
    @OpenIdAuthRequired
    public ResponseEntity refundApply(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody RefundApplyParam refundApplyParam, @RequestHeader("openId") String openId) {
        ResponseEntity<Long> resultVo = new ResponseEntity();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isEmpty(refundApplyParam.getOrderId())) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }
        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }
        if (refundApplyParam.getRefundAmount().compareTo(orderInfo.get(0).getShouldPay()) == 1) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("退款金额不能大于订单总金额");
            return resultVo;
        }
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApplyParam.getOrderId());
        Integer orderSts = orderTicketVoList.get(0).getStatus();
        if (orderSts!=null && orderTicketVoList.get(0).getStatus().equals(1)){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("已核销订单不可退款,请联系运营人员");
            return resultVo;
        }

        refundApplyParam.setRefundAmount(orderInfo.get(0).getShouldPay());
        long result = refundApplyService.refundApply(refundApplyParam, openId);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }

        return resultVo;
    }


    @PostMapping(value = "/unify/apply")
    @ApiOperation(value = "统一申请退款", notes = "统一申请退款")
    public ResponseEntity unifyRefundApply(@RequestBody RefundApplyParam refundApplyParam) {
        log.info("refund/unify/apply start");
        ResponseEntity<Long> resultVo = new ResponseEntity();

        if (Strings.isEmpty(refundApplyParam.getOrderId())) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }
        if (refundApplyParam.getPlatform()==null){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("退款方缺少");
            return resultVo;
        }
        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }
        if (refundApplyParam.getRefundAmount().compareTo(orderInfo.get(0).getShouldPay()) == 1) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("退款金额不能大于订单总金额");
            return resultVo;
        }

        long result = refundApplyService.unifyRefundApply(refundApplyParam);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }
        log.info("refund/unify/apply end ");
        return resultVo;
    }



    @GetMapping(value = "/detail")
    @OpenIdAuthRequired
    @ApiModelProperty(value = "退款详情", notes = "退款详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity findRefundDetail(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) {
        ResponseEntity<RefundApply> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }
        RefundApply refundApply = refundApplyService.findRefundDetail(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(refundApply);
        return resultVo;
    }


    @GetMapping(value = "/app/detail/v2")
    @ApiOperation(value = "APP退款详情", notes = "APP退款详情")
    public ResponseEntity<ComposeRefundOrderVo> appRefundDetailV2(String orderCode) {
        ResponseEntity<ComposeRefundOrderVo> resultVo = new ResponseEntity();
        if (Strings.isEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }

        ComposeRefundOrderVo composeRefundOrderVo = refundApplyService.appRefundDetailV2(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(composeRefundOrderVo);
        return resultVo;
    }



    @GetMapping(value = "/app/detail")
    @ApiOperation(value = "APP退款详情", notes = "APP退款详情")
    public ResponseEntity<ComposeRefundOrderVo> appRefundDetail(String orderCode) {

        //TODO 下次发版需要去掉
        ResponseEntity<ComposeRefundOrderVo> resultVo = new ResponseEntity();
        if (Strings.isEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }

        ComposeRefundOrderVo composeRefundOrderVo = refundApplyService.appRefundDetail(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(composeRefundOrderVo);
        return resultVo;
    }



    @GetMapping(value = "/laiU8/detail")
    @ApiOperation(value = "laiU8订单退款详情", notes = "laiU8订单退款详情")
    public ResponseEntity<ComposeRefundOrderVo> laiU8RefundDetail(String orderCode) {

         ResponseEntity<ComposeRefundOrderVo> resultVo = new ResponseEntity();
        if (Strings.isEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }

        ComposeRefundOrderVo composeRefundOrderVo = refundApplyService.laiU8RefundDetail(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(composeRefundOrderVo);
        return resultVo;
    }



    @GetMapping(value = "/order/detail")
    @ApiOperation(value = "退款订单详情", notes = "退款订单详情")
    public ResponseEntity<List<OrderRefundVo>> refundOrderDetail(String orderCode) {
        ResponseEntity<List<OrderRefundVo>> resultVo = new ResponseEntity();

        if (Strings.isEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }

        List<OrderRefundVo> orderRefundVoList = refundApplyService.refundOrderDetail(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(orderRefundVoList);
        return resultVo;
    }

    @PostMapping(value = "/list/pc")
    @ApiOperation(value = "退款待审核列表PC", notes = "退款待审核列表PC")
    public ResponseEntity<PageInfo<RefundOrderPc>> findRefundListPc(@RequestBody RefundReqParam refundReqParam) throws Exception{
        ResponseEntity<PageInfo<RefundOrderPc>> resultVo = new ResponseEntity();
        PageHelper.startPage(refundReqParam.getPageNum(), refundReqParam.getPageSize());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!com.google.common.base.Strings.isNullOrEmpty(refundReqParam.getApplyStartTime()) && !com.google.common.base.Strings.isNullOrEmpty(refundReqParam.getApplyEndTime())) {
            Date startDate = sdf.parse(refundReqParam.getApplyEndTime() + " 00:00:00");
            Date endDate = sdf.parse(refundReqParam.getApplyEndTime() + " 23:59:59");
            refundReqParam.setApplyStartTime(sdf.format(startDate));
            refundReqParam.setApplyEndTime(sdf.format(endDate));
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(refundReqParam.getApprovalsStartTime()) && !com.google.common.base.Strings.isNullOrEmpty(refundReqParam.getApprovalsEndTime())) {
            Date startDate = sdf.parse(refundReqParam.getApprovalsStartTime() + " 00:00:00");
            Date endDate = sdf.parse(refundReqParam.getApprovalsEndTime() + " 23:59:59");
            refundReqParam.setApprovalsStartTime(sdf.format(startDate));
            refundReqParam.setApprovalsEndTime(sdf.format(endDate));
        }
        PageInfo<RefundOrderPc> refundListPc = refundApplyService.findRefundListPc(refundReqParam);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(refundListPc);
        return resultVo;
    }

    @GetMapping(value = "/detail/pc")
    @ApiOperation(value = "退款明细PC", notes = "退款明细PC")
    public ResponseEntity<RefundDetailPcVo> findRefundDetailPc(String refundNum) {
        ResponseEntity<RefundDetailPcVo> resultVo = new ResponseEntity();
        RefundDetailPcVo refundDetailPcVo = refundApplyService.findRefundDetailPc(refundNum);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(refundDetailPcVo);
        return resultVo;
    }


    @PostMapping(value = "/batch")
    @ApiOperation(value = "确认退款PC", notes = "确认退款PC")
    public ResponseEntity<Long> batchRefund(@RequestBody String[] id) {
        ResponseEntity<Long> resultVo = new ResponseEntity();
        long result = refundApplyService.batchRefund(id);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(result);
        return resultVo;
    }


    @GetMapping(value = "/send/message")
    @ApiOperation(value = "PC补发短信", notes = "PC补发短信")
    public ResponseEntity<Long> sendRefund(String refundNum) {
        ResponseEntity<Long> resultVo = new ResponseEntity();
        long result = refundApplyService.sendRefund(refundNum);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(result);
        return resultVo;
    }

    @PostMapping(value = "/update/approvals")
    @ApiOperation(value = "h5审批退款申请单", notes = "h5审批退款申请单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "noPassReason", value = "不通过原因", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "approvalsSts", value = "审批状态: 1:待审批 2.审批通过 3.审批不通过", required = true)})
    public ResponseEntity<Long> updateApprovalsSts(@RequestBody UpdateApprovalsParam updateApprovalsParam) {
        ResponseEntity<Long> resultVo = new ResponseEntity();
        boolean isProper = withdrawalService.checkPhoneAuthCode(auditerPhone, updateApprovalsParam.getCode());
        if (!isProper) {
            resultVo.setResult(GeneConstant.PHONE_NO_PADD);
            resultVo.setMessage("验证码错误");
            resultVo.setValue(null);
            return resultVo;
        }
        long result = refundApplyService.updateApprovalsSts(updateApprovalsParam);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(result);
        return resultVo;
    }


    @PostMapping(value = "/unify/update/approvals")
    @ApiOperation(value = "h5统一审批退款申请单", notes = "h5统一审批退款申请单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "noPassReason", value = "不通过原因", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "approvalsSts", value = "审批状态: 1:待审批 2.审批通过 3.审批不通过", required = true)})
    public ResponseEntity<Long> unifyUpdateApprovalsSts(@RequestBody UpdateApprovalsParam updateApprovalsParam) {
        ResponseEntity<Long> resultVo = new ResponseEntity();
        boolean isProper = withdrawalService.checkPhoneAuthCode(auditerPhone, updateApprovalsParam.getCode());
        if (!isProper) {
            resultVo.setResult(GeneConstant.PHONE_NO_PADD);
            resultVo.setMessage("验证码错误,请填写正确验证码");
            resultVo.setValue(null);
            return resultVo;
        }
        long result = refundApplyService.unifyUpdateApprovalsSts(updateApprovalsParam);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(result);
        return resultVo;
    }



    @GetMapping(value = "/boat/order/detail")
    @OpenIdAuthRequired
    @ApiOperation(value = "退款船票订单详情", notes = "退款船票订单详情")
    public ResponseEntity<RefundBoatOrderVo> refundBoatOrderDetail(@Value("#{request.getAttribute('currentUser')}") User user,String orderCode) {
        ResponseEntity<RefundBoatOrderVo> resultVo = new ResponseEntity();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        List<Order> orderInfo = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(orderInfo)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单,请核实");
            return resultVo;
        }

        RefundBoatOrderVo refundBoatOrderVo = refundApplyService.refundBoatOrderDetail(orderCode,user.getId());
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(refundBoatOrderVo);
        return resultVo;
    }


    @PostMapping(value = "/boat/apply")
    @OpenIdAuthRequired
    @ApiOperation(value = "船票申请退票", notes = "船票申请退票")
    public ResponseEntity refundApplyBoat(@Value("#{request.getAttribute('currentUser')}") User user,@RequestBody BoatRefundApplyParam boatRefundApplyParam) {
        ResponseEntity<Long> resultVo = new ResponseEntity();

        if (Strings.isEmpty(boatRefundApplyParam.getOrderCode())){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号缺失");
            return resultVo;
        }

        long applyResult = refundApplyService.refundApplyBoat(boatRefundApplyParam,user.getId());
        resultVo.setResult(applyResult > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (applyResult == 0) {
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(applyResult);
        return resultVo;
    }


    @GetMapping(value = "/phone")
    @ApiOperation(value = "退款客服电话", notes = "退款客服电话")
    public ResponseEntity queryServerPhone() {

        return new ResponseEntity(getPhoneMap());
    }

    private static Map<String, Object> getPhoneMap() {
        Map<String, Object> map = new HashMap<>();
       // map.put("退款客服电话", "0779-6015700");
        map.put("退款客服电话", "0577—57686237");
        return map;
    }

    /*@PostMapping(value = "/update")
    @ApiOperation(value = "申请退款",notes = "申请退款")
    public long  refundApply(String orderId){
        return refundApplyService.updateRefundSts(orderId);
    }*/
}
