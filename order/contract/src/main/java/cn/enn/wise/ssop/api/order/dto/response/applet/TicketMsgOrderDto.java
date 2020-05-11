package cn.enn.wise.ssop.api.order.dto.response.applet;

import lombok.Data;

@Data
public class TicketMsgOrderDto {

    private String ticketName;

    private String ticketCode;

    private Byte ticketStatus;


}
