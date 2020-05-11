package cn.enn.wise.platform.mall.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 百邦达离线订单下单参数
 *
 * @author baijie
 * @date 2020-01-07
 */
@Data
@Builder
public class BbdOrderSaveParams {

    @ApiModelProperty(value = "产品id（排班产品传flightId过来）")
    private String productId;

    @ApiModelProperty("体验日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date departureDate;

    @ApiModelProperty("游客姓名")
    private String tourismName;

    @ApiModelProperty("phone,游客手机号")
    private String phone;

    @ApiModelProperty("分销商业务编号")
    private String businessNumber;

    @ApiModelProperty("是否是景区接送服务 1 是 2 不是")
    private Integer isScenicService;

    @ApiModelProperty("订单号")
    private String thirdOrderNo;

    @ApiModelProperty("订单金额")
    private BigDecimal orderPrice;

    @ApiModelProperty("票型 1成人 2 儿童 3 特优免票")
    private Integer ticketType;

    @ApiModelProperty("票型 1成人 2 儿童 3 特优免票")
    private Long amount;


    @ApiModelProperty("佰邦达订单号")
    private String orderSerialBbd;

    @ApiModelProperty("佰邦达票二维码")
    private String ticketQrCodeBbd;

    @ApiModelProperty("佰邦达票号")
    private String ticketSerialBbd;



}
