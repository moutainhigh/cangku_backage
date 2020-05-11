package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/27 10:10
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatPcOrderDetailVo {

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "第三方编号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "下单时间")
    private String createTime;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer orderSts;

    @ApiModelProperty(value = "订单金额")
    private String goodsPrice;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "联系人手机号")
    private String phone;

    @ApiModelProperty(value = "证件类型")
    private String idCardType;

    @ApiModelProperty(value = "证件号")
    private String idNumber;


    @ApiModelProperty(value = "锁位时间")
    private String payTime;

    @ApiModelProperty(value = "订单类型 5 船单")
    private Integer orderType;

    @ApiModelProperty(value = "使用时间")
    private String expiredTime;

    @ApiModelProperty(value = "乘客信息")
    private List<OrderTicketVo> orderTicketVoList;

    @ApiModelProperty(value = "退票信息")
    private List<BoatRefundOrderVo> boatRefundOrderVoList;
}
