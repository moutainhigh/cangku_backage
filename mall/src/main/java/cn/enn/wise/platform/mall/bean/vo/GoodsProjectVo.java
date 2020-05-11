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
 * 首页商品和项目列表VO
 *
 * @author baijie
 * @date 2019-10-29
 */
@Data
@ApiModel("首页项目商品集合")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsProjectVo {

    private Long id;

    @ApiModelProperty("类型 1 项目 2商品")
    private Integer type = 2;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("商品类型 3 门票")
    private Integer goodsType;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目简介")
    private String projectSummary;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("基础价格 ,商品的话就是基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty("活动价, 拼团活动则是拼团价,分销活动则是分销价,该商品 ")
    private BigDecimal activityPrice;

    @ApiModelProperty("活动类型 1 无活动 2拼团活动 3 分销活动 4 优惠活动 5 套装商品")
    private Integer activityType = 1;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签名称")
    private List<String> tagNameList;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("项目最低价")
    private BigDecimal minPrice;

    @ApiModelProperty("拼团活动Id")
    private Long promotionId;

    @ApiModelProperty("是否是套餐商品 1 是　2　不是")
     private Integer isSuit;

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

    @ApiModelProperty(value = "商品简介")
    private String synopsis;
}
