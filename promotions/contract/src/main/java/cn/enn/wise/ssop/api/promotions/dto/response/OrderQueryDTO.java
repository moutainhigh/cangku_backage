package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单查询结果
 *
 * @author jiabaiye
 */
@Data
@ApiModel("订单查询")
@Builder
public class OrderQueryDTO {

    @ApiModelProperty("是否支付成功 1支付成功 2 支付失败")
    private Integer isPaySuccess;

    @ApiModelProperty("订单类型 1线上订单 2 离线订单 3拼团订单")
    private Integer orderType;

    @ApiModelProperty("订单支付价格")
    private BigDecimal orderPrice;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("订单Id")
    private Long orderId;

    @ApiModelProperty("购买商品单价")
    private BigDecimal goodsPrice;

    @ApiModelProperty("剩余拼团人数")
    private Integer remainingNumber;

    @ApiModelProperty("拼团剩余秒数")
    private Integer remainingSeconds;

    @ApiModelProperty("团单Id")
    private Long groupOrderId;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("拼团是否成功 1 成功 2 失败")
    private Integer isSuccess;

    @ApiModelProperty("团规定人数")
    private Integer groupSize;

    @ApiModelProperty("团状态")
    private Integer groupOrderStatus;

//    @ApiModelProperty("拼团结果信息")
//    private ResponseEntity responseEntity;

    @ApiModelProperty("拼团订单类型 1 创建拼团 2 加入拼团")
    private Integer groupOrderType;
}
