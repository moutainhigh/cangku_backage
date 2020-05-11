package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/19
 */
@Data
@ApiModel("商品参数")
public class GoodsPackageItemsParams {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主键套装")
    private Long packageId;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("SKUId")
    private Long goodsExtendId;

    @ApiModelProperty("基础价格")
    private BigDecimal baseSalePrice;

    @ApiModelProperty("分销价格")
    private BigDecimal baseRetailPrice;

    @ApiModelProperty("分销分摊价格")
    private BigDecimal retailPrice;

    @ApiModelProperty("销售分摊价格")
    private BigDecimal salePrice;

    @ApiModelProperty("服务地点")
    private String servicePlaceId;

    @ApiModelProperty("线路")
    private Long routeId;
}
