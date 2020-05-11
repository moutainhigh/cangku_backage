package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

/**
 * 下单响应信息
 */
@Data
public class CreateOrderVo{

    /**
     * 订单号
     */
    private String orderSerial;
    /**
     * 订单状态
     * 0：未预定
     * 1：已预订
     * 2：已取消
     */
    private String orderState;
    /**
     * 查询码
     */
    private String code;
    /**
     * 取票密码
     */
    private String o_TicketPassWord;
}
