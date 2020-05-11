package cn.enn.wise.ssop.api.order.dto.response.scan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


@Data
@ApiModel("订单用户记录列表")
public class ContactDTO {

    @ApiModelProperty("联系人id 如果核销时用就加")
    private Long orderId;//联系人id 如果核销时用就加

    @ApiModelProperty("联系人对应票的code 如果核销时用就加")
    private String contactTicketCode;//联系人对应票的code 如果核销时用就加

    @ApiModelProperty("联系人姓名")
    private String contactName;//联系人姓名

    @ApiModelProperty("联系人身份证号")
    private String contactIdCardNumber;//联系人身份证号

    @ApiModelProperty("票状态")
    private String ticketStateStr;//票状态

    @ApiModelProperty("可否核验，1：可以；0：不可以")
    private int canChargeOff;

    @ApiModelProperty("使用记录")
    private List<UseRecordDTO> useRecordList;//使用记录


}
