package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券列表")
public class CouponListProDTO {

    @ApiModelProperty("主键-电子券id")
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

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

    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp startTime;

    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp endTime;

    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;


}
