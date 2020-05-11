package cn.enn.wise.ssop.service.order.feign;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@Data
public class Sale {
    /**
     * 活动id
     */
    private Long activityBaseId;

    /**
     * 营销策略id
     */
    private Long saleId;

    /**
     * 优惠券所属商品id
     */
    private Long skuId;

    /**
     * 优惠券所属人id
     */
    private Long customerId;

    /**
     * 营销策略类型
     */
    private Byte saleType;

    /**
     * 营销策略名称
     */
    private String saleName;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 优惠金额
     */
    private BigDecimal decreasePrice;

    /**
     * 有效日期
     */
    private Date expireTime;
}
