package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.service.order.model.OrderRefundRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderRefundRecordMapper extends BaseMapper<OrderRefundRecord> {

    void batchInsert(List<OrderRefundRecord> orderRefundRecordList);
}
