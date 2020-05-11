package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDrawRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDrawRuleCashDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDrawRuleDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDrawRuleDetailDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityGoodsDTO;
import cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityDrawRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 活动抽奖规则信息
 */
@Service("activityDrawRuleService")
public class ActivityDrawRuleService extends ServiceImpl<ActivityDrawRuleMapper, ActivityDrawRule> {

    @Resource
    ActivityRuleService ruleService;

    @Resource
    ActivityPlatformService activityPlatformService;

    @Resource
    ActivityGoodsService activityGoodsService;

    @Autowired
    ActivityBaseService activityBaseService;

    /**
     * 添加抽奖规则
     * @param param
     * @return
     */
    public Boolean saveDrawRule(ActivityDrawRuleAddParam param) {

        // 检验参数
        if(param == null
                || param.getActivityBaseId() ==null || param.getPlatformIds() == null
                || param.getDrawRule() ==null || param.getCashList() == null || param.getGoods() == null){
            SAVE_RULE_ERROR.assertIsNull("");
        }

        // 添加基础规则
        ActivityRule activityRule = new ActivityRule();
        activityRule.setActivityBaseId(param.getActivityBaseId());
        activityRule.setActivityType(ActivityTypeEnum.DRAW.getValue());
        activityRule.setRefundType(param.getDrawRule().getRefundType());
        activityRule.setGoodsLimit(param.getDrawRule().getGoodsLimit());
        Boolean result = ruleService.save(activityRule);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }

