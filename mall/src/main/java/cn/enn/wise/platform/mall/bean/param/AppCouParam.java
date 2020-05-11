package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-03-31 15:04
 **/
@Data
@ApiModel("核销列表页参数")
public class AppCouParam {

    @ApiModelProperty("手机号、联系人、订单号")
    private String msg;

    @ApiModelProperty("状态  2待使用   3已使用  全部不用传")
    private String state;

    @ApiModelProperty("商家id")
    private Long businessId;
}
