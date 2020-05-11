package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleUpdateParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityGroupRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author jiaby
 */
@Service("activityGroupRuleService")
public class ActivityGroupRuleService extends ServiceImpl<ActivityGroupRuleMapper, ActivityGroupRule> {

    @Autowired
    private ActivityRuleService ruleService;

    @Autowired
    private ActivityPlatformService activityPlatformService;

    @Autowired
    private ActivityGoodsService activityGoodsService;

    @Autowired
    private ActivityBaseService activityBaseService;

    public boolean  saveGroupRule(ActivityGroupRuleAddParam activityGroupRuleAddParam) {
        //根据baseId查询ActivityRule信息
        ActivityRule rule = this.getActivityRule(activityGroupRuleAddParam.getActivityBaseId());
        if (rule != null) {
            ACTIVITY_RULE_IS_EXIST.assertFail(rule.getActivityBaseId(),rule.getId());
        }

        // 添加基础规则
        ActivityRule activityRule = new ActivityRule();
        activityRule.setActivityBaseId(activityGroupRuleAddParam.getActivityBaseId());
        activityRule.setActivityType(ActivityTypeEnum.GROUPON.getValue());
        activityRule.setRefundType(activityGroupRuleAddParam.getRefundType());
        activityRule.setGoodsLimit(activityGroupRuleAddParam.getGoodsLimit());
        Boolean result = ruleService.saveOrUpdate(activityRule);
        if(!result){
            SAVE_RULE_ERROR.assertFail("");
        }

        ActivityGroupRuleParam activityGroupRuleParam = activityGroupRuleAddParam.getActivityGroupRuleParam();
//        activityGroupRuleParam.setGroupType(new Byte("1"));
        ActivityGroupRule bo = new ActivityGroupRule();
        BeanUtils.copyProperties(activityGroupRuleParam,bo,getNullPropertyNames(activityGroupRuleParam));
        bo.setActivityRuleId(activityRule.getId());
        this.saveOrUpdate(bo);

        // 添加投放渠道
        result = activityPlatformService.addPlatform(activityGroupRuleAddParam.getPlatformIds(), activityRule.getId());
        if(!result){
            SAVE_PLATFORM_ERROR.assertFail("");
        }

        // 添加产品
        result = activityGoodsService.addGoods(activityGroupRuleAddParam.getGoods(),activityRule.getId());
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        return result;
    }

    /**
     * 根据baseId查询ActivityRule信息
     * @param activeBaseId
     * @return
     */
    public ActivityRule getActivityRule(Long activeBaseId){
        QueryWrapper<ActivityRule> activityRuleQueryWrapper = new QueryWrapper<>();
        activityRuleQueryWrapper.eq("activity_base_id",activeBaseId);
        activityRuleQueryWrapper.eq("isdelete",1);
        activityRuleQueryWrapper.last(" limit 1");
        ActivityRule rule = ruleService.getOne(activityRuleQueryWrapper);
        return rule;
    }

    /**
     * 获取详情
     * @param activeBaseId
     * @return
     */
    public ActivityGroupRuleDetailDTO getActivityGroupRuleByBaseId(Long activeBaseId) {
        //初始化返回实体
        ActivityGroupRuleDetailDTO activityGroupRuleDetailDTO = new ActivityGroupRuleDetailDTO();
        //根据baseId查询ActivityRule信息
        ActivityRule rule = this.getActivityRule(activeBaseId);
        if(rule == null){
            ACTIVITY_RULE_IS_NOT_EXIST.assertFail(activeBaseId);
        }
        activityGroupRuleDetailDTO.setActivityType(rule.getActivityType());
        activityGroupRuleDetailDTO.setRefundType(rule.getRefundType());
        activityGroupRuleDetailDTO.setGoodsLimit(rule.getGoodsLimit());
        // 拼团规则
        QueryWrapper<ActivityGroupRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_rule_id",rule.getId());
        queryWrapper.last(" limit 1");
        ActivityGroupRule activityGroupRule = this.getOne(queryWrapper);
        if(activityGroupRule == null){
            // TODO 抛出异常
        }
        ActivityGroupRuleDTO activityGroupRuleDTO = new ActivityGroupRuleDTO();
        BeanUtils.copyProperties(activityGroupRule,activityGroupRuleDTO,getNullPropertyNames(activityGroupRule));
        activityGroupRuleDetailDTO.setActivityGroupRuleDTO(activityGroupRuleDTO);

        // 投放渠道
        List<Long> ids =activityPlatformService.getActivityPlatformList(rule.getId());
        activityGroupRuleDetailDTO.setPlatformIds(ids);

        // 产品列表
        List<ActivityGoodsDTO> goodsDTOList =activityGoodsService.getActivityGoodsList(rule.getId());
        activityGroupRuleDetailDTO.setGoods(goodsDTOList);

        return activityGroupRuleDetailDTO;
    }


    public boolean  updateGroupRule(ActivityGroupRuleUpdateParam activityGroupRuleUpdateParam) {
        //根据baseId查询ActivityRule信息
        ActivityRule rule = this.getActivityRule(activityGroupRuleUpdateParam.getActivityBaseId());
        if(rule == null){
            // 添加基础规则
            rule = new ActivityRule();
            rule.setActivityBaseId(activityGroupRuleUpdateParam.getActivityBaseId());
            rule.setActivityType(ActivityTypeEnum.GROUPON.getValue());
            rule.setRefundType(activityGroupRuleUpdateParam.getRefundType());
            rule.setGoodsLimit(activityGroupRuleUpdateParam.getGoodsLimit());

            // 更新编辑步骤为 2
            ActivityBase activityBase = activityBaseService.getById(activityGroupRuleUpdateParam.getActivityBaseId());
            activityBase.setEditStep(EditStepEnum.TWO.getValue());
            activityBaseService.updateById(activityBase);
        }
        rule.setActivityType(ActivityTypeEnum.GROUPON.getValue());
        rule.setGoodsLimit(activityGroupRuleUpdateParam.getGoodsLimit());
        rule.setRefundType(activityGroupRuleUpdateParam.getRefundType());
        // 修改规则
        Boolean result = ruleService.saveOrUpdate(rule);
        if(!result){
            SAVE_RULE_ERROR.assertFail("");
        }


        ActivityGroupRuleParam activityGroupRuleParam = activityGroupRuleUpdateParam.getActivityGroupRuleParam();
//        activityGroupRuleParam.setGroupType(GeneConstant.BYTE_1);
        ActivityGroupRule bo = new ActivityGroupRule();
        BeanUtils.copyProperties(activityGroupRuleParam,bo,getNullPropertyNames(activityGroupRuleParam));
        bo.setActivityRuleId(rule.getId());
        this.saveOrUpdate(bo);


        // 修改投放渠道
        result = activityPlatformService.updatePlatform(activityGroupRuleUpdateParam.getPlatformIds(), rule.getId());
        if(!result){
            SAVE_PLATFORM_ERROR.assertFail("");
        }

        // 修改产品
        result = activityGoodsService.updateActivityGoods(activityGroupRuleUpdateParam.getGoods(),rule.getId());
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        return result;
    }

}
