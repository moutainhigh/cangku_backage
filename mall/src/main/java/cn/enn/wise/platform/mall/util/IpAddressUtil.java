/**  
 * Project Name:snake-web  
 * File Name:IpAddressUtil.java  
 * Package Name:com.bocom.snake.core.util  
 * Date:2017年10月24日上午11:44:50  
 * Fixed by whz in 2017/10/24 
 *  
*/  
  
package cn.enn.wise.platform.mall.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**  
 * 提取ip
 * ClassName: IpAddressUtil    
 * date: 2017年10月24日 上午11:44:50 
 * @author whz   
 * @since JDK 1.8 
 */
public class IpAddressUtil {
	/**
	 * 
	 * 通过HttpServletRequest获取客户端ip;
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()原因是有可能用户使用了代理软件方式避免真实IP地址.
	 * 这是需要取X-Forwarded-For的值获取ip，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，
	 * 而是一串IP值，取X-Forwarded-For中第一个非unknown的有效IP即为真实ip) 
	 * @author whz  
	 * @param request HttpServletRequest客户端请求
	 * @return 客户端ip
	 * @since JDK 1.8
	 */
	public static String getIp(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (!checkIp(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        } 
        if (!checkIp(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (!checkIp(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (!checkIp(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (!checkIp(ip)) {  
            ip = request.getRemoteAddr();  
        } 
        //处理windows上用localhost访问时解析ip的表现形式是ipv6
        if("0:0:0:0:0:0:0:1".equals(ip)){
        	ip = getLocalhostIp();
        }
        return ip;  
    }  
	/**
	 * 检查ip是否合法
	 * @author whz  
	 * @param ip
	 * @return  
	 * @since JDK 1.8
	 */
	private static boolean checkIp(String ip){
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			return false;
		else 
			return true;
	}
	/**
	 * 
	 * 获取本地ip 
	 * @author whz  
	 * @return  
	 * @throws UnknownHostException 
	 * @since JDK 1.8
	 */
	public static String getLocalhostIp(){
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		return localHost.getHostAddress();
	}
}
  
