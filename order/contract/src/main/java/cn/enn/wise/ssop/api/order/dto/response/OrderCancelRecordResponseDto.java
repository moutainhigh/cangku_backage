package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 订单取消记录表
 *
 * @author baijie
 * @date 2019-12-10
 */
@Data
public class OrderCancelRecordResponseDto{

    /**
     * 主键id
     */
    @ApiModelProperty(value = "id")
    private Long id;

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
     * 父订单id
     */
    @ApiModelProperty(value = "父订单id")
    private Long parentOrderId;

    /**
     * 取消时间
     */
    @ApiModelProperty(value = "取消时间")
    private Date cancelTime;

    /**
     * 取消类型 1 订单支付超时自动取消 2 用户自动取消订单
     */
    @ApiModelProperty(value = "取消类型 1 订单支付超时自动取消 2 用户自动取消订单")
    private Byte cancelType;

    /**
     * 取消原因类型
     */
    @ApiModelProperty(value = "取消原因类型")
    private Byte cancelReasonType;

    /**
     * 取消原因
     */
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;
}
