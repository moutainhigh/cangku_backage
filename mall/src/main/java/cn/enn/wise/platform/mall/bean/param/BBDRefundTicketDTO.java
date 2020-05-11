package cn.enn.wise.platform.mall.bean.param;

import lombok.Data;

@Data
public class BBDRefundTicketDTO {

    /** 退票费用 **/
    private String refundFee="0.00";

    /** 票ID **/
    private String ticketId;

    /** 票号 **/
    private String ticketNo;

    /** 票价 **/
    private String ticketPrice;
}
