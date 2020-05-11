package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/27 10:51
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatRefundOrderVo {

    @ApiModelProperty(value = "退票时间")
    private String refundTime;

    @ApiModelProperty(value = "退票数量")
    private String refundAmount;

    @ApiModelProperty(value = "原价")
    private String price;

    @ApiModelProperty(value = "退款")
    private String refundPrice;

    @ApiModelProperty(value = "手续费扣除")
    private String fee;

}
