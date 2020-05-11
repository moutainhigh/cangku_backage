package cn.enn.wise.ssop.api.promotions.facade;


import cn.enn.wise.ssop.api.promotions.dto.response.ActivityRuleDetailsDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityTypeDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author jiabaiye
 */
@FeignClient("promotions-service")
public interface ActivityDiscountAppletsFacade {

    @ApiOperation(value = "根据产品id查询是优惠或拼团", notes = "根据产品id查询是优惠或拼团")
    @GetMapping(value = "/applets/activityDiscount/getActivityTypeListByGoods")
    R<List<ActivityTypeDTO>> getActivityTypeListByGoods(@RequestParam(value = "goods") List<Long> goods);

    @ApiOperation(value = "根据产品id查询优惠或拼团规则详情", notes = "根据产品id查询优惠或拼团规则详情")
    @GetMapping(value = "/applets/activityDiscount/getActivityRuleDetailsByGoods/{id}")
    R<List<ActivityRuleDetailsDTO>> getActivityRuleDetailsByGoods(@PathVariable("id") Long id);

}
