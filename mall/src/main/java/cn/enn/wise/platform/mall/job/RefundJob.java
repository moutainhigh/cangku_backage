package cn.enn.wise.platform.mall.job;

import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.bo.RefundApply;
import cn.enn.wise.platform.mall.bean.vo.OrderTicketVo;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.RefundApplyMapper;
import cn.enn.wise.platform.mall.service.BBDTicketService;
import cn.enn.wise.platform.mall.service.GroupOrderService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.service.RefundApplyService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.WX.WxPayUtils;
import cn.enn.wise.platform.mall.util.thirdparty.nxj.XmlHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

import static cn.enn.wise.platform.mall.util.thirdparty.nxj.NxjSoapUtil.binding;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/6/12 11:45
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:退款Job
 ******************************************/
@Slf4j
@RestController
@RequestMapping("/pay")
public class RefundJob {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RefundApplyService refundApplyService;

    @Autowired
    private RefundApplyMapper refundApplyMapper;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private BBDTicketService bbdTicketService;

    private static final String RETURN_CODE = "SUCCESS";

    private static String USER_NAME = "355135";
    private static String PASSWORD = "bb1eb0746dc9e5c9e8bd2db3dc219046";

    //public static String USER_NAME = "100019";
    //public static String PASSWORD = "ae20bedfe0c43ca2d548e845bd2e3829";


    //region 2019-07-09 暂时注释异常微信退款功能
    //@Scheduled(cron = "0 */1 * * * ?")
    public void refund() {

        List<Order> list = orderService.queryRefundOrder();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(o -> {
                handleOrder(o);
            });
        }
    }
    //endregion

    public void handleOrder(Order order) {
        try {
            BigDecimal couponPrice = new BigDecimal(100);
            Integer toPayPrice = order.getGoodsPrice().multiply(couponPrice).intValue();
            //getRefundPay(order.getOrderCode(),order.getBatCode(),order.getBatCode()+"TK",String.valueOf(toPayPrice),String.valueOf(toPayPrice));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getRefundPay error" + e);
        }
    }

    //@Scheduled(cron = "0 */1 * * * ?")
    @PostMapping(value = "/refund")
    public void refundOrder() {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getReturnMoneySts, 1);
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);
        if (CollectionUtils.isNotEmpty(refundApplyList)) {
            refundApplyList.stream().forEach(refundApply -> {
                handleRefundApplyOrder(refundApply);
            });
        }
    }

    public void handleRefundApplyOrder(RefundApply refundApply) {
        try {
            BigDecimal couponPrice = new BigDecimal(100);
            Integer totalPrice = refundApply.getOrderAmount().multiply(couponPrice).intValue();
            Integer refundPrice = refundApply.getRefundAmount().multiply(couponPrice).intValue();
            getRefundPay(refundApply, refundApply.getOrderId(), refundApply.getFlowTradeNo(), refundApply.getOutRefundNo(), String.valueOf(totalPrice), String.valueOf(refundPrice));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getRefundPay error" + e);
        }
    }

    private void getRefundPay(RefundApply refundApply, String orderCode, String orderNum, String refundNum, String totalPrice, String refundPrice) throws Exception {
        log.info("---------------------> refund pay is start ");
        Map<String, String> map = WxPayUtils.refund(orderNum, refundNum, totalPrice, refundPrice);

        log.info("--------------------->" + map);

        String returnCode = map.get("return_code");
        String resultCode = map.get("result_code");

        if (returnCode.equals(RETURN_CODE) && resultCode.equals(RETURN_CODE)) {
            log.info("---------------------> refund pay is success");
            BigDecimal couponPrice = new BigDecimal(100);
            Order order = orderDao.findComposeOrderDetail(orderCode);
            BigDecimal totalPrices = order.getActualPay();
            BigDecimal refundPrices = new BigDecimal(refundPrice);
            BigDecimal surplusPrice = totalPrices.subtract(refundPrices.divide(couponPrice)).setScale(2, RoundingMode.HALF_UP);
            orderService.updateOrderPayStatus(orderNum, surplusPrice);
            refundApplyService.updateRefundSts(refundNum);
            groupOrderService.updateItemStatusById(orderNum);
            //refundBBDOrder(refundApply);
            refundPFT(order);
        }
        log.info("---------------------> refund pay is end");
    }

    private void refundPFT(Order order){
        try {
            log.info("--------------order"+order);
            if (Strings.isNotEmpty(order.getTicketOrderCode())){
                String xml = binding.order_Change_Pro(USER_NAME,
                        PASSWORD,
                        order.getTicketOrderCode(),
                        "0",
                        "",
                        "");
                String json = XmlHelper.xml2json(xml);
                log.info("--------------------pft return"+json);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", XmlHelper.parseJson2List(json));

                log.info("-----------------------pwt result:"+resultMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void refundBBDOrder(RefundApply refundApply) {
        try {
            List<OrderTicketVo> orderTicketVoList = orderService.findTicketOrderDetail(refundApply.getOrderId());
            if (refundApply.getPlatform().equals(3)) {
                log.info("bbd refundOrder H5 start");
                if (Strings.isNotEmpty(orderTicketVoList.get(0).getTicketSerialBbd())) {
                    orderTicketVoList.stream().forEach(orderTicketVo -> {
                        orderService.updateBBDOrderRefundSts(orderTicketVo.getTicketSerialBbd());
                        boolean refundNotifyBBD = bbdTicketService.refundNotifyBBD(orderTicketVo.getTicketSerialBbd());
                        log.info("refundNotifyBBD service return" + refundNotifyBBD);
                    });
                }
            } else {
                log.info("bbd refundOrder PC and APP start");
                if (Strings.isNotEmpty(orderTicketVoList.get(0).getTicketSerialBbd())) {
                    List<String> orderItemIdList = Arrays.asList(refundApply.getOrderItemId().split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
                    orderTicketVoList.stream().forEach(orderTicketVo -> {
                        orderItemIdList.stream().forEach(orderItemId -> {
                            if (orderTicketVo.getId().equals(Integer.valueOf(orderItemId))) {
                                orderService.updateBBDOrderRefundSts(orderTicketVo.getTicketSerialBbd());
                                boolean refundNotifyBBD = bbdTicketService.refundNotifyBBD(orderTicketVo.getTicketSerialBbd());
                                log.info("refundNotifyBBD service return" + refundNotifyBBD);
                            }
                        });
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("bbd refundOrder error" + e);
        }
    }

    public long orderFailureRefund(Orders orders) {
        try {
            log.info("orderFailureRefund start"+orders);
            if (null == orders) {
                log.info("orders is null");
                return 0;
            }
            BigDecimal couponPrice = new BigDecimal(100);
            Map<String, String> map = WxPayUtils.refund(orders.getBatCode(),orders.getBatCode()+"TK", String.valueOf(orders.getGoodsPrice().multiply(couponPrice).intValue()), String.valueOf(orders.getActualPay().multiply(couponPrice).intValue()));

            log.info("--------------------->" + map);
            String returnCode = map.get("return_code");

            if (returnCode.equals(RETURN_CODE)) {
                long result = orderService.updateOrderFailureRefund(orders.getOrderCode());
                return result > 0 ? GeneConstant.INT_1
                        : GeneConstant.INT_0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("orderFailureRefund error"+e);
        }
        return 0;
    }

}
