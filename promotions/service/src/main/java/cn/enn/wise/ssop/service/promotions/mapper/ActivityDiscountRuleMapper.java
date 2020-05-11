package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.ActivityDiscountRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 活动抽奖规则Mapper
 */
@Mapper
@Repository
public interface ActivityDiscountRuleMapper extends BaseMapper<ActivityDiscountRule> {

}
