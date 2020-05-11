package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/2 16:16
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundApplyDetailedParam {

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "金额")
    private String price;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "项目ID")
    private Integer itemId;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "流水号")
    private String refundNum;

    @ApiModelProperty(value = "退款id")
    private Integer orderRefundId;

    @ApiModelProperty(value = "订单项id")
    private Integer orderItemId;

}
