package cn.enn.wise.ssop.service.order.service;


/**
 * 提现限额
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
public interface WithdrawLimitService {

    /**
     * 更新已提现额度
     *
     * @param distributorId 分销商ID
     * @param money 提现金额
     */
    void updateLimit(Long distributorId,Double money);


    /**
     * 验证是否符合提现条件
     *
     * @param distributorId 分销商ID
     * @param thisApply 此次申请金额
     * @return 0：符合提现条件；-1：提现次数已达上限；-2：提现金额已达上限
     */
    int checkLimit(Long distributorId,double thisApply);

}
