package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.response.PayResponseDto;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.service.order.service.PayService;
import cn.enn.wise.ssop.service.order.wx.SsopConfig;
import cn.enn.wise.ssop.service.order.wx.WXPay;
import cn.enn.wise.ssop.service.order.wx.WXPayConstants;
import cn.enn.wise.ssop.service.order.wx.WXPayUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Override
    public Map unifiedOrder(String orderSn, String body, String total, String openId) throws Exception {
        // 2 微信统一下单
        SsopConfig ssopConfig = new SsopConfig();
        Map<String, String> params = new HashMap();
        params.put("body", body);
        params.put("out_trade_no", orderSn);
        Double price = Double.parseDouble(total) * 100;
        params.put("total_fee", String.valueOf(price.intValue()));
        params.put("trade_type", "JSAPI");

        //TODO 回调地址更改
        params.put("notify_url", "https://genius.enn.cn/tx/ssopapi/ssop/api/order-v1/pay/freeApi/callback");
        params.put("openid", openId);

        Map<String, String> result = new HashMap<>();
        try{
            WXPay pay= new WXPay(ssopConfig);

            result = pay.unifiedOrder(params);
        }catch(Exception e){
            log.error("调用微信统一下单接口异常：{}",e);
            OrderExceptionAssert.ORDER_WECHAT_UNIFIED_EXCEPTION.assertFail();
        }
        // 微信下单 region 返回格式
        /**
         * {
         "nonce_str" : "9ALjaAc9H4HiHggF",
         "appid" : "wx6539ab32ca3b14e0",
         "sign" : "03C1E4EE3AE57BAC0BE57E164E23CD19720C8311E339ABE1D98E0C2279AE12ED",
         "trade_type" : "JSAPI",
         "return_msg" : "OK",
         "result_code" : "SUCCESS",
         "mch_id" : "1502607821",
         "return_code" : "SUCCESS",
         "prepay_id" : "wx0116473676855865fb30226b2992192404"
         }
         * */
        return result;
    }



    @Override
    public R pay(String prepayId) throws Exception {
        SsopConfig ssopConfig = new SsopConfig();
        Map<String,String> payMap=new HashMap<>();
        payMap.put("appId", ssopConfig.getAppID());
        Long timeStampSec = System.currentTimeMillis()/1000;
        payMap.put("timeStamp",timeStampSec.toString());
        payMap.put("nonceStr",String.valueOf(System.currentTimeMillis()));
        payMap.put("package","prepay_id="+prepayId);
        payMap.put("signType", WXPayConstants.SignType.MD5.toString());
        payMap.put("paySign", WXPayUtil.generateSignature(payMap,ssopConfig.getKey(), WXPayConstants.SignType.MD5));
        payMap.put("prepayId",prepayId);
        return R.success(payMap);
    }

}
