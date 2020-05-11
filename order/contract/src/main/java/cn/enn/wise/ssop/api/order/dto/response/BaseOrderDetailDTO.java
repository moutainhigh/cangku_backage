package cn.enn.wise.ssop.api.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道方订单详情
 *
 * @author baijie
 * @date 2019-12-12
 */
@Data
@ApiModel(description = "订单详情基础信息")
public class BaseOrderDetailDTO {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long id;

    /**
     * 会员昵称
     */
    @ApiModelProperty(value = "会员昵称")
    private String memberName;

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
     *订单类型  1 普通订单
     */
    @ApiModelProperty(value = "订单类型  1 普通订单")
    private Byte type;

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
     * 订单创建时间
     */
    @ApiModelProperty(value = "订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Date createTime;

    /**
     * 订单更新时间
     */
    @ApiModelProperty(value = "订单更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Date updateTime;

    /**
     * 订单支付时间
     */
    @ApiModelProperty(value = "订单支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Date payTime;

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

    @ApiModelProperty("合作伙伴用户Id")
    private Long threeMemberId;

}
