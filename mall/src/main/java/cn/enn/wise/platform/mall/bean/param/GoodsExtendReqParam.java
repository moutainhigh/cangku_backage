package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 响应api商品扩展类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "商品扩展类请求参数")
public class GoodsExtendReqParam implements Serializable {
    @ApiModelProperty(value = "商品时间段信息主键")
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品使用时段字符串，如：08:00-09:00")
    private String timespan;

    @ApiModelProperty(value = "排序序号")
    private Byte orderby;

    @ApiModelProperty(value = "该时段的售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "与该时段对应的商品归属项目的时段的ID")
    private Long periodId;

    @ApiModelProperty(value = "标签")
    private String timeLabel;

    @ApiModelProperty(value = "时段信息列表，用于提交商品时段信息时使用")
    private List<GoodsExtendReqParam> goodsExtendReqParamList;

    public GoodsExtendReqParam() {
        super();
    }

    public GoodsExtendReqParam(Long id, Long goodsId, String timespan, Byte orderby, BigDecimal salePrice, Long periodId) {
        this.id = id;
        this.goodsId = goodsId;
        this.timespan = timespan;
        this.orderby = orderby;
        this.salePrice = salePrice;
        this.periodId = periodId;
    }
}