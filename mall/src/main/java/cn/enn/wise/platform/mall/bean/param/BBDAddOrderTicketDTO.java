package cn.enn.wise.platform.mall.bean.param;


import lombok.Data;

@Data
public class BBDAddOrderTicketDTO {

    /** 票价 **/
    private  String price;

    /** 心仪涠洲票ID **/
    private String appTicketId;

    /** 票型，1全票 2儿童票 3特优免票 **/
    private int ticketType = 0;

}
