package cn.enn.wise.ssop.service.promotions.controller.pc;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDiscountRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDiscountRuleDTO;
import cn.enn.wise.ssop.api.promotions.facade.DiscountActivityFacade;
import cn.enn.wise.ssop.service.promotions.service.ActivityDiscountRuleService;
import cn.enn.wise.ssop.service.promotions.service.ActivityRuleService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 石斋
 * 优惠活动
 */
@RestController
@Api(value = "优惠活动管理接口", tags = {"优惠活动管理接口"})
@RequestMapping("/activity/discount")
@ApiSort(1)
public class DiscountActivityController implements DiscountActivityFacade {

    @Autowired
    ActivityDiscountRuleService activityDiscountRuleService;

    @ApiOperation(value = "1 保存优惠活动规则", notes = "保存优惠活动规则")
    @PostMapping(value = "/save")
    @ApiOperationSupport(order = 1)
    @Override
    public R<Long> saveActivityDiscountRole(@Validated @RequestBody ActivityDiscountRuleAddParam activityDiscountRuleAddParam) {
        Long actId = activityDiscountRuleService.saveActivityDiscountRole(activityDiscountRuleAddParam);
        return R.success(actId);
    }

    @ApiOperation(value = "4 获取抽奖活动规则详情", notes = "获取抽奖活动规则详情")
    @GetMapping(value = "/rule/details")
    @ApiOperationSupport(order = 4)
    @Override
    public R<ActivityDiscountRuleDTO> ruleDetails(Long activeBaseId) {
        return activityDiscountRuleService.getActivityDiscountRuleDTOByBaseId(activeBaseId);
    }


}
