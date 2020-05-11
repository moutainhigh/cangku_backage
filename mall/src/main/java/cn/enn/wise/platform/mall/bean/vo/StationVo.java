package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/4
 */
@ApiModel("公交车")
@Data
public class StationVo {

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("精度")
    private String lng;

    @ApiModelProperty("编号")
    private String name;

    @ApiModelProperty("车站id")
    private String sId;

    @ApiModelProperty("排序")
    private Integer order;

    @ApiModelProperty("是否换乘车站")
    private String isChangeStation;

    @ApiModelProperty("是否当前公交车停靠站")
    private String isCurrentStation;

}
