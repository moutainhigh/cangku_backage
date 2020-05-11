package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/9
 */
@Data
@ApiModel("资源点")
public class PoiVo {


    @ApiModelProperty("资源点id")
    private Long id;

    @ApiModelProperty("lat")
    private Double lat;

    @ApiModelProperty("lng")
    private Double lng;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("phone")
    private String phone;

}
