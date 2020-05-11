package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 14:42
 **/
@Data
@ApiModel("优惠卷详情")
public class CouInfo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "面额")
    private Double price;

    @ApiModelProperty(value = "优惠金额")
    private Double couponPrice;

    @ApiModelProperty(value = "活动状态( 1领取未使用 2已使用 3已过期 4装让中 5已转让)")
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("使用时间")
    private Timestamp enterTime;
}
