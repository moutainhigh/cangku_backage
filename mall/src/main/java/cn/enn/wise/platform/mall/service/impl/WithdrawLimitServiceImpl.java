package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.WithdrawLimit;
import cn.enn.wise.platform.mall.mapper.WithdrawLimitMapper;
import cn.enn.wise.platform.mall.service.WithdrawLimitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class WithdrawLimitServiceImpl extends ServiceImpl<WithdrawLimitMapper, WithdrawLimit> implements WithdrawLimitService{


    private Double maxMoney = 5000.0;
    private Integer maxTimes = 10;

    @Transactional(rollbackFor = Exception.class)
    public void updateLimit(Long distributorId,Double money){
        String date = LocalDate.now().toString();
        QueryWrapper<WithdrawLimit> queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_date",date);
        queryWrapper.eq("distributor_id",distributorId);
        WithdrawLimit limit = getOne(queryWrapper);
        if(limit==null){
            limit = new WithdrawLimit();
            limit.setCDate(LocalDate.now());
            limit.setDistributorId(distributorId);
            limit.setMoney(money);
            limit.setTimes(1);
            save(limit);
        }else{
            limit.setMoney(limit.getMoney()+money);
            limit.setTimes(limit.getTimes()+1);
            UpdateWrapper<WithdrawLimit> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("c_date",date);
            updateWrapper.eq("distributor_id",distributorId);
            update(limit,updateWrapper);
        }
    }


    public boolean checkLimit(Long distributorId){
        String date = LocalDate.now().toString();
        QueryWrapper<WithdrawLimit> queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_date",date);
        queryWrapper.eq("distributor_id",distributorId);
        WithdrawLimit limit = getOne(queryWrapper);
        if(limit==null){
            return true;
        }
        if(limit.getTimes()>=maxTimes){
            return false;
        }
        if(limit.getMoney()>=maxMoney){
            return false;
        }
        return true;
    }

}
