package cn.enn.wise.platform.mall.util.WX;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/6/12 13:03
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:参数配置类:勿动
 ******************************************/
public class XZConfig extends WXPayConfig {

    @Override
    public String getAppID() {
        return "wx73466b979c886373";
    }

    @Override
    public String getMchID() {
        return "1504256541";
    }

    @Override
    public String getKey() {
        return "849f35d652f825eb7028369517ee1f87";
    }

    @Override
    public String getAppSecret() {
        return "14bbded952712e3dab5818b1019c6f65";
    }

    @Override
    public InputStream getCertStream() {
        try {
            return new FileInputStream(new File("/data/weixin_xz_cert/apiclient_cert.p12"));
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
