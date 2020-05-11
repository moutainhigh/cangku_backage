package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券-用户参数")
public class UserOfCouponsListParam extends QueryParam {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("券码")
    private Long couponCode;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("发放日期")
    private Timestamp grantTimeStart;

    @ApiModelProperty("发放日期")
    private Timestamp grantTimeEnd;

    @ApiModelProperty("领取日期")
    private Timestamp receiveTimeStart;

    @ApiModelProperty("领取日期")
    private Timestamp receiveTimeEnd;

    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @ApiModelProperty("用户名称（会员名称）")
    private String userName;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("券状态( 1未领取 2领取未使用 3已使用  4已过期 5转让中 6已转让)")
    private Byte state;
}
