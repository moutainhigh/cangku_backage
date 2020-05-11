package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品和活动规则信息Vo
 *
 * @author baijie
 * @date 2019-09-18
 */
@Data
public class GoodsAndGroupPromotionInfoVo {


    private Long projectId;

    private Long goodsId;

    private BigDecimal goodsPrice;

    private BigDecimal groupPrice;

    private String goodsNum;

    private String goodsName;

    private Integer groupSize;

    private Integer isAutoCreateGroup;

    private Integer groupType;

    private Integer groupValidHours;

    private Integer groupLimit;

    private Integer type;


}
