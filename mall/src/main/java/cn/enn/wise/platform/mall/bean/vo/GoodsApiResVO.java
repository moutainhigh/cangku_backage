package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 响应api商品类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "GoodsApiResVO")
public class GoodsApiResVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")

    private Long id;

    /**
     * 计价单位
     */
    @ApiModelProperty(value = "计价单位")
    private String goodsName;

    /**
     * 是否套餐 1 是 0 否
     */
    @ApiModelProperty(value = "是否套餐 1 是 0 否")
    private Integer isPackage;

    /**
     * 基础价格
     */
    @ApiModelProperty(value = "基础价格")
    private BigDecimal basePrice;

    /**
     * 人数上限
     */
    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    /**
     * 人数上限
     */
    @ApiModelProperty(value = "使用规则")
    private String rules;

    /**
     * 使用场地
     */
    @ApiModelProperty(value = "使用场地")
    private String servicePlace;

    /**
     * 1:热气球 2:船
     */
    @ApiModelProperty(value = "1:热气球 2:船")
    private Integer goodsType;

    /**
     * 商品状态 1 上架 2 下架
     */
    @ApiModelProperty(value = "商品状态 1 上架 2 下架")
    private Integer goodsStatus;

    @ApiModelProperty(value = "分销价格")
    private BigDecimal retailPrice;

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    private String goodsCode;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /**
     * 商品扩展列表
     */
    @ApiModelProperty(value = "商品扩展列表")
    private List<GoodsApiExtendResVo> goodsApiExtendResVoList;

    /**
     * 多人票优惠价格
     */
    private String tips;


    /**
     * 单票返回商品规格信息
     */

    private List<GoodsApiSkuVo> skuInfo;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "是否分时段售卖")
    private Integer isByPeriodOperation;


    @ApiModelProperty(value = "是否是分销商品 1 是 2 不是 ,通过小程序扫分销二维码进入,判断该分销商的身份是否可以分销该商品")
    private Integer isDistributeGoods  ;

    @ApiModelProperty("项目图片")
    private String headImg;

    @ApiModelProperty("是否是组合商品  1 套餐 2 单品")
    private Integer isSuit;


}
