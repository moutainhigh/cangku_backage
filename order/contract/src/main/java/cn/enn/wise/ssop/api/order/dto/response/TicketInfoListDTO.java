package cn.enn.wise.ssop.api.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 票信息
 *
 * @author yangshuaiquan
 * @date 2020-04-29
 */
@Data
@ApiModel(description = "票信息")
public class TicketInfoListDTO {

    @ApiModelProperty(value = "商品名")
    private String goodsName;

    @ApiModelProperty(value = "百邦德票务状态")
    private String ticketStateBbd;

    @ApiModelProperty(value = "百邦德票务")
    private String ticketSerialBbd;
}
