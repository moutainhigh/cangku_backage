package cn.enn.wise.ssop.service.promotions.controller.util;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityPriceUtilParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityPriceUtilDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityPriceUtilFacade;
import cn.enn.wise.ssop.service.promotions.service.ActivityPriceUtilService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangshuaiquan
 * @Description
 * @Version V1.0
 **/
@Slf4j
@Api(value = "计算优惠券价格" , tags = {"计算优惠券价格"})
@RestController
@RequestMapping("/activityutil")
public class ActivityPriceUtilController implements ActivityPriceUtilFacade {
    @Autowired
    private ActivityPriceUtilService activityPriceUtilService;

    @ApiOperation("计算优惠券价格")
    @PostMapping("/getprice")
    public R<ActivityPriceUtilDTO> getCouponPrice(@RequestBody ActivityPriceUtilParam activityPriceUtilParam) {
        log.info("====================================={}",activityPriceUtilParam);

        log.info("uoufffffffffffffffffffffffffff,{}",activityPriceUtilService);
        return  new R(activityPriceUtilService.getCouponPrice(activityPriceUtilParam));
    }
}
