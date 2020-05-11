package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.WithdrawSerialMapper;
import cn.enn.wise.ssop.service.order.model.WithdrawSerial;
import cn.enn.wise.ssop.service.order.service.WithdrawSerialService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDate;



/**
 * 提现序列号
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
@Service
public class WithdrawSerialServiceImpl extends ServiceImpl<WithdrawSerialMapper, WithdrawSerial> implements WithdrawSerialService {


    /**
     * 查询当前使用的序列号，并更新数据表序列号
     *
     * @return
     */
    @Override
    public int getAndUpdateSerial() {
        String date = LocalDate.now().toString();
        QueryWrapper<WithdrawSerial> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(WithdrawSerial::getCDate,date);
        WithdrawSerial withdrawSerial = getOne(queryWrapper);
        if(withdrawSerial==null){
            withdrawSerial = new WithdrawSerial();
            withdrawSerial.setCDate(LocalDate.now());
            withdrawSerial.setSerial(2);
            save(withdrawSerial);
            return 1;
        }else{
            int c = withdrawSerial.getSerial();
            withdrawSerial.setSerial(c+1);
            updateById(withdrawSerial);
            return c;
        }
    }


}
