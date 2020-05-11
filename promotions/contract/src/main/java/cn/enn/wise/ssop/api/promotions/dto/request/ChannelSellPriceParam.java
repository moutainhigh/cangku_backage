package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 渠道销售价格
 * @author 耿小洋
 */
@Data
@ApiModel("渠道价格相关参数")
public class ChannelSellPriceParam {

    @ApiModelProperty("商品id")
    @NotNull
    private Long goodsId;

    @ApiModelProperty("商品销售价格")
    @NotNull
    private Integer sellPrice;

    @ApiModelProperty("商品成本价格")
    @NotNull
    private Integer costPrice;
/*
    @ApiModelProperty("分销商id")
    @NotNull
    private Long distributorId;*/

    @ApiModelProperty("分销商手机号")
    private String phone;


}
