package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.service.SmsService;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/27 12:24
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:短信Api
 ******************************************/
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {


    /**
     * 短信用户名
     */
    public static final String MESSAGE_USERNAME = "SDK-SKY-010-03001";
    /**
     * 短信密码
     */
    public static final String MESSAGE_PASSWORD = "SDK-SKY-010-03001139990";
    /**
     * 短信验验证码url
     */
    public static final String MESSAGE_URL = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend";


    @Override
    public void sendSms(String mobile, String content) {
        log.info("send sms start,mobile={},content={}",mobile,content);
        try {
            List<NameValuePair> params = getTransitByNameParams(mobile, URLEncoder.encode(content,"utf-8"));
            String post = HttpClientUtil.post(MESSAGE_URL, null,params);
            log.info("send sms result,post={}",post);
        } catch (Exception e) {
            log.error("sendSms throw an exception", e);
        }
    }



    private static List<NameValuePair> getTransitByNameParams(String mobile,String content) {
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            private static final long serialVersionUID = 1L;
            {
                add(new BasicNameValuePair("sn", MESSAGE_USERNAME));
                add(new BasicNameValuePair("pwd", md5(MESSAGE_PASSWORD).toUpperCase()));
                add(new BasicNameValuePair("mobile", mobile));
                add(new BasicNameValuePair("content", content));
                add(new BasicNameValuePair("ext", ""));
                add(new BasicNameValuePair("stime", ""));
                add(new BasicNameValuePair("rrid", ""));
                add(new BasicNameValuePair("msgfmt", ""));
            }
        };
        return params;
    }

    public static String md5(String s){
        char hexDigist[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
            //16个字节的长整数;注意编码造成结果不一样;
            byte[] datas = md.digest(s.getBytes("utf-8"));
            char[] str = new char[2 * 16];
            int k = 0;
            for(int i=0;i<16;i++){
                byte b= datas[i];
                //高4位
                str[k++] = hexDigist[b >>> 4 & 0xf];
                //低4位
                str[k++] = hexDigist[b & 0xf];
            }
            s = new String(str);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.toString());
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        }
        return s;
    }
}
