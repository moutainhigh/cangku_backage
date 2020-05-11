package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单实体类
 *
 * @author lishuiquan
 */
@Data
@Table(name = "orders")
public class Orders extends TableBase {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;


    /**
     * 订单id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 会员id
     */
    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "会员id")
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 团单id
     */
    @Column(name = "group_order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "团id")
    @ApiModelProperty(value = "团单id")
    private Long groupOrderId;

    /**
     * 渠道channelId
     */
    @Column(name = "channel_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "渠道channelId")
    @ApiModelProperty(value = "渠道channelId")
    private Long channelId;

    /**
     * 渠道类型
     */
    @Column(name = "channel_type",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "渠道类型")
    @ApiModelProperty(value = "渠道类型")
    private String channelType;

    /**
     * 渠道名称
     */
    @Column(name = "channel_name",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "渠道名称")
    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    /**
     * 第三方订单号
     */
    @Column(name = "three_order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "第三方订单号")
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 景区名
     */
    @Column(name = "scenic_name",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "景区名")
    @ApiModelProperty(value = "景区名")
    private String scenicName;

    /**
     * 订单类型  1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单
     */
    @Column(name = "order_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单类型  1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单")
    @ApiModelProperty(value = "订单类型  1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单")
    private Byte orderType;

    /**
     * 订单来源 1小程序 2 APP 3 第三方订单 4  H5
     */
    @Column(name = "order_source",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单来源 1小程序 2 APP 3 第三方订单 4  H5")
    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5")
    private Byte orderSource;

    /**
     * 来源方名称
     */
    @Column(name = "order_source_name",type = MySqlTypeConstant.TINYINT,length = 20,comment = "来源方名称")
    @ApiModelProperty(value = "来源方名称")
    private String orderSourceName;

    /**
     * 应付金额
     */
    @Column(name = "should_pay_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "应付金额")
    @ApiModelProperty(value = "应付金额")
    private BigDecimal shouldPayPrice;

    /**
     * 实付金额
     */
    @Column(name = "actual_pay_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "实付金额")
    @ApiModelProperty(value = "实付金额")
    private BigDecimal actualPayPrice;

    /**
     * 优惠金额
     */
    @Column(name = "decrease_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "优惠金额")
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal decreasePrice;

    /**
     * 退款金额
     */
    @Column(name = "refund_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "退款金额")
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPrice;

    /**
     * 订单过期时间
     */
    @Column(name = "expire_time",type = MySqlTypeConstant.DATETIME,comment = "订单过期时间")
    @ApiModelProperty(value = "订单过期时间")
    private Date expireTime;

    /**
     * 订单支付时间
     */
    @Column(name = "pay_time",type = MySqlTypeConstant.DATETIME,comment = "订单支付时间")
    @ApiModelProperty(value = "订单支付时间")
    private Date payTime;

    /**
     * 支付方式 1 微信 2 支付宝  3 云闪付
     */
    @Column(name = "pay_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "支付方式 1 微信 2 支付宝  3 云闪付")
    @ApiModelProperty(value = "支付方式 1 微信 2 支付宝  3 云闪付")
    private Byte payType;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @Column(name = "pay_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "支付状态 1 待支付 2 已支付")
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer payStatus;

    /**
     * 交易状态
     */
    @Column(name = "transaction_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "交易状态")
    @ApiModelProperty(value = "交易状态")
    private Integer transactionStatus;

    /**
     * 订单状态
     */
    @Column(name = "order_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单状态")
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    /**
     * 退款状态
     */
    @Column(name = "refund_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "退款状态")
    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    /**
     * 系统状态
     */
    @Column(name = "system_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "系统状态")
    @ApiModelProperty(value = "退款状态")
    private Integer systemStatus;

    /**
     * 附加信息
     */
    @Column(name = "extra_information",type = MySqlTypeConstant.TEXT,comment = "附加信息")
    @ApiModelProperty(value = "附加信息")
    private String extraInformation;

    /**
     * 支付流水号
     */
    @Column(name = "pay_order_no",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "支付流水号")
    @ApiModelProperty(value = "支付流水号")
    private String payOrderNo;

    /**
     * 请求流水号，标识每次http请求
     */
    @Column(name = "apply_id",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "请求流水号，标识每次http请求")
    @ApiModelProperty("请求流水号，标识每次http请求")
    private String applyId;

    /**
     * 预下单单号
     */
    @Column(name = "prepay_id",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "预下单单号")
    @ApiModelProperty("预下单单号")
    private String prepayId;

}
