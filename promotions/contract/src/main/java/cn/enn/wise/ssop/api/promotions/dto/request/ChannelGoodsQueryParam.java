package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChannelGoodsQueryParam {



    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty("产品/资源类型（数据调用产品接口 全部请传-1）")
    private Long  projectId;
 /*   @ApiModelProperty("产品/资源类型（数据调用产品接口 全部请传-1）")
    private String  goodsExtendName;*/

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