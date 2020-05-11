package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 安辉
 */
@Data
@ApiModel("客群列表")
public class CustomerProportionParam {

    /**
     * 客群id
     */
    @ApiModelProperty("客群id")
    private Long id;

    /**
     * 客群名称
     */
    @ApiModelProperty("客群名称")
    private String customerTagName;

    /**
     * 百分比
     */
    @ApiModelProperty("百分比")
    private Double ratio;

    /**
     * 成交量 单
     */
    @ApiModelProperty("成交量 单")
    private Integer amount;

    /**
     * 成交额 元
     */
    @ApiModelProperty("成交额 元")
    private BigDecimal total;
}
