package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.api.order.dto.response.OrderCancelListDTO;
import cn.enn.wise.ssop.service.order.model.OrderCancelRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderCancelRecordMapper extends BaseMapper<OrderCancelRecord> {

    void batchInsert(List<OrderCancelRecord> orderCancelRecordList);
    List<OrderCancelListDTO> orderCancelList(List<Long> orderIds);
}
