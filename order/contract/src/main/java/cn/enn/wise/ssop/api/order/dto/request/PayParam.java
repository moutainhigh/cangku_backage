package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安辉
 */
@Data
@ApiModel("支付参数")
public class PayParam {

    @ApiModelProperty("预下单单号")
    private String prepayId;
}
