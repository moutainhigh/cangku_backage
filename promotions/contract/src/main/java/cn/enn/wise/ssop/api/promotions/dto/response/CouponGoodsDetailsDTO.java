package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:52 下午
 */
@Data
@ApiModel("优惠券绑定产品信息")
public class CouponGoodsDetailsDTO {

    @ApiModelProperty(value = "产品id")
    private Long goodsId;

    @ApiModelProperty("产品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品Id")
    private Long goodsExtendId;

    @ApiModelProperty("商品名称")
    private String goodsExtendName;

    @ApiModelProperty("产品/资源类型，来自于产品")
    private Long projectId;

    @ApiModelProperty("产品/资源类型，来自于产品")
    private String projectName;

    @ApiModelProperty("销售价格")
    private Integer sellPrice;

    @ApiModelProperty("价格类目根据产品中记录")
    private Byte priceType;

    @ApiModelProperty("成本价格")
    private Integer costPrice;

    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;
}
