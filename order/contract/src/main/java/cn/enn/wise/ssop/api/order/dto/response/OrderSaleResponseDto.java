package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderSaleResponseDto{

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 营销策略id
     */
    @ApiModelProperty(value = "营销策略id")
    private Long saleId;

    /**
     * 营销策略类型
     */
    @ApiModelProperty(value = "营销策略类型")
    private Byte saleType;

    /**
     * 营销策略名称
     */
    @ApiModelProperty(value = "营销策略名称")
    private String saleName;

    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    private Long ruleId;

    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    /**
     * 父订单id
     */
    @ApiModelProperty(value = "父订单id")
    private Long parentOrderId;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String extraInformation;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("优惠金额")
    private Integer salePrice;
}
