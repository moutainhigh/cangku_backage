package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/28 11:00
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatRefundApplyParam {

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "票ID")
    private String ticketId;

    @ApiModelProperty(value = "退款原因")
    private String reason;
}
