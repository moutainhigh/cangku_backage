package cn.enn.wise.ssop.service.promotions.controller.applets;


import cn.enn.wise.ssop.api.promotions.dto.response.ActivityShareAppetsPageInfoDTO;
import cn.enn.wise.ssop.service.promotions.service.ActivityShareApplesService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 耿小洋
 * 小程序端促销活动相关接口
 */
@RestController
@Api(value = "小程序端促销活动相关接口", tags = {"小程序端促销活动相关接口"})
@RequestMapping("/applets/activityShareApplets")
public class ActivityShareAppletsController {

    @Autowired
    ActivityShareApplesService activityShareApplesService;



    @ApiOperation(value = "根据促销活动id获取分享页面相关信息", notes = "根据促销活动id获取分享页面相关信息")
    @GetMapping(value = "/getActivitySharePageInfoByActivityBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityBaseId", value = "促销活动id", required = true)
    })
    public R<ActivityShareAppetsPageInfoDTO> getActivitySharePageInfoByActivityBaseId(Long activityBaseId) {
        R<ActivityShareAppetsPageInfoDTO> activityShareAppetsPageInfoDTO = activityShareApplesService.getActivitySharePageInfoByActivityBaseId(activityBaseId);
        return activityShareAppetsPageInfoDTO;
    }


}
