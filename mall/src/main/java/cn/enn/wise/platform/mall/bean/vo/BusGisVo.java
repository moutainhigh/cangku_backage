package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/9
 */
@Data
@ApiModel("gis")
public class BusGisVo {

    @ApiModelProperty("类型:1厕所 2停车场 3小卖部 4暖水服务 5租车 501商务轿车 502便捷小车 6咨询台")
    private Long type;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("资源点信息")
    private List<PoiVo> list;

}
