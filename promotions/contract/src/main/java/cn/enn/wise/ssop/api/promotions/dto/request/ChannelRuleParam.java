package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChannelRuleParam {

    @ApiModelProperty("id, 不传为新增数据")
    private Long id;

    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1，多个商品用逗号分开)")
    @NotNull
    private String goodsIds;

    @NotNull
    @ApiModelProperty("渠道政策可用日期(多个日期用逗号分开)")
    private String[] ruleDay;

    @NotNull
    @ApiModelProperty("返利单位  1 商品个数")
    private Byte rebateUnit;

    @NotNull
    @ApiModelProperty("返利格式  1 百分比 2金额 ")
    private Byte rebateFormat;

    @NotNull
    @ApiModelProperty("是否渠道商等级  1 是 2 否 ")
    private Byte isdistribuorLevel;

    @ApiModelProperty(value = "基础政策信息")
    @NotNull
    private String baseRule;

    @ApiModelProperty("渠道商等级  1 初级 2 中级 3 高级")
    private String  awardDistribuorLevel;

    @ApiModelProperty(value = "奖励政策信息")
    private String awardRule;

    @ApiModelProperty("指定销售产品 1 不限 2 指定")
    private Byte saleGoodsType;

    @ApiModelProperty(value = "其他服务政策保存Byte数组")
    private String multipleServer;
}