package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/2 16:51
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderRefundExtend {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "退款主键ID")
    private Integer orderRefundId;

    private Integer orderItemId;

    @ApiModelProperty(value = "项目Id")
    private Integer projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "流水单号")
    private String refundNum;

    @ApiModelProperty(value = "退款数量")
    private Integer refundAmount;

    @ApiModelProperty(value = "退款金额")
    private String refundPrice;
}
