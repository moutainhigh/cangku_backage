package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 响应api商品扩展类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "商品扩展响应数据类")
public class GoodsExtendOperationResVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品使用时段")
    private String timespan;

    @ApiModelProperty(value = "排序序号")
    private Byte orderby;

    @ApiModelProperty(value = "售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "时段ID")
    private Long periodId;

    @ApiModelProperty(value = "该时段是否启用 true-是 false-否")
    private boolean operationFlag;

    @ApiModelProperty(value = "标签")
    private String timeLabel;
}