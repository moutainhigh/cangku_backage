package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 项目管理响应Vo
 *
 * @author baijie
 * @date 2019-07-30
 */
@Data
@ApiModel("项目中线路与地点详情")
public class GoodsProjectResVo {

    @ApiModelProperty("地点列表")
    private List<ServicePlaceVo> servicePlaces;

    @ApiModelProperty("线路列表")
    private List<RouteVo> routes;

    @ApiModelProperty("选中路线id")
    private Long routeCheckedId;

    @ApiModelProperty("选中地点id数组")
    private List<Long> placesCheckedIds;

    private Long  lineId;
    private String lineName;
    private Integer type;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("运营时长")
    private Integer dayOperationTime;

    @ApiModelProperty("单次服务人数")
    private Integer maxServiceAmount;

}
