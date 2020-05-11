package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChannelGoodsDeleteParam {


    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty(value = "商品Id")
    @NotNull
    private String goodsId;



}