package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface OrderChangeRecordMapper extends BaseMapper<OrderChangeRecord> {

    void batchUpdateOrderChangeRecord(List<OrderChangeRecord> orderChangeRecordList);

    void batchInsertOrderChangeRecord(List<OrderChangeRecord> orderChangeRecordList);
}