        // 添加抽奖规则
        ActivityDrawRule drawRule = new ActivityDrawRule();
        BeanUtils.copyProperties(param.getDrawRule(),drawRule,getNullPropertyNames(param.getDrawRule()));
        drawRule.setActivityRuleId(activityRule.getId());
        drawRule.setActivityRuleId(activityRule.getId());
        drawRule.setCash(JSON.toJSONString(param.getCashList()));
        drawRule.setCashCode(JSON.toJSONString(param.getDrawRule().getCashCode()));
        result = save(drawRule);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }

        // 添加投放渠道
        List<Long> ids = param.getPlatformIds();
        Long drawRuleId =drawRule.getActivityRuleId();
        result = addPlatform(ids, drawRuleId);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }

        // 添加产品
        List<ActivityGoodsAddParam> goodsAddParams = param.getGoods();
        result = addGoods(goodsAddParams,drawRuleId);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }

        // 更新编辑步骤为 2
        ActivityBase activityBase = activityBaseService.getById(param.getActivityBaseId());
        activityBase.setEditStep(EditStepEnum.TWO.getValue());
        activityBaseService.updateById(activityBase);

        return result;
    }

    /**
     * 添加渠道
     * @param ids 渠道id数组
     * @param ruleId 规则id
     * @return
     */
    public Boolean addPlatform(List<Long> ids, Long ruleId) {
        Boolean result;
        List<ActivityPlatform> platformList = new ArrayList<>();
        ids.forEach(x->{
            ActivityPlatform platform = new ActivityPlatform();
            platform.setActivityRuleId(ruleId);
            platform.setPlatformId(x);
            platform.setState(new Byte("1"));
            platformList.add(platform);
        });
        result = activityPlatformService.saveBatch(platformList);
        return result;
    }

    /**
     * 添加商品
     * @param goodsAddParams
     * @return
     */
    public Boolean addGoods(List<ActivityGoodsAddParam> goodsAddParams,Long ruleId) {
        Boolean result;
        List<ActivityGoods> activityGoods = new ArrayList<>();
        goodsAddParams.forEach(x->{
            ActivityGoods target = new ActivityGoods();
            BeanUtils.copyProperties(x,target,getNullPropertyNames(x));
            target.setProjectName(x.getProjectName());
            target.setProjectId(x.getGoodsType());
            target.setActivityRuleId(ruleId);
            activityGoods.add(target);
        });
        // 商品列表无数据时，默认全部，增加一条id -1 的数据
        if(activityGoods.size()==0){
            ActivityGoods goods = new ActivityGoods();
            goods.setId(-1L);
            goods.setActivityRuleId(ruleId);
            activityGoods.add(goods);
        }
        result =activityGoodsService.saveBatch(activityGoods);
        return result;
    }

    /**
     * 获取详情
     * @param activeBaseId
     * @return
     */
    public R<ActivityDrawRuleDetailDTO> getActivityDrawRuleByBaseId(Long activeBaseId) {

        ActivityDrawRuleDetailDTO detailDTO = new ActivityDrawRuleDetailDTO();
        QueryWrapper<ActivityRule> activityRuleQueryWrapper = new QueryWrapper<>();
        activityRuleQueryWrapper.eq("activity_base_id",activeBaseId);
        activityRuleQueryWrapper.last(" limit 1");
        ActivityRule rule = ruleService.getOne(activityRuleQueryWrapper);
        if(rule == null){
            // TODO 抛出异常
        }

        QueryWrapper<ActivityDrawRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_rule_id",rule.getId());
        queryWrapper.last(" limit 1");
        ActivityDrawRule drawRule = getOne(queryWrapper);
        if(drawRule == null){
            // TODO 抛出异常
        }
        ActivityDrawRuleDTO drawRuleDTO = new ActivityDrawRuleDTO();
        BeanUtils.copyProperties(drawRule,drawRuleDTO,getNullPropertyNames(drawRule));
        drawRuleDTO.setCashCode(JSON.parseArray(drawRule.getCashCode()));
        drawRuleDTO.setGoodsLimit(rule.getGoodsLimit());
        drawRuleDTO.setRefundType(rule.getRefundType());
        detailDTO.setActivityBaseId(rule.getActivityBaseId());

        // 抽奖规则
        detailDTO.setDrawRule(drawRuleDTO);

        // 奖品类型
        List<ActivityDrawRuleCashDTO> cashList = JSON.parseArray(detailDTO.getDrawRule().getCash(), ActivityDrawRuleCashDTO.class);
        detailDTO.setCashList(cashList);

        // 投放渠道
        List<Long> ids = new ArrayList<>();
        QueryWrapper<ActivityPlatform> platformQueryWrapper = new QueryWrapper<>();
        platformQueryWrapper.eq("activity_rule_id",rule.getId());
        List<ActivityPlatform> platforms = activityPlatformService.list(platformQueryWrapper);
        platforms.forEach(x->{
            ids.add(x.getPlatformId());
        });
        detailDTO.setPlatformIds(ids);

        // 产品列表
        QueryWrapper<ActivityGoods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("activity_rule_id",rule.getId());
        List<ActivityGoods> goods = activityGoodsService.list(goodsQueryWrapper);
        List<ActivityGoodsDTO> goodsDTOList = new ArrayList<>();
        goods.forEach(x->{
            ActivityGoodsDTO goodsDTO = new ActivityGoodsDTO();
            BeanUtils.copyProperties(x,goodsDTO,getNullPropertyNames(x));
            goodsDTO.setProjectId(x.getProjectId());
            goodsDTO.setProjectName(x.getProjectName());
            goodsDTO.setGoodsType(x.getProjectId());
            goodsDTOList.add(goodsDTO);
        });
        detailDTO.setGoods(goodsDTOList);
        return new R(detailDTO);
    }

    /**
     * 编辑抽奖规则
     * @param param
     * @return
     */
    public Boolean updateDrawRule(ActivityDrawRuleAddParam param) {
        // 检验参数
        if(param == null
                || param.getActivityBaseId() ==null || param.getPlatformIds() == null
                || param.getDrawRule() ==null || param.getCashList() == null || param.getGoods() == null){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        // 获取基础规则
        QueryWrapper<ActivityRule> ruleQueryWrapper = new QueryWrapper<>();
        ruleQueryWrapper.eq("activity_base_id", param.getActivityBaseId());
        ruleQueryWrapper.last(" limit 1");
        ActivityRule rule = ruleService.getOne(ruleQueryWrapper);
        if (rule == null) {
            ACTIVITY_RULE_IS_NOT_EXIST.assertIsNull("");
        }
        // 更新基础规则
        ActivityRule activityRule =rule;
        activityRule.setActivityType(ActivityTypeEnum.DRAW.getValue());
        activityRule.setRefundType(param.getDrawRule().getRefundType());
        activityRule.setGoodsLimit(param.getDrawRule().getGoodsLimit());
        Boolean result = ruleService.saveOrUpdate(activityRule);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        // 添加抽奖规则
        QueryWrapper<ActivityDrawRule> drawRuleQueryWrapper = new QueryWrapper<>();
        drawRuleQueryWrapper.eq("activity_rule_id",rule.getId());
        drawRuleQueryWrapper.last(" limit 1 ");
        ActivityDrawRule drawRule = getOne(drawRuleQueryWrapper);
        Long id = drawRule.getId();
        Long activityRuleId =  drawRule.getActivityRuleId();
        BeanUtils.copyProperties(param.getDrawRule(),drawRule,getNullPropertyNames(param.getDrawRule()));
        drawRule.setId(id);
        drawRule.setActivityRuleId(activityRule.getId());
        drawRule.setCash(JSON.toJSONString(param.getCashList()));
        drawRule.setCashCode(JSON.toJSONString(param.getDrawRule().getCashCode()));
        result = saveOrUpdate(drawRule);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        // 添加投放渠道
        List<Long> ids = param.getPlatformIds();
        Long drawRuleId =drawRule.getActivityRuleId();
        // 删除投放渠道
        removePlatform(drawRuleId);
        result = addPlatform(ids, drawRuleId);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        // 添加产品
        List<ActivityGoodsAddParam> goodsAddParams = param.getGoods();
        // 删除商品
        removeGoods(drawRuleId);
        result = addGoods(goodsAddParams,drawRuleId);
        if(!result){
            SAVE_RULE_ERROR.assertIsNull("");
        }
        return result;
    }

    /**
     * 删除已选商品
     * @param drawRuleId
     */
    public void removeGoods(Long drawRuleId) {
        QueryWrapper<ActivityGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_rule_id",drawRuleId);
        activityGoodsService.remove(queryWrapper);
    }

    /**
     * 删除投放渠道
     * @param drawRuleId
     */
    public void removePlatform(Long drawRuleId) {
        QueryWrapper<ActivityPlatform> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("activity_rule_id",drawRuleId);
        activityPlatformService.remove(queryWrapper);
    }


}
