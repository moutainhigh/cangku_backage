package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityDiscountRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDiscountRuleDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 优惠活动
 */
@FeignClient("promotions-service")
public interface DiscountActivityFacade {
    @ApiOperation(value = "1 保存优惠活动规则", notes = "保存优惠活动规则")
    @PostMapping(value = "/save")
    @ApiOperationSupport(order = 1)
    R<Long> saveActivityDiscountRole(@Validated @RequestBody ActivityDiscountRuleAddParam activityDiscountRuleAddParam);

    @ApiOperation(value = "4 获取抽奖活动规则详情", notes = "获取抽奖活动规则详情")
    @GetMapping(value = "/rule/details")
    @ApiOperationSupport(order = 4)
    R<ActivityDiscountRuleDTO> ruleDetails(Long activeBaseId);
}
