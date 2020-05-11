package cn.enn.wise.ssop.api.order.dto.response.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单筛选列表
 *
 * @author yangshuaiquan
 * @date 2020-04-23
 */

@Data
@ApiModel("订单筛选列表")
public class OrderAppSearchListDTO {

    @ApiModelProperty("景点名称")
    private String scenicName;

    @ApiModelProperty("订单Id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品价格")
    private String goodsPrice;

    @ApiModelProperty("订单状态 1 待支付 2 超时取消 3 已取消 4 待使用 5 已使用 6 已关闭 7 已完成 8  锁定")
    private Byte state;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
    pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("景点营业时间")
    private String timespan;

    @ApiModelProperty("商品数量")
    private Integer amount;

    @ApiModelProperty("订单类型 1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单 ")
    private Integer orderType;

    @ApiModelProperty("是否可退  1:不可退 2:可退")
    private Integer isCanRefund;

    @ApiModelProperty("商品总价")
    private BigDecimal shouldPay;

    @ApiModelProperty("附加信息")
    private String extraInformation;

    @ApiModelProperty("出票状态 1已出票 2已核验")
    private Integer discriminateBBDSts;
}
