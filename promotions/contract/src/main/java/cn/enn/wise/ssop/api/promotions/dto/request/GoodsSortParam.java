package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author anhui@jytrip.net
 */
@Data
@ApiModel("产品设置参数")
public class GoodsSortParam {

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 订单数量
     */
    @ApiModelProperty("订单数量")
    private Integer goodsAmount;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    private BigDecimal goodsTotal;


}
