package cn.enn.wise.ssop.service.promotions.controller.pc;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBaseAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBenefitAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDrawRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DrawRecordListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;

import cn.enn.wise.ssop.service.promotions.model.ActivityRule;

import cn.enn.wise.ssop.service.promotions.model.Platform;
import cn.enn.wise.ssop.service.promotions.service.*;

import cn.enn.wise.uncs.base.constant.BusinessEnumInit;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author 安辉
 * 抽奖活动
 */
@RestController
@Api(value = "抽奖信息管理接口", tags = {"抽奖信息管理接口"})
@RequestMapping("/draw")
@ApiSort(1)
public class DrawController {
    @Autowired
    ActivityBaseService baseService;

    @Autowired
    ActivityDrawRuleService ruleService;

    @Autowired
    ActivityRuleService activityRuleService;

    @Autowired
    PlatformService platformService;

    @Autowired
    ActivityBenefitService benefitService;

    @Autowired
    DrawRecordService drawRecordService;

    @ApiOperation(value = "1 保存抽奖活动基础信息", notes = "保存基础信息")
    @PostMapping(value = "/save")
    @ApiOperationSupport(order = 1)
    public R<ActivityBaseDTO> save(@RequestBody ActivityBaseAddParam param) {
        return baseService.saveActivityBase(param);
    }

    @ApiOperation(value = "2 抽奖活动基础信息详情", notes = "保存基础信息")
    @GetMapping(value = "/detail")
    @ApiOperationSupport(order = 2)
    public R<ActivityBaseDTO> detail(Long id) {
        return  baseService.getActivityBaseById(id);
    }

    @ApiOperation(value = "3 保存抽奖活动规则", notes = "保存抽奖活动规则")
    @PostMapping(value = "/rule/save")
    @ApiOperationSupport(order = 3)
    public R saveRule(@Validated @RequestBody ActivityDrawRuleAddParam param) {
        QueryWrapper<ActivityRule> ruleQueryWrapper = new QueryWrapper<>();
        ruleQueryWrapper.eq("activity_base_id", param.getActivityBaseId());
        ruleQueryWrapper.last(" limit 1");
        ActivityRule rule = activityRuleService.getOne(ruleQueryWrapper);
        if (rule == null) {
            return new R(ruleService.saveDrawRule(param));
        }else{
            return new R(ruleService.updateDrawRule(param));
        }
    }

    @ApiOperation(value = "4 获取抽奖活动规则详情", notes = "获取抽奖活动规则详情")
    @GetMapping(value = "/rule/details")
    @ApiOperationSupport(order = 4)
    public R<ActivityDrawRuleDetailDTO> ruleDetails(Long activeBaseId) {
        return ruleService.getActivityDrawRuleByBaseId(activeBaseId);
    }

    @ApiOperation("4 枚举列表")
    @GetMapping("/enumList")
    @ApiOperationSupport(order = 4)
    public R<Map<String, List<SelectData>>> getExampleContentList() {
        return new R<>(BusinessEnumInit.enumMap);
    }

    @ApiOperation("5 投放渠道")
    @GetMapping("/channel/list")
    @ApiOperationSupport(order = 5)
    public R<PlatformDTO> channelList() {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Platform> list = platformService.list();
        List<PlatformDTO> result= new ArrayList<>();
        list.forEach(x->{
            PlatformDTO target = new PlatformDTO();
            BeanUtils.copyProperties(x,target,getNullPropertyNames(x));
            result.add(target);
        });
        return new R(result);
    }

    @ApiOperation("6 保存效益预估")
    @PostMapping("/benefit/save")
    @ApiOperationSupport(order = 6)
    public R<Boolean> benefitSave (@Validated @RequestBody List<ActivityBenefitAddParam> param) {
        return benefitService.saveBenefitByBatch(param);
    };

    @ApiOperation("7 效益预估列表")
    @PostMapping("/benefit/list")
    @ApiOperationSupport(order = 7)
    public R<List<ActivityBenefitDTO>> benefitList (Long activityBaseId) {
        return benefitService.getBenefitByBatch(activityBaseId);
    };

    @ApiOperation("8 效益预估列表")
    @GetMapping("/record/list")
    @ApiOperationSupport(order = 8)
    public R<QueryData<DrawRecordDTO>> drawRecord (@Validated DrawRecordListParam params) {
        return drawRecordService.listByPage(params);
    };
}
