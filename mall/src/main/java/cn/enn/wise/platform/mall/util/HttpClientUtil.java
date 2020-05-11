package cn.enn.wise.platform.mall.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/4/23 15:35
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public class HttpClientUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * httpclient get请求
     *
     * @author whz
     * @param url
     * @param params
     * @return
     * @since JDK 1.8
     */
    public static String get(String url, List<BasicNameValuePair> params) {

        // 创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resStr = null;
        try {
            // 创建URIBuilder
            URIBuilder uri = new URIBuilder(url);
            if (GeneUtil.isNotNullAndEmpty(params)) {
                for (BasicNameValuePair param : params) {
                    // 设置参数
                    uri.addParameter(param.getName(), param.getValue());
                }
            }
            // 创建httpGet对象
            HttpGet hg = new HttpGet(uri.build());
            // 设置请求的报文头部的编码
            hg.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            // 设置期望服务端返回的编码
//			hg.setHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
            // 请求服务
            response = client.execute(hg);
            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 获取返回实例entity
                HttpEntity entity = response.getEntity();
                // 通过EntityUtils的一个工具方法获取返回内容
                resStr = EntityUtils.toString(entity, "utf-8");
            } else {
                LOGGER.error("get request error ,the status is : " + statusCode);
            }
        } catch (Exception e) {
            LOGGER.error("get request error");
        } finally {
            // 关闭response和client
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                LOGGER.error("close io error");
            }
        }
        return resStr;
    }
    /**
     * httpclient get请求
     *
     * @author whz
     * @param url
     * @param params
     * @return
     * @since JDK 1.8
     */
    public static String get(String url) {

        return get(url, new ArrayList<BasicNameValuePair>());
    }
    /**
     * httpPost请求
     * @author whz
     * @param url
     * @return
     * @since JDK 1.8
     */
    public static String post(String url) {
        List<BasicNameValuePair> params = new ArrayList<>();
        return post(url, params);
    }
    /**
     * httpPost请求
     * @author whz
     * @param url
     * @param params
     * @return
     * @since JDK 1.8
     */
    public static String post(String url, List<BasicNameValuePair> params) {
        long startTime = System.currentTimeMillis();
        // 创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建一个post对象
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        String resStr = null;
        try {

            // 包装成一个Entity对象
            StringEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            // 设置请求的内容
            post.setEntity(entity);
            // 设置请求的报文头部的编码
//            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
//			设置期望服务端返回的编码
			post.setHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
            // 执行post请求
            response = client.execute(post);
            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 获取数据
                resStr = EntityUtils.toString(response.getEntity());
            }else {
                LOGGER.error("get request error ,the status is : " + statusCode);
            }
        } catch (Exception e) {
            LOGGER.error("post request error",e);
        } finally {
            try {
                // 关闭response和client
                response.close();
                client.close();
            } catch (Exception e) {
                LOGGER.error("close io error");
            }
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("post used time is "+((endTime-startTime))/1000d+" s");
        return resStr;
    }

    /**
     * httpPost请求
     * @author whz
     * @param url
     * @param params
     * @return
     * @since JDK 1.8
     */
    public static String post(String url, Map<String,String> params) {
        List<BasicNameValuePair> paramPairs = new ArrayList<>();
        Set<Map.Entry<String,String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
            paramPairs.add(pair);
        }
        return post(url,paramPairs);
    }

    /**
     * http get请求
     * @author 82702
     * @param url
     * @param params
     * @param encode：对相应是否设置编码
     * @return
     * @since JDK 1.8
     */
    public static String get(String url, List<BasicNameValuePair> params,boolean iscoding,String encode) {

        // 创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resStr = null;
        try {
            // 创建URIBuilder
            URIBuilder uri = new URIBuilder(url);
            if (GeneUtil.isNotNullAndEmpty(params)) {
                for (BasicNameValuePair param : params) {
                    // 设置参数
                    uri.addParameter(param.getName(), param.getValue());
                }
            }
            // 创建httpGet对象
            HttpGet hg = new HttpGet(uri.build());
            // 设置请求的报文头部的编码
            hg.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            // 设置期望服务端返回的编码
            if(iscoding)
                hg.setHeader(new BasicHeader("Accept", "application/json;charset=" + encode));
            // 请求服务
            response = client.execute(hg);
            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 获取返回实例entity
                HttpEntity entity = response.getEntity();
                // 通过EntityUtils的一个工具方法获取返回内容
                resStr = EntityUtils.toString(entity, "utf-8");
            } else {
                LOGGER.error("get request error ,the status is : " + statusCode);
            }
        } catch (Exception e) {
            LOGGER.error("get request error");
        } finally {
            // 关闭response和client
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                LOGGER.error("close io error");
            }
        }
        return resStr;
    }







    /**
     * http post
     * @author guodong
     * @param url
     * @param headers
     * @param queryString：
     * @since JDK 1.8
     */
    public static String post(String url, List<org.apache.http.NameValuePair> headers, List<org.apache.http.NameValuePair> queryString) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(queryString));
        } catch (UnsupportedEncodingException var21) {
            var21.printStackTrace();
        }

        if(CollectionUtils.isNotEmpty(headers)) {
            headers.stream().forEach((header) -> {
                httpPost.setHeader(header.getName(), header.getValue());
            });
        }

        CloseableHttpResponse response = null;

        try {
            LOGGER.info("headers:{}", httpPost.getAllHeaders());
            response = httpclient.execute(httpPost);
        } catch (IOException var20) {
            var20.printStackTrace();
        }

        String result = null;

        try {
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException | ParseException var18) {
            var18.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }

        }

        return result;
    }

    /**
     * 从request中获取IP
     * 用一句话描述这个方法的作用及注意事项
     * @author 82702
     * @return
     * @since JDK 1.8
     */
    public static String getIp(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");
//		logger.info("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            logger.info("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            logger.info("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            logger.info("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            logger.info("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            logger.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            logger.info("getRemoteAddr ip: " + ip);
        }
//        logger.info("获取客户端ip: " + ip);
        return ip;
    }


    /**
     * @author haogd
     * @return
     * @since JDK 1.8
     */
    public static String httpPostWithJSON(String url,JSONObject jsonObject) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        System.out.println();

        HttpResponse resp = client.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }
        return respContent;
    }

}
