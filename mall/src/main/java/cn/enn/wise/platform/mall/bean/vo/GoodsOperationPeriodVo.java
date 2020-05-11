package cn.enn.wise.platform.mall.bean.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 热气球实时运营状况
 *
 * @author anhui
 * @since 2019-05-23
 */
@Data
public class GoodsOperationPeriodVo {

    @ApiModelProperty(value = "时段标题")
    private String periodTitle;

    @ApiModelProperty(value = "时段id")
    private Long periodId;

    @ApiModelProperty(value = "状态：1可飞 2不可飞")
    private Byte status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "体验度")
    private Integer probability;

    @ApiModelProperty(value = "体验地点")
    private Long servicePlaceId;

    @ApiModelProperty(value = "热度")
    private Long hot;

    @ApiModelProperty(value = "排序字段")
    private Byte orderby;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "地点名称")
    private String placeName;

    @ApiModelProperty(value = "地点Id")
    private Long placeId;

    @ApiModelProperty(value = "状态值描述")
    private String statusValue;

    @ApiModelProperty(value = "影响级别")
    private String degreeOfInfluence;





}
