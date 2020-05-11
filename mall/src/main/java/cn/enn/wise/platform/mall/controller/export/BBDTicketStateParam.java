package cn.enn.wise.platform.mall.controller.export;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BBDTicketStateParam {

    @ApiModelProperty("心仪涠洲票ID")
    private Long ticketId;

    @ApiModelProperty("票状态,用于票状态更新")
    private int status;

    @ApiModelProperty("新的票号，用于换票业务")
    private String ticketNum;

    @ApiModelProperty("新的二维码，用于换票业务")
    private String qrCode;

}
