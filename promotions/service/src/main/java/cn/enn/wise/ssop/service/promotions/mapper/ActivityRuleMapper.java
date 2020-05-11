package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.ActivityRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 活动基础规则信息Mapper
 */
@Mapper
@Repository
public interface ActivityRuleMapper extends BaseMapper<ActivityRule> {

}
