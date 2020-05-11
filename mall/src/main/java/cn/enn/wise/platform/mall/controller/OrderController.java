package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.autotable.DistributeBindUser;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrder;
import cn.enn.wise.platform.mall.bean.bo.autotable.GroupOrderItem;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 18:28
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:订单管理Api
 ******************************************/
@Api(value = "订单管理Controller", tags = {"订单管理Controller"})
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OrderController.class);

    private static final int REFUND_COMPLETED_ORDER = 11;

    private static final int NO_USE_ORDER = 2;

    private static final int CHECK_ORDER = 1;

    private static final int USE_ORDER = 3;

    private static final int FINISH_ORDER = 9;

    private static final int GROUP_SUCCESS = 3;

    private static final int GROUP_ORDER = 3;

    private static final int CANCEL_ORDER = 5;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Value("${companyId}")
    private String companyId;


    @PostMapping("/query/all")
    @ResponseBody
    @ApiOperation(value = "分页查询所有订单信息", notes = "分页查询所有订单信息")
    public ResponseEntity<PageInfo<Order>> findAllOrderList(@RequestBody OrderBean orderBean) throws Exception {
        if (orderBean.getStates() != null && orderBean.getStates().size() == 0) {
            orderBean.setStates(null);
        }
        LOGGER.info("/order/query/all/,分页查询所有订单信息 start");
        ResponseEntity<PageInfo<Order>> resultVo = new ResponseEntity<>();

        PageHelper.startPage(orderBean.getPageNum(), orderBean.getPageSize());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!Strings.isNullOrEmpty(orderBean.getStartTime()) && !Strings.isNullOrEmpty(orderBean.getEndTime())) {
            Date startDate = sdf.parse(orderBean.getStartTime() + " 00:00:00");
            Date endDate = sdf.parse(orderBean.getEndTime() + " 23:59:59");
            orderBean.setStartTime(sdf.format(startDate));
            orderBean.setEndTime(sdf.format(endDate));
        }
        PageInfo<Order> pageInfo = orderService.findAllOrderList(orderBean);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(pageInfo);
        LOGGER.info("/order/query/all/,分页查询所有订单信息 end");
        return resultVo;
    }

    @PostMapping("/refund")
    @ResponseBody
    @ApiOperation(value = "异常退单", notes = "异常退单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCodes", value = "异常退单号", required = true)})
    public ResponseEntity<Order> refundByOrderId(String orderCodes) {
        LOGGER.info("/order/refund/,异常退单 start");
        ResponseEntity<Order> resultVo = new ResponseEntity<>();

        if (Strings.isNullOrEmpty(orderCodes)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCodes);

        List<Order> resultOrderList = order.stream().filter(o -> o.getState() == FINISH_ORDER || o.getState() == NO_USE_ORDER).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(resultOrderList)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("待使用或退单完成不可以操作");
            return resultVo;
        }

        Boolean result = orderService.refundByOrderId(orderCodes);
        if (result) {
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage(GeneConstant.SUCCESS);
        } else {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage(GeneConstant.ERROR);
        }
        LOGGER.info("/order/refund/,异常退单 end");
        return resultVo;
    }


    @GetMapping("/detail/pc")
    @ResponseBody
    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<Order> findOrderDetailPc(String orderCode) {

        ResponseEntity<Order> resultVo = new ResponseEntity<>();

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(order.get(0));

        return resultVo;
    }


    @GetMapping("/retail/detail")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "分销订单进入订单详情", notes = "分销订单进入订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<Order> findRetailOrderDetail(String orderCode, @Value("#{request.getAttribute('currentUser')}") User user) {
        LOGGER.info("/order/retail/detail/,查询订单详情 start");
        ResponseEntity<Order> resultVo = new ResponseEntity<>();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(order.get(0));
        LOGGER.info("/order/retail/detail/,查询订单详情 end");
        return resultVo;
    }


    @GetMapping("/detail")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleName", value = "角色标识", required = true)})
    public ResponseEntity<Order> findOrderDetail(String orderCode, String roleName, @Value("#{request.getAttribute('currentUser')}") User user) {
        LOGGER.info("/order/detail/,查询订单详情 start");
        ResponseEntity<Order> resultVo = new ResponseEntity<>();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }


        if (StringUtil.isNullOrEmpty(roleName)) {
            GoodsProject goodsProject = orderService.queryByStaffIdAndProjectId(user.getId(), Long.valueOf(order.get(0).getProjectId()));

            if (goodsProject == null) {
                resultVo.setResult(GeneConstant.INT_2);
                resultVo.setMessage("当前项目权限有变更,请前去核实服务信息。");
                return resultVo;
            }
        }
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(order.get(0));
        LOGGER.info("/order/detail/,查询订单详情 end");
        return resultVo;
    }


    @PostMapping("/app/query/all")
    @ResponseBody
    @ApiOperation(value = "分页查询APP所有订单信息", notes = "分页查询APP所有订单信息")
    public ResponseEntity<List<AppOrderVo>> findAppAllOrderList(AppOrderBean appOrderBean) throws Exception {
        LOGGER.info("/order/app/query/all/,分页查询所有订单信息 start");
        ResponseEntity<List<AppOrderVo>> resultVo = new ResponseEntity<>();
        if (CollectionUtils.isEmpty(appOrderBean.getStates())) {
            appOrderBean.setStates(null);
        }
        List<AppOrderVo> list = orderService.findAppAllOrderList(appOrderBean);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(list);
        LOGGER.info("/order/app/query/all/,分页查询APP所有订单信息 end");
        return resultVo;
    }

    @PostMapping("/app/query/all/V2")
    @ResponseBody
    @ApiOperation(value = "分页查询APP所有订单信息", notes = "分页查询APP所有订单信息")
    public ResponseEntity<AppOrdersVo> findAppAllOrderListV2(AppOrderBean appOrderBean) throws Exception {
        LOGGER.info("/order/app/query/all/,分页查询所有订单信息 start");
        ResponseEntity<AppOrdersVo> resultVo = new ResponseEntity<>();
        if (CollectionUtils.isEmpty(appOrderBean.getStates())) {
            appOrderBean.setStates(null);
        }
        AppOrdersVo list = orderService.findAppAllOrderListV2(appOrderBean);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(list);
        LOGGER.info("/order/app/query/all/,分页查询APP所有订单信息 end");
        return resultVo;
    }


    @PostMapping("/app/refund/")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "核销,退单操作", notes = "核销,退单操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "type", value = "核销,退单标识 1.核销订单 2.退单", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleName", value = "角色标识", required = true)})
    public ResponseEntity<Long> closeAppOrder(Integer type, String orderCode, String roleName, @Value("#{request.getAttribute('currentUser')}") User user) {
        LOGGER.info("/order/app/refund//,核销,退单操作 start");
        ResponseEntity<Long> resultVo = new ResponseEntity<>();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (null == type || null == orderCode) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);
        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        if (type.equals(CHECK_ORDER)) {
            if (order.get(0).getState().equals(USE_ORDER) || order.get(0).getState().equals(FINISH_ORDER) || order.get(0).getState().equals(CANCEL_ORDER)) {
                resultVo.setResult(GeneConstant.INT_0);
                resultVo.setMessage("已使用或者体验完成不可核销");
                return resultVo;
            }
        }

        long result = orderService.closeAppOrder(type, orderCode, user, roleName);

        if (result == -1) {
            resultVo.setResult(GeneConstant.INT_2);
            resultVo.setMessage("当前项目权限有变更,请前去核实服务信息。");
        } else if (result == 1) {
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage("操作成功");
        } else if (result == 0) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setValue(result);
        LOGGER.info("/order/app/refund//,核销,退单操作 end");
        return resultVo;
    }


    @PostMapping("/app/confirm")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "核销订单", notes = "核销订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "tactics", value = "策略ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleName", value = "角色标识", required = true)})
    public ResponseEntity<Long> confirmAppOrder(String orderCode, Integer tactics, String roleName, @Value("#{request.getAttribute('currentUser')}") User user) {

        ResponseEntity<Long> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        if (order.get(0).getOrderType().equals(GROUP_ORDER)) {

            GroupOrderItem groupOrderItem = orderService.findGroupOrderItemByOrderId(order.get(0).getId());
            if (null == groupOrderItem) {
                resultVo.setResult(GeneConstant.INT_0);
                resultVo.setMessage("该用户没有参加拼团");
                return resultVo;
            }

            GroupOrder groupOrder = orderService.findGroupOrderByGroupOrderId(groupOrderItem.getGroupOrderId());
            if (!groupOrder.getStatus().equals(GROUP_SUCCESS)) {
                resultVo.setResult(GeneConstant.INT_0);
                resultVo.setMessage("该订单没有拼团成功,无法核销");
                return resultVo;
            }
        }
        if (order.get(0).getState().equals(USE_ORDER) || order.get(0).getState().equals(FINISH_ORDER) || order.get(0).getState().equals(CANCEL_ORDER)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("已使用或者体验完成不可核销");
            return resultVo;
        }

        long result = orderService.confirmAppOrder(orderCode, user, tactics, roleName);

        if (result == -1) {
            resultVo.setResult(GeneConstant.INT_2);
            resultVo.setMessage("当前项目权限有变更,请前去核实服务信息。");
        } else if (result == 1) {
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage("操作成功");
        } else if (result == 0) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("服务器出错,请稍后重试");
        }
        resultVo.setValue(result);

        return resultVo;
    }


    @PostMapping("/h5/query/all")
    @OpenIdAuthRequired
    @ApiOperation(value = "小程序查看所有订单", notes = "小程序查看所有订单")
    public ResponseEntity<List<H5OrderVo>> findH5AllOrderList(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody OrderQueryBean orderQueryBean) throws Exception {
        LOGGER.info("/order/h5/query/all/,小程序查看所有订单 start");
        ResponseEntity<List<H5OrderVo>> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }
        orderQueryBean.setUserId(user.getId());
        List<H5OrderVo> list = orderService.findH5AllOrderList(orderQueryBean);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(list);
        LOGGER.info("/order/h5/query/all/,小程序查看所有订单 end");
        return resultVo;
    }


    @GetMapping("/detail/h5")
    @OpenIdAuthRequired
    @ApiOperation(value = "查询订单详情H5", notes = "查询订单详情H5")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<H5OrderVo> findOrderDetailH5(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) {

        ResponseEntity<H5OrderVo> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<H5OrderVo> order = orderService.findH5OrderInfo(orderCode, user.getId());

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.SYSTEM_ERROR);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(order.get(0));

        return resultVo;
    }


    @PostMapping("/h5/delete")
    @OpenIdAuthRequired
    @ApiOperation(value = "小程序删除订单", notes = "小程序删除订单")
    public ResponseEntity deleteH5Order(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) throws Exception {
        LOGGER.info("/order/h5/delete,小程序删除订单 start");
        ResponseEntity<Long> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }
        long result = orderService.deleteH5Order(orderCode);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }
        LOGGER.info("/order/h5/delete,小程序删除订单 end");
        return resultVo;
    }


    @GetMapping("/distinct/detail")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "App组合订单详情", notes = "App组合订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<DistinctOrderDetail> findAppOrderDetail(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode, String roleName) {
        LOGGER.info("/order/detail/,查询订单详情 start");
        ResponseEntity<DistinctOrderDetail> resultVo = new ResponseEntity<>();
        DistinctOrderDetail distinctOrderDetail = new DistinctOrderDetail();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        if (StringUtil.isNullOrEmpty(roleName)) {
            GoodsProject goodsProject = orderService.queryByStaffIdAndProjectId(user.getId(), Long.valueOf(order.get(0).getProjectId()));
            if (null == goodsProject) {
                resultVo.setResult(GeneConstant.INT_2);
                resultVo.setMessage("当前项目权限有变更,请前去核实服务信息。");
                resultVo.setValue(null);
                return resultVo;
            }
        }

        if (order.get(0).getOrderType().equals(4)) {
            ComposeOrderVo composeOrderVo = orderService.findComposeOrderDetail(orderCode);
            distinctOrderDetail.setComposeOrderVo(composeOrderVo);
            distinctOrderDetail.setOrderType(2);
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage(GeneConstant.SUCCESS);
            resultVo.setValue(distinctOrderDetail);
            return resultVo;
        }

        distinctOrderDetail.setOrder(order.get(0));
        distinctOrderDetail.setOrderType(1);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(distinctOrderDetail);
        return resultVo;
    }


    @PostMapping("/boat/query/all")
    @ResponseBody
    @ApiOperation(value = "分页查询所有船票订单", notes = "分页查询所有船票订单")
    public ResponseEntity<PageInfo<BoatOrderVo>> findAllBoatOrderList(@RequestBody BoatPcOrderBean boatPcOrderBean) throws Exception {
        LOGGER.info("/boat/order/query/all/,分页查询所有船票订单 start");
        ResponseEntity<PageInfo<BoatOrderVo>> resultVo = new ResponseEntity<>();
        PageHelper.startPage(boatPcOrderBean.getPageNum(), boatPcOrderBean.getPageSize());
        PageInfo<BoatOrderVo> pageInfo = orderService.findAllBoatOrderList(boatPcOrderBean);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(pageInfo);
        LOGGER.info("/boat/order/query/all/,分页查询所有船票订单 end");
        return resultVo;
    }


    @GetMapping("/boat/detail")
    @ResponseBody
    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<BoatPcOrderDetailVo> findBoatOrderDetailPc(String orderCode) {
        ResponseEntity<BoatPcOrderDetailVo> resultVo = new ResponseEntity<>();
        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        BoatPcOrderDetailVo boatPcOrderDetailVo = orderService.findBoatOrderDetailPc(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(boatPcOrderDetailVo);

        return resultVo;
    }


    @GetMapping("/sea/detail")
    @ResponseBody
    @StaffAuthRequired
    @ApiOperation(value = "查询来游吧订单详情", notes = "查询来游吧订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleName", value = "角色标识", required = true)})
    public ResponseEntity<Order> findSeaOrderDetail(String orderCode, String roleName, @Value("#{request.getAttribute('currentUser')}") User user) {
        LOGGER.info("/order/sea/detail/,查询来游吧订单详情 start");
        ResponseEntity<Order> resultVo = new ResponseEntity<>();

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<Order> order = orderService.findOrderInfo(orderCode);

        if (CollectionUtils.isEmpty(order)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }

        if (StringUtil.isNullOrEmpty(roleName)) {
            GoodsProject goodsProject = orderService.queryByStaffIdAndProjectId(user.getId(), Long.valueOf(order.get(0).getProjectId()));

            if (goodsProject == null) {
                resultVo.setResult(GeneConstant.INT_2);
                resultVo.setMessage("当前项目权限有变更,请前去核实服务信息。");
                return resultVo;
            }
        }
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(order.get(0));
        LOGGER.info("/order/sea/detail/,查询来游吧订单详情 end");
        return resultVo;
    }


    @GetMapping("/ticket/detail")
    @StaffAuthRequired
    @ApiOperation(value = "查询票务订单信息", notes = "查询票务订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity<List<OrderTicketVo>> findTicketOrderDetail(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) {
        ResponseEntity<List<OrderTicketVo>> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isNullOrEmpty(orderCode)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        List<OrderTicketVo> orderTicketVoList = orderService.findTicketOrderDetail(orderCode);

        if (CollectionUtils.isEmpty(orderTicketVoList)) {
            resultVo.setResult(GeneConstant.SYSTEM_ERROR);
            resultVo.setMessage("暂无该订单号");
            return resultVo;
        }
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(orderTicketVoList);
        return resultVo;
    }

    @PostMapping(value = "/update/printed")
    @SignRequired
    @ApiOperation(value = "修改票务订单打印状态", notes = "修改票务订单打印状态")
    public ResponseEntity updateLaiU8PrintedSts(@RequestBody OrderPrintedParams orderPrintedParams) {
        LOGGER.info("/order/update/printed,修改票务订单打印状态 start");
        ResponseEntity<Long> resultVo = new ResponseEntity<>();
        if (Strings.isNullOrEmpty(orderPrintedParams.getOrderCode())) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少订单票号");
            resultVo.setValue(0L);
            return resultVo;
        }
        if (null == orderPrintedParams.getPrintedSts()) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少打印状态");
            resultVo.setValue(0L);
            return resultVo;
        }

        OrderTicketVo orderTicketVo = orderService.findLaiU8OrderDetail(orderPrintedParams.getOrderCode());
        if (null == orderTicketVo) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该订单");
            return resultVo;
        }

        long result = orderService.updateLaiU8PrintedSts(orderPrintedParams, orderTicketVo.getOrderCode());
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }
        LOGGER.info("/order/h5/delete,修改票务订单打印状态 end");
        return resultVo;

    }


    @PostMapping("/distribute/bind")
    @OpenIdAuthRequired
    @ApiOperation(value = "分销商绑定用户", notes = "分销商绑定用户")
    public ResponseEntity distributeBindUser(@Value("#{request.getAttribute('currentUser')}") User user, String phone) throws Exception {
        LOGGER.info("/order/distribute/bind,分销商绑定用户 start");
        ResponseEntity<Long> resultVo = new ResponseEntity<>();

        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("找不到该用户信息");
            return resultVo;
        }

        if (Strings.isNullOrEmpty(phone)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        ResponseEntity checkUserResult = remoteServiceUtil.getCheckUserResult(phone, Long.valueOf(companyId));
        if (null == checkUserResult) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("该用户不是分销商,不可建立绑定关系");
            return resultVo;
        }

        DistributeBindUser distributeBindUser = orderService.findIsDisBindUser(phone, user.getId());
        if (distributeBindUser != null) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("不可重复绑定");
            return resultVo;
        }

        long result = orderService.distributeBindUser(phone, user.getId());
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                : GeneConstant.INT_0);
        if (result == 0) {
            resultVo.setMessage("服务器出错");
        }

        LOGGER.info("/order/distribute/bind,分销商绑定用户 end");
        return resultVo;
    }


    @RequestMapping("/callback")
    @ResponseBody
    @ApiOperation(value = "进阶回调通知", notes = "进阶回调通知")
    public Object callback(@RequestBody String pftCallbackParam, HttpServletResponse response) throws Exception {
        PFTOrderCallBack pftOrderCallBack = JSON.parseObject(pftCallbackParam, PFTOrderCallBack.class);
        if ("1".equals(pftOrderCallBack.getOrderState()) || "7".equals(pftOrderCallBack.getOrderState())) {
            orderService.orderStateCallBack(pftOrderCallBack);
        }

        if ("8".equals(pftOrderCallBack.getOrderState()) || "9".equals(pftOrderCallBack.getOrderState())) {
            orderService.handlePftRefundOrder(pftOrderCallBack);
        }

        String succ = "200";
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(succ);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
        return null;
    }

    @PostMapping("/cancel")
    public ResponseEntity cancelOrder() {
        LOGGER.info("===cancelOrder===start");
        orderService.cancelOrder();
        LOGGER.info("===cancelOrder===end");
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "执行成功");
    }

    @PostMapping("/orderCheckIn")
    @ApiOperation(value = "订单核销")
    public void orderStateCallBack(@RequestBody PFTOrderCallBack orderStateCallBack) {
        LOGGER.info("/order/orderCheckIn  订单核销 --->入参对象" + orderStateCallBack);
        orderService.orderStateCallBack(orderStateCallBack);

    }

}
