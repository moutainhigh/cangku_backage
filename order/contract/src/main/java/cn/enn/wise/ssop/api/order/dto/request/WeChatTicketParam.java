package cn.enn.wise.ssop.api.order.dto.request;

import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsResponseDto;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("微信订单参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatTicketParam extends QueryParam {

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;
}
