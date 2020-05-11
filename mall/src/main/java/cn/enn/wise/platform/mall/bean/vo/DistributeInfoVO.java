package cn.enn.wise.platform.mall.bean.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class DistributeInfoVO {


    /**
     * 主键
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 分销商ID
     */
    @ApiModelProperty(value ="分销商ID")
    private Long distributorId;

    /**
     *订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 策略组Id
     */
    @ApiModelProperty(value ="策略组Id")
    private Long strategyId;

    /**
     * 策略Id
     */
    @ApiModelProperty(value ="策略Id")
    private Long strategyItemId;

    /**
     * 策略快照
     */
    @ApiModelProperty(value ="策略快照")
    private String snapshot;

    /**
     * 商品Id
     */
    @ApiModelProperty(value ="商品Id")
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value ="商品名称")
    private String goodsName;

    /**
     * 商品价格
     */
    @ApiModelProperty(value ="商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value ="商品单价")
    private String singlePrice;

    @ApiModelProperty(value ="商品单价*商品数量")
    private String singlePriceMultiAmount;

    /**
     * 分润
     */
    @ApiModelProperty(value ="分润")
    private BigDecimal profit;

    /**
     * 联系人
     */
    @ApiModelProperty(value ="联系人")
    private String contacts;

    /**
     * 分销订单状态
     */
    @ApiModelProperty(value ="分销单状态 1 已支付 2 已使用 3 已完成 4未领取收益 5已领收益 6 取消 7 退单")
    private Byte status;

    /**
     * 购买数量
     */
    @ApiModelProperty(value ="购买数量")
    private Integer amount;

    /**
     * 订单金额
     */
    @ApiModelProperty(value ="订单金额")
    private BigDecimal orderPrice;

    /**
     * 创建时间
     */
    @ApiModelProperty(value ="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(value ="创建人id")
    private Long createUserId;

    /**
     * 创建人
     */
    @ApiModelProperty(value ="创建人")
    private String createUserName;


    /**
     * 更新时间
     */
    @ApiModelProperty(value ="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value ="更新人id")
    private Long updateUserId;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value ="更新人姓名")
    private String updateUserName;

    /**
     * 角色id
     */
    @ApiModelProperty(value ="角色id")
    private Long roleId;

    /**
     * 订单完成
     */
    @ApiModelProperty(value ="订单完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 订单完成
     */
    @ApiModelProperty(value ="收益发放时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date withdrawTime;


    /**
     * 订单号
     */
    @ApiModelProperty(value ="订单号")
    private String orderCode;

    /**
     * 分销人手机号
     */
    @ApiModelProperty(value ="分销人手机号")
    private String distributorCellphone;

    /**
     * 分销人姓名
     */
    @ApiModelProperty("分销人姓名")
    private String distributorName;

    /**
     * 分销人角色
     */
    @ApiModelProperty("分销人角色信息")
    private String distributorMessage;

    /**
     * 商品项目标示
     */
    @ApiModelProperty("项目标示")
    private String projectCode;

    /**
     * 商品项目名称
     */
    @ApiModelProperty("项目名称")
    private String projectName;



}
