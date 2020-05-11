package cn.enn.wise.ssop.service.order.service;


/**
 * 提现序列号
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
public interface WithdrawSerialService {


    /**
     * 查询并更新序列号设置
     *
     * @return 返回此次提现序列号
     */
    int getAndUpdateSerial();


}
