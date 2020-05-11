package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 订单流转历史记录表
 * @program: mall
 * @author: zsj
 * @create: 2020-01-15 16:06
 **/
@Data
@Table(name = "order_state_history")
public class OrderStateHistory extends Model<OrderStateHistory> {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "订单表id")
    @ApiModelProperty("订单表id")
    private Long orderId;

    @Column(name = "order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "订单号")
    @ApiModelProperty("订单号")
    private String orderCode;

    @Column(name = "state",type = MySqlTypeConstant.INT, length = 4,comment = "状态")
    @ApiModelProperty("状态")
    private int state;

    @Column(name = "pay_status",type = MySqlTypeConstant.INT, length = 11,comment = "支付状态")
    @ApiModelProperty("支付状态")
    private int payStatus;

    @Column(name = "history_time",type = MySqlTypeConstant.TIMESTAMP,comment = "新增记录时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("新增记录时间")
    private Timestamp historyTime;

    @Column(name = "operate",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "操作说明")
    @ApiModelProperty("操作说明")
    private String operate;

    @Column(name = "order_json",type = MySqlTypeConstant.TEXT,comment = "订单json数据")
    @ApiModelProperty("订单json数据")
    private String orderJson;

    /*@Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "订单表id")
    @ApiModelProperty("订单表id")
    private Long orderId;

    @Column(name = "order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "订单号")
    @ApiModelProperty("订单号")
    private String orderCode;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "景区id")
    @ApiModelProperty("景区id")
    private Long scenicId;

    @Column(name = "type",type = MySqlTypeConstant.BIGINT, length = 20,comment = "类型")
    @ApiModelProperty("类型")
    private Long type;

    @Column(name = "goods_Id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商品id")
    @ApiModelProperty("商品id")
    private Long goodsId;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    @Column(name = "goods_price",type = MySqlTypeConstant.DECIMAL, length = 10,comment = "价格")
    @ApiModelProperty("价格")
    private BigDecimal goodsPrice;

    @Column(name = "state",type = MySqlTypeConstant.INT, length = 4,comment = "状态")
    @ApiModelProperty("状态")
    private int state;

    @Column(name = "expired_time",type = MySqlTypeConstant.TIMESTAMP,comment = "过期时间")
    @ApiModelProperty("过期时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredTime;

    @Column(name = "amount",type = MySqlTypeConstant.INT, length = 11,comment = "数量")
    @ApiModelProperty("数量")
    private int amount;

    @Column(name = "pay_type",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "支付方式")
    @ApiModelProperty("支付方式")
    private String payType;

    @Column(name = "enter_time",type = MySqlTypeConstant.TIMESTAMP,comment = "过期时间")
    @ApiModelProperty("过期时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp enterTime;

    @Column(name = "ticket_order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "对接平台编号")
    @ApiModelProperty("对接平台编号")
    private String ticketOrderCode;

    @Column(name = "id_number",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "购票人身份证")
    @ApiModelProperty("购票人身份证")
    private String idNumber;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "购票人姓名")
    @ApiModelProperty("购票人姓名")
    private String name;

    @Column(name = "prepay_id",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "支付凭证")
    @ApiModelProperty("支付凭证")
    private String prepayId;

    @Column(name = "bat_code",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "第三方底单号")
    @ApiModelProperty("第三方底单号")
    private String batCode;

    @Column(name = "bat_code_other",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "第三方底其他信息")
    @ApiModelProperty("第三方底其他信息")
    private String batCodeOther;

    @Column(name = "checked_num",type = MySqlTypeConstant.INT, length = 11,comment = "检票数量")
    @ApiModelProperty("检票数量")
    private int checkedNum;

    @Column(name = "return_num",type = MySqlTypeConstant.INT, length = 11,comment = "退款数量")
    @ApiModelProperty("退款数量")
    private int returnNum;

    @Column(name = "uncheked_num",type = MySqlTypeConstant.INT, length = 11,comment = "未检票数量")
    @ApiModelProperty("未检票数量")
    private int unchekedNum;

    @Column(name = "sigle_price",type = MySqlTypeConstant.DECIMAL, length = 10,comment = "单价")
    @ApiModelProperty("单价")
    private BigDecimal siglePrice;

    @Column(name = "is_distribute_order",type = MySqlTypeConstant.INT, length = 4,comment = "是否分销")
    @ApiModelProperty("是否分销")
    private int isDistributeOrder;

    @Column(name = "order_source",type = MySqlTypeConstant.INT, length = 11,comment = "订单来源")
    @ApiModelProperty("订单来源")
    private int orderSource;

    @Column(name = "pay_status",type = MySqlTypeConstant.INT, length = 11,comment = "支付状态")
    @ApiModelProperty("支付状态")
    private int payStatus;

    @Column(name = "actual_pay",type = MySqlTypeConstant.DECIMAL, length = 10,comment = "实付")
    @ApiModelProperty("实付")
    private BigDecimal actualPay;

    @Column(name = "should_pay",type = MySqlTypeConstant.DECIMAL, length = 10,comment = "应收")
    @ApiModelProperty("应收")
    private BigDecimal shouldPay;

    @Column(name = "user_wechat_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "用户微信")
    @ApiModelProperty("用户微信")
    private String userWechatName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "购票人手机号")
    @ApiModelProperty("购票人手机号")
    private String phone;

    @Column(name = "max_number_of_users",type = MySqlTypeConstant.INT, length = 11,comment = "最大使用人数")
    @ApiModelProperty("最大使用人数")
    private int maxNumberOfUsers;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @Column(name = "create_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "创建人id")
    @ApiModelProperty("创建人id")
    private Long createUserId;

    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty("创建人名称")
    private String createUserName;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @Column(name = "update_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "更新人id")
    @ApiModelProperty("更新人id")
    private Long updateUserId;

    @Column(name = "distributor_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "分销商id")
    @ApiModelProperty("分销商id")
    private Long distributorId;

    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "更新人名称")
    @ApiModelProperty("更新人名称")
    private String updateUserName;

    @Column(name = "profit_status",type = MySqlTypeConstant.INT, length = 6,comment = "参与分润状态")
    @ApiModelProperty("参与分润状态")
    private int profit_status;

    @Column(name = "order_type",type = MySqlTypeConstant.INT, length = 6,comment = "订单类型")
    @ApiModelProperty("订单类型")
    private int orderType;

    @Column(name = "offline_user",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "离线操作人")
    @ApiModelProperty("离线操作人")
    private String offlineUser;

    @Column(name = "offline_status",type = MySqlTypeConstant.INT, length = 6,comment = "离线订单状态")
    @ApiModelProperty("离线订单状态")
    private int offlineStatus;

    @Column(name = "snapshot",type = MySqlTypeConstant.TEXT,comment = "分销商信息快照")
    @ApiModelProperty("分销商信息快照")
    private String snapshot;

    @Column(name = "project_id",type = MySqlTypeConstant.INT, length = 2,comment = "项目id")
    @ApiModelProperty("项目id")
    private int projectId;

    @Column(name = "qr_code",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "核销二维码地址")
    @ApiModelProperty("核销二维码地址")
    private String qrCode;

    @Column(name = "proof",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "凭证码")
    @ApiModelProperty("凭证码")
    private String proof;

    @Column(name = "reason",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "订单取消原因")
    @ApiModelProperty("订单取消原因")
    private String reason;

    @Column(name = "comm_sts",type = MySqlTypeConstant.INT, length = 1,comment = "评论状态")
    @ApiModelProperty("评论状态")
    private int commSts;

    @Column(name = "delete_status",type = MySqlTypeConstant.INT, length = 1,comment = "小程序订单展示")
    @ApiModelProperty("小程序订单展示")
    private int deleteStatus;

    @Column(name = "group_order_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "拼团id")
    @ApiModelProperty("拼团id")
    private Long groupOrderId;

    @Column(name = "pay_time",type = MySqlTypeConstant.TIMESTAMP,comment = "支付时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("支付时间")
    private Timestamp payTime;

    @Column(name = "promotion_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "")
    //@ApiModelProperty("拼团id")
    private Long promotionId;

    @Column(name = "user_of_coupon_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "用户领取的优惠券id")
    @ApiModelProperty("用户领取的优惠券id")
    private Long userOfCouponId;

    @Column(name = "coupon_price",type = MySqlTypeConstant.DECIMAL, length = 10,comment = "优惠金额")
    @ApiModelProperty("优惠金额")
    private BigDecimal couponPrice;

    @Column(name = "ship_ticket_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "船票订单号")
    @ApiModelProperty("船票订单号")
    private String shipTicketCode;

    @Column(name = "transaction_id",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "微信支付订单流水号")
    @ApiModelProperty("微信支付订单流水号")
    private String transactionId;

    @Column(name = "history_time",type = MySqlTypeConstant.TIMESTAMP,comment = "新增记录时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("新增记录时间")
    private Timestamp historyTime;

    @Column(name = "operate",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "操作说明")
    @ApiModelProperty("操作说明")
    private String operate;*/

}
