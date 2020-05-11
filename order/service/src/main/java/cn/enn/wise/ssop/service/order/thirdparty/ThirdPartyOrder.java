package cn.enn.wise.ssop.service.order.thirdparty;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * 深大票务系统
 */
@Data
public class ThirdPartyOrder {

    /**
     * 商品价格
     */
    long productPrice;
    /**
     * 数量
     */
    int amount;

    /**
     * 下单日期
     */
    String day;
    /**
     * 身份证号码
     */
    String idNumber;
    /**
     * 订单名称
     */
    String orderName;
    /**
     * 订单手机号
     */
    String orderPhone;
    /**
     * 订单编号
     */
    String orderCode;
    /**
     * 入园时间
     */
	String enterTime;
    /**
     * 产品编码
     */
	String productCode;
    /**
     * 商品名称
     */
	String productName;
    /**
     * 景区id
     */
	Long scenicId;

    /**
     * 游客
     */
    private List<ThirdPartyOrderItem> items;
}
