package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商结算价格相关返回参数
 * @author 耿小洋
 */
@Data
@ApiModel("分销商结算价格相关返回参数")
public class ChannelSettlementPriceDTO {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品销售价格")
    private Integer sellPrice;

    @ApiModelProperty("商品成本价格")
    private Integer costPrice;

    @ApiModelProperty("分销商id")
    private Long distributorId;

    
    @ApiModelProperty("是否可以计算渠道价格 1可以 2不可以")
    private Integer isChannelSellPrice;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("结算价格")
    private Integer settlementPrice;

    
    



}
