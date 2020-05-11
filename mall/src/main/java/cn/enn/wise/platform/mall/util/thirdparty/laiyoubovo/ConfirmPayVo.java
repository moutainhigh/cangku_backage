package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

/**
 * 确认支付响应信息
 */
@Data
public class ConfirmPayVo {

    /**
     * 电子登船单的网络地址
     */
    private String e_tickets_url;
    /**
     * 座位号
     */
    private String SeatInfo;
}