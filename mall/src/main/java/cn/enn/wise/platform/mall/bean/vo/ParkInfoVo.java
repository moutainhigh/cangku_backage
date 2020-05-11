package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 停车场信息
 */
@Data
public class ParkInfoVo {

    @ApiModelProperty("停车场id")
    private Integer parkId;

    @ApiModelProperty("价格介绍")
    private String feeDescribe;

    @ApiModelProperty("停车场信息")
    private String parkInfo;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("总车位数")
    private Integer total;

    @ApiModelProperty("空车位数")
    private Integer usable;

    @ApiModelProperty("已用车位数")
    private Integer used;

    @ApiModelProperty("经纬度")
    private String lonlat;

    @ApiModelProperty("地址")
    private String parkAddress;

    public ParkInfoVo() {
    }

    public ParkInfoVo(Integer parkId, String feeDescribe, String parkInfo, Date updateTime, Integer total, Integer usable, Integer used) {
        this.parkId = parkId;
        this.feeDescribe = feeDescribe;
        this.parkInfo = parkInfo;
        this.updateTime = updateTime;
        this.total = total;
        this.usable = usable;
        this.used = used;
    }
}
