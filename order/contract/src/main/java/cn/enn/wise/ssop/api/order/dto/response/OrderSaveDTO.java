package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单保存返回参数
 *
 * @author lishuiquan
 * @date 2019-12-13
 */
@Data
@ApiModel(description = "订单保存返回数据")
public class OrderSaveDTO {

    @ApiModelProperty("订单Id")
    private Long orderId;

}
