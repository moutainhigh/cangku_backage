package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 安辉
 * 抽奖规则添加商品参数
 */
@Data
@ApiModel("活动商品")
public class ActivityGoodsAddParam {

    @NotNull
    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    private Long goodsId;

    @NotNull
    @ApiModelProperty("商品名称")
    private String goodsName;

    @NotNull
    @ApiModelProperty(value = "商品一级规格Id(如果是全部产品id为-1)")
    private Long goodsExtendId;

    @NotNull
    @ApiModelProperty("商品一级规格名称")
    private String goodsExtendName;

    @ApiModelProperty(value = "第三级规格id")
    private Long goodsProjectPeriodId;

    @ApiModelProperty("第三层规格名称")
    private String title;

    @NotNull
    @ApiModelProperty(value = "商产品/资源类型Id")
    private Long projectId;

    @NotNull
    @ApiModelProperty("产品/资源类型，来自于产品")
    private String projectName;

    @NotNull
    @ApiModelProperty("销售价格")
    private Integer sellPrice;

    @NotNull
    @ApiModelProperty("拼团价格")
    private Integer groupPrice;

    @NotNull
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;

    @NotNull
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Long goodsType;

}
