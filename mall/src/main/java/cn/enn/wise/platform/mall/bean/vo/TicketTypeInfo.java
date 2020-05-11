package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 船票类型信息
 *
 * @author baijie
 * @date 2019-12-24
 */
@Data
@ApiModel("票类型信息")
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeInfo {
    @ApiModelProperty("goodsExtendId 商品skuId")
    private Long id;

    @ApiModelProperty("票型")
    private Integer ticketType;

    @ApiModelProperty("票型名称")
    private String ticketTypeName;

    @ApiModelProperty("票价")
    private BigDecimal salePrice;
}

