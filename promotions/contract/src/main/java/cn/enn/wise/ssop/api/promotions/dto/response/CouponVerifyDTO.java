package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wangliheng
 * @date 2020/4/20 10:57 上午
 */
@Data
@ApiModel("用户体验券")
public class CouponVerifyDTO {

    @ApiModelProperty("优惠券状态 1 有效 2 无效")
    private Byte couponStatus;

    @ApiModelProperty("券码")
    private String couponCode;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("体验次数")
    private Integer experienceNumber;

    @ApiModelProperty("体验次数是否共享 1 共享次数  2各产品次数")
    private Byte experienceMethod;

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

    @ApiModelProperty("体验商品")
    private List<CouponExperienceGoodsDTO> couponExperienceGoodsDTOS;

    @ApiModelProperty("使用记录")
    private List<String> userHistory;


}
