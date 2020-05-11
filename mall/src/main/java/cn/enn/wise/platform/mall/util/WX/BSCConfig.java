package cn.enn.wise.platform.mall.util.WX;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/8/19 10:51
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public class BSCConfig extends WXPayConfig {


    @Override
    public String getAppID() {
        return "wxc545473f261825b0";
    }

    @Override
    public String getMchID() {
        return "1528261671";
    }

    @Override
    public String getKey() {
        return "Xzlybsc600792019Xzlybsc600792019";
    }

    @Override
    public String getAppSecret() {
        return "aaf4e6715e592827da020563082c8113";
    }

    @Override
    public InputStream getCertStream() {
        try {
            return new FileInputStream(new File("/data/weixin_bsc_cert/apiclient_cert.p12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {


        IWXPayDomain wx =new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                DomainInfo domain= new DomainInfo("api.mch.weixin.qq.com/",true);
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
