package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

/**
 *订单信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "orders")
public class OrdersTable extends TableBase {
  @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("主键")
  private Long id;

  @Column(name = "order_code",type = MySqlTypeConstant.VARCHAR,length =50,comment = "订单号")
  @ApiModelProperty(value = "订单号")
  private String orderCode;

  @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "用户id")
  @ApiModelProperty(value = "用户id")
  private Long userId;

  @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "景区")
  @ApiModelProperty(value = "景区")
  private Long scenicId;

  @Column(name = "type",type = MySqlTypeConstant.BIGINT,length =20,comment = "类型：5，门票；6，二销产品")
  @ApiModelProperty(value = "类型：5，门票；6，二销产品")
  private Long type;

  @Column(name = "goods_Id",type = MySqlTypeConstant.BIGINT,length =20,comment = "商品id")
  @ApiModelProperty(value = "商品id")
  private Long goodsId;

  @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "商品名称")
  @ApiModelProperty(value = "商品名称")
  private String goodsName;

  @Column(name = "goods_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "价格")
  @ApiModelProperty(value = "价格")
  private Double goodsPrice;

  @Column(name = "state",type = MySqlTypeConstant.INT,length =4,comment = "1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单 ")
  @ApiModelProperty(value = "1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单 ")
  private Integer state;

  @Column(name = "expired_time",type = MySqlTypeConstant.TIMESTAMP,comment = "过期时间")
  @ApiModelProperty(value = "过期时间")
  private Date expiredTime;

  @Column(name = "amount",type = MySqlTypeConstant.INT,length =11,comment = "数量")
  @ApiModelProperty(value = "数量")
  private Integer amount;

  @Column(name = "pay_type",type = MySqlTypeConstant.VARCHAR,length =50,comment = "支付方式：weixin")
  @ApiModelProperty(value = "支付方式：weixin")
  private String payType;

  @Column(name = "enter_time",type = MySqlTypeConstant.TIMESTAMP,comment = "入园时间")
  @ApiModelProperty(value = "入园时间")
  private Date enterTime;

  @Column(name = "ticket_order_code",type = MySqlTypeConstant.VARCHAR,length =50,comment = "对接平台编号")
  @ApiModelProperty(value = "对接平台编号")
  private String ticketOrderCode;

  @Column(name = "id_number",type = MySqlTypeConstant.TEXT,comment = "购票人身份证")
  @ApiModelProperty(value = "购票人身份证")
  private String idNumber;

  @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "购票人姓名")
  @ApiModelProperty(value = "购票人姓名")
  private String name;

  @Column(name = "prepay_id",type = MySqlTypeConstant.VARCHAR,length =50,comment = "支付凭证")
  @ApiModelProperty(value = "支付凭证")
  private String prepayId;

  @Column(name = "bat_code",type = MySqlTypeConstant.VARCHAR,length =255,comment = "第三方底单号")
  @ApiModelProperty(value = "第三方底单号")
  private String batCode;

  @Column(name = "bat_code_other",type = MySqlTypeConstant.TEXT,comment = "第三方其它信息")
  @ApiModelProperty(value = "第三方其它信息")
  private String batCodeOther;

  @Column(name = "checked_num",type = MySqlTypeConstant.INT,length =11,comment = "检票数量")
  @ApiModelProperty(value = "检票数量")
  private Integer checkedNum;

  @Column(name = "return_num",type = MySqlTypeConstant.INT,length =11,comment = "退款数量")
  @ApiModelProperty(value = "退款数量")
  private Integer returnNum;

  @Column(name = "uncheked_num",type = MySqlTypeConstant.INT,length =11,comment = "未检票数量")
  @ApiModelProperty(value = "未检票数量")
  private Integer unchekedNum;

  @Column(name = "sigle_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "单价")
  @ApiModelProperty(value = "单价")
  private Double siglePrice;

  @Column(name = "is_distribute_order",type = MySqlTypeConstant.INT,length =4,comment = "是否分销订单 1是 0 否 ")
  @ApiModelProperty(value = "是否分销订单 1是 0 否 ")
  private Integer isDistributeOrder;

  @Column(name = "order_source",type = MySqlTypeConstant.INT,length =11,comment = "订单来源 1 大峡谷 2 二维码 ")
  @ApiModelProperty(value = "订单来源 1 大峡谷 2 二维码 ")
  private Integer orderSource;

  @Column(name = "pay_status",type = MySqlTypeConstant.INT,length =11,comment = "支付状态:待补充 1 未支付 2已付款 3已退款")
  @ApiModelProperty(value = "支付状态:待补充 1 未支付 2已付款 3已退款")
  private Integer payStatus;

  @Column(name = "actual_pay",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "实付")
  @ApiModelProperty(value = "实付")
  private Double actualPay;

  @Column(name = "should_pay",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "应收")
  @ApiModelProperty(value = "应收")
  private Double shouldPay;

  @Column(name = "user_wechat_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "用户微信")
  @ApiModelProperty(value = "用户微信")
  private String userWechatName;

  @Column(name = "phone",type = MySqlTypeConstant.VARCHAR,length =50,comment = "购票人手机号")
  @ApiModelProperty(value = "购票人手机号")
  private String phone;

  @Column(name = "max_number_of_users",type = MySqlTypeConstant.INT,length =11,comment = "最大使用人数")
  @ApiModelProperty(value = "最大使用人数")
  private Integer maxNumberOfUsers;

  @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "创建人名称")
  @ApiModelProperty(value = "创建人名称")
  private String createUserName;

  @Column(name = "distributor_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "分销商id")
  @ApiModelProperty(value = "分销商id")
  private Long distributorId;

  @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "修改人名称")
  @ApiModelProperty(value = "修改人名称")
  private String updateUserName;

  @Column(name = "profit_status",type = MySqlTypeConstant.INT,length =6,comment = "参与分润状态  1 参与分润 2 不参与分润")
  @ApiModelProperty(value = "参与分润状态  1 参与分润 2 不参与分润")
  private Integer profitStatus;

  @Column(name = "order_type",type = MySqlTypeConstant.INT,length =6,comment = "订单类型 1 线上订单 2 离线订单")
  @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单")
  private Integer orderType;

  @Column(name = "offline_user",type = MySqlTypeConstant.VARCHAR,length =45,comment = "离线操作人")
  @ApiModelProperty(value = "离线操作人")
  private String offlineUser;

  @Column(name = "offline_status",type = MySqlTypeConstant.INT,length =6,comment = "离线订单的状态 1 可编辑可删除 2 不可编辑不可删除 3 删除")
  @ApiModelProperty(value = "离线订单的状态 1 可编辑可删除 2 不可编辑不可删除 3 删除")
  private Integer offlineStatus;

  @Column(name = "snapshot",type = MySqlTypeConstant.TEXT,comment = "分销商信息快照")
  @ApiModelProperty(value = "分销商信息快照")
  private String snapshot;

  @Column(name = "project_id",type = MySqlTypeConstant.INT,length =20,comment = "项目id")
  @ApiModelProperty(value = "项目id")
  private Integer projectId;

  @Column(name = "qr_code",type = MySqlTypeConstant.VARCHAR,length =255,comment = "核销二维码地址")
  @ApiModelProperty(value = "核销二维码地址")
  private String qrCode;

  @Column(name = "proof",type = MySqlTypeConstant.VARCHAR,length =255,comment = "凭证码")
  @ApiModelProperty(value = "凭证码")
  private String proof;

  @Column(name = "reason",type = MySqlTypeConstant.VARCHAR,length =100,comment = "订单取消原因")
  @ApiModelProperty(value = "订单取消原因")
  private String reason;

  @Column(name = "comm_sts",type = MySqlTypeConstant.INT,length =1,defaultValue = "1",comment = "评论状态： 1:未评价  2:已评价")
  @ApiModelProperty(value = "评论状态： 1:未评价  2:已评价")
  private Integer commSts;

  @Column(name = "delete_status",type = MySqlTypeConstant.INT,length =1,defaultValue = "1",comment = "小程序订单展示： 1:没有删除,2:删除")
  @ApiModelProperty(value = "小程序订单展示： 1:没有删除,2:删除")
  private Integer deleteStatus;

  @Column(name = "group_order_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "拼团团id")
  @ApiModelProperty(value = "拼团团id")
  private Long groupOrderId;

  @Column(name = "pay_time",type = MySqlTypeConstant.TIMESTAMP,comment = "支付时间")
  @ApiModelProperty(value = "支付时间")
  private Date pay_time;

  @Column(name = "promotion_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "活动id")
  @ApiModelProperty(value = "活动id")
  private Long promotion_id;

  @Column(name = "user_of_coupon_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "用户领取的优惠券记录Id")
  @ApiModelProperty(value = "用户领取的优惠券记录Id")
  private Long userOfCouponId;

  @Column(name = "coupon_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "优惠金额")
  @ApiModelProperty(value = "优惠金额")
  private Double coupon_price;

  @Column(name = "ship_ticket_code",type = MySqlTypeConstant.VARCHAR,length =50,comment = "船票订单号")
  @ApiModelProperty(value = "船票订单号")
  private String ship_ticket_code;

  @Column(name = "transaction_id",type = MySqlTypeConstant.VARCHAR,length =45,comment = "微信支付订单流水号")
  @ApiModelProperty(value = "微信支付订单流水号")
  private String transaction_id;

}
