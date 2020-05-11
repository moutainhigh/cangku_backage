package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.WithdrawSerial;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WithdrawSerialService extends IService<WithdrawSerial> {


    int getAndUpdateSerial();

}
