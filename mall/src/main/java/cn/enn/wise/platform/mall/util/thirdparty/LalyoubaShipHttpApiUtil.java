package cn.enn.wise.platform.mall.util.thirdparty;

import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.*;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


/**
 * 北部湾旅游网　船票api接口
 */
@Slf4j
public class LalyoubaShipHttpApiUtil {

    @AllArgsConstructor
    static
    class ApiParam {
        //网络地址
        String apiUrl;
        //key
        String key;
        //密钥
        String secret;
    }

    static Map<String, ApiParam> profileMap = new HashMap();

    static {
        //加载api变量到系统
        Props lalyoubaShipProps = new Props("conf/thirdparty/LalyoubaShip.properties");
        List<String> profileList = Arrays.asList("test", "prod");
        for (String profileName : profileList) {
            String url = lalyoubaShipProps.getStr(profileName + ".url");
            String key = lalyoubaShipProps.getStr(profileName + ".key");
            String secret = lalyoubaShipProps.getStr(profileName + ".secret");
            ApiParam apiParam = new ApiParam(url, key, secret);
            profileMap.put(profileName, apiParam);
            if ("test".equals(profileName)) {
                profileMap.put("integrated", apiParam);
                profileMap.put("local", apiParam);
            }
        }
    }

    public static void main(String[] args) {
//        查询港口信息
//        List<PortInfo> data = getPorts("prod");
//        获取班次
//        ShipBaseVo<ShiftData> data = getShift("test", "18", "16", "2020-01-10");
//        下单
//        CreateOrderVo data = createOrder("test", new ArrayList<TicketsItems>() {{
//            add(new TicketsItems(1190061018774695937L, 19, "1072392650415497217", "23:47", 192F, "674825921211702948", "石斋", "130421199307062411", 1, null));
//        }}, "855645153135056756756", null, 16, 17, "石斋", "15011246078", null);
//        确认付款
//        ShipBaseVo<ConfirmPayVo> test1 = confirmPay("prod", "1219205186177916929", "20200120182902067579482r", "4200000481202001207483149303", "2");
//        取消订单
//        ShipBaseVo data = cancelOrder("test", "1207227455171272705");
//        退票
//        refundTicket("test",1,null,"1207227455171272705","不想去");
////        查询退票费率
//        getRefundPrice("test","1207227455171272705",null,1);
//        查询订单详情
        ShipBaseVo<OrderDetailVo> test1 = orderDetail("prod", "1219205186177916929 ", null);

//        ShipBaseVo<ShiftData> data = getShipLineTicketInfo("prod","16","17",
//                "2020-01-02",19L,"商务舱C区","1072399049077907458");
        System.out.println(JSON.toJSONString(test1));
    }





    //public static void main(String[] a){
//        ShipBaseVo<OrderDetailVo> prod1 = orderDetail("prod","0120200119195233058776524", null);
//        ShipBaseVo<RefundPriceVo> prod = getRefundPrice("test", "0120200119195233058776524", null, 2);
//        System.out.println(prod);
//    }


