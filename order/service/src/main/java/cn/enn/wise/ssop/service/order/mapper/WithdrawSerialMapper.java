package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.service.order.model.WithdrawSerial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WithdrawSerialMapper extends BaseMapper<WithdrawSerial> {


    WithdrawSerial selectByDate(@Param("cDate") String date);

    void updateSerial(@Param("cDate") String date, @Param("serial")Integer serial);

    void insertSerial(@Param("cDate") String date, @Param("serial")Integer serial);

}
