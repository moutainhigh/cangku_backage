package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/4/17 2:45 下午
 */
@Data
@ApiModel("优惠券-订单")
public class CouponOrderDTO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券规格id")
    private Long couponRuleId;

    @ApiModelProperty("券码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("优惠计算方式 1 金额  2 折扣/百分比")
    private Byte rebateMethod;

    @ApiModelProperty("加价多少/折扣多少")
    private Integer rebatePrice;

    @ApiModelProperty("是否随机金额 1 是  2 不是")
    private Byte israndom;

    @ApiModelProperty("随机最小金额")
    private Integer minPrice;

    @ApiModelProperty("随机最大金额")
    private Integer maxPrice;

    @ApiModelProperty("是否产品专用  1 是 2 否")
    private Byte goodsSpecial;

    @ApiModelProperty("是否可以转赠 1 是 2 否")
    private Byte issend;

    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp startTime;

    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp endTime;

    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @ApiModelProperty("不可用开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp unuseStartTime;

    @ApiModelProperty("不可用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp unuseEndTime;

    @ApiModelProperty("体验次数是否共享 1 共享次数  2各产品次数")
    private Byte experienceMethod;

    @ApiModelProperty("体验次数")
    private Integer experienceNumber;

    @ApiModelProperty(value = "时段类型 1 上午08-13  2 下午13-18 3 全天08-18   ")
    private Byte periodType;

    @ApiModelProperty("满足多少价钱的金额")
    private Integer satisfyPrice;

    @ApiModelProperty("是否可用 1 可用 2 不可用")
    private Byte canUse;

    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    private Long goodsExtendId;

}
