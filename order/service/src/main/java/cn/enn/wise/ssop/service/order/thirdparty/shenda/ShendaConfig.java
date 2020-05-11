/**  
 * @Project tourism-tickit
 * @Title ShengdaConfig.java   
 * @Package cn.enn.tourism.tickit.customer   
 * @author whz     
 * @date 2019年4月16日 下午4:45:56   
 * @version V1.0 
 * @Copyright 2019 All rights Reserved, Designed By whz.
 *  
*/  
  
package cn.enn.wise.ssop.service.order.thirdparty.shenda;

/**
 * 票务对接配置
 * @Description 
 * @ClassName  ShengdaConfig    
 * @date  2019年4月16日 下午4:45:56 
 * @author whz  
 * @since JDK 1.8  
 */
public class ShendaConfig implements Conf{
	/**
	 * 企业吗
	 */
	private String commerceCode;
	/**
	 * 用户名
	 */
	private String commerceName;
	/**
	 * 密钥
	 */
	private String key;
	
	public ShendaConfig() {
		super();
	}
	public ShendaConfig(String commerceCode, String commerceName, String key) {
		super();
		this.commerceCode = commerceCode;
		this.commerceName = commerceName;
		this.key = key;
	}
	public String getCommerceCode() {
		return commerceCode;
	}
	public void setCommerceCode(String commerceCode) {
		this.commerceCode = commerceCode;
	}
	public String getCommerceName() {
		return commerceName;
	}
	public void setCommerceName(String commerceName) {
		this.commerceName = commerceName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "ShengdaConfig [commerceCode=" + commerceCode + ", commerceName="
				+ commerceName + ", key=" + key + "]";
	}
}
 
