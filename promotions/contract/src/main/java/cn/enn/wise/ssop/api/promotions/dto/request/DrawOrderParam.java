package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@ApiModel("抽奖订单相关参数")
public class DrawOrderParam {


    @ApiModelProperty("用户id")
    @NotNull
    private Long  userId;


    @ApiModelProperty("商品id")
    @NotNull
    private Long  goodsId;

    @ApiModelProperty("商品名称")
    @NotNull
    private String  goodsName;


    @ApiModelProperty(value = "订单金额")
    @NotNull
    private BigDecimal actualPayPrice;


    @ApiModelProperty(value = "订单人数")
    @NotNull
    private Integer payPeopleNumber;


}
