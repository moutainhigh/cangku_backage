package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.WithdrawLimitMapper;
import cn.enn.wise.ssop.service.order.model.WithdrawLimit;
import cn.enn.wise.ssop.service.order.service.WithdrawLimitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * 提现限额
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
@Service
public class WithdrawLimitServiceImpl extends ServiceImpl<WithdrawLimitMapper, WithdrawLimit> implements WithdrawLimitService {


    private Double maxMoney = 5000.0;
    private Integer maxTimes = 10;


    /**
     * 此次提现后，更新当前限额
     *
     * @param distributorId 分销商ID
     * @param money 提现金额
     */
    @Override
    public void updateLimit(Long distributorId, Double money) {
        String date = LocalDate.now().toString();
        QueryWrapper<WithdrawLimit> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(WithdrawLimit::getCDate,date)
                .eq(WithdrawLimit::getDistributorId,distributorId)
                .orderByDesc(WithdrawLimit::getId);
        List<WithdrawLimit> limit = list(queryWrapper);
        if(limit==null || limit.size()==0){
            WithdrawLimit limitN = new WithdrawLimit();
            limitN.setCDate(LocalDate.now());
            limitN.setDistributorId(distributorId);
            limitN.setMoney(money);
            limitN.setTimes(1);
            save(limitN);
        }else{
            WithdrawLimit limitN = limit.get(0);
            limitN.setMoney(limitN.getMoney()+money);
            limitN.setTimes(limitN.getTimes()+1);
            UpdateWrapper<WithdrawLimit> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda()
                    .eq(WithdrawLimit::getCDate,date)
                    .eq(WithdrawLimit::getDistributorId,distributorId);
            update(limitN,updateWrapper);
        }
    }



    /**
     * 检查当前提现限额
     *
     * @param distributorId 分销商ID
     * @param thisApply 此次申请金额
     * @return 0：符合提现条件；-1：提现次数已达上限；-2：提现金额已达上限
     */
    @Override
    public int checkLimit(Long distributorId, double thisApply) {
        String date = LocalDate.now().toString();
        QueryWrapper<WithdrawLimit> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(WithdrawLimit::getCDate,date)
                .eq(WithdrawLimit::getDistributorId,distributorId)
                .orderByDesc(WithdrawLimit::getId);
        List<WithdrawLimit> limit = list(queryWrapper);
        if(limit==null || limit.size()==0){
            return 0;
        }
        WithdrawLimit limitN = limit.get(0);
        if(limitN.getTimes()>=maxTimes){
            return -1;
        }
        if(limitN.getMoney()+thisApply>maxMoney){
            return -2;
        }
        return 0;
    }




}
