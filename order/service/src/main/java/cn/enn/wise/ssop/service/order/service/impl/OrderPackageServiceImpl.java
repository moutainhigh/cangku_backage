package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.OrderPackageMapper;
import cn.enn.wise.ssop.service.order.model.OrderPackage;
import cn.enn.wise.ssop.service.order.service.OrderPackageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 套餐订单明细
 *
 * @author baijie
 * @date 2020-05-03
 */
@Service
public class OrderPackageServiceImpl extends ServiceImpl<OrderPackageMapper, OrderPackage> implements OrderPackageService {
}
