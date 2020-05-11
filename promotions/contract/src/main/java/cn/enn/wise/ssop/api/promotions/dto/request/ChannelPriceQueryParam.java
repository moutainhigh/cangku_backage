package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChannelPriceQueryParam {


    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty(value = "商品Id")
    @NotNull
    private String goodsId;

//    @ApiModelProperty("渠道政策可用日期(多个日期用逗号分开)")
//    @NotNull
//    private String[] ruleDay;



}