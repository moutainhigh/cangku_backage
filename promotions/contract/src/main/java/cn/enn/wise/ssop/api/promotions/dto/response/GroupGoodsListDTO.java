package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 展示拼团活动商品返回的参数
 * @author yangshuaiquan
 */
@Data
@ApiModel("展示拼团活动商品返回的参数")
public class GroupGoodsListDTO {

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品一级规格Id(如果是全部产品id为-1)")
    private Long goodsExtendId;

    @ApiModelProperty("商品一级规格名称")
    private String goodsExtendName;

    @ApiModelProperty(value = "第三级规格id")
    private Long goodsProjectPeriodId;

    @ApiModelProperty("第三层规格名称")
    private String title;

    @ApiModelProperty(value = "商产品/资源类型Id")
    private Long projectId;

    @ApiModelProperty("产品/资源类型，来自于产品")
    private String projectName;

    @ApiModelProperty("销售价格")
    private Integer sellPrice;

    @ApiModelProperty("拼团价格")
    private Integer groupPrice;

    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;

    @ApiModelProperty("活动Id")
    private Long activityBaseId;

    @ApiModelProperty("活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty("商品的拼团人数")
    private Integer total;
}
