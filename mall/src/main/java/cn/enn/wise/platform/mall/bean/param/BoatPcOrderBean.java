package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/26 14:40
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatPcOrderBean {


    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "第三方订单号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer orderSts;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "船票状态 1 未使用 2已使用 3 已退款")
    private Integer boatSts;

    @ApiModelProperty(value = "游船名称")
    private String boatName;

    @ApiModelProperty(value = "证件号")
    private String idCard;

    @ApiModelProperty(value = "页码(默认1)")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大条目数(默认10)")
    private Integer pageSize = 10;}
