package cn.enn.wise.platform.mall.bean.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/9
 */
@Data
@ApiModel("优惠券列表参数")
public class GoodsCouponParam {

    @ApiModelProperty("来源 1 优惠券列表 2 选择优惠券")
    private String type;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券类型")
    private Integer couponType;

    @ApiModelProperty("优惠券状态")
    private Integer status;

    @ApiModelProperty("优惠券业务标签")
    private Integer tag;

    @ApiModelProperty("开始 结束时间")
    private String[]  validityTime;

}
