package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDiscountRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.MinusRule;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDiscountRuleDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityGoodsDTO;
import cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityDiscountRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 活动抽奖规则信息
 */
@Service("activitydiscountRuleService")
public class ActivityDiscountRuleService extends ServiceImpl<ActivityDiscountRuleMapper, ActivityDiscountRule> {

    @Resource
    ActivityRuleService ruleService;
    @Resource
    ActivityDrawRuleService activityDrawRuleService;
    @Resource
    ActivityPlatformService activityPlatformService;
    @Resource
    ActivityGoodsService activityGoodsService;
    @Autowired
    private ActivityBaseService activityBaseService;


    /**
     * 报错优惠活动规则
     * @param param
     * @return
     */
    @Transactional
    public Long saveActivityDiscountRole(ActivityDiscountRuleAddParam param) {
        //1 保存优惠规则
        //优惠算法 1 早定优惠 2 特价直减 3 满减优惠
        Byte algorithm = param.getAlgorithm();

        //满减优惠规则
        MinusRule minusRule = param.getMinusRule();
        //早定优惠规则
        ActivityDiscountRuleAddParam.ReservationRole reservationRole = param.getReservationRole();
        //特价直减规则
        ActivityDiscountRuleAddParam.SaleRole saleRole = param.getSaleRole();

        if(RESERVATIONROLE.getValue().equals(algorithm) &&reservationRole==null||
                SALEROLE.getValue().equals(algorithm) &&saleRole==null||
                MINUSRULE.getValue().equals(algorithm) &&minusRule==null){
            PARAM_ERROR.assertFail();
        }

        QueryWrapper<ActivityRule> ruleQueryWrapper = new QueryWrapper<>();
        ruleQueryWrapper.eq("activity_base_id", param.getActivityBaseId());
        ruleQueryWrapper.last(" limit 1");
        ActivityRule activityRule = ruleService.getOne(ruleQueryWrapper);
        if(activityRule!=null){
            Long activityRuleId = activityRule.getId();

            ActivityDiscountRule one = this.getOne(new LambdaQueryWrapper<ActivityDiscountRule>()
                    .eq(ActivityDiscountRule::getActivityRuleId, activityRule));
            if(one!=null){
                //删除商品/投放渠道
                activityDrawRuleService.removeGoods(activityRuleId);

                activityDrawRuleService.removePlatform(activityRuleId);

                //删除优惠规则
                this.removeById(one.getId());
            }
        }else{
            // 添加基础规则
            activityRule = new ActivityRule();
            activityRule.setActivityBaseId(param.getActivityBaseId());
            activityRule.setActivityType(ActivityTypeEnum.DEDUCTION.getValue());
            activityRule.setRefundType(param.getRefundType());
            activityRule.setGoodsLimit(param.getGoodsLimit());

            // 更新编辑步骤为 2
            ActivityBase activityBase = activityBaseService.getById(param.getActivityBaseId());
            activityBase.setEditStep(EditStepEnum.TWO.getValue());
            activityBaseService.updateById(activityBase);
        }
        Boolean result = ruleService.saveOrUpdate(activityRule);

        SAVE_RULE_ERROR.assertIsTrue(result);
        Long activityRuleId = activityRule.getId();

        // 添加优惠规则
        ActivityDiscountRule activityDiscountRule = new ActivityDiscountRule();

        String roleInfoJson=null;
        if(RESERVATIONROLE.getValue().equals(algorithm)){
            roleInfoJson = JSON.toJSONString(reservationRole);
        }else if(SALEROLE.getValue().equals(algorithm)){
            roleInfoJson = JSON.toJSONString(saleRole);
        }else if(MINUSRULE.getValue().equals(algorithm)){
            roleInfoJson = JSON.toJSONString(minusRule);
        }

        activityDiscountRule.setDiscountRule(roleInfoJson);
        activityDiscountRule.setRemark(param.getRemark());
        activityDiscountRule.setActivityRuleId(activityRule.getId());
        activityDiscountRule.setAlgorithms(algorithm);
        boolean saveOk = this.saveOrUpdate(activityDiscountRule);

        SAVE_RULE_ERROR.assertIsTrue(saveOk);
        Long ruleId = activityDiscountRule.getId();
        // 增加渠道
        Boolean addPlatformOK = activityDrawRuleService.addPlatform(param.getPlatformIds(),activityRuleId) ;
        SAVE_RULE_ERROR.assertIsTrue(addPlatformOK);
        //增加商品
        Boolean addGoodsOk = activityDrawRuleService.addGoods(param.getGoods(), activityRuleId);
        SAVE_RULE_ERROR.assertIsTrue(addGoodsOk);

        return param.getActivityBaseId();
    }



    /**
     * 获取详情
     * @param activeBaseId
     * @return
     */
    public R<ActivityDiscountRuleDTO> getActivityDiscountRuleDTOByBaseId(Long activeBaseId) {

        ActivityDiscountRuleDTO detailDTO = new ActivityDiscountRuleDTO();

        QueryWrapper<ActivityRule> activityRuleQueryWrapper = new QueryWrapper<>();
        activityRuleQueryWrapper.eq("activity_base_id",activeBaseId);
        activityRuleQueryWrapper.last(" limit 1");
        ActivityRule rule = ruleService.getOne(activityRuleQueryWrapper);
        if(rule == null){
            SAVE_RULE_ERROR.assertFail();
        }
        detailDTO.setActivityBaseId(activeBaseId);

        QueryWrapper<ActivityDiscountRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_rule_id",rule.getId());
        queryWrapper.last(" limit 1");
        ActivityDiscountRule discountRule = getOne(queryWrapper);
        if(discountRule == null){
            SAVE_RULE_ERROR.assertFail();
        }

        String discounRule = discountRule.getDiscountRule();
        Byte algorithm = discountRule.getAlgorithms();
        if(RESERVATIONROLE.getValue().equals(algorithm)) {
            ActivityDiscountRuleDTO.ReservationRole reservationRole = JSON.parseObject(discounRule, ActivityDiscountRuleDTO.ReservationRole.class);
            detailDTO.setReservationRole(reservationRole);
        }
        else if(SALEROLE.getValue().equals(algorithm)){
            ActivityDiscountRuleDTO.SaleRole saleRole = JSON.parseObject(discounRule, ActivityDiscountRuleDTO.SaleRole.class);
            detailDTO.setSaleRole(saleRole);
        }else if(MINUSRULE.getValue().equals(algorithm)){
            MinusRule minusRule = JSON.parseObject(discounRule, MinusRule.class);
            detailDTO.setMinusRule(minusRule);
        }

        detailDTO.setRemark(discountRule.getRemark());
        detailDTO.setAlgorithm(algorithm);
        detailDTO.setRefundType(discountRule.getAlgorithms());
        detailDTO.setGoodsLimit(rule.getGoodsLimit());



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
            goodsDTOList.add(goodsDTO);
        });
        detailDTO.setGoods(goodsDTOList);
        return new R(detailDTO);
    }
}
