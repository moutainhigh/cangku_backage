package cn.enn.wise.ssop.service.order.wx;

import java.io.InputStream;

/**
 * Created by anhui257@163.com
 */
public class SsopConfig extends WXPayConfig {



    @Override
    public String getAppID() {
        return "wx17727d2732e03351";
    }

    @Override
    public String getMchID() {
        return "1510696711";
    }

    @Override
    public String getKey() {
        return "ySEXoJBkZ8wurAQc6pJjzXRiK9nD6YR2";
    }


    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {


        IWXPayDomain wx =new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                DomainInfo domain= new  DomainInfo("api.mch.weixin.qq.com/",true);
                return domain;
            }
        };
        return wx;

    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return super.getHttpConnectTimeoutMs();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return super.getHttpReadTimeoutMs();
    }

    @Override
    public int getReportQueueMaxSize() {
        return super.getReportQueueMaxSize();
    }

    @Override
    public boolean shouldAutoReport() {
        return super.shouldAutoReport();
    }

    @Override
    public int getReportBatchSize() {
        return super.getReportBatchSize();
    }

    @Override
    public int getReportWorkerNum() {
        return super.getReportWorkerNum();
    }



}
