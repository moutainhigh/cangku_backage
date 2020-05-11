package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 10:01
 **/
@Data
@ApiModel("核销列表页返回")
public class OrderCouInfo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "总价")
    private Double goodsPrice;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单 ")
    private Integer state;

    @ApiModelProperty(value = "支付状态 ")
    private Integer payType;

    @ApiModelProperty(value = "订单类型 ")
    private Integer orderType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty("使用时间")
    private Timestamp enterTime;

    @ApiModelProperty(value = "优惠金额")
    private Double couponPrice;

    @ApiModelProperty(value = "优惠券面额")
    private Double couPrice;

    @ApiModelProperty("user_of_coupon_id")
    private Long couId;
}
