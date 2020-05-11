package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


/**
 * 计算的优惠活动返回的参数
 * @author yangshuaiquan
 */
@Data
@ApiModel("计算优惠券或活动返回的参数")
public class ActivityPriceUtilDTO {

    @ApiModelProperty("优惠券")
    private List<CouponPriceDTO> couponPriceDTOS;

    @ApiModelProperty("活动")
    private List<ActivityPriceDTO> activityPriceDTOS;

    @ApiModelProperty("优惠类型 1 活动优惠   4 优惠券")
    private Byte activityType;

    @ApiModelProperty("是否分销商 1 是 2否")
    private Byte isDistribute;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("优惠后价格")
    private Integer afterPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty("产品使用时间时间")
    private Timestamp orderUseTime;

    @ApiModelProperty("总售价")
    private Integer totalPrice;
}
