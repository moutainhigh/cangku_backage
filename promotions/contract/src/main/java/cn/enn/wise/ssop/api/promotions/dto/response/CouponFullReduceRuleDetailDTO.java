package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券详情-满减券")
public class CouponFullReduceRuleDetailDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;

    @ApiModelProperty("满足多少价钱的金额")
    private Integer satisfyPrice;

    @ApiModelProperty("优惠计算方式 1 金额  2 折扣/百分比")
    private Byte rebateMethod;

    @ApiModelProperty("加价多少/折扣多少")
    private Integer rebatePrice;

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

    @ApiModelProperty("优惠券绑定商品信息")
    List<CouponGoodsDetailsDTO> couponGoodsDetailsDTOS;
}
