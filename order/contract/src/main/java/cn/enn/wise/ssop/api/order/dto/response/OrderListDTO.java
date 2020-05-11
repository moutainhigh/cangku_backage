package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


/**
 * 订单列表展示返回的参数
 *
 * @author yangshuaiquan
 * @date 2020-04-21
 */

@Data
@ApiModel("订单列表展示返回的参数")
public class OrderListDTO {
    @ApiModelProperty("订单条数")
    private Integer peopleNumBer;

    @ApiModelProperty("待使用订单条数")
    private Integer waitCount;

    @ApiModelProperty("订单参数")
    private List<OrderGoodsListDTO> orderGoodsListDTOS;
}
