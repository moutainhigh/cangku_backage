package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 佰邦达订单改签参数
 *
 * @author baijie
 * @date 2020-01-13
 */
@Data
@ApiModel("改签请求参数")
public class BbdOrderChangeParam {

    @ApiModelProperty("改签数据")
    public List<ChangeData> changeData;


    /**
     * 改签数据
     */
    @Data
    @ApiModel("改签数据列表")
    public static class ChangeData {

        @ApiModelProperty("需要改签的佰邦达票Id")
        private String changeTicketSerialBbd;

        @ApiModelProperty("需要改签的佰邦达订单号")
        private String changeOrderSerialBbd;


        @ApiModelProperty("改签后的佰邦达佰邦达票Id")
        private String ticketSerialBbdId;


        @ApiModelProperty("改签后的佰邦达票二维码")
        private String ticketQrCodeBbd;


        @ApiModelProperty("改签后的佰邦达票号")
        private String ticketSerialBbd;


        @ApiModelProperty("新的佰邦达票状态0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款  410 票改签 ")
        private Integer ticketStateBbd;


        @ApiModelProperty("打印状态 0.未打印 1.已打印")
        private Integer isTicketPrinted;


        @ApiModelProperty("改签后的产品id（排班产品传flightId过来）")
        private String productId;


        @ApiModelProperty("改签后的体验日期")
        private String departureDate;


    }
}
