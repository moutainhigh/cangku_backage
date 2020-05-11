package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券-用户保存信息")
public class UserOfCouponsSaveParam {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券状态 1 有效 2 无效")
    private Byte couponStatus;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("发放日期")
    private Timestamp grantTime;

    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @ApiModelProperty("用户名称（会员名称）")
    private String userName;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty(value = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动状态 1 活动中 2 未开始 3 结束 4 已失效")
    private Byte activityStatus;

    @ApiModelProperty("有效期")
    private Timestamp validityTime;

    @ApiModelProperty("用户信息-用户用户转赠记录")
    private String openId;

    @ApiModelProperty("订单价格")
    private Integer orderPrice;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("抵扣金额")
    private Integer replacePrice;
}
