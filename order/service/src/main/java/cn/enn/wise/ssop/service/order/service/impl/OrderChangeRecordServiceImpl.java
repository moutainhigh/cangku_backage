package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.OrderChangeRecordMapper;
import cn.enn.wise.ssop.service.order.model.OrderChangeRecord;
import cn.enn.wise.ssop.service.order.service.OrderChangeService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderChangeRecordServiceImpl implements OrderChangeService {

    @Autowired
    private OrderChangeRecordMapper orderChangeRecordMapper;

    /**
     * 查看操作记录结果
     * @param orderId
     * @return List<OrderChangeRecord>
     */

    @Override
    public List<OrderChangeRecord> getOrderChangeRecordList(Long orderId) {
        QueryWrapper<OrderChangeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        return orderChangeRecordMapper.selectList(queryWrapper);
    }
}
