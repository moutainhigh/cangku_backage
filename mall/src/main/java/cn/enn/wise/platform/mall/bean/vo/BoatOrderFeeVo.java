package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/27 17:07
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatOrderFeeVo {

    @ApiModelProperty(value = "订单编号")
    private String orderSerial;

    @ApiModelProperty(value = "票数量")
    private Integer count;

    @ApiModelProperty(value = "票总价")
    private String totalAmount;

    @ApiModelProperty(value = "费率")
    private String totalBackRate;

    @ApiModelProperty(value = "退款")
    private String totalBack;
}
