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
@ApiModel(value = "商品扩展类数据", description = "包含商品的运营时段及对应的价格等数据")
public class GoodsExtendResVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品使用时段，如：08:00-09:00")
    private String timespan;

    @ApiModelProperty(value = "排序序号")
    private Byte orderby;

    @ApiModelProperty(value = "售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "该时段最大服务人数")
    private Integer maxNum;

    @ApiModelProperty(value = "标签")
    private String timeLabel;

    @ApiModelProperty(value = "创建时间")
    private Integer createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    @ApiModelProperty(value = "更新时间")
    private Integer updateTime;

    @ApiModelProperty(value = "更新人ID")
    private Long updateUserId;

    @ApiModelProperty(value = "修改人名称")
    private String updateUserName;

    @ApiModelProperty(value = "持续时长 单位分钟")
    private Integer duration;

    @ApiModelProperty(value = "时段id")
    private Long periodId;
}