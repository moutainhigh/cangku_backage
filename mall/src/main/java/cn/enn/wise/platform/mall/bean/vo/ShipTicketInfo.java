package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 船票订单预下单VO
 *
 * @author baijie
 * @date 2019-12-24
 */
@Data
@ApiModel("船票订单预下单VO")
public class ShipTicketInfo {


    @ApiModelProperty("goodsExtendId 商品skuId")
    private Long id;

    @ApiModelProperty("使用日期")
    private String lineDate;

    @ApiModelProperty("船舱名称")
    private String cabinName;

    @ApiModelProperty("历时 单位：分钟")
    private int takeTime;

    @ApiModelProperty("出发点名称")
    private String lineFrom;

    @ApiModelProperty("到达点名称")
    private String lineTo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("项目名称")
    private Long projectId;

    @ApiModelProperty("出发时间")
    private String startTime;

    @ApiModelProperty("到达时间")
    private String endTime;

    @ApiModelProperty("航线信息")
    private String shipLineInfo;

    @ApiModelProperty("开船时间")
    private String timespan;

    @ApiModelProperty("票型信息")
    private List<TicketTypeInfo> ticketList;



}
