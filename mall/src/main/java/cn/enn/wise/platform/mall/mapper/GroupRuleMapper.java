package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 活动接口
 *
 * @author anhui
 * @since 2019/9/12
 */
public interface GroupRuleMapper extends BaseMapper<GroupRuleBo> {

    GroupRuleBo getGroupRuleByGroupPromotionId(@Param("groupPromotionId") Long groupPromotionId);

}
