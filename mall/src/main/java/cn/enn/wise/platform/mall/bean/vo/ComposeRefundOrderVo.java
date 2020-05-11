package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/26 16:13
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class ComposeRefundOrderVo {

    @ApiModelProperty(value = "订单状态")
    private Integer orderSts;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "单价/人数")
    private String prices;

    @ApiModelProperty(value = "单价")
    private String price;

    @ApiModelProperty(value = "人数")
    private Long amount;

    @ApiModelProperty(value = "实付金额")
    private String actualPay;

    @ApiModelProperty(value = "优惠总金额")
    private String couponTotalPrice;

    @ApiModelProperty(value = "订单总金额")
    private String orderTotalPrice;

    @ApiModelProperty(value = "退单操作人")
    private String handleName;

    @ApiModelProperty(value = "是否参加优惠 1.没参加 2.参加")
    private Integer isJoinCoupon=1;

    @ApiModelProperty(value = "未体验商品明细")
    private List<NoPlayVo> noPlayVoList;

    @ApiModelProperty(value = "已退商品明细")
    private List<RefundDetailVo> refundDetailVoList;

    @ApiModelProperty(value = "1.已出票 2.已核验")
    private Integer discriminateBBDSts=1;

}
