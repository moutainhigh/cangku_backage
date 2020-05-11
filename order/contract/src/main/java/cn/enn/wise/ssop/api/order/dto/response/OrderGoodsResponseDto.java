package cn.enn.wise.ssop.api.order.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
public class OrderGoodsResponseDto {

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;


    @ApiModelProperty(value = "游客姓名")
    private String customerName;

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
     *商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal shouldPayPrice;

    /**
     * 实付金额
     */
    @ApiModelProperty(value = "实付金额")
    private BigDecimal actualPayPrice;

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
     * 附加信息集合
     */
    @ApiModelProperty(value = "附加信息json集合")
    private Map<String,Object> ExtraInfo;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    /**
     * 小程序端显示状态
     */
    @ApiModelProperty("小程序端显示状态")
    private String wechatStatus;

    /**
     * 小程序端显示状态值
     */
    @ApiModelProperty("小程序端显示状态")
    private String wechatStatusValue;
    /**
     * APP端显示状态
     */
    @ApiModelProperty("APP端显示状态")
    private String appStatus;//供应方运营人员状态

    /**
     * APP端显示状态值
     */
    @ApiModelProperty("APP端显示状态值")
    private String appStatusValue;//供应方运营人员状态

    /**
     * PC端显示状态
     */
    @ApiModelProperty("PC端显示状态")
    private String pcStatus;//景区财务&生态业务管理人员状态

    /**
     * PC端显示状态值
     */
    @ApiModelProperty("PC端显示状态值")
    private String pcStatusValue;//景区财务&生态业务管理人员状态


    @ApiModelProperty(value = "房间数量")
    private Integer roomNum;

    @ApiModelProperty("订单联系人手机号")
    private String phone;

    @ApiModelProperty("订单联系人证件号")
    private String certificateNo;

    @ApiModelProperty("订单联系人证件类型：1.身份证 ，2：护照 3：学生证")
    private Byte certificateType;

    @ApiModelProperty("有效时间")
    private String effectiveTime;

    @ApiModelProperty("入住时间")
    private String startTime;


    @ApiModelProperty("离店时间")
    private String outTime;

    @ApiModelProperty("入住多少晚")
    private Integer daysNum;

    @ApiModelProperty("预下单单号")
    private String prepayId;

    @ApiModelProperty("外部订单号")
    private String threeOrderNo;

    @ApiModelProperty("会员等级")
    private String vipLevel;


    @ApiModelProperty("运营人员信息")
    private OrderOperatorsListDTO orderOperatorsListDTO;
}
