package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/24
 */
@Data
@ApiModel("参数")
public class CouponRecordsParams {

    @ApiModelProperty("券类型 1 抵扣券 2 折扣券")
    private String couponType;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("券号")
    private String code;

    @ApiModelProperty("会员id")
    private String userId;

    @ApiModelProperty("消费情况")
    private String status;

    @ApiModelProperty("优惠券id")
    private Long couponId;

}
