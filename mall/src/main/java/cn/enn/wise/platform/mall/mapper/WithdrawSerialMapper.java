package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.WithdrawSerial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface WithdrawSerialMapper extends BaseMapper<WithdrawSerial> {



    WithdrawSerial selectByDate(@Param("cDate") String date);


    void updateSerial(@Param("cDate") String date, @Param("serial")Integer serial);

    void insertSerial(@Param("cDate") String date, @Param("serial")Integer serial);


}
