package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 响应api商品扩展类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "GoodsApiExtendResVo")
public class GoodsApiExtendResVo implements Serializable {
    /**
     * 主键
     */
    private Long id;


    /**
     * 商品使用时段
     */
    @ApiModelProperty(value = "商品使用时段")
    private String timespan;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal salePrice;

    /**
     * 时段Id
     */
    @ApiModelProperty(value = "时段Id")
    private Integer periodId;

    /**
     * 热气球运营状态
     */
    @ApiModelProperty(value = "热气球运营状态")
    private Integer operationSatus;

    @ApiModelProperty(value = "分销价格")
    private BigDecimal retailPrice;

    /**
     * 飞行概率
     */
    @ApiModelProperty(value = "飞行概率")
    private Integer probability;

    @ApiModelProperty(value = "多人票中实际优惠金额")
    private String realTips;

    /**
     * 热气球运营时间
     */

    @ApiModelProperty(value = "热气球运营时间")
    private Date operationDate;

    /**
     * 时段 标签
     */
    @ApiModelProperty(value = "时段标签")
    private String timeLabel;


    @ApiModelProperty(value = "影响范围程度")
    private String degreeOfInfluence;

    @ApiModelProperty(value = "1数值 2 文字标签")
    private Integer operationStatusType;

    @ApiModelProperty(value = "文字标签的值")
    private String label;

    @ApiModelProperty("是否在小程序端展示 1是 2 否")
    private Integer isShowApplet;

    @ApiModelProperty("附加信息")
    private String shipLineInfo;

}
