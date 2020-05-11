package cn.enn.wise.ssop.api.order.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@ApiModel("交易记录列表条目返回数据")
@Data
public class TradeDTO {

    @ApiModelProperty("交易时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date tradeTime;

    @ApiModelProperty("交易金额")
    private BigDecimal amount;

    @ApiModelProperty("商户")
    private String merchant;

//    @ApiModelProperty("通道类型")
//    private String appType;
//
//    @ApiModelProperty("通道名称")
//    private String appName;

    @ApiModelProperty("渠道类型")
    private String channelType;

    @ApiModelProperty("渠道名称")
    private String channelName;


    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单类型")
    private Long orderType;



}