package cn.enn.wise.platform.mall.service;

/**
 * 订单流转记录接口
 * @program: mall
 * @author: zsj
 * @create: 2020-01-15 17:26
 **/
public interface OrderStateHistoryService {

    /**
     * 根据ID查询出修改后的订单信息
     * 再保存在订单流转历史记录表
     * @param id
     * @param operate
     */
    public void saveOrderStateHistory(Long id, String operate);
}
