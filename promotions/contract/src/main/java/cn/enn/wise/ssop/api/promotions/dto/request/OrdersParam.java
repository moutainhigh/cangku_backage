package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
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
 * @author jiabaiye
 */
@Data
//@Table(name = "orders")
@ApiModel("订单实体类")
public class OrdersParam{
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;


    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

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
    @ApiModelProperty(value = "订单状态 1待付款 2 待使用 3已取消 4已使用 5部分使用 6 退款中 7已退款 8 体验完成")
    private Byte orderStatus;

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

}
