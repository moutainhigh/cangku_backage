package cn.enn.wise.ssop.service.promotions.controller.applets;



import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityImgDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.UserReviewDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityAppletsFacade;
import cn.enn.wise.ssop.service.promotions.service.ActivityBaseService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序活动接口
 * @author jiaby
 */

@RestController
@RequestMapping("/applets/activity")
@Api(value = "小程序拼团API" , tags = {"小程序拼团API"})
public class ActivityAppletsController implements ActivityAppletsFacade {

    @Autowired
    private  ActivityBaseService activityBaseService;


    @ApiOperation(value = "获取活动推广图", notes = "获取活动推广图")
    @GetMapping(value = "/activityImg")
    @Override
    public R<List<ActivityImgDTO>> getActivityImg(){

        return new R(activityBaseService.getActivityImg());
    }



}
