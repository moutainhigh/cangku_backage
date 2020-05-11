package cn.enn.wise.ssop.api.promotions.dto.response;

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
@ApiModel("用户拥有的优惠券")
public class UserHaveCouponDTO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("券码")
    private String couponCode;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否产品专用  1 是 2 否")
    private Byte goodsSpecial;

    @ApiModelProperty("优惠计算方式 1 金额  2 折扣/百分比")
    private Byte rebateMethod;

    @ApiModelProperty("加价多少/折扣多少")
    private Integer rebatePrice;

    @ApiModelProperty("优惠券绑定商品信息")
    List<CouponGoodsDetailsDTO> couponGoodsDetailsDTOS;

    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    @ApiModelProperty("结束时间（有效时间）")
    private Timestamp endTime;

}
