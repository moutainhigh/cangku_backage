package cn.enn.wise.ssop.service.order.utils;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * http协议客户端，支持普通请求与SSL请求
 */
public class HttpClient {

    // 连接超时
    private static final int CONNECT_TIMEOUT = 7000;

    // 端口连接超时
    private static final int SOCKET_TIMEOUT = 7000;

    // 最大连接数
    private static final int MAX_CONN = 50;

    //
    private static final int MAX_PER_ROUTE= 10;

    private static final int MAX_ROUTE = 20;

    // 发送请求的客户端单例
    private static CloseableHttpClient httpClient;

    // 连接池管理类
    private static PoolingHttpClientConnectionManager manager;

    //
    private static ScheduledExecutorService monitorExecutor;

    private final static Object syncLock = new Object();



    private static void setRequestConfig(HttpRequestBase httpRequestBase){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        httpRequestBase.setConfig(requestConfig);
    }


    private static CloseableHttpClient getHttpClient(String url){
        String host = url.split("/")[2];
        System.out.println("host:"+host);
        int port = 80;

        if(host.contains(":")){
            String[] args = host.split(":");
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        if(httpClient == null){
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock){
                if (httpClient == null){
                    httpClient = createHttpClient(host, port);
                    //开启监控线程,对异常和空闲线程进行关闭
                    monitorExecutor = Executors.newScheduledThreadPool(1);
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            //关闭异常连接
                            manager.closeExpiredConnections();
                            //关闭5s空闲的连接
                            manager.closeIdleConnections(5, TimeUnit.SECONDS);
                        }
                    }, 30, 30, TimeUnit.SECONDS);
                }
            }
        }
        return httpClient;
    }


    /**
     * 根据host和port构建httpclient实例
     *
     * @param host 要访问的域名
     * @param port 要访问的端口
     * @return
     */
    private static CloseableHttpClient createHttpClient(String host, int port) {
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainSocketFactory).register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(MAX_CONN); // 最大连接数
        manager.setDefaultMaxPerRoute(MAX_PER_ROUTE); // 路由最大连接数

        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);

        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 3) {// 从试超过3次放弃请求
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    // 服务器没有响应，可能是服务器断开了连接，应当从试
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    // 超时
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    return false;
                }
                if (e instanceof SSLException) {
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    // 如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
        return client;

    }



    /**
     * 设置post请求的参数
     * @param httpPost
     * @param params
     */
    private static void setPostParams(HttpPost httpPost, Map<String, String> params){
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for (String key: keys){
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送POST请求
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params, Map<String,String>... headers){
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        setPostParams(httpPost, params);
        CloseableHttpResponse response = null;
        try {
            if(headers != null && headers.length > 0){
                Map<String,String> headerVals = headers[0];
                headerVals.forEach((key,val) ->{
                    httpPost.setHeader(key,val);
                });
            }
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, Charset.defaultCharset());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    /**
     * GET请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String get(String url, Map<String,String> params,Map<String,String>... headers) {
        HttpGet httpGet = new HttpGet(url);
        setRequestConfig(httpGet);
        CloseableHttpResponse response = null;

        if(params != null && params.size() > 0){
            StringBuffer sb = new StringBuffer();
            params.forEach((key,val)->{
                sb.append(key+"="+val+"&");
            });
            String strParams = sb.toString();
            strParams = strParams.substring(0,strParams.length()-1);
            url = url + "?" + strParams;
        }

        if(headers != null && headers.length > 0){
            Map<String,String> headerVals = headers[0];
            headerVals.forEach((key,val) ->{
                httpGet.setHeader(key,val);
            });
        }

        try{
            response = getHttpClient(url).execute(httpGet, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, Charset.defaultCharset());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 关闭连接池
     */
    public static void closeConnectionPool(){
        try {
            httpClient.close();
            manager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
