package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 订单退票审核展示列表
 *
 * @author yangshuaiquan
 * @date 2020-4-28
 */
@Data
public class OrderCancelListDTO {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "商品名")
    private String goodsName;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty("应退金额")
    private BigDecimal refundMoney;

    @ApiModelProperty("应退金额")
    private BigDecimal refundPrice;

    @ApiModelProperty("商品优惠价格")
    private BigDecimal decreasePrice;

    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("顾客名")
    private String customerName;

    @ApiModelProperty(value = "取消订单原因")
    private String refundReasonDesc;

    @ApiModelProperty(value = "有效时间")
    private String effectiveTime;

    @ApiModelProperty(value = "退票申请时间")
    private Timestamp cancelTime;


    @ApiModelProperty(value = "订单附加信息")
    private String extraInformation;
}
