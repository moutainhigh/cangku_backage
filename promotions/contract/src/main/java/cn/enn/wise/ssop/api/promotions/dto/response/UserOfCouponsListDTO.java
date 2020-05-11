package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("用户优惠券列表")
public class UserOfCouponsListDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券状态 1 有效 2 无效")
    private Byte couponStatus;

    @ApiModelProperty("券码")
    private Long couponCode;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("名称-电子券名称")
    private String name;

    @ApiModelProperty("发放日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Timestamp grantTime;

    @ApiModelProperty("领取日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Timestamp receiveTime;

    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @ApiModelProperty("用户名称（会员名称）")
    private String userName;

    @ApiModelProperty("消费日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Timestamp consumptionTime;

    @ApiModelProperty("状态( 1领取未使用 2已使用 3已过期 4转让中 5已转让)")
    private Byte state;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty(value = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动状态 1 活动中 2 未开始 3 结束 4 已失效")
    private Byte activityStatus;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("抵扣金额")
    private Integer replacePrice;
}
