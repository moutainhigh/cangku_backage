package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.autotable.OrderHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/19 15:39
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderTicketVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "订单主键")
    private Long orderId;

    @ApiModelProperty(value = "项目Id")
    private Integer projectId;

    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "地点Id")
    private Integer projectPlaceId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "核销状态")
    private Integer status;

    @ApiModelProperty(value = "退款状态 1.未退款 2.已退款")
    private Integer refundSts;

    @ApiModelProperty(value = "商品单价")
    private String singlePrice;

    private Double singlePrices;

    @ApiModelProperty(value = "单品优惠分摊金额")
    private String couponPrice;


    private String checkInTime;





    @ApiModelProperty(value = "乘客姓名")
    private String ticketUserName;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "101:成人 203:儿童 308:小童票")
    private String ticketType;

    @ApiModelProperty(value = "座位号")
    private String seatNumber;

    @ApiModelProperty(value = "船票状态 1.待出票  2.已取消 3.已出票 4.已核销")
    private Integer shipTicketStatus;

    @ApiModelProperty(value = "携童信息")
    private String babyInfo;

    @ApiModelProperty(value = "船票Id")
    private Integer ticketId;

    private Double refund;

    private String refundRatio;



    @ApiModelProperty(value = "百邦达票状态 0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款")
    private Integer ticketStateBbd;

    @ApiModelProperty(value = "百邦达票号")
    private String ticketSerialBbd;

    @ApiModelProperty(value = "百邦达票二维码")
    private String ticketQrCodeBbd;

    @ApiModelProperty(value = "打印状态 0.未打印 1.已打印")
    private Integer isTicketPrinted;

    @ApiModelProperty(value = "百邦达票ID")
    private String ticketIdBbd;

    @ApiModelProperty(value = "百邦达订单号")
    private String orderSerialBbd;


    @ApiModelProperty(value = "换票记录")
    private List<OrderHistory> orderHistoryList;


}
