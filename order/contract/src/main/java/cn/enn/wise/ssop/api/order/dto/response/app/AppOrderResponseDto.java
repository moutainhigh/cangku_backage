package cn.enn.wise.ssop.api.order.dto.response.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class AppOrderResponseDto {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 渠道channelId
     */
    @ApiModelProperty(value = "渠道channelId")
    private Long channelId;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    /**
     * 第三方订单号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 订单类型  1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单
     */
    @ApiModelProperty(value = "订单类型  1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单")
    private Byte orderType;

    /**
     * 订单来源 1小程序 2 APP 3 第三方订单 4  H5
     */
    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5")
    private Byte orderSource;

    /**
     * 来源方名称
     */
    @ApiModelProperty(value = "来源方名称")
    private String orderSourceName;

    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private BigDecimal shouldPayPrice;

    /**
     * 实付金额
     */
    @ApiModelProperty(value = "实付金额")
    private BigDecimal actualPayPrice;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal decreasePrice;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPrice;

    /**
     * 订单过期时间
     */
    @ApiModelProperty(value = "订单过期时间")
    private Date expireTime;

    /**
     * 订单支付时间
     */
    @ApiModelProperty(value = "订单支付时间")
    private Date payTime;

    /**
     * 支付方式 1 微信 2 支付宝  3 云闪付
     */
    @ApiModelProperty(value = "支付方式 1 微信 2 支付宝  3 云闪付")
    private Byte payType;

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
    private Integer goodsPrice;

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
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 联系人证件号
     */
    @ApiModelProperty(value = "联系人证件号")
    private String certificateNo;

    /**
     * 联系人证件类型
     */
    @ApiModelProperty(value = "联系人证件类型")
    private Byte certificateType;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer payStatus;

    /**
     * 交易状态
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
    @ApiModelProperty(value = "退款状态")
    private Integer systemStatus;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String extraInformation;

    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    private String payOrderNo;

    /**
     * 请求流水号，标识每次http请求
     */
    @ApiModelProperty("请求流水号，标识每次http请求")
    private String applyId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
