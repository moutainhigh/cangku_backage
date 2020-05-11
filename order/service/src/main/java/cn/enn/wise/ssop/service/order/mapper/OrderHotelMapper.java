package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.service.order.model.OrderHotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderHotelMapper  extends BaseMapper<OrderHotel> {
}
