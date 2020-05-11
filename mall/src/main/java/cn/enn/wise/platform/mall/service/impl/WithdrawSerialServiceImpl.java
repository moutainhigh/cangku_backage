package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.WithdrawSerial;
import cn.enn.wise.platform.mall.mapper.WithdrawSerialMapper;
import cn.enn.wise.platform.mall.service.WithdrawSerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class WithdrawSerialServiceImpl extends ServiceImpl<WithdrawSerialMapper, WithdrawSerial> implements WithdrawSerialService {


    @Autowired
    private WithdrawSerialMapper mapper;

    @Override
    public int getAndUpdateSerial() {
        String date = LocalDate.now().toString();
        WithdrawSerial withdrawSerial =  mapper.selectByDate(date);
        if(withdrawSerial==null){
            mapper.insertSerial(date,2);
            return 1;
        }else{
            int c = withdrawSerial.getSerial();
            mapper.updateSerial(date,c+1);
            return c;
        }

    }
}
