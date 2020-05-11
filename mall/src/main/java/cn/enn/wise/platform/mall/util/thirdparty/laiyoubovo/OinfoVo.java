package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

/**
 * 子订单信息
 */
@Data
public class OinfoVo {

    /**
     * 票号 ，单退时用到
     */
    private String TkdtID;
    /**
     * 乘客姓名
     */
    private String PassengerName;
    /**
     * 乘客证件号
     */
    private String PassengerCertificateNo;
    /**
     * 单票状态
     * 已售
     * 已出票
     * 已取消
     * 已核销
     * 已窗口退票
     */
    private String StatusName;
    /**
     * 携童信息
     */
    private String vChildInfo;
    /**
     * 打印时间
     */
    private String PrintTime;
    /**
     * 退票时间
     */
    private String DelTime;
    /**
     * 退票费率
     */
    private String Ratio;
    /**
     * 单张票退款金额
     */
    private Float BackMoney;
    /**
     * 座位号
     */
    private String SeatInfo;
}
