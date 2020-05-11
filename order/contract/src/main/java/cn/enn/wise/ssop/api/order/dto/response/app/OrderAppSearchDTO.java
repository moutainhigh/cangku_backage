package cn.enn.wise.ssop.api.order.dto.response.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 订单参数
 *
 * @author yangshuaiquan
 * @date 2020-04-23
 */

@Data
@ApiModel("订单筛选列表返回参数")
public class OrderAppSearchDTO {

    @ApiModelProperty("总条数")
    private int peopleNumBer;

    @ApiModelProperty("订单列表")
    private List<OrderAppSearchListDTO> orderAppletSearchListDTOS;
}
