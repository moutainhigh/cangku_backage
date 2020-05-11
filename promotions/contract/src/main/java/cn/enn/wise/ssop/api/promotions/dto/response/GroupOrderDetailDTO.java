package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author jiabaiye
 */
@Data
@ApiModel(description = "拼团返回订单详情信息")
public class GroupOrderDetailDTO {

    @ApiModelProperty(value = "团详情")
     private GroupOrderDTO groupInfo;

    @ApiModelProperty(value = "订单列表")
     private List<OrderDTO> orderList;

}
