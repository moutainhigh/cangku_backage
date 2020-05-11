package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 订单更新记录
 *
 * @author baijie
 * @date 2019-12-10
 */
@Data
public class OrderChangeRecordResponseDto{

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

   /* *//**
     * 支付状态 1 待支付 2 已支付
     *//*
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer payStatus;

    *//**
     * 交易状态
     *//*
    @ApiModelProperty(value = "交易状态")
    private Integer transactionStatus;

    *//**
     * 订单状态
     *//*
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    *//**
     * 退款状态
     *//*
    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    *//**
     * 系统状态
     *//*
    @ApiModelProperty(value = "统状态")
    private Integer systemStatus;

    *//**
     * 支付状态 1 待支付 2 已支付
     *//*
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer newPayStatus;

    *//**
     * 交易状态
     *//*
    @ApiModelProperty(value = "交易状态")
    private Integer newTransactionStatus;

    *//**
     * 订单状态
     *//*
    @ApiModelProperty(value = "订单状态")
    private Integer newOrderStatus;

    *//**
     * 退款状态
     *//*
    @ApiModelProperty(value = "退款状态")
    private Integer newRefundStatus;

    *//**
     * 系统状态
     *//*
    @ApiModelProperty(value = "系统状态")
    private Integer newSystemStatus;

    */
    @ApiModelProperty(value = "PC状态名称")
    private String pcStatusName;

    @ApiModelProperty(value = "APP状态名称")
    private String appStatusName;

    @ApiModelProperty(value = "游客状态名称")
    private String youStatusName;

    /**
     * 更改原因
     */
    @ApiModelProperty(value = "更改原因")
    private String changeReason;

    /**
     * 更改时间
     */
    @ApiModelProperty(value = "更改时间")
    private Date changeTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
