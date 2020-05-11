package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChannelRuleQueryParam {

//    @ApiModelProperty("id, 不传为新增数据")
//    private Long id;

    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty(value = "商品Id")
    private String goodsId;

    @ApiModelProperty("渠道政策可用日期")
    private String ruleDay;

//    @ApiModelProperty("返利单位  1 商品个数")
//    private Byte rebateUnit;
//
//    @ApiModelProperty("返利格式  1 百分比 2金额 ")
//    private Byte rebateFormat;
//
//    @ApiModelProperty("是否渠道商等级  1 是 2 否 ")
//    private Byte isdistribuorLevel;
//
//    @ApiModelProperty(value = "基础政策信息")
//    private String baseRule;
//
//    @ApiModelProperty(value = "奖励政策信息")
//    private String awardRule;
//
//    @ApiModelProperty("指定销售产品 1 不限 2 指定")
//    private Byte saleGoodsType;

}