package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 14:05
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundApplyParam {

    @ApiModelProperty(value = "1.PC端 2.App端 3.小程序")
    private Integer platform;

    @ApiModelProperty(value = "订单Id")
    private String orderId;

    @ApiModelProperty(value = "订单项Id")
    private String orderItemId;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponPrice;

    @ApiModelProperty(value = "退票数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "操作人")
    private String handleName;

    @ApiModelProperty(value = "1.景区操作原因 2.不可抗力 3.游客原因")
    private Integer buyerMsgType;

    @ApiModelProperty(value = "退款原因")
    private String buyerMsg;

    @ApiModelProperty(value = "退款标签")
    private Integer reasonLable;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "商品集合")
    private List<RefundApplyDetailedParam> refundApplyDetailedParamList;

}
