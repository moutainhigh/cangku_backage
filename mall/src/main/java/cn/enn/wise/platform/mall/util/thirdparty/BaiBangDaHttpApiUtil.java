package cn.enn.wise.platform.mall.util.thirdparty;

import cn.enn.wise.platform.mall.bean.param.BBDAddOrderDTO;
import cn.enn.wise.platform.mall.bean.param.BBDRefundOrderDTO;
import cn.enn.wise.platform.mall.bean.param.UpdateSeaTicketApprovalStatusDTO;
import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * 佰邦达售票api接口
 */
@Slf4j
public class BaiBangDaHttpApiUtil {


    static HttpClient client = wrapClient();
    static String cookie = null;
    static String charSet = "UTF-8";
    static String profileActive;

    private static  HttpClientBuilder httpClientBuilder;


    static{
        try {
            SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            httpClientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * cookie存储 对象
     */
    private static CookieStore store = new BasicCookieStore();

    @AllArgsConstructor
    static class ApiParam {
        //网络地址
        String apiUrl;
        String name;
        String password;
    }

    static Map<String, ApiParam> profileMap = new HashMap();

    static {
        //加载api变量到系统
        Props lalyoubaShipProps = new Props("conf/thirdparty/BaiBangDa.properties");
        profileActive = lalyoubaShipProps.getStr("profileActive");
        List<String> profileList = Arrays.asList("test", "prod");

        String url = lalyoubaShipProps.getStr(profileActive + ".url");
        String loginName = lalyoubaShipProps.getStr(profileActive + ".name");
        String pass = lalyoubaShipProps.getStr(profileActive + ".password");

        ApiParam apiParam = new ApiParam(url, loginName, pass);
        profileMap.put(profileActive, apiParam);
        if ("test".equals(profileActive)) {
            profileMap.put("integrated", apiParam);
            profileMap.put("local", apiParam);
        }
        login();


    }
    /*public static void main(String[] args) {
       //
        // getGoodsType("1203844402801995777");


        Map<String, String> map = new HashMap<>();
        map.put("userId", 4027+"");


        Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(Long.valueOf(11));
        String url = "http://tx.enn.cn/wzd-distribute-v1/distributor/getbyuserid";//urlConfig.get(AppConstants.DISTRIBUTE_URL)+"/distributor/getbyuserid";
        String str =  HttpClientUtil.post(url, map);
        String a  = str;


    }*/


    /**
     * 登录
     *
     * @return
     */
    public static void login() {
        ApiParam apiParam = profileMap.get(profileActive);
        String apiUrl = apiParam.apiUrl + "loginUser";
        //String url = "https://api.testing.laiu8.cn/loginUser";
        JSONObject paramMap = new JSONObject();
        paramMap.put("loginName", apiParam.name);
        paramMap.put("password", apiParam.password);
        paramMap.put("source", "OTA");
        paramMap.put("userVerifyCode", "1");
        paramMap.put("verifyCode", "1");
        try {
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(apiUrl));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json");
            //StringEntity entity = new StringEntity("{\"loginName\": \"6666\",\"password\": \"tangchenghai\",\"source\": \"OTA\",\"userVerifyCode\": 1,\"verifyCode\": 1}", charSet);
            StringEntity entity = new StringEntity(paramMap.toString(), charSet);
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            String str = response.getEntity().toString();
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                if (header.getName().equals("Set-Cookie")) {
                    cookie = header.getValue();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

//        scheduledExecutorService.schedule(()->{
//            login(profileActive);
//        },2L, TimeUnit.HOURS);

    }

    /**
     * 获取商品详情
     *
     * @param profileActive
     * @return
     */

    public static String getGoodsInfo(String profileActive, String goodsId) {
        String url = "pub/seaCenter/seaProduct/details";
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsId);
        String result = postCookie(url, map);
        return result;
    }


    public static HttpClient wrapClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public static String post(String url, Map<String, Object> params) {
        ApiParam apiParam = profileMap.get(profileActive);
        //log.info("配置获取=>"+ com.alibaba.fastjson.JSONObject.toJSONString(apiParam));
        String apiUrl = apiParam.apiUrl + url;
        // 实例化HTTP方法
        try {
            if (cookie == null) {
                login();
            }
            HttpPost request1 = new HttpPost();
            request1.setURI(new URI(apiUrl));
            request1.addHeader("Cookie", cookie);
            request1.setHeader("Accept", "application/json");
            request1.setHeader("Content-Type", "application/json");
            StringEntity entity1 = new StringEntity(JSON.toJSONString(params), charSet);
            request1.setEntity(entity1);
            log.info("请求路径：{}\nHEADER：{}", request1.toString(), request1.getAllHeaders());
            HttpResponse response1 = client.execute(request1);
            String str = EntityUtils.toString(response1.getEntity());
            com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(str);
            String code = object.get("code").toString();
            //处理cookie超时问题
            if ("4".equals(code)) {
                login();
                request1.addHeader("Cookie", cookie);
                HttpResponse response2 = client.execute(request1);
                return EntityUtils.toString(response2.getEntity());
            }
            return str;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 带cookie参数的post访问
     * @param url
     * @param params
     * @return
     */

    public static String postCookie(String url, Map<String, Object> params) {
        ApiParam apiParam = profileMap.get(profileActive);
        String apiUrl = apiParam.apiUrl + url;
        // 实例化HTTP方法
        try {
            CloseableHttpClient closeableHttpClient = httpClientBuilder.setDefaultCookieStore(store).build();

            //请求参数
            HttpPost httpPost = new HttpPost(apiUrl);
            log.info("当前请求接口=>{}",apiUrl);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            String paramsJSON = JSON.toJSONString(params);
            StringEntity stringEntity = new StringEntity(paramsJSON, charSet);
            log.info("接口请求参数=>{}",paramsJSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            String str = EntityUtils.toString(closeableHttpResponse.getEntity());
            log.info("返回值=>{}",str);
            JSONObject jsonObject = JSONObject.parseObject(str);
            if("4".equals(jsonObject.getString("code"))){
                //当前需要登录、
                log.info("当前需要登录:=>");
                String loginUrl = apiParam.apiUrl + "loginUser";
                //String url = "https://api.testing.laiu8.cn/loginUser";
                JSONObject paramMap = new JSONObject();
                paramMap.put("loginName", apiParam.name);
                paramMap.put("password", apiParam.password);
                paramMap.put("source", "OTA");
                paramMap.put("userVerifyCode", "1");
                paramMap.put("verifyCode", "1");
                HttpPost httpPostLogin = new HttpPost(loginUrl);
                log.info("当前请求接口=>{}",loginUrl);
                httpPostLogin.setHeader("Accept", "application/json");
                httpPostLogin.setHeader("Content-Type", "application/json");
                String paramsJSONLogin  =paramMap.toJSONString();
                StringEntity stringEntityLogin  = new StringEntity(paramMap.toString(), charSet);
                log.info("接口请求参数=>{}",paramsJSONLogin);
                httpPostLogin.setEntity(stringEntityLogin);
                CloseableHttpResponse closeableHttpResponseLogin  = closeableHttpClient.execute(httpPostLogin);
                String strLogin  = EntityUtils.toString(closeableHttpResponseLogin.getEntity());
                log.info("返回值=>{}",strLogin);
                log.info("登录结束。存储cookie");
                List<Cookie> cookies = store.getCookies();
                for (Cookie cookie1 : cookies) {
                    log.info("当前cookie:,name={},value={}",cookie1.getName(),cookie1.getValue());
                }
                postCookie(url,params);
            }else {
                return str;
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }





    /**
     * 执行百邦达下单
     *
     * @return
     */
    public static com.alibaba.fastjson.JSONObject synchronizeAddOrder(BBDAddOrderDTO bbdAddOrderDTO) {
        final String url = "pub/seaCenter/seaOrder/addSeaOrderForApp";

        Map<String, Object> params = new HashMap();
        params.put("payment", bbdAddOrderDTO.getPayment());
        params.put("productId", bbdAddOrderDTO.getProductId());
        params.put("ticketTypeInfoList", bbdAddOrderDTO.getTicketTypeInfoList());
        params.put("contact", bbdAddOrderDTO.getContact());
        params.put("custId", bbdAddOrderDTO.getCustId());
        params.put("departureDate", bbdAddOrderDTO.getDepartureDate());
        params.put("memo", bbdAddOrderDTO.getMemo());
        params.put("mobile", bbdAddOrderDTO.getMobile());
        params.put("payType", bbdAddOrderDTO.getPayType());

        String rst = postCookie(url, params);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rst);
        return jsonObject;
    }



    /**
     * 根据本地OrderTicketID获取百邦达票（订单）详情
     *
     * @param ticketId
     * @return
     */
    public static com.alibaba.fastjson.JSONObject getBbdTicketDetail(Long ticketId) {

        final String url = "pub/seaCenter/seaOrder/queryTicketInfoByAppTicketId";
        Map<String, Object> params = new HashMap();
        params.put("appTicketId", ticketId);
        String rst = postCookie(url, params);
        log.info("====> 百邦达数据返回：{}", rst);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rst);
        return jsonObject;

    }

    /**
     * 执行退票通知，将本地退票通知到百邦达退票
     *
     * @param refundOrderDTO
     * @return
     */
    public static com.alibaba.fastjson.JSONObject notifyBbdTicketRefund(BBDRefundOrderDTO refundOrderDTO) {
        final String url = "pub/seaCenter/seaOrder/refundTicket";
        Map<String, Object> params = new HashMap();
        params.put("orderId", refundOrderDTO.getOrderId());
        params.put("payMethod", refundOrderDTO.getPayMethod());
        params.put("ticketRefundList", refundOrderDTO.getTicketRefundList());
        String rst = postCookie(url, params);
        log.info("====> 百邦达数据返回：{}", rst);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rst);
        return jsonObject;
    }

    public static String getGoodsList() {
        String url = "pub/seaCenter/seaProduct/querySeaProductForSale";

        Map<String, Object> map = new HashMap<>();
        map.put("current", "1");
        map.put("size", "100");

        return postCookie(url, map);
    }

    public static String getGoodsFlightList(String flightDate, String flightTime, String productId) {
        String url = "pub/seaCenter/seaFlight/queryFlightForSale";

        Map<String, Object> map = new HashMap<>();
        map.put("flightDate", flightDate);
        map.put("flightTime", flightTime);
        map.put("productId", productId);
        return postCookie(url, map);
    }

    public static String getGoodsType(String key) {
        String url = "pub/basicDataCenter/constant/querySeaProduct";
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        String str = postCookie(url, map);
        com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(str);
        String data = object.get("data").toString();
        com.alibaba.fastjson.JSONObject object1 = com.alibaba.fastjson.JSONObject.parseObject(data);
        return object1.get("value").toString();
    }


    /**
     * 更新审批状态
     **/
    public static com.alibaba.fastjson.JSONObject approvalsRefundSts(UpdateSeaTicketApprovalStatusDTO updateSeaTicketApprovalStatusDTO) {
        final String url = "pub/seaCenter/seaOrder/updateSeaTicketApprovalStatusForApp";
        Map<String, Object> params = new HashMap();
        params.put("ticketIds", updateSeaTicketApprovalStatusDTO.getTicketIds());
        params.put("approvalStatus", updateSeaTicketApprovalStatusDTO.getApprovalStatus());
        String rst = postCookie(url, params);
        log.info("====> 百邦达数据返回：{}", rst);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rst);
        return jsonObject;
    }


    public static com.alibaba.fastjson.JSONObject getBBDOrderDetail(String orderCode) {

        final String url = "pub/seaCenter/seaOrder/details";
        Map<String, Object> params = new HashMap();
        params.put("orderId", orderCode);
        String rst = postCookie(url, params);
        log.info("====> 百邦达数据返回：{}", rst);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rst);
        return jsonObject;

    }

}
