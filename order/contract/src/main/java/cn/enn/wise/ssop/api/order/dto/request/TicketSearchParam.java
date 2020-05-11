package cn.enn.wise.ssop.api.order.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchParam extends QueryParam {

    @ApiModelProperty(value = "订单来源")
    private Byte orderSource;

    @ApiModelProperty(value = "渠道id")
    private Integer channelId;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "订单状态")
    private Byte orderStatus;

    @ApiModelProperty(value = "支付状态")
    private Byte payStatus;

    @ApiModelProperty(value = "交易状态")
    private Byte transactionStatus;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "电话/订单编号/姓名,搜索条件")
    private String search;

}
