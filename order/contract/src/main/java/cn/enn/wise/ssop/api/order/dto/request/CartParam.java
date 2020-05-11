package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class CartParam implements Serializable {

    private static final long serialVersionUID = 42L;

    @ApiModelProperty(value = "商品规格Id",required = true)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty(value = "商品数量",required = true)
    @NotNull(message = "数量不能为空")
    private Integer amount;

    private String channelId;
}
