package cn.enn.wise.ssop.service.order.mapper;


import cn.enn.wise.ssop.service.order.model.OrderChargeOff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderChargeOffMapper extends BaseMapper<OrderChargeOff> {

}
