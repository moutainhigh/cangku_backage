package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityImgDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.UserReviewDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author jiabaiye
 */
@FeignClient("promotions-service")
public interface ActivityAppletsFacade {

    @ApiOperation(value = "获取活动推广图", notes = "获取活动推广图")
    @GetMapping(value = "/applets/activity/activityImg")
    R<List<ActivityImgDTO>> getActivityImg();

}
