package cn.enn.wise.platform.mall.util.WX;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/6/12 12:43
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:微信支付-封装类
 ******************************************/
@Slf4j
public class WxPayUtils {


    private WXPayConfig wxPayConfig;

    /**
     * 微信退款
     * @author haogd
     * @throws Exception
     * @since JDK 1.8
     */

    public static Map<String, String> refund(String orderNum, String refundNum, String totalPrice, String refundPrice) throws Exception{

        log.info("传给微信相关数据如下，totalPrice :" + totalPrice + ",refundPrice :" + refundPrice);
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("out_trade_no", orderNum);
        dataMap.put("out_refund_no", refundNum);
        dataMap.put("total_fee", totalPrice);
        dataMap.put("refund_fee", refundPrice);
        return xzRefund(dataMap);
    }

    /**
     * 统一 - 退款
     * @author haogd
     * @throws Exception
     * @since JDK 1.8
     */
    private static Map<String, String> xzRefund(Map<String, String> dataMap) throws Exception {
        log.info("开始退款:" + dataMap);
        WXPay pay = new WXPay(XZParameter.WEIXIN_NXJ_CONFIG);
        return pay.refund(dataMap);
    }
}
