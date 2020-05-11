package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 包含项目VO
 *
 * @author baijie
 * @date 2019-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("套装商品明细VO")
public class PackageGoodsItemVO {



    @ApiModelProperty("套装商品明细商品Id")
    private Long id;

    @ApiModelProperty("项目Id")
    private Long packageProjectId;

    @ApiModelProperty("套装商品项目名称")
    private String packageProjectName;

    @ApiModelProperty("头图")
    private String headImg;

    @ApiModelProperty("套装商品明细单个售价")
    private BigDecimal packageSalePrice;

    @ApiModelProperty("套装商品名称")
    private String packageGoodsName;

    @ApiModelProperty("套装商品中明细商品的skuId")
    private Long packageGoodsExtendId;

    @ApiModelProperty("套装商品明细项目地点Id")
    private String servicePlaceId;

    @ApiModelProperty("套装商品明细项目编码")
    private String packageGoodsProjectCode;
}
