package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:51
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:订单信息
 ******************************************/
@Data
@ApiModel("订单信息")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders implements Serializable {

    private static final long serialVersionUID = 7283247105523185691L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败")
    private Integer state;

    @ApiModelProperty(value = "最近操作日期")
    private Timestamp updateTime;

    @ApiModelProperty(value = "未检票数量")
    private Integer unchekedNum;

    @ApiModelProperty(value = "商品数量")
    private Long amount;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty(value = "类型 5 门票 ,6 二销产品")
    private Long type;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredTime;


    @ApiModelProperty(value = "支付方式：weixin")
    private String payType;

    @ApiModelProperty(value = "对接平台编号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "购票人身份证")
    private String idNumber;

    @ApiModelProperty(value = "支付凭证")
    private String prepayId;

    @ApiModelProperty(value = "第三方底单号")
    private String batCode;

    @ApiModelProperty(value = "第三方其它信息")
    private String batCodeOther;

    @ApiModelProperty(value = "检票数量")
    private Integer checkedNum;

    @ApiModelProperty(value = "退款数量")
    private Integer returnNum;

    @TableField(exist = false)
    @ApiModelProperty(value = "体验时间")
    private String timespan;


    @ApiModelProperty(value = "入园时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date enterTime;

    @ApiModelProperty(value = "订单日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty(value = "是否分销订单 1是 0 否")
    private Integer isDistributeOrder;

    @ApiModelProperty(value = "订单来源 1 大峡谷 2 二维码 3 现场转化 4 百邦达下单")
    private Integer orderSource;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualPay;

    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;

    @ApiModelProperty(value = "用户微信名")
    private String userWechatName;


    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "单价")
    private BigDecimal siglePrice;

    @ApiModelProperty(value = "单价")
    private Integer maxNumberOfUsers;

    @TableField(exist = false)
    @ApiModelProperty(value = "订单剩余支付时间")
    private Long leaveTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "分销商id")
    private Long distributorId;

    @ApiModelProperty(value = "分润状态")
    private Integer profitStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "分销商角色Id")
    private String  roleId;

    @ApiModelProperty(value = "分销商信息快照")
    private String  snapshot;

    @TableField(exist = false)
    @ApiModelProperty(value = "分销商姓名")
    private String  distributorName;

    @TableField(exist = false)
    @ApiModelProperty(value = "分销商电话")
    private String  distributorPhone;

    @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单 3拼团订单 4 组合套餐订单 5 船票订单 6 百邦达订单")
    private Integer  orderType;

    @ApiModelProperty(value = "离线操作人")
    private String  offlineUser;

    @ApiModelProperty(value = "离线订单的状态 1 可编辑可删除 2 不可编辑不可删除")
    private Integer  offlineStatus;

    @TableField(exist = false)
    private String profiles;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @TableField(exist = false)
    @ApiModelProperty(value = "项目编号")
    private String projectCode;


    @ApiModelProperty(value = "二维码地址")
    private String qrCode;

    @ApiModelProperty(value = "凭证码")
    private String proof;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "拼团团id")
    private Long groupOrderId;

    @ApiModelProperty("支付时间")
    private Timestamp payTime;

    @ApiModelProperty("团单活动Id")
    private Long promotionId;

    @ApiModelProperty("用户领取优惠券记录的Id")
    private Long userOfCouponId;

    @ApiModelProperty("优惠的金额")
    private BigDecimal couponPrice;

    @ApiModelProperty("微信支付订单号")
    private String transactionId;
}
