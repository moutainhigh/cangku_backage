package cn.enn.wise.ssop.service.promotions.controller.applets;



import cn.enn.wise.ssop.api.promotions.dto.response.ActivityRuleDetailsDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityTypeDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityDiscountAppletsFacade;
import cn.enn.wise.ssop.service.promotions.service.ActivityGoodsService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序优惠活动满减
 */

@RestController
@RequestMapping("/applets/activityDiscount")
@Api(value = "小程序优惠API" , tags = {"小程序优惠API"})
public class ActivityDiscountAppletsController implements ActivityDiscountAppletsFacade {

    @Autowired
    private ActivityGoodsService activityGoodsService;


    @ApiOperation(value = "根据产品id查询是优惠或拼团", notes = "根据产品id查询是优惠或拼团")
    @GetMapping(value = "/getActivityTypeListByGoods")
    @Override
    public R<List<ActivityTypeDTO>> getActivityTypeListByGoods(@RequestParam(value = "goods") List<Long> goods){
        return R.success(activityGoodsService.getActivityTypeListByGoods(goods));
    }

    @ApiOperation(value = "根据产品id查询优惠或拼团规则详情", notes = "根据产品id查询优惠或拼团规则详情")
    @GetMapping(value = "/getActivityRuleDetailsByGoods/{id}")
    @Override
    public R<List<ActivityRuleDetailsDTO>> getActivityRuleDetailsByGoods(@PathVariable Long id){
        return R.success(activityGoodsService.getActivityRuleDetailsByGoods(id));
    }

}
