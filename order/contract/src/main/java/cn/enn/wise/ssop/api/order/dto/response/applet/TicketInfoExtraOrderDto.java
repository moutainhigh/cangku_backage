package cn.enn.wise.ssop.api.order.dto.response.applet;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TicketInfoExtraOrderDto {

    private String goodsName;

    private GoodsSkuExtraOrderDto goodsSku;

    private ArrayList<TicketMsgOrderDto> ticketMsg;

    private ArrayList<TicketInfoComboDto> ticketInfoCombo;


}

