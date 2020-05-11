package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 航班订单信息
 */
@Data
public class SeatInfoVo {

    @ApiModelProperty("证件号")
    private String idCard;

    @ApiModelProperty("航线")
    private String lineName;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("qrCode")
    private String qrCode;

    @ApiModelProperty("开船时间")
    private Date shipDate;

    @ApiModelProperty("开船时间字符串")
    private String shipDateStr;

    @ApiModelProperty("舱位信息")
    private String shipName;

    @ApiModelProperty("座位号")
    private String ticketNo;

    @ApiModelProperty("手机号")
    private String phone;


    public SeatInfoVo(String idCard, String lineName, String name, String qrCode, Date shipDate, String shipName, String ticketNo) {
        this.idCard = idCard;
        this.lineName = lineName;
        this.name = name;
        this.qrCode = qrCode;
        this.shipDate = shipDate;
        this.shipName = shipName;
        this.ticketNo = ticketNo;
    }
}
