package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDrawRuleParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityRule;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 安辉
 * 活动规则基础信息
 */
@Service("activityRuleService")
public class ActivityRuleService extends ServiceImpl<ActivityRuleMapper, ActivityRule> {



}
