package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 优惠券信息Vo
 *
 * @author baijie
 * @date 2019-12-17
 */
@Data
public class CouponInfoVo {

    @ApiModelProperty("用户领取记录ID")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("优惠券Id")
    private Long goodsCouponId;

    @ApiModelProperty("优惠活动Id")
    private Long promotionId;

    @ApiModelProperty("优惠券过期时间")
    private Timestamp validityTime;

    @ApiModelProperty(" 优惠券领取状态 1领取未使用 2已使用 3已过期 4装让中 5已转让)")
    private Integer status;

    @ApiModelProperty("优惠券使用规则名称")
    private Long goodsCouponRuleId;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券类型 1-抵用券 2-折扣券")
    private Integer couponType;

    @ApiModelProperty("面值 抵用券的时候代表多少元，折扣券的时候是折扣力度（7折，输入70）")
    private Integer price;

    @ApiModelProperty("使用条件 1 订单总价 2 商品单价")
    private Integer useRule;

    @ApiModelProperty("达到限额可用（满足多少钱可用）")
    private Integer minUse;

    @ApiModelProperty("是否可以叠加 1可以 2 不可以")
    private Integer isOverlay;

    @ApiModelProperty("活动状态 活动状态( 1未开始 2活动中 3已结束 4 已失效)")
    private Integer promotionStatus;

    @ApiModelProperty("优惠券状态")
    private Byte goodsCouponStatus;

    @ApiModelProperty("分销商手机号")
    private String phone;
}
