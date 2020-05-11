/**  
 * @Project tourism-tickit
 * @Title Adapter.java   
 * @Package cn.enn.tourism.tickit.core   
 * @author whz     
 * @date 2019年4月14日 下午8:37:53   
 * @version V1.0 
 * @Copyright 2019 All rights Reserved, Designed By whz.
 *  
*/  
  
package cn.enn.wise.ssop.service.order.thirdparty.shenda;

import java.util.HashMap;
import java.util.Map;

/**  
 * 适配类
 * @Description 
 * @ClassName  Adapter    
 * @date  2019年4月14日 下午8:37:53 
 * @author whz  
 * @since JDK 1.8 
 */
public class Adapter {
	/**
	 * 支付适配
	 */
	public static final Map<String, PayHeader> PAY_MAP = new HashMap<>();
	/**
	 * 支付配置
	 */
	public static final Map<Long,Map<String,Conf>> PAY_CONF_MAP = new HashMap<>();
	/**
	 * 出票配置
	 */
	public static final Map<Long,Conf> TICKET_CONF_MAP = new HashMap<>();
	/**
	 * 出票适配
	 */
	public static final Map<Long,ShendaOrder> TICKET_MAP = new HashMap<>();
}
 
