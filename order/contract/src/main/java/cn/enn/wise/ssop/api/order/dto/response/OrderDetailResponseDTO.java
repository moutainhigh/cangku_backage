package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailResponseDTO {

    @ApiModelProperty(value = "订单商品信息",notes = "订单商品信息")
    private OrderGoodsResponseDto orderGoodsResponseDto;

    @ApiModelProperty(value = "订单联系人信息",notes = "订单联系人信息")
    private OrderRelatePeopleResponseDto orderRelatePeopleResponseDto;

}
