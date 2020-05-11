package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/27 15:16
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundBoatOrderVo {

    @ApiModelProperty(value = "起点")
    private String lineFrom;

    @ApiModelProperty(value = "终点")
    private String lineTo;

    @ApiModelProperty(value = "船舱名称")
    private String cabinName;

    @ApiModelProperty(value = "船名称")
    private String nickName;

    @ApiModelProperty(value = "航班开始时间")
    private String boatStartTime;

    @ApiModelProperty(value = "航班结束时间")
    private String boatEndTime;


    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineDate;

    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineEndDate;

    @ApiModelProperty(value = "来游吧订单号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "乘客信息")
    private List<PassengerVo> passengerVoList;

    @ApiModelProperty(value = "取票须知")
    private String shouldKnow;

    @ApiModelProperty(value = "航班信息")
    private String shipLineInfo;

    @ApiModelProperty(value = "经过时间(单位分钟)")
    private Integer afterTime;


}
