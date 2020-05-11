package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/26 09:45
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatOrderVo {

    @ApiModelProperty(value = "游船名称")
    private String boatName;

    @ApiModelProperty(value = "订单总金额")
    private String goodsPrice;

    @ApiModelProperty(value = "有效数量")
    private Integer noUseAmount;

    @ApiModelProperty(value = "检票数量")
    private Integer useAmount;

    @ApiModelProperty(value = "退票数量")
    private Integer refundAmount;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer orderSts;

    @ApiModelProperty(value = "起点")
    private String lineFrom;

    @ApiModelProperty(value = "终点")
    private String lineTo;

    @ApiModelProperty(value = "船舱名称")
    private String cabinName;

    @ApiModelProperty(value = "航班开始时间")
    private String boatStartTime;

    @ApiModelProperty(value = "航班结束时间")
    private String boatEndTime;



    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineDate;

    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineEndDate;

    @ApiModelProperty(value = "来游吧订单号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "下单时间")
    private String createTime;

    @ApiModelProperty(value = "预订人")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "航班信息")
    private String shipLineInfo;

    @ApiModelProperty(value = "身份证")
    private String idCard;


    @ApiModelProperty(value = "未检票状态")
    private String noCheckTicketSts;

    @ApiModelProperty(value = "退票状态")
    private String refundTicketSts;

    @ApiModelProperty(value = "检票状态")
    private String checkTicketSts;

    private Integer shipTicketSts;

}
