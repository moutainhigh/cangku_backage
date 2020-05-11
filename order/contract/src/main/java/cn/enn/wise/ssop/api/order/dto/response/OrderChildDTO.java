package cn.enn.wise.ssop.api.order.dto.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 二销订单子表DTO
 *
 * @author baijie
 * @date 2019-12-30
 */
@Data
public class OrderChildDTO {

    /**
     * 主键Id
     */
    private Long id ;

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     *商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 退款状态 1 不退款  2退款中 3 退款失败 4 退款成功
     */
    private Byte refundStatus;

    /**
     * sku名称
     */
    private String skuName;
}
