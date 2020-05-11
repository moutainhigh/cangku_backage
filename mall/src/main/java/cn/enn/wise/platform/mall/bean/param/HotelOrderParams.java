package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/23
 */
@Data
public class HotelOrderParams {
    @ApiModelProperty(value = "订单号")
    private String orderCode;
}
