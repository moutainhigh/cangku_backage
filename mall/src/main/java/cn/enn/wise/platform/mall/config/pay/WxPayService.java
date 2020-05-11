package cn.enn.wise.platform.mall.config.pay;

import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.GeneUtil;
import cn.enn.wise.platform.mall.util.XmlUtil;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 微信支付服务
 *
 * @author baijie
 * @date 2019-07-29
 */
@Service
public class WxPayService {

    private static final Logger logger = LoggerFactory.getLogger(WxPayService.class);

    @Value("${companyId}")
    private String companyId;

    /**
     * 微信预下单
     *
     * @param companyId
     * @param goodName
     * @param openId
     * @param outTradeNo
     * @param totalFee
     * @param spbillCreateIp
     * @return
     */
    public Object pay(
            Long companyId,
            String goodName,
            String openId,
            String outTradeNo,
            String totalFee,
            String spbillCreateIp,
            String orderCode) {

        if (StringUtils.isEmpty(goodName)
                || StringUtils.isEmpty(openId)
                || StringUtils.isEmpty(outTradeNo)
                || StringUtils.isEmpty(totalFee)
                || StringUtils.isEmpty(spbillCreateIp)
                || companyId == null
                || StringUtils.isEmpty(orderCode)) {

            logger.error("pay param is error: companyId :{},goodName:{},openId:{},outTradeNo:{},totalFee:{},spbillCreateIp:{}", companyId, goodName, openId, outTradeNo, totalFee, spbillCreateIp);
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "支付参数不正确");

        }

        WxPayConfigBean wxPayConfigBean = SystemAdapter.PAY_MAP.get(companyId);

        //组装统一下单参数
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put(PayConstants.APPID, wxPayConfigBean.getAppId());
        paramMap.put(PayConstants.MCH_ID, wxPayConfigBean.getMchId());
        //生成随机字符串
        String nonceStr = getRandomStringByLength(32);
        paramMap.put(PayConstants.NONCE_STR, nonceStr);
        paramMap.put(PayConstants.BODY, goodName);
        // 商户订单号,自己的订单ID
        paramMap.put(PayConstants.OUT_TRADE_NO, outTradeNo);
        // 支付金额，这边需要转成字符串类型，否则后面的签名会失败
        paramMap.put(PayConstants.TOTAL_FEE, totalFee);
        paramMap.put(PayConstants.SPBILL_CREATE_IP, spbillCreateIp);
        // 支付成功后的回调地址
        paramMap.put(PayConstants.NOTIFY_URL, wxPayConfigBean.getNotifyUrl());
        // 支付方式
        paramMap.put(PayConstants.TRADE_TYPE, wxPayConfigBean.getTradeType());
        // 用户的openID，自己获取
        paramMap.put(PayConstants.OPENID, openId);

        //拼接字符串
        String paramString = createLinkString(paramMap);
        String key = wxPayConfigBean.getKey();
        // MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String sign = sign(paramString, key, "utf-8").toUpperCase();

        // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        paramMap.put(PayConstants.SIGN, sign);
        String xml = getXmlParams(paramMap);
        paramMap.put(PayConstants.KEY, key);
        paramMap.put(PayConstants.SIGN_TYPE, wxPayConfigBean.getSignType());

        String result = httpRequest(wxPayConfigBean.getPayUrl(), "POST", xml);

        // 将解析结果存储在HashMap中
        Map<String, Object> respMap = getRespMap(result, nonceStr, paramMap);
        if (GeneUtil.isNullOrEmpty(respMap)) {

            throw new RuntimeException("pay is error: " + result);
        }
        respMap.put("orderCode", orderCode);

