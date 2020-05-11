package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

import java.util.List;

/**
 * 订单详情
 */
@Data
public class OrderDetailVo {

    /**
     * 订单号
     */
    private String sn;
    /**
     * 查询码
     */
    private String code;
    /**
     * 订单金额
     */
    private String amonut;
    /**
     * 退款金额
     */
    private String refund;
    /**
     * 整单状态(旧) 参照船票订单说明
     */
    private String orderState;
    /**
     * 整单状态（新）参照status船票订单说明（新）
     */
    private String status;
    /**
     * 是否是停航订单 1为停航订单 0为非停航订单
     */
    private Integer stopstatus;
    /**
     * 下单用户名
     */
    private String customer;
    /**
     * 用户手机
     */
    private String mobele;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String idCard;
    /**
     * 取票码
     */
    private String ticketPassWord;
    /**
     * 锁位时间
     */
    private String lock_time;
    /**
     * 创建时间
     */
    private String orderDate;
    /**
     * 订单类型
     */
    private String type;
    /**
     * 电子登船单的地址
     */
    private String e_tickets_url;
    /**
     * 使用时间
     */
    private String usetime;
    /**
     * 订单明细
     */
    private List<OinfoVo> oinfo;
}