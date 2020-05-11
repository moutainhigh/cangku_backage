package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderDetailResponse {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 会员昵称
     */
    @ApiModelProperty(value = "会员昵称")
    private String memberName;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private Integer channelId;

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

    /**
     * 第三方订单号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;


    /**
     *商品类型
     */
    @ApiModelProperty(value = "商品类型")
    private Byte goodsType;

    /**
     *商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    /**
     *商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     *skuId
     */
    @ApiModelProperty(value = "skuId")
    private Long skuId;

    /**
     *sku名称
     */
    @ApiModelProperty(value = "sku名称")
    private String skuName;

    /**
     *商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer amount;

    /**
     * 订单来源 1小程序 2 APP 3 第三方订单 4  H5
     */
    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5")
    private Byte orderSource;

    /**
     * 订单价格
     */
    @ApiModelProperty(value = "订单价格")
    private BigDecimal orderPrice;

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
     * 数据类型 1 线上数据 2测试数据
     */
    @ApiModelProperty(value = "数据类型 1 线上数据 2测试数据")
    private Byte orderType;

    /**
     * 支付方式 1 微信 2 支付宝  3 云闪付
     */
    @ApiModelProperty(value = "支付方式 1 微信 2 支付宝  3 云闪付")
    private Byte payType;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Byte payStatus;

    /**
     * 交易状态 1交易创建 2 交易关闭 3 交易成功
     */
    @ApiModelProperty(value = "交易状态 1交易创建 2 交易关闭 3 交易成功")
    private Byte transactionStatus;

    /**
     * 评价状态 1待评价 2已评价
     */
    @ApiModelProperty(value = "评价状态 1待评价 2已评价")
    private Byte evaluationStatus;

    /**
     * 订单状态1待付款 2 待使用 3已取消 4已使用 5部分使用 6 退款中 7已退款 8 体验完成
     */
    @ApiModelProperty(value = "订单状态1待付款 2 待使用 3已取消 4已使用 5部分使用 6 退款中 7已退款 8 体验完成")
    private Byte orderStatus;

    /**
     * 订单联系人手机号
     */
    @ApiModelProperty(value = "订单联系人手机号")
    private String phone;

    /**
     * 订单联系人证件号
     */
    @ApiModelProperty(value = "订单联系人证件号")
    private String certificateNo;

    /**
     * 订单联系人证件类型
     */
    @ApiModelProperty(value = "订单联系人证件类型")
    private String certificateType;

    /**
     * 客人姓名
     */
    @ApiModelProperty(value = "客人姓名")
    private String touristName;

    /**
     * 优惠类型 1 不优惠 2 满减 3 优惠券 4 活动
     */
    @ApiModelProperty(value = "优惠类型 1 不优惠 2 满减 3 优惠券 4 活动")
    private Byte couponType;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountPrice;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String extraInformation;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(value = "支付流水号")
    private String payOrderNo;

    @ApiModelProperty("合作伙伴用户Id")
    private Long threeMemberId;

    @ApiModelProperty("使用日期")
    private String useTime;

    @ApiModelProperty("申请编号")
    private String applyId;

    @ApiModelProperty("商家名称")
    private String businessName;

    @ApiModelProperty("商家类型")
    private Byte businessType;

}
