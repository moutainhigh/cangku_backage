package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/5 14:16
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderQueryBean {

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer orderSts;

    @ApiModelProperty(value = "评论状态： 1:未评价  2:已评价")
    private Integer commSts;

    @ApiModelProperty(value = "支付状态: 1 未支付 2已付款 3已退款")
    private Integer paySts;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

}
