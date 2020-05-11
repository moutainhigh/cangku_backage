package cn.enn.wise.ssop.api.order.dto.request;

import lombok.Data;

@Data
public class OrderCancelParam {

    private String orderNo;

    private Long orderId;

    private String cancleReason;

}
