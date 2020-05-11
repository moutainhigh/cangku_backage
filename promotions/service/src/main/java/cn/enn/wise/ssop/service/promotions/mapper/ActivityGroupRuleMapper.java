package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.ActivityGroupRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author jiaby
 */
@Mapper
@Repository
public interface ActivityGroupRuleMapper extends BaseMapper<ActivityGroupRule> {
}
