package cn.enn.wise.ssop.service.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @作者 anhui257@163.com
 * @日期 2019/5/27
 * @描述
 */
@Data
@ApiModel("分销订单")
public class OrderBean {
    @ApiModelProperty("景区名称")
    private String scenicName;
    @ApiModelProperty("订单编号")
    private String orderCode;
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("商品价格")
    private String goodsPrice;
    @ApiModelProperty("状态")
    private int state;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("时间")
    private String timespan;
    @ApiModelProperty("单价")
    private double siglePrice;
    @ApiModelProperty("数量")
    private int amount;
    @ApiModelProperty("是否分校单")
    private int isDistributeOrder;
    @ApiModelProperty("订单类型")
    private int orderType;
    @ApiModelProperty("受否可退")
    private int isCanRefund;
    @ApiModelProperty("应付")
    private String shouldPay;
}
