package cn.enn.wise.ssop.service.order.thirdparty.shenda;

import cn.enn.wise.ssop.service.order.thirdparty.IThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrderItem;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.common.http.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author anhui257@163.com
 */
@Slf4j
public class ShendaOrder implements IThirdPartyOrder {
	private static final String URL = "http://ifdist.zhiyoubao.com/boss/service/code.htm";
	/**
	 * 深大下单
	 *
	 * @param order
	 * @return
	 */
	@Override
	public R order(ThirdPartyOrder order) {
		return issue(order.getProductPrice(), order.getAmount(), order.getDay(),
				order.getIdNumber(), order.getOrderName(), order.getOrderPhone(),
				order.getOrderCode(), order.getItems(), order.getEnterTime(),
				order.getProductCode(), order.getProductName(), order.getScenicId());
	}


	/**
	 * @param productPrice
	 * @param amount
	 * @param day          预定日期
	 * @param idNumber     身份证
	 * @param orderName    订单名称
	 * @param orderPhone   订单来呢西人
	 * @param orderCode    订单编号
	 * @param orderItems
	 * @param enterTime    入园时间
	 * @param productCode  商品编码（深大智能编码）
	 * @param productName  商品名称
	 * @param scenicId     景区id
	 * @return
	 */
	public R issue(long productPrice, int amount, String day, String idNumber, String orderName, String orderPhone, String orderCode,
				   List<ThirdPartyOrderItem> orderItems, String enterTime, String productCode, String productName, Long scenicId) {
		try {
			ShendaOrder ticketHeader = Adapter.TICKET_MAP.get(scenicId);
			if (ticketHeader == null) {
				throw new RuntimeException("not found ticket header for scenic " + scenicId);
			}
			Conf conf = Adapter.TICKET_CONF_MAP.get(scenicId);
			if (conf == null) {
				throw new RuntimeException("not found ticket conf for scenic " + scenicId);
			}
			ShendaConfig config = (ShendaConfig) conf;
			String xmlMsg = xmlMsg(productPrice, amount, day, idNumber, orderName, orderPhone, orderCode, orderItems, enterTime, productCode, productName, config);
			String sign = MD5Util.MD5("xmlMsg=" + xmlMsg + config.getKey());
			String strXml = orderTicket(xmlMsg, sign, URL);
			Map xmlParse = XmlUtil.doXMLParse(strXml, 1);
			String state = (String) xmlParse.get("description");
			if (!GeneConstant.SUCCESS_CHINESE.equals(state)) {
				log.error("issue is error : " + strXml);

				R r = new R();
				r.setData(xmlParse);
				r.setCode(1);
				r.setMessage("三方下单失败");
				return r;
			}
			Map orderMap = (Map) ((Map) xmlParse.get("orderResponse")).get("order");
			return R.success(orderMap);
		} catch (Exception e) {
			log.error("issue error：" + e);
			return null;
		}
	}


	/**
	 * 出票接口
	 *
	 * @param xmlMsg
	 * @param sign
	 * @param url
	 * @return
	 */
	private String orderTicket(String xmlMsg, String sign, String url) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>() {
			private static final long serialVersionUID = 1L;
			{
				add(new BasicNameValuePair("xmlMsg", xmlMsg));
				add(new BasicNameValuePair("sign", sign));
			}
		};
		return HttpClientUtil.post(url, params);
	}

	/**
	 * double转化为String，保留两位小数
	 *
	 * @param d
	 * @return
	 * @throws
	 * @Title douToStr
	 * @since JDK 1.8
	 */
	private static String douToStr(double d) {
		double dd = (Math.round(d * 100)) / 100d;
		return String.valueOf(dd);
	}

	/**
	 * 深大出票接口
	 * @param productPrice
	 * @param amount
	 * @param day
	 * @param idNumber
	 * @param orderName
	 * @param orderPhone
	 * @param orderCode
	 * @param orderItems
	 * @param enterTime
	 * @param productCode
	 * @param productName
	 * @param config
	 * @return
	 */
	private String xmlMsg(long productPrice, int amount, String day, String idNumber, String orderName, String orderPhone, String orderCode
			, List<ThirdPartyOrderItem> orderItems, String enterTime, String productCode, String productName, ShendaConfig config) {
		String totalPrice = douToStr(productPrice / 100d);
		String singlePrice = douToStr(productPrice / 100d / amount);
		String param = "<PWBRequest>" +
							"<transactionName>SEND_CODE_REQ</transactionName>" +
							"<header>" +
									"<application>SendCode</application>" +
									"<requestTime>" + day + "</requestTime>" +
							"</header>" +
							"<identityInfo>" +
									"<corpCode>" + config.getCommerceCode() + "</corpCode>" +
									"<userName>" + config.getCommerceName() + "</userName>" +
							"</identityInfo>" +
							"<orderRequest>" +
									"<order>" +
											"<certificateNo>" + idNumber + "</certificateNo>" +
											"<linkName>" + orderName + "</linkName>" +
											"<linkMobile>" + orderPhone + "</linkMobile>" +
											"<orderCode>" + orderCode + "</orderCode>" +
											"<orderPrice>" + totalPrice + "</orderPrice>" +
											"<groupNo></groupNo>" +
											"<payMethod>" + "</payMethod>" +
											"<ticketOrders>" +
														"<ticketOrder>" +
															"<orderCode>" + orderCode + "CD" + "</orderCode>" +
															"<credentials>" + getCredentials(orderItems) + "</credentials>" +
															"<price>" + singlePrice + "</price>" +
															"<quantity>" + amount + "</quantity>" +
															"<totalPrice>" + totalPrice + "</totalPrice>" +
															"<occDate>" + enterTime + "</occDate>" +
															"<goodsCode>" + productCode + "</goodsCode>" +
															"<goodsName>" + productName + "</goodsName>" +
															"<remark>" + productName + "</remark>" +
														"</ticketOrder>" +
											"</ticketOrders>" +
									"</order>" +
							"</orderRequest>" +
						"</PWBRequest>";
		return param;
	}

	/**
	 * 获取身份证及用户名
	 * @param childOrders
	 * @return
	 * @throws
	 * @Title getCredentials
	 * @since JDK 1.8
	 */
	private String getCredentials(List<ThirdPartyOrderItem> childOrders) {
		String str = "";
		for (ThirdPartyOrderItem childOrder : childOrders) {
			str += "<credential><name>" + childOrder.getConsumerName() + "</name><id>" + childOrder.getIdNumber() + "</id></credential>";
		}
		return str;
	}
}
