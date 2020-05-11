package cn.enn.wise.ssop.api.order.dto.response.scan;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("子订单")
@Data
public class OrderChildDTO {

    @ApiModelProperty("子票的id 如果核销时用就加")
    private Long orderId;//子票的id 如果核销时用就加

    @ApiModelProperty("子票code 如果核销时用就加")
    private String childTicketCode;//子票code 如果核销时用就加

    @ApiModelProperty("产品名称")
    private String productName;//产品名称

    @ApiModelProperty("订单状态描述")
    private String orderStateStr;//订单状态描述

    @ApiModelProperty("规格值名称—— 1级规格值及细化规格值")
    private String specificationValue;//规格值名称—— 1级规格值及细化规格值

    @ApiModelProperty("预订日期")
    private String bookingDate;//预订日期

    @ApiModelProperty("可否核验，1：可以；0：不可以")
    private int canChargeOff;

    @ApiModelProperty("子票下联系人列表 （单票没有）")
    private ContactInfoDTO contactList;//子票下联系人列表 （单票没有）

    @ApiModelProperty("核销方式：0整批 1分批")
    private int isCheckAll;//0整批 1分批


}
