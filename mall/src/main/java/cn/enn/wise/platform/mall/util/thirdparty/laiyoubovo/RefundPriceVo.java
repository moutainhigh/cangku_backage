package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

/**
 * 退票费率信息
 */
@Data
public class RefundPriceVo {


    /**
     * 订单编号 eg: 8000xxx
     */
    private String orderSerial;
    /**
     * 票数量  eg:2
     */
    private Integer Count;
    /**
     * 票总价  360.00
     */
    private String totalAmount;
    /**
     * 费率   eg:0.1
     */
    private String totalBackRate;
    /**
     * 退款   eg:324.00
     */
    private String totalBack;
}