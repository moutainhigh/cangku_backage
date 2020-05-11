package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *票务订单信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "order_ticket")
public class OrderTicketTable extends TableBase{
  @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("主键")
  private Long id;

  @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "订单id")
  @ApiModelProperty(value = "订单id")
  private Long orderId;

  @Column(name = "ticket_code",type = MySqlTypeConstant.VARCHAR,length =50,comment = "订单明细编码")
  @ApiModelProperty(value = "订单明细编码")
  private String ticketCode;

  @Column(name = "check_in_time",type = MySqlTypeConstant.TIMESTAMP,comment = "核销时间")
  @ApiModelProperty(value = "核销时间")
  private Date checkInTime;

  @Column(name = "timespan",type = MySqlTypeConstant.INT,length =11,comment = "时间段")
  @ApiModelProperty(value = "时间段")
  private Integer timespan;

  @Column(name = "check_in_operation_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "核销人")
  @ApiModelProperty(value = "核销人")
  private Long checkInOperationId;

  @Column(name = "confirm_completed_time",type = MySqlTypeConstant.TIMESTAMP,comment = "确认结束时间")
  @ApiModelProperty(value = "确认结束时间")
  private Date confirmCompletedTime;

  @Column(name = "confirm_operator_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "确认完成运营id")
  @ApiModelProperty(value = "确认完成运营id")
  private Long confirmOperatorId;

  @Column(name = "settle_time",type = MySqlTypeConstant.TIMESTAMP,comment = "完结时间")
  @ApiModelProperty(value = "完结时间")
  private Date settleTime;

  @Column(name = "settler_operator_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "确认人")
  @ApiModelProperty(value = "确认人")
  private Long settlerOperatorId;

  @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length =255,comment = "标注")
  @ApiModelProperty(value = "标注")
  private String remark;

  @Column(name = "status",type = MySqlTypeConstant.INT,length =11,comment = "订单明细状态")
  @ApiModelProperty(value = "订单明细状态")
  private Integer status;


  @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "创建人名称")
  @ApiModelProperty(value = "创建人名称")
  private String createUserName;


  @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "修改人名称")
  @ApiModelProperty(value = "修改人名称")
  private String updateUserName;

  @Column(name = "snapshot",type = MySqlTypeConstant.TEXT,comment = "核销信息")
  @ApiModelProperty(value = "核销信息")
  private String snapshot;

  @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "商品ID")
  @ApiModelProperty(value = "商品ID")
  private Long goodsId;

  @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "商品 skuId")
  @ApiModelProperty(value = "商品 skuId")
  private Long goodsExtendId;

  @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "商品名称")
  @ApiModelProperty(value = "商品名称")
  private String goodsName;

  @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "项目 id")
  @ApiModelProperty(value = "项目 id")
  private Long projectId;

  @Column(name = "project_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "项目名称")
  @ApiModelProperty(value = "项目名称")
  private String projectName;

  @Column(name = "project_place_id",type = MySqlTypeConstant.VARCHAR,length =45,comment = "项目服务场地")
  @ApiModelProperty(value = "项目服务场地")
  private String projectPlaceId;

  @Column(name = "refund_sts",type = MySqlTypeConstant.INT,length =11,defaultValue = "1",comment = "退款状态 1.未退款 2.已退款")
  @ApiModelProperty(value = "退款状态 1.未退款 2.已退款")
  private Integer refundSts;

  @Column(name = "single_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "商品单价，套装商品为其分摊价格 ，单品为售卖价")
  @ApiModelProperty(value = "商品单价，套装商品为其分摊价格 ，单品为售卖价")
  private Double singlePrice;

  @Column(name = "coupon_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "优惠后的金额，退款时使用")
  @ApiModelProperty(value = "优惠后的金额，退款时使用")
  private Double couponPrice;

  @Column(name = "ticket_user_name",type = MySqlTypeConstant.VARCHAR,length =45,comment = "乘客姓名")
  @ApiModelProperty(value = "乘客姓名")
  private String ticket_user_name;

  @Column(name = "id_card",type = MySqlTypeConstant.VARCHAR,length =45,comment = "身份证号")
  @ApiModelProperty(value = "身份证号")
  private String idCard;

  @Column(name = "phone",type = MySqlTypeConstant.VARCHAR,length =45,comment = "手机号")
  @ApiModelProperty(value = "手机号")
  private String phone;

  @Column(name = "ticket_type",type = MySqlTypeConstant.VARCHAR,length =45,comment = "101是成人203是儿童308是小童票")
  @ApiModelProperty(value = "101是成人203是儿童308是小童票")
  private String ticketType;

  @Column(name = "seat_number",type = MySqlTypeConstant.VARCHAR,length =45,comment = "座位号")
  @ApiModelProperty(value = "座位号")
  private String seatNumber;

  @Column(name = "ship_ticket_status",type = MySqlTypeConstant.VARCHAR,length =45,comment = "船票状态 1待付款 2已售 3已出票 4已取消 5已核销 6已窗口退票")
  @ApiModelProperty(value = "船票状态 1待付款 2已售 3已出票 4已取消 5已核销 6已窗口退票")
  private String shipTicketStatus;

  @Column(name = "baby_info",type = MySqlTypeConstant.VARCHAR,length =45,comment = "携童信息")
  @ApiModelProperty(value = "携童信息")
  private String babyInfo;

  @Column(name = "ticket_id",type = MySqlTypeConstant.VARCHAR,length =45,comment = "船票Id")
  @ApiModelProperty(value = "船票Id")
  private String ticketId;

  @Column(name = "refund",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "单张票退款金额")
  @ApiModelProperty(value = "单张票退款金额")
  private Double refund;

  @Column(name = "refund_ratio",type = MySqlTypeConstant.VARCHAR,length =45,comment = "退款费率")
  @ApiModelProperty(value = "退款费率")
  private String refund_ratio;

  @Column(name = "ticket_state_bbd",type = MySqlTypeConstant.INT,length =11,comment = "百邦达票状态，0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款")
  @ApiModelProperty(value = "百邦达票状态，0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款")
  private Integer ticketStateBbd;

  @Column(name = "ticket_serial_bbd",type = MySqlTypeConstant.VARCHAR,length =45,comment = "百邦达票号")
  @ApiModelProperty(value = "百邦达票号")
  private String ticketSerialBbd;

  @Column(name = "order_serial_bbd",type = MySqlTypeConstant.VARCHAR,length =45,comment = "百邦达订单ID")
  @ApiModelProperty(value = "百邦达订单ID")
  private String orderSerialBbd;

  @Column(name = "ticket_id_bbd",type = MySqlTypeConstant.VARCHAR,length =45,comment = "百邦达票ID")
  @ApiModelProperty(value = "百邦达票ID")
  private String ticketIdBbd;

  @Column(name = "ticket_qr_code_bbd",type = MySqlTypeConstant.VARCHAR,length =45,comment = "百邦达票二维码")
  @ApiModelProperty(value = "百邦达票二维码")
  private String ticketQrCodeBbd;

  @Column(name = "is_ticket_printed",type = MySqlTypeConstant.INT,length =2,comment = "打印状态 0.未打印 1.已打印")
  @ApiModelProperty(value = "打印状态 0.未打印 1.已打印")
  private Integer isTicketPrinted;

  @Column(name = "change_info",type = MySqlTypeConstant.TEXT,comment = "佰邦达票改签信息")
  @ApiModelProperty(value = "佰邦达票改签信息")
  private String changeInfo;


}
