package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 套餐商品VO
 *
 * @author baijie
 * @date 2019-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("套装商品信息VO")
public class PackageGoodVO {

    @ApiModelProperty("套装商品Id")
    private Long id;

    @ApiModelProperty("套装商品名称")
    private String goodsName;

    @ApiModelProperty("套装基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("套装售卖价格")
    private BigDecimal salePrice;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String img;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String description;


    @ApiModelProperty("套装商品项目编码,下单时使用")
    private String projectCode;

    @ApiModelProperty("套装商品skuId")
    private Long goodsExtendId;

    @ApiModelProperty("是否分时段售卖 1是 2 不是")
    private Integer isByPeriodOperation;

    @ApiModelProperty("套装商品明细")
    private List<PackageGoodsItemVO>  itemVOList;

}
