package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.bean.vo.TicketResVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.OrderAppletsService;
import cn.enn.wise.platform.mall.util.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author bj
 * @Description
 * @Date19-5-24 下午4:54
 * @Version V1.0
 **/
@RestController
@RequestMapping("/orders")
@Api(value = "小程序热气球订单相关接口")
public class OrderAppletsController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(OrderAppletsController.class);
    @Autowired
    private OrderAppletsService orderAppletsService;

    /**
     * 获取支付信息
     *
     * @return
     */
    @PostMapping("/save")
    @OpenIdAuthRequired
    @ApiOperation(value = "保存订单", notes = "保存订单")
    public ResponseEntity saveOrder(@RequestBody @ApiParam(name = "payParam") PayParam payParam,
                                    HttpServletRequest request,
                                    @Value("#{request.getAttribute('currentUser')}") User user,
                                    @RequestHeader("openId") String openId) throws Exception {
        logger.info("===开始下单===");

        ParamValidateUtil.validatePay(payParam, user, openId);

        GoodsReqParam goodsReqParam = new GoodsReqParam();
        //封装查询参数
        goodsReqParam = MallUtil.setCommonParam(goodsReqParam);
        goodsReqParam.setGoodsId(payParam.getGoodsId());
        goodsReqParam.setPeriodId(payParam.getPeriodId());
        goodsReqParam.setOperationDate(MallUtil.getDateByType(payParam.getTimeFrame()));

        payParam.setGoodsReqParam(goodsReqParam);
        payParam.setUserId(user.getId());
        payParam.setOpenId(openId);
        payParam.setIp(IpAddressUtil.getIp(request));
        Object result = orderAppletsService.saveOrder(payParam);



        return new ResponseEntity(GeneConstant.INT_1, "下单成功", result);

    }

    /**
     * 预定票
     *
     * @return
     */
    @PostMapping("/getpredestinateinfo")
    @ApiOperation(value = "获取预订票信息", notes = "获取预订票信息")
    public ResponseEntity<TicketResVo> predestinate(@RequestBody @ApiParam(name = "goodsReqQry") GoodsReqParam goodsReqQry) throws Exception {
        logger.info("===开始预定门票====");
        ParamValidateUtil.validateBookingTicket(goodsReqQry);

        //设置查询公共参数
        goodsReqQry = MallUtil.setCommonParam(goodsReqQry);
        //设置查询运营时间
        goodsReqQry.setOperationDate(MallUtil.getDateByType(goodsReqQry.getTimeFrame()));

        TicketResVo ticketResVo = orderAppletsService.predestinate(goodsReqQry);

        return new ResponseEntity<>(GeneConstant.INT_1, "门票信息获取成功", ticketResVo);

    }


    /**
     * 待支付订单支付
     *
     * @param orderCode
     * @param request
     * @param user
     * @param openId
     * @param scenicId
     * @return
     */
    @PostMapping(value = "/payoriginal")
    @OpenIdAuthRequired
    @ApiOperation(value = "待支付订单支付", notes = "待支付订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query"),
    })
    public ResponseEntity payOriginalOrder(
            @RequestParam("orderCode") String orderCode,
            HttpServletRequest request,
            @Value("#{request.getAttribute('currentUser')}") User user,
            @RequestHeader("openId") String openId,
            Long scenicId) throws Exception {
        logger.info("===支付原有订单==payOldOrder");
        ParamValidateUtil.validatePayOldOrder(orderCode, user, openId, scenicId);

        String ip = IpAddressUtil.getIp(request);
        PayParam payParam = new PayParam();
        payParam.setIp(ip);
        payParam.setUserId(user.getId());
        payParam.setOpenId(openId);
        payParam.setScenicId(scenicId);
        payParam.setOrderCode(orderCode);
        payParam.setPayType("weixin");

        Object result = orderAppletsService.payOriginalOrder(payParam);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "获取待支付订单支付信息成功", result);

    }

    /**
     * 根据用户Id和订单号查询订单详情
     */
    @GetMapping("/detail")
    @OpenIdAuthRequired
    @ApiOperation(value = "根据订单号查询用户详情", notes = "根据订单号查询用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query")
    })
    public ResponseEntity<Orders> getOrderByIdAndUserId(@Value("#{request.getAttribute('currentUser')}") User user,
                                                        String orderCode) {
       ParamValidateUtil.validateGetOrderByIdAndUserId(user, orderCode);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setOrderCode(orderCode);
        Orders orderByIdAndUserId = orderAppletsService.getOrderByIdAndUserId(orders);

        return new ResponseEntity<>(GeneConstant.INT_1, "获取订单详情成功", orderByIdAndUserId);


    }

    /**
     * 用户查询所有订单
     *
     * @param user
     */
    @PostMapping("/userorder")
    @OpenIdAuthRequired
    @ApiOperation(value = "查询用户所有订单", notes = "查询用户所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "订单状态,该参数不传输表示查询所有订单,值为2表示查询未使用订单列表", paramType = "query")
    })
    public ResponseEntity<List<Orders>> listOrderByUserId(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody PayParam payParam) {

        Long scenicId = payParam.getScenicId();
        Integer state = payParam.getState();

 ParamValidateUtil.validateListOrderByUserId(user, scenicId, state);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setState(state);
        orders.setScenicId(scenicId);
        List<Orders> userOrder = orderAppletsService.getUserOrder(orders);
        return new ResponseEntity<>(GeneConstant.INT_1, "获取订单列表成功", userOrder);

    }

    /**
     * 支付成功,修改订单状态
     */
    @PostMapping("/complate")
    public ResponseEntity complateOrder(HttpServletRequest request) throws Exception {


        // TODO 签名 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7&index=8
        // TODO 防止假通知

        String stringXml = getParam(request);
        Map<String,String> map = XmlUtil.doXMLParse(stringXml, 0);
        String orderCode =map.get("out_trade_no");
        Orders orders = new Orders();
        orders.setOrderCode(orderCode);
        int i = orderAppletsService.complateOrder(orders);
        if (i > 0) {
            return new ResponseEntity(GeneConstant.INT_1, "修改订单支付状态成功", null);
        } else {
            return new ResponseEntity(GeneConstant.INT_0, "修改订单支付状态失败", null);
        }
    }

    /**
     * 取消订单
     */
    @PostMapping("/refundorder")
    @OpenIdAuthRequired
    @ApiOperation(value = "用户取消订单", notes = "用户取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "订单号", paramType = "query"),
    })
    public ResponseEntity refundOrder(@Value("#{request.getAttribute('currentUser')}") User user, String orderCode) throws Exception {
  ParamValidateUtil.validateRefundOrder(user, orderCode);

        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setOrderCode(orderCode);
        int i = orderAppletsService.refundOrder(orders);
        if (i > 0) {
            return new ResponseEntity(GeneConstant.INT_1, "取消订单成功", null);
        } else {
            return new ResponseEntity(GeneConstant.INT_0, "取消订单失败", null);
        }
    }

    /**
     * 取消未支付的过期的订单
     * @return
     */
    @PostMapping("/expire")
    public ResponseEntity cancelExpireOrder(){
        logger.info("===cancelExpireOrder===start");
        orderAppletsService.cancelExpireOrder();
        logger.info("===cancelExpireOrder===end");
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"执行成功");
    }

    /**
     * 获取返回值
     * @Title getParam
     * @param request
     * @return
     * @since JDK 1.8
     * @throws
     */
    private String getParam(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(request.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("getParam error", e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("getParam close stream error", e);
                }
        }
        return sb.toString();
    }



}

