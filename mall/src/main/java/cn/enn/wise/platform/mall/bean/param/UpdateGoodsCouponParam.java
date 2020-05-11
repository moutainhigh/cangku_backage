package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/13
 */
@Data
@ApiModel("参数")
public class UpdateGoodsCouponParam {

    @ApiModelProperty("id")
    private Long[] id;
    @ApiModelProperty("状态 1 可用 2 不可用")
    private Byte status;
}
