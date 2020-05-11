package cn.enn.wise.ssop.api.order.dto.response.applet;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TicketInfoComboDto{

    //套餐商品名称
    private String goodsName;
    //套餐商品规格
    private GoodsSkuExtraOrderDto goodsSku;
    //套餐票据信息
    private ArrayList<TicketMsgOrderDto> ticketMsg;



}
