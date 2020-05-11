package cn.enn.wise.ssop.api.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 订单参数
 *
 * @author yangshuaiquan
 * @date 2020-04-21
 */

@Data
@ApiModel("订单参数")
public class OrderGoodsListDTO {

    @ApiModelProperty("景点名称")
    private String scenicName;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品价格")
    private String goodsPrice;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
    pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名称")
    private String customerName;

    @ApiModelProperty("景点营业时间")
    private String timespan;

    @ApiModelProperty("商品数量")
    private int amount;

    @ApiModelProperty("订单类型 1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单 ")
    private int orderType;

    @ApiModelProperty("是否可退 1 不可退 2 可退")
    private int isCanRefund;

    @ApiModelProperty("入住时间")
    private String startTime;


    @ApiModelProperty("离店时间")
    private String outTime;
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
     * 支付状态 1 待支付 2 已支付
     */
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "来源方名称")
    private String orderSourceName;

    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 交易状态
     */
    @ApiModelProperty(value = "交易状态 1 核销中 2 已核销 3 部分核销 4 已核销(锁定) 5 已取票(锁定) 6 已退票 7 已取票 8 已过期 9 锁定")
    private Integer transactionStatus;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态 1 待支付 2 超时取消 3 已取消 4 待使用5, 已使用 6 已关闭 7 已完成 8 锁定")
    private Integer orderStatus;

    @ApiModelProperty(value = "房间间数")
    private Integer roomNum;

    @ApiModelProperty(value = "户型")
    private String roomType;


    @ApiModelProperty("附加信息")
    private Map<String,Object> extraInformations;

    @ApiModelProperty("出票状态 1已出票 2已核验")
    private Integer discriminateBBDSts;

    @ApiModelProperty(value = "附加信息")
    private String extraInformation;
}
