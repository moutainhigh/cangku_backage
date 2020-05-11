package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 渠道方订单列表数据
 *
 * @author baijie
 * @date 2019-12-12
 */
@Data
@ApiModel(description = "订单列表数据")
public class BaseOrderListDTO {
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
    private Timestamp createTime;

    /**
     * 订单更新时间
     */
    @ApiModelProperty(value = "订单更新时间")
    private Timestamp updateTime;

    /**
     * 订单支付时间
     */
    @ApiModelProperty(value = "订单支付时间")
    private Timestamp payTime;

    /**
     * 订单是否展示在c端 1是 2否
     */
    @ApiModelProperty(value = "订单是否展示在c端 1是 2否")
    private Byte isShow;

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
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty("合作伙伴用户Id")
    private Long threeMemberId;
}
