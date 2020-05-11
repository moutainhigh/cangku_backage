package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/4
 */
@Data
@ApiModel("公交")
public class BusVo {

    @ApiModelProperty("线路编号")
    private String lineNo;

    @ApiModelProperty("线路id")
    private String stationId;

    @ApiModelProperty("附近站点")
    private Integer ifRecommend;

    @ApiModelProperty("起点站")
    private String startStationName;

    @ApiModelProperty("终点站")
    private String endStationName;

    @ApiModelProperty("最近站点")
    private String nearestStationName;

    @ApiModelProperty("线路")
    private List<StationVo> lineInfo;


    @ApiModelProperty("首班时间")
    private String startTime;

    @ApiModelProperty("末班时间")
    private String endTime;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("到当前站点用时")
    private Integer userTime;


}


