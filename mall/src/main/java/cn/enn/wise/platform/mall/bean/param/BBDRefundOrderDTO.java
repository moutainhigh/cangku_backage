package cn.enn.wise.platform.mall.bean.param;

import lombok.Data;

import java.util.List;

/**
 * 退款操作
 */
@Data
public class BBDRefundOrderDTO {

    /** 订单ID **/
    private String orderId;

    /** 退款方式 **/
    private String payMethod = "Weixin";

    /** 退款票列表 **/
    private List<BBDRefundTicketDTO> ticketRefundList;


}
