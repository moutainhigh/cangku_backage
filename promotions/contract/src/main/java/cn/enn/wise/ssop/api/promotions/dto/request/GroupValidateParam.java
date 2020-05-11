package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiaby
 */
@Data
@ApiModel("验证拼团参数")
public class GroupValidateParam {

    @ApiModelProperty("商品主键")
    @NotNull
    Long goodsId;

    @ApiModelProperty("拼团活动主键")
    @NotNull
    Long activityBaseId;

    @ApiModelProperty("团单主键")
    Long groupOrderId;

    @ApiModelProperty("团单类型 1 开团 2 参团")
    @NotNull
    Integer type;
}
