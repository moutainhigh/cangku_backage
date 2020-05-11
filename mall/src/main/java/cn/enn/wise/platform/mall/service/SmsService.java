package cn.enn.wise.platform.mall.service;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/27 12:34
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description：短信Api
 ******************************************/
public interface SmsService {
	
	void sendSms(String mobile, String content);
}
