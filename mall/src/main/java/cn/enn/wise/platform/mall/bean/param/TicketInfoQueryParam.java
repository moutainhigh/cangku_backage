package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 船票查询参数
 *
 * @author baijie
 * @date 2019-12-24
 */
@Data
public class TicketInfoQueryParam {


    @ApiModelProperty("出发点名称")
    private String lineFrom;

    @ApiModelProperty("到达点名称")
    private String lineTo;

    @ApiModelProperty("使用日期")
    private String lineDate;

    @ApiModelProperty("船舱名称")
    private String cabinName;

    @ApiModelProperty("开船时间")
    private String timespan;

    @ApiModelProperty("船名称")
    private String shipName;
}
