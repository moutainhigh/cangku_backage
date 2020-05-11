package cn.enn.wise.platform.mall.util.WX;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/3 13:55
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:楠溪江配置
 ******************************************/
public class NXJConfig extends WXPayConfig {


    @Override
    public String getAppID() {
        return "wxcdbacc55093a60d0";
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
    public String getAppSecret() {
        // ab1c97a5f232fec3b26c76ee0b8a95c1
        return "ec302860e653b2927a8106f9e10f8c19";
    }

    @Override
    public InputStream getCertStream() {
        try {
            return new FileInputStream(new File("/data/weixin_nxj_cert/apiclient_cert.p12"));
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
