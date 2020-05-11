package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 商品信息基本参数
 *
 * @author lishuiquan
 * @date 2020-12-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "商品信息基本参数")
public class GoodsInfoParam {

    @ApiModelProperty(value = "商品规格Id",required = true)
    private Long skuId;

    @ApiModelProperty(value = "商品数量",required = true)
    private Integer amount;

    @ApiModelProperty(value = "联系人Id")
    private Long customerId;

    @ApiModelProperty(value = "策略Id")
    private Long saleId;

}
