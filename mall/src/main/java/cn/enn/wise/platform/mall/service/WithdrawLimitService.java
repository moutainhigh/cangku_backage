package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.WithdrawLimit;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WithdrawLimitService extends IService<WithdrawLimit> {

    /**
     * 更新限制状态
     * @param distributorId
     * @param money
     */
    void updateLimit(Long distributorId,Double money);


    /**
     * 验证是否符合提现条件
     * @param distributorId
     * @return
     */
    boolean checkLimit(Long distributorId);
}
