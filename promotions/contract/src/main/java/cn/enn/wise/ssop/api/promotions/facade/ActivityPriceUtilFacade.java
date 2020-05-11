package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityPriceUtilParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityPriceUtilDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author yangshuaiquan
 * @date 2020/4/7 5:18 下午
 */
@FeignClient("promotions-service")
public interface ActivityPriceUtilFacade{

    @ApiOperation(value = "计算优惠后价格", notes = "计算优惠后价格")
    @PostMapping(value = "/activityutil/getprice")
    R<ActivityPriceUtilDTO> getCouponPrice(ActivityPriceUtilParam activityPriceUtilParam);

}
