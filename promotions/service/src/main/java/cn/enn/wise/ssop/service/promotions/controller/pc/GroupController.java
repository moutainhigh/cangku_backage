package cn.enn.wise.ssop.service.promotions.controller.pc;


import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleUpdateParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityDrawRuleDetailDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityGroupRuleDetailDTO;
import cn.enn.wise.ssop.service.promotions.service.ActivityGroupRuleService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author jiabaiye
 * @Description
 * @Version V1.0
 **/
@RestController
@RequestMapping("/group")
@Api(value = "拼团活动后台管理", tags = {"拼团活动后台管理"})
public class GroupController {

    @Autowired
    private ActivityGroupRuleService activityGroupRuleService;

//    @PostMapping("rule/save")
//    @ApiOperationSupport(order = 1)
//    @ApiOperation(value = "1 添加拼团规则", notes = "添加拼团规则")
//    public R<Boolean> addRule(@RequestBody ActivityGroupRuleAddParam param) {
//        return new R(activityGroupRuleService.saveGroupRule(param));
//    }

    @ApiOperation(value = "1 获取拼团活动规则详情", notes = "获取拼团活动规则详情")
    @GetMapping(value = "/rule/details")
    @ApiOperationSupport(order = 2)
    public R<ActivityGroupRuleDetailDTO> ruleDetails(Long activeBaseId) {
        return new R(activityGroupRuleService.getActivityGroupRuleByBaseId(activeBaseId));
    }

    @ApiOperation(value = "2 保存拼团活动规则详情", notes = "保存拼团活动规则详情")
    @PostMapping(value = "/rule/update")
    @ApiOperationSupport(order = 3)
    public R<Boolean> updateRule(@RequestBody ActivityGroupRuleUpdateParam param) {
        return new R(activityGroupRuleService.updateGroupRule(param));
    }

}
