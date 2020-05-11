package cn.enn.wise.ssop.api.promotions.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:jiabaiye
 */

@Data
@ApiModel("订单信息")
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 7283247105523185691L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品总价 IOS专用")
    private String goodsPriceIOS;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer state;

    @ApiModelProperty(value = "最近操作日期")
    private String updateTime;

    @ApiModelProperty(value = "商品数量")
    private Long amount;

    @ApiModelProperty(value = "入园时间")
    private String enterTime;

    @ApiModelProperty(value = "订单日期")
    private String createTime;

    @ApiModelProperty(value = "是否分销订单 1是 0 否")
    private Byte isDistributeOrder;

    @ApiModelProperty(value = "订单来源 1 大峡谷 2 二维码")
    private Integer orderSource;

    @ApiModelProperty(value = "支付状态 1.待付款 2.已付款 3.已退款")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualPay;

    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;

    @ApiModelProperty(value = "用户微信名")
    private String userWechatName;

    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "购票人身份证")
    private String idNumber;

    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "单价")
    private BigDecimal siglePrice;

    @ApiModelProperty(value = "单价 IOS专用")
    private String siglePriceIOS;

    @ApiModelProperty("时间段")
    private String timeSpan;

    @ApiModelProperty(value = "分销商ID")
    private Integer distributorId;

    @ApiModelProperty(value = "分销商信息快照")
    private String  snapshot;

    @ApiModelProperty(value = "分销商姓名")
    private String  distributorName;

    @ApiModelProperty(value = "分销商电话")
    private String  distributorPhone;

    @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单 3拼团订单")
    private Integer  orderType;

    @ApiModelProperty(value = "离线操作人")
    private String  offlineUser;

    @ApiModelProperty(value = "离线订单的状态 1 未生效 可编辑可删除 2已生效 不可编辑不可删除")
    private String  offlineStatus;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "第三方底单号")
    private String batCode;


    @ApiModelProperty(value = "核销人")
    private String checkName;

    @ApiModelProperty(value = "核销信息")
    private String ticketSnapshot;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String checkInTime;

    @ApiModelProperty(value = "订单取消原因")
    private String reason;

    @ApiModelProperty(value = "用户角色ID")
    private String userRole;

//    @ApiModelProperty(value = "拼团信息")
//    private List<GroupItemVo> groupOrderVoList;

    @ApiModelProperty(value = "评论状态： 1:未评价  2:已评价")
    private Integer commSts;

//    @ApiModelProperty(value = "评论数据")
//    private ProdComm prodComm;

    @ApiModelProperty(value = "拼团团id")
    private Long groupOrderId;


    @ApiModelProperty(value = "体验")
    private String experienceTime;

    private String experienceTimes;

    @ApiModelProperty(value = "是否退款 1.无退款 2.已退款")
    private Integer isRefund =1;

    @ApiModelProperty(value = "申请来源 1.PC端 2.App端 3.小程序")
    private Integer platform;

    @ApiModelProperty(value = "1.不可退 2.可退")
    private Integer isCanRefund=1;

//    @ApiModelProperty(value = "退款明细")
//    private List<OrderRefundVo> orderRefundVoList;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPrice;

    @ApiModelProperty(value = "优惠总金额")
    private BigDecimal couponPrice;

    private BigDecimal couponPrices;

    @ApiModelProperty(value = "对接平台订单编号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "用户领取的优惠券记录Id")
    private Integer userOfCouponId;

    @ApiModelProperty(value = "支付时间")
    private String payTime;

//    @ApiModelProperty(value = "票务信息")
//    private List<OrderTicketVo> orderTicketVoList;

    @ApiModelProperty(value = "1.已出票 2.已核验")
    private Integer discriminateBBDSts=1;

}
