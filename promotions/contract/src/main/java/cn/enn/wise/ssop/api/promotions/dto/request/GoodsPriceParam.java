package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 优惠的商品
 * @author yangshuaiquan
 */
@Data
@ApiModel("商品参数")
public class GoodsPriceParam {

    @ApiModelProperty("商品")
    @NotNull
    private Long goodsId;

    @ApiModelProperty("商品价格")
    @NotNull
    private Integer goodsPrice;

    @ApiModelProperty("商品名称")
    @NotNull
    private String goodsName;

    @ApiModelProperty("个数")
    @NotNull
    private Integer goodsNum;

    @ApiModelProperty("商品分销价格")
    @NotNull
    private Integer distributePrice;
}
