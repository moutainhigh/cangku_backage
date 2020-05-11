package cn.enn.wise.ssop.api.order.dto.response.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderAppGoodsResponseDto {

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderCode;


    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     *商品单价
     */
    @ApiModelProperty(value = "商品单价")
    private Integer siglePrice;

    /**
     * 商品分类
     */
    @ApiModelProperty(value = "商品分类")
    private Byte goodsType;

    /**
     * 商品skuId
     */
    @ApiModelProperty(value = "商品skuId")
    private Long skuId;

    /**
     * 商品SkuName
     */
    @ApiModelProperty(value = "商品SkuName")
    private String skuName;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer amount;

    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private Byte businessType;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String businessName;


    /**
     *商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal shouldPayPrice;

    /**
     *商品优惠价格
     */
    @ApiModelProperty(value = "商品优惠价格")
    private BigDecimal decreasePrice;

    /**
     *商品退款价格
     */
    @ApiModelProperty(value = "商品退款价格")
    private BigDecimal refundPrice;

    /**
     * 使用日期
     */
    @ApiModelProperty("使用日期")
    private String useTime;

    /**
     * 支付超时时间
     */
    @ApiModelProperty("支付超时时间")
    private Integer payTimeOut;

    /**
     * 使用次数
     */
    @ApiModelProperty("使用次数")
    private Integer useCount;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @ApiModelProperty(value = "支付状态  1 待支付 2 已支付")
    private Integer payStatus;

    /**
     * 交易状态 1交易创建 2 交易关闭 3 交易成功
     */
    @ApiModelProperty(value = "交易状态")
    private Integer transactionStatus;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    /**
     * 系统状态
     */
    @ApiModelProperty(value = "系统状态")
    private Integer systemStatus;

    /**
     * 父订单id
     */
    @ApiModelProperty(value = " 父订单id")
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


}
