package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 二销订单详情
 *
 * @author baijie
 * @date 2019-12-21
 */
@Data
public class OrderDetailDTO {

    @ApiModelProperty(value = "订单信息",notes = "订单信息")
    private OrderResponseDto orderResponseDto;

    @ApiModelProperty(value = "订单商品信息",notes = "订单商品信息")
    private List<OrderGoodsResponseDto> orderGoodsResponseDtoList;

    @ApiModelProperty(value = "订单联系人信息",notes = "订单联系人信息")
    private List<OrderRelatePeopleResponseDto> orderRelatePeopleResponseDtoList;

    @ApiModelProperty(value = "订单状态变更记录",notes = "订单状态变更记录")
    private List<OrderChangeRecordResponseDto> orderChangeRecordResponseDtoList;
}
