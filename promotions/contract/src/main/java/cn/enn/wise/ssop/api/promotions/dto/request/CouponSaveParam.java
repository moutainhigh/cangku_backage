package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券添加信息")
public class CouponSaveParam {

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

    @ApiModelProperty("产品专用-选择的产品")
    private List<CouponGoodsSaveParam> couponGoodsSaveParams;

    @ApiModelProperty("是否可以转赠 1 是 2 否")
    private Byte issend;

    @ApiModelProperty("有效期类型 1-日期 2领取后")
    private Integer validityType;

    @ApiModelProperty("有效期范围类型为1日期-开始时间")
    private Timestamp startTime;

    @ApiModelProperty("有效期范围类型为1日期-开始时间")
    private Timestamp endTime;

    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @ApiModelProperty("不可用日期-开始")
    private Timestamp unuseStartTime;

    @ApiModelProperty("不可用日期-结束")
    private Timestamp unuseEndTime;

    @ApiModelProperty("体验次数是否共享 1 共享次数  2各产品次数")
    private Byte experienceMethod;

    @ApiModelProperty("体验次数")
    private Integer experienceNumber;

    @ApiModelProperty(value = "时段类型 1 上午08-13  2 下午13-18 3 全天08-18   ")
    private Byte periodType;

    @ApiModelProperty("满足多少价钱的金额")
    private Integer satisfyPrice;

}
