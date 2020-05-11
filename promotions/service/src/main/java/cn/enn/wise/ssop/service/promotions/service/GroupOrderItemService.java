package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.service.promotions.mapper.GroupOrderItemMapper;
import cn.enn.wise.ssop.service.promotions.model.GroupOrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * GroupOrder服务实现类
 *
 * @author jiabaiye
 */
@Service
public class GroupOrderItemService extends ServiceImpl<GroupOrderItemMapper, GroupOrderItem>  {

    private Logger logger = LoggerFactory.getLogger(GroupOrderItemService.class);


}