        return respMap;

    }

    /**
     * 将解析结果存储在Map中
     *
     * @return
     * @throws
     * @Title getRespMap
     * @since JDK 1.8
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> getRespMap(String result, String nonceStr, Map<String, String> paramMap) {
        Map map = XmlUtil.doXMLParse(result, 0);
        // 返回给小程序端需要的参数
        Map<String, Object> response = new HashMap<String, Object>();
        // 返回状态码
        String returnCode = (String) map.get(PayConstants.RETURN_CODE);
        // 返回状态码
        String resultCode = (String) map.get(PayConstants.RESULT_CODE);
        if (GeneConstant.SUCCESS_UPPERCASE.equals(returnCode) && returnCode.equals(resultCode)) {
            // 返回的预付单信息
            String prepayId = (String) map.get("prepay_id");
            response.put("nonceStr", nonceStr);
            response.put("package", "prepay_id=" + prepayId);
            Long timeStamp = System.currentTimeMillis() / 1000;
            // 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            response.put("timeStamp", timeStamp + "");
            // 拼接签名需要的参数
            String stringSignTemp = "appId=" + paramMap.get("appid") + "&nonceStr="
                    + nonceStr + "&package=prepay_id=" + prepayId + "&signType="
                    + paramMap.get("sign_type") + "&timeStamp=" + timeStamp;
            // 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign =
                    sign(stringSignTemp, paramMap.get("key"), "utf-8")
                            .toUpperCase();

            response.put("paySign", paySign);
            response.put(PayConstants.APPID, paramMap.get("appid"));
        }
        return response;
    }

    /**
     * @param requestUrl    请求地址
     * @param requestMethod 请求方法
     * @param outputStr     参数
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        // 创建SSLContext
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    private String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 签名字符串
     *
     * @param text         需要签名的字符串
     * @param key          密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String inputCharset) {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(getContentBytes(text, inputCharset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws java.security.SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 拼接统一下单接口使用的xml数据
     *
     * @return
     * @throws
     * @Title getXmlParams
     * @since JDK 1.8
     */
    private String getXmlParams(Map<String, String> paramMap) {
        String xml = "<xml>" + "<appid>" + paramMap.get("appid") + "</appid>"
                + "<body><![CDATA[" + paramMap.get("body") + "]]></body>"
                + "<mch_id>" + paramMap.get("mch_id") + "</mch_id>" + "<nonce_str>"
                + paramMap.get("nonce_str") + "</nonce_str>" + "<notify_url>"
                + paramMap.get("notify_url") + "</notify_url>" + "<openid>"
                + paramMap.get("openid") + "</openid>" + "<out_trade_no>"
                + paramMap.get("out_trade_no") + "</out_trade_no>"
                + "<spbill_create_ip>" + paramMap.get("spbill_create_ip") + "</spbill_create_ip>"
                // 支付的金额，单位：分
                + "<total_fee>" + paramMap.get("total_fee") + "</total_fee>"
                + "<trade_type>" + paramMap.get("trade_type") + "</trade_type>"
                + "<sign>" + paramMap.get("sign") + "</sign>" + "</xml>";
        return xml;
    }


    /**
     * map转成为xml保温数据
     *
     * @param map 参数集合
     * @return
     */
    public static String map2Xml(Map<String, String> map) {

        if (MapUtils.isEmpty(map)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<xml>");
        Set<Map.Entry<String, String>> entries = map.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append("<");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(">");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("</");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(">");

        }
        stringBuilder.append("</xml>");


        return stringBuilder.toString();
    }


    /**
     * 支付后主动查询微信订单
     *
     * @param companyId  景区Id
     * @param outTradeno 商户在微信的订单号
     * @return
     */
    public Map<String, Object> orderQuery(Long companyId,
                                          String outTradeno) {

        //获取微信配置
        WxPayConfigBean wxPayConfigBean = SystemAdapter.PAY_MAP.get(companyId);

        if (wxPayConfigBean == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "当前companyId未找到相应配置");
        }

        //组装统一查询参数
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put(PayConstants.APPID, wxPayConfigBean.getAppId());
        paramMap.put("mch_id", wxPayConfigBean.getMchId());
        paramMap.put("out_trade_no", outTradeno);

        //生成随机字符串
        String nonceStr = getRandomStringByLength(32);
        paramMap.put("nonce_str", nonceStr);
        //拼接字符串
        String str = createLinkString(paramMap);
        //获取微信商户秘钥
        String key = wxPayConfigBean.getKey();
        String sign = sign(str, key, "utf-8").toUpperCase();
        paramMap.put("sign", sign);
        String paramString = map2Xml(paramMap);
        String resultXmlString = httpRequest(wxPayConfigBean.getOrderQueryUrl(), "POST", paramString);
        return getOrderQueryResult(resultXmlString, key);
    }

    /**
     * 解析微信返回结果
     *
     * @param resultXmlString 微信接口返回的xml字符串数据
     * @return 结果封装map
     */
    private static Map<String, Object> getOrderQueryResult(String resultXmlString, String key) {

        Map<String, Object> resultMap = new HashMap<>(8);

        if (StringUtils.isEmpty(resultXmlString)) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "微信查询订单失败,微信返回为空");
        }

        Map map = XmlUtil.doXMLParse(resultXmlString, 0);
        String return_code = map.get("return_code").toString();

        if (!GeneConstant.SUCCESS_UPPERCASE.equals(return_code)) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "微信查询订单失败,微信返回fail:" + map.get("return_msg"));
        }


        String tradeState = map.get("trade_state").toString();
        if (!GeneConstant.SUCCESS_UPPERCASE.equals(tradeState)) {
            resultMap.put("status", 2);
            resultMap.put("msg", tradeState);
            return resultMap;
        }

        resultMap.put("status", 1);
        resultMap.put("outTradeno", map.get("out_trade_no"));
        resultMap.put("transactionId", map.get("transaction_id"));

        return resultMap;
    }


    /**
     * 检查支付回调参数
     *
     * @param map 微信回调参数列表
     */
    public void checkNotifyParam(Map<String, String> map) {

        checkParam(map,Long.parseLong(companyId));


    }
    /**
     * 检查支付回调参数
     * @param companyId 跟景区关联的配置Id
     * @param map 微信回调参数列表
     */
    public void checkNotifyParam(Map<String, String> map,Long companyId) {

        checkParam(map,companyId);

    }

    /**
     * 检查支付回调参数
     * @param map
     * @param companyId
     */
    private void checkParam(Map<String, String> map,Long companyId) {



        if (map == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "支付回调参数错误");
        }

        if (!GeneConstant.SUCCESS_UPPERCASE.equals(map.get(PayConstants.RETURN_CODE))) {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "微信返回错误");

        }

        //获取返回的签名
        String wxSign = map.get(PayConstants.SIGN);
        map.remove(PayConstants.SIGN);
        String linkString = createLinkString(map);
        WxPayConfigBean wxPayConfigBean = SystemAdapter.PAY_MAP.get(companyId);
        //获取商户秘钥
        String key = wxPayConfigBean.getKey();
        String sign = sign(linkString, key, "utf-8").toUpperCase();


        if(!sign.equals(wxSign)){
            logger.info("签名校验失败!请检查签名校验方式!");
            logger.info("Wxsign:" + wxSign);
            logger.info("mysign:" + sign);
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"签名校验失败");
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(16);
        map.put("transaction_id", "4200000429201911111576320091");
        map.put("nonce_str", "9hepqzu966ef5oyizxntd2awb45hgn5l");
        map.put("bank_type", "BOC_DEBIT");
        map.put("openid", "oJcc75EcWuSA5SZc9zghbQBoLe-Q");
        map.put("sign", "8D9EBBF560FBCC95DE0FB9F43AA700E6");
        map.put("fee_type", "CNY");
        map.put("mch_id", "1325029701");
        map.put("cash_fee", "1");
        map.put("out_trade_no", "20191111171351197820192r");
        map.put("appid", "wx76453a21da3fe5b3");
        map.put("total_fee", "1");
        map.put("trade_type", "JSAPI");
        map.put("result_code", "SUCCESS");
        map.put("time_end", "20191111171419");
        map.put("is_subscribe", "N");
        map.put("return_code", "SUCCESS");
        map.remove(PayConstants.SIGN);
        String string = createLinkString(map);
        String upperCase = sign(string, "91b147c700de21a6163ed68833903dd9", "utf-8").toUpperCase();

        System.out.println(upperCase);

    }


    }