    /**
     * 获取港口信息
     *
     * @param profileActive 　服务器环境
     * @return
     */
    public static List<PortInfo> getPorts(String profileActive) {
        String url = "?method=ticketsCheck&edit=TKProtQuery&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        if(status==1){
            String data = baseVo.getString("data");
            List<PortInfo> portInfos = JSON.parseObject(data, new TypeReference<List<PortInfo>>() {
            });
            return portInfos;
        }else{
            return null;
        }
    }


    /**
     * 获取班次信息
     *
     * @param profileActive 　服务器环境
     * @param iStart        　出发港ID
     * @param iArrive       　到达港ID
     * @param dDate         　班次日期
     * @return
     */
    public static ShipBaseVo<ShiftData> getShift(String profileActive,
                                                 String iStart,
                                                 String iArrive,
                                                 String dDate) throws RuntimeException {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("iStart", iStart);
        paramMap.put("iArrive", iArrive);
        paramMap.put("dDate", dDate);

        String url = "?method=ticketsCheck&edit=TKQueryClass&sign=%s&key=%s";

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        if(status==1){
            String data = baseVo.getString("data");
            try{
                ShiftData shiftData;
                if(iStart.trim().equals("18")){
                    shiftData = new ShiftData();
                    JSONObject bean = JSON.parseObject(data);
                    JSONArray flight = bean.getJSONArray("Flight");
                    List<ShiftInfo> shiftInfos = new ArrayList<>();
                    for (int i = 0; i < flight.size(); i++) {
                        JSONObject obj = flight.getJSONObject(i);
                        System.out.println(obj);
                        String clId = obj.getString("ClId");
                        String clCabinID = obj.getString("ClCabinID");
                        String lineID = obj.getString("LineID");
                        String lineName = obj.getString("LineName");
                        String clCode = obj.getString("ClCode");
                        String clTime = obj.getString("ClTime");
                        String shipID = obj.getString("ShipID");
                        String shipName = obj.getString("ShipName");
                        Long prop = obj.getLongValue("Prop");
                        String propName = obj.getString("PropName");
                        String cabinName = obj.getString("CabinName");

                        Long freeSeats = obj.getLongValue("FreeSeats");
                        Long startPortID = obj.getLongValue("StartPortID");

                        String startPortName = obj.getString("StartPortName");

                        Long arrivePortID = obj.getLongValue("ArrivePortID");
                        String arrivePortName = obj.getString("ArrivePortName");
                        Long sellSeats = obj.getLongValue("SellSeats");
                        String clOverTime = obj.getString("ClOverTime");
                        float ticketFullPrice = obj.getFloatValue("TicketFullPrice");

                        long ticketFullType = obj.getLongValue("TicketFullType");
                        String ticketFullName = obj.getString("TicketFullName");
                        float ticketChildPrice = obj.getFloatValue("TicketChildPrice");
                        long ticketChildType = obj.getLongValue("TicketChildType");
                        String ticketChildName = obj.getString("TicketChildName");
                        String clDateStr = obj.getString("ClDate");// yyyy-MM-dd HH:mm
                        SimpleDateFormat ymdHm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date clDate = ymdHm.parse(clDateStr);

                        ShiftInfo shiftInfo = new ShiftInfo(clId, lineID, lineName, clCode, clDate, clTime, shipID, shipName, prop, propName, clCabinID,
                                cabinName, freeSeats, startPortID, startPortName, arrivePortID, arrivePortName, sellSeats,
                                clOverTime, ticketFullPrice, ticketFullType, ticketFullName, ticketChildPrice, ticketChildType, ticketChildName, "0", "0");
                        shiftInfos.add(shiftInfo);
                    }
                    shiftData.setFlight(shiftInfos);
                }else{
                    shiftData = JSON.parseObject(data, new TypeReference<ShiftData>() {
                    });
                }
                return new ShipBaseVo<ShiftData>(status,message,shiftData);
            }catch (Exception e){
                e.printStackTrace();
                return new ShipBaseVo<ShiftData>(status,message,new ShiftData(new ArrayList<>()));
            }
        }else{
            return null;
        }
    }

    /**
     * 下单接口（锁位）
     *
     * @param profileActive  　服务器环境
     * @param ticketsItems   票务集合
     * @param trade_no       　OTA订单编号　如果订单存在则返回状态
     * @param reserveOrderId 　预留票池id，选填
     * @param iStart         出发港ID
     * @param iArrive        到达港ID
     * @param o_CustomerName 　取票人姓名
     * @param o_Mobele       　取票人手机
     * @param remark         　备注，选填
     * @return
     * @throws RuntimeException
     */
    public static ShipBaseVo<CreateOrderVo> createOrder(String profileActive,
                                                        List<TicketsItems> ticketsItems,
                                                        String trade_no,
                                                        String reserveOrderId,
                                                        Integer iStart,
                                                        Integer iArrive,
                                                        String o_CustomerName,
                                                        String o_Mobele,
                                                        String remark) throws RuntimeException {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("ticketsItems", ticketsItems);
        paramMap.put("trade_no", trade_no);
        paramMap.put("iStart", iStart);
        paramMap.put("iArrive", iArrive);

        paramMap.put("o_CustomerName", o_CustomerName);
        paramMap.put("o_Mobele", o_Mobele);

        if (reserveOrderId != null) {
            paramMap.put("reserveOrderId", reserveOrderId);
        }
        if (remark != null) {
            paramMap.put("remark", remark);
        }

        String url = "?method=orders&edit=TKLock&sign=%s&key=%s";

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        log.info("接口请求结果:"+baseVo);
        if(status==1){
            String data = baseVo.getString("data");
            try{
                CreateOrderVo createOrderVo = JSON.parseObject(data, new TypeReference<CreateOrderVo>() {
                });
                return new ShipBaseVo<>(status,message,createOrderVo);
            }catch (Exception e){
                return null;
            }
        }else{
            return new ShipBaseVo<>(status,message,null);
        }
    }

    /**
     * 取消订单
     *
     * @param profileActive 服务器环境
     * @param orderSerial   　订单号
     * @return
     */
    public static ShipBaseVo cancelOrder(String profileActive, String orderSerial) {
        String url = "?method=orders&edit=TKUnLock&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderSerial", orderSerial);

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        Object data = baseVo.get("data");
        return new ShipBaseVo(status,message,data);
    }

    /**
     * 订单确认付款
     *
     * @param profileActive 服务器环境
     * @param orderSerial   　订单号
     * @param tradeNo   　商家付款订单号
     * @param callbackTradeNo   　支付平台返回的流水号
     * @param payType   　支付方式： 1=支付宝  2=微信  3=哆啦宝支付宝  4=哆啦宝微信
     * @return
     */
    public static ShipBaseVo<ConfirmPayVo> confirmPay(String profileActive,
                                                      String orderSerial,
                                                      String tradeNo,
                                                      String callbackTradeNo,
                                                      String payType) {
        String url = "?method=orders&edit=TKCommit&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderSerial", orderSerial);
        paramMap.put("trade_no", tradeNo);
        paramMap.put("callback_trade_no", callbackTradeNo);
        paramMap.put("pay_type", payType);

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        if(status==1){
            String data = baseVo.getString("data");
            try{
                ConfirmPayVo createOrderVo = JSON.parseObject(data, new TypeReference<ConfirmPayVo>() {
                });
                return new ShipBaseVo<>(status,message,createOrderVo);
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 退票，　　只支持全部退和单张退
     * 单张id　需要查询订单接口拿到
     * @param profileActive　服务器环境
     * @param refundType　取消类型　1：船票订单全部退票　2：船票部份退票
     * @param tkdtID　单张票据id,船票部份退款时必填
     * @param orderSerial 订单号
     * @param vBackReason 退票原因 客人填/客服填 例子: 不想去了/改期
     * @return
     */
    public static ShipBaseVo refundTicket(String profileActive,
                                          Integer refundType,
                                          Integer tkdtID,
                                          String orderSerial,
                                          String vBackReason) {
        String url = "?method=orders&edit=refund&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("refundType", refundType);
        paramMap.put("TkdtID", tkdtID);
        paramMap.put("orderSerial", orderSerial);
        paramMap.put("vBackReason", vBackReason);

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        Object data = baseVo.get("data");
        return new ShipBaseVo(status,message,data);
    }


    /**
     * 订单详情
     *
     * @param profileActive 服务器环境
     * @param orderSerial   　订单号
     * @param tradeNo   　OTA订单号,选填
     * @return
     */
    public static ShipBaseVo<OrderDetailVo> orderDetail(String profileActive, String orderSerial,String tradeNo) {
        String url = "?method=orders&edit=checkOrder&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderSerial", orderSerial);
        if(tradeNo!=null){
            paramMap.put("trade_no", tradeNo);
        }

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        if(status==1){
            String data = baseVo.getString("data");
            try{
                OrderDetailVo orderDetailVo = JSON.parseObject(data, new TypeReference<OrderDetailVo>() {
                });
                return new ShipBaseVo<>(status,message,orderDetailVo);
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }
    //测试订单详情
    /*public static void main(String[] args) {
        ShipBaseVo<OrderDetailVo> shipBaseVo = orderDetail("test","1215082191048114178","KL20010080test");
        System.out.println(shipBaseVo.getStatus());
        System.out.println(shipBaseVo.getMessage());
        System.out.println(shipBaseVo.getData().getStatus());
    }*/

    /**
     * 查询退票费率
     * @param profileActive　服务器环境
     * @param orderSerial   系统订单号
     * @param tkdtID    单张票，可不填
     * @param flag  查询标识　1 用户查询 2后台查询
     * @return
     */
    public static ShipBaseVo<RefundPriceVo> getRefundPrice(String profileActive, String orderSerial,Integer tkdtID,Integer flag) {
        String url = "?method=orders&edit=TKGetBackTicketAmount&sign=%s&key=%s";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderSerial", orderSerial);
        paramMap.put("TkdtID", tkdtID);
        paramMap.put("Flag", flag);

        JSONObject baseVo = post(profileActive, url, paramMap);
        int status = baseVo.getIntValue("status");
        String message = baseVo.getString("message");
        if(status==1){
            String data = baseVo.getString("data");
            try{
                RefundPriceVo refundPriceVo = JSON.parseObject(data, new TypeReference<RefundPriceVo>() {
                });
                return new ShipBaseVo<>(status,message,refundPriceVo);
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }









    /**
     * post 请求封装
     *
     * @param profileActive 　服务器环境类型
     * @param url           url请求参数
     * @param params        body　参数
     * @return
     */
    public static JSONObject post(String profileActive, String url, Map<String, Object> params) {
//        ApiParam apiParam = profileMap.get(profileActive);
//        log.info("配置获取=>{}",JSONObject.toJSONString(apiParam));
        ApiParam apiParam;
        if("prod".equals(profileActive)){
            apiParam = new ApiParam("https://otaapi.laiu8.cn/ticket/tickets","esf8a31b3brgfwgwdf8dges16af8cdfwf","f5d655sfa7sfsfwefwafaa409sf89d462b");
        }else{
//            apiParam = new ApiParam("https://otaapi.laiu8.cn/ticket/tickets","esf8a31b3brgfwgwdf8dges16af8cdfwf","f5d655sfa7sfsfwefwafaa409sf89d462b");
            apiParam = new ApiParam("http://webtestota.laiu8.cn/ticket/tickets","esf8a31b3brgfwgwdf8dge16a8cd71bf","f5d655e847sfsfwefwafaa409sf89d462b");
        }
        String apiUrl = apiParam.apiUrl;
        String key = apiParam.key;
        String secret = apiParam.secret;



        String sign = getSign(params, secret);

        url = String.format(apiUrl + url, sign, key);
        log.info("OTA URL,{}",url);
        String result = HttpUtil.post(url, JSON.toJSONString(params));

        TypeUtils.compatibleWithJavaBean = true;
        JSONObject baseVo;
        try{
            baseVo = JSON.parseObject(result);
            return baseVo;
        }catch (Exception e){
            log.error(result);
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            String message = jsonObject.getString("message");
//            if(message.contains("传入票价不对")){
//                return null;
//            }else {
//                throw new BusinessException(6,message);
//            }
        }
        return null;


    }


    /**
     * 获取加密参数
     *
     * @return
     */
    public static String getSign(Map<String, Object> dicsrc, String secret) {
        HashMap<String, Object> dic = new HashMap<>(dicsrc);
        log.debug("dic -> {}", dic);
        int length = 0;
        List<String> list = new ArrayList<>();
        for (String key : dic.keySet()) {
            Object valueObj = dic.get(key);
            String value;
            if (valueObj instanceof List) {
                value = "Array";
            } else {
                value = String.valueOf(valueObj);
            }

            if (value.length() > length) {
                length = value.length();
            }
            dic.put(key, value);
        }

        for (String key : dic.keySet()) {
            Object valueObj = dic.get(key);
            String value = String.valueOf(valueObj);
            String tmp = padRight(value, length, ' ') + key;
            list.add(tmp);
        }

        list.sort(Comparator.reverseOrder());
        StringBuilder fieldsb = new StringBuilder();
        for (String param : list) {
            String tmpkey = param.substring(length, param.length() - length + length);
            fieldsb.append(tmpkey).append(dic.get(tmpkey));
        }
        String field = fieldsb.toString();
        log.debug("field -> {}", field);
        String sign = field.toUpperCase();
        sign = SecureUtil.md5(SecureUtil.md5(sign).toUpperCase() + secret);
        log.debug("sign -> {}", sign);
        return sign;
    }

    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }


    /**
     * 获取船票的详细信息
     * @param profileActive 运行环境
     * @param iStart 出发港Id
     * @param iArrive 到达港Id
     * @param dDate 出发日期
     * @param lineId 航线Id
     * @param cabinName 船舱名称
     * @param shipId 船Id
     * @return
     */
    public static ShiftInfo getShipLineTicketInfo(String profileActive,
                                                  String iStart,
                                                  String iArrive,
                                                  String dDate,
                                                  String lineId,
                                                  String cabinName,
                                                  String shipId,
                                                  String startTime){

        try {
        ShipBaseVo<ShiftData> baseVo = getShift(profileActive,iStart,iArrive,dDate);
        ShiftData shift = baseVo.getData();
            if(shift == null ){
                return null;
            }
            List<ShiftInfo> flight = shift.getFlight();
            if(CollectionUtils.isEmpty(flight)){
                return null;
            }
            List<ShiftInfo> collect = flight.stream().filter(
                    x -> lineId.equals(x.getLineID()) &&
                    cabinName.equals(x.getCabinName()) &&
                    shipId.equals(x.getShipID()) &&
                    startTime.equals(x.getClTime())
            ).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(collect)){
                log.error("实时查询船票信息失败:profile=>{},isStart=>{},iArrive=>{},dDate=>{},lineId=>{}，cabinName=>{},shipId=>{},startTime=>{},接口返回结果=>{}",
                        profileActive,iStart,iArrive,dDate,lineId,cabinName,shipId,startTime,JSONObject.toJSONString(shift));
                return null;
            }

            ShiftInfo shiftInfo = collect.get(0);
            return shiftInfo;
        }catch (Exception e){

            log.error("获取船票详细信息错误:",e);
            return null;
        }

    }

    /**
     * 支付船票订单
     * @param title 商品名称
     * @param orderCode 订单号 统一船票API订单号 ， 加密串  由  订单号^金额^商户编码^小程序序号 组成并加密 小程序序号 1内部订票 2驻岛单位
     * 举例：201904260002^0.01^code^1
     * @param orderPrice 订单金额
     * @param mchId 商户编码
     * @param appletNo 小程序序号
     * @param callback 回调地址 {"amount":0.01,"msg":"操作成功",
     * "order_id":"201904260003","pay_time":"2019-04-26 16:14:45","status":1,
     * "sign":"k\/Un4vraCWQL2lVV1AJ3RldZ6szpjgnug\/\/GknmS4TOlaqDl+wFTY\/
     * lokRZlV1UWY3V9pXmwOmvtGPfY3Rdh6w=="}
     * @param jscode 微信登录成功后返回的 code  需要用 放到数组里
     * dfnKUgEqRT2oS/jCZUNFOKTdKm/X60A8U8JBxGk2Hg=
     * @param code 商户编码 不参与签名
     * @return
     */
    public static Map<String,Object> payShipOrder(String title,
                                                  String orderCode,
                                                  String orderPrice,
                                                  String mchId,
                                                  String appletNo,
                                                  String callback,
                                                  String jscode,
                                                  String code) throws Exception {
        //参数加密分隔符
        String delimiter = "^";

        StringBuilder stringBuilder = new StringBuilder();
        //需要加密的字符串
        StringBuilder tokenStr  = stringBuilder
                .append(orderCode)
                .append(delimiter)
                .append(orderPrice)
                .append(delimiter)
                .append(mchId)
                .append(delimiter)
                .append(appletNo);

//        String token = encrypt(tokenStr.toString());


        return null;
    }


//    public static void main(String[] args) throws Exception {
//        String mchId= "1325029701";
////        payShipOrder()
////        String s = signRSA(mchId);
////        System.out.println(s);
//        String token = "201904260002^0.01^code^1";
//        String encrypt = encryptRSA(token);
//
//        System.out.println(encrypt);
//
//    }


    /**
     * 签名
     * @param token
     * @return
     * @throws Exception
     */
    public static  String signRSA(String token) throws Exception{
        byte[] data = token.getBytes();
        String path = "D:\\work\\code\\source\\xizangproject\\mall\\src\\main\\resources\\conf\\thirdparty\\private.key";
        String privateKey = FileUtil.readUtf8String(path);
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        //获取私钥
        PrivateKey generatePrivate = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //使用私钥进行数字签名
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(generatePrivate);
        signature.update(data);


        return (new BASE64Encoder()).encodeBuffer(signature.sign());
    }

    /**
     * 加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encryptRSA(String str) throws Exception{
        InputStream stream = LalyoubaShipHttpApiUtil.class.getResourceAsStream("/conf/thirdparty/public2.key");

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        String key = result.toString(StandardCharsets.UTF_8.name());
//        String key = Arrays.toString(ByteStreams.toByteArray(stream));
//        String path = "D:\\work\\code\\source\\xizangproject\\mall\\src\\main\\resources\\conf\\thirdparty\\public2.key";
        //base64编码
        String strUTF8  = new BASE64Encoder().encode(str.getBytes("UTF-8"));
        byte[] data = (new BASE64Decoder()).decodeBuffer(strUTF8);
//        String key = FileUtil.readUtf8String(path);
        //对公钥解密
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
        //获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] rs = cipher.doFinal(data);

        return new BASE64Encoder().encodeBuffer(rs);


    }



}
