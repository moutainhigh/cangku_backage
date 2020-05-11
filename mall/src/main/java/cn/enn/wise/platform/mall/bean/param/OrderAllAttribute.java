package cn.enn.wise.platform.mall.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单所有属性
 * @program: mall
 * @author: zsj
 * @create: 2020-01-17 10:34
 **/
@Data
@ApiModel("订单所有属性")
public class OrderAllAttribute {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("景区id")
    private Long scenicId;

    @ApiModelProperty("类型")
    private Long type;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("状态")
    private int state;

    @ApiModelProperty("过期时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredTime;

    @ApiModelProperty("数量")
    private int amount;

    @ApiModelProperty("支付方式")
    private String payType;

    @ApiModelProperty("过期时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp enterTime;

    @ApiModelProperty("对接平台编号")
    private String ticketOrderCode;

    @ApiModelProperty("购票人身份证")
    private String idNumber;

    @ApiModelProperty("购票人姓名")
    private String name;

    @ApiModelProperty("支付凭证")
    private String prepayId;

    @ApiModelProperty("第三方底单号")
    private String batCode;

    @ApiModelProperty("第三方底其他信息")
    private String batCodeOther;

    @ApiModelProperty("检票数量")
    private int checkedNum;

    @ApiModelProperty("退款数量")
    private int returnNum;

    @ApiModelProperty("未检票数量")
    private int unchekedNum;

    @ApiModelProperty("单价")
    private BigDecimal siglePrice;

    @ApiModelProperty("是否分销")
    private int isDistributeOrder;

    @ApiModelProperty("订单来源")
    private int orderSource;

    @ApiModelProperty("支付状态")
    private int payStatus;

    @ApiModelProperty("实付")
    private BigDecimal actualPay;

    @ApiModelProperty("应收")
    private BigDecimal shouldPay;

    @ApiModelProperty("用户微信")
    private String userWechatName;

    @ApiModelProperty("购票人手机号")
    private String phone;

    @ApiModelProperty("最大使用人数")
    private int maxNumberOfUsers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("更新人id")
    private Long updateUserId;

    @ApiModelProperty("分销商id")
    private Long distributorId;

    @ApiModelProperty("更新人名称")
    private String updateUserName;

    @ApiModelProperty("参与分润状态")
    private int profit_status;

    @ApiModelProperty("订单类型")
    private int orderType;

    @ApiModelProperty("离线操作人")
    private String offlineUser;

    @ApiModelProperty("离线订单状态")
    private int offlineStatus;

    @ApiModelProperty("分销商信息快照")
    private String snapshot;

    @ApiModelProperty("项目id")
    private int projectId;

    @ApiModelProperty("核销二维码地址")
    private String qrCode;

    @ApiModelProperty("凭证码")
    private String proof;

    @ApiModelProperty("订单取消原因")
    private String reason;

    @ApiModelProperty("评论状态")
    private int commSts;

    @ApiModelProperty("小程序订单展示")
    private int deleteStatus;

    @ApiModelProperty("拼团id")
    private Long groupOrderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("支付时间")
    private Timestamp payTime;

    @ApiModelProperty("拼团id")
    private Long promotionId;

    @ApiModelProperty("用户领取的优惠券id")
    private Long userOfCouponId;

    @ApiModelProperty("优惠金额")
    private BigDecimal couponPrice;

    @ApiModelProperty("船票订单号")
    private String shipTicketCode;

    @ApiModelProperty("微信支付订单流水号")
    private String transactionId;
}
