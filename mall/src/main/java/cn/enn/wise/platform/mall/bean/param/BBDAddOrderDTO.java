package cn.enn.wise.platform.mall.bean.param;


import lombok.Data;

import java.util.List;


@Data
public class BBDAddOrderDTO {

    /** 心仪涠洲OrderCode **/
    private String localOrderCode;

    /** 支付金额 **/
    private String payment;

    /** 百邦达产品id（排班产品传flightId） **/
    private String productId;

    /** 船票列表 **/
    private List<BBDAddOrderTicketDTO> ticketTypeInfoList;

    /** 联系人 **/
    private String  contact;

    /** 客户ID **/
    private String custId;

    /** 出发日期 **/
    private String departureDate;

    /** 备注 **/
    private String memo;

    /** 联系电话 **/
    private String mobile;

    /** 支付方式:Weixin(2, "微信支付"),Alipay(3, "支付宝") **/
    private String payType;

}
