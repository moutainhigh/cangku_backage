package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 船票订单列表
 *
 * @author baijie
 * @date 2019-12-21
 */
@Data
public class TicketOrderListDTO extends BaseOrderListDTO {

    @ApiModelProperty("船票订单数据")
    private List<TicketChildDTO> childList;

}
