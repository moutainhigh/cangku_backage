package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 项目展示VO
 *
 * @author baijie
 * @date 2019-11-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ProjectVO")
public class ProjectVo {

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("商品类型 3 门票")
    private Integer goodsType;

    @ApiModelProperty("项目图集")
    private List<String> imageUrls;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目简介")
    private String introduction;

    @ApiModelProperty("基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty("拼团价格")
    private BigDecimal groupPrice;

    @ApiModelProperty("分销价格")
    private BigDecimal distributePrice;

    @ApiModelProperty("售卖价格")
    private BigDecimal salePrice;

    @ApiModelProperty("运营地点")
    private String operationPlace;

    @ApiModelProperty("运营时段")
    private String operationSpan;

    @ApiModelProperty("图文介绍")
    private String graphicIntroduction;

    @ApiModelProperty("使用须知")
    private String usageNotice;

    @ApiModelProperty("活动类型 1 无活动 2拼团活动 3 分销活动 4 优惠活动,5 套餐商品")
    private Integer activityType;

    @ApiModelProperty("客服电话")
    private String hotelLine ;

    @ApiModelProperty("拼团活动id")
    private Long promotionId;

    @ApiModelProperty("当前最早可预约时间")
    private String appointment;

    @ApiModelProperty("是否分时段售卖 1是 2 不是")
    private Integer isByPeriodOperation;

    @ApiModelProperty("套装商品明细")
    private List<PackageGoodsItemVO>  itemVOList;

    @ApiModelProperty("拼团活动是否有效 1 有效 2无效")
    private Integer is_available;

    @ApiModelProperty("套装商品可售卖时间")
    private List<Map<Integer,Object>> saleDate;

    @ApiModelProperty("最早可预订日期")
    private String availableDates;

    @ApiModelProperty("项目头图")
    private String headImg;

}
