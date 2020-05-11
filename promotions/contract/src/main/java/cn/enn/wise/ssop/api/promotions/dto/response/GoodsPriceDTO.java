package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 计算的商品
 * @author yangshuaiquan
 */
@Data
@ApiModel("商品返回的参数")
public class GoodsPriceDTO {

    @ApiModelProperty("商品")
    private Long goodsId;

    @ApiModelProperty("商品价格")
    private Integer goodsPrice;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("个数")
    private Integer goodsNum;

    @ApiModelProperty("商品分销价格")
    private Integer distributePrice;
}
