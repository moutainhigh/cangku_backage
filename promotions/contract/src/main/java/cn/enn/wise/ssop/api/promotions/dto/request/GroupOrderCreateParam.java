package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("创建拼团订单参数实体类")
public class GroupOrderCreateParam {

    @ApiModelProperty("活动基础信息id")
    private Long  activityBaseId;

    @ApiModelProperty("订单实体类")
    private OrdersParam ordersParam;

    @ApiModelProperty("订单关联商品信息")
    private  OrderGoodsParam orderGoodsParam;

    @ApiModelProperty("订单关联活动信息")
    private OrderSaleParam orderSaleParam;

}