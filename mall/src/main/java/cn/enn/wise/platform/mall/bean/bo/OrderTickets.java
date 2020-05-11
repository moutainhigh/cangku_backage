package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/8/1 18:51
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderTickets {

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String checkInTime;

    @ApiModelProperty(value = "快照")
    private String snapshot;

    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String ticketCode;

    @ApiModelProperty(value = "商品单价")
    private Double singlePrice;

    @ApiModelProperty(value = "优惠后的金额，退款时使用")
    private Double couponPrice;

    @ApiModelProperty(value = "创票商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "船票名称")
    private String goodsName;

    // -------------------------------- //
    @ApiModelProperty(value = "百邦达票号")
    private String ticketSerialBbd;

    @ApiModelProperty(value = "百邦达订单号")
    private String orderSerialBbd;

    @ApiModelProperty(value = "百邦达票ID")
    private String ticketIdBbd;

    @ApiModelProperty(value = "退款费率")
    private String refundRatio;

    @ApiModelProperty(value = "票类型")
    private String ticketType;

    @ApiModelProperty(value = "百邦达票状态")
    private Integer ticketStateBbd;

    @ApiModelProperty(value = "打印状态 0.未打印 1.已打印")
    private Integer isTicketPrinted;






}


