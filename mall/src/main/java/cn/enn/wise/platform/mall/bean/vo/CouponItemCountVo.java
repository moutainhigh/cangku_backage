package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 优惠券明细VO
 *
 * @author baijie
 * @date 2020-04-03
 */
@Data
public class CouponItemCountVo {

    @ApiModelProperty("优惠券券号")
    private String couponNo;

    @ApiModelProperty("面值")
    private BigDecimal couponPrice;

    @ApiModelProperty("优惠券类型")
    private String couponType;

    @ApiModelProperty("状态")
    private String couponStatus;

    @ApiModelProperty("游客Id")
    private Long userId;

    @ApiModelProperty("领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Timestamp createTime;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Timestamp orderCreateTime;

    @ApiModelProperty("核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Timestamp checkTime;

    @ApiModelProperty("核销商家")
    private String businessName;

    @ApiModelProperty("抵扣金额")
    private BigDecimal disaccount;

    @ApiModelProperty("结算折扣")
    private String settlementDiscount;

    @ApiModelProperty("结算金额")
    private BigDecimal settlementAmount;

    @ApiModelProperty("来源")
    private String source;
}
