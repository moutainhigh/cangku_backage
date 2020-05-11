package cn.enn.wise.ssop.service.promotions.controller.pc;


import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBaseListParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityShareAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityBaseDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityShareDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.UserReviewDTO;
import cn.enn.wise.ssop.service.promotions.service.ActivityBaseService;
import cn.enn.wise.ssop.service.promotions.service.ActivityShareService;
import cn.enn.wise.ssop.service.promotions.service.UserReviewService;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 安辉
 * 抽奖活动
 */
@RestController
@Api(value = "活动管理接口", tags = {"活动管理接口"})
@RequestMapping("/activity")
@ApiSort(1)
public class ActivityController {
    @Autowired
    ActivityBaseService baseService;

    @Autowired
    ActivityShareService shareService;

    public static List<Map<String,Object>>  templateList  =new ArrayList<>();

    @Autowired
    private UserReviewService userReviewService;

    static{
        Map<String,Object> map = new HashMap<>();
        map.put("value",1);
        map.put("name","效益目标模板");
        map.put("body","效益目标：指定日期，完成业绩提升20% \n活动类型：抽奖活动\n主要客群：忠诚");
        templateList.add(map);

        map = new HashMap<>();
        map.put("value",2);
        map.put("name","拉新目标模板");
        map.put("body","拉新目标：指定日期，完成业绩提升20% \n活动类型：抽奖活动\n主要客群：新人");
        templateList.add(map);

        map = new HashMap<>();
        map.put("value",3);
        map.put("name","市场目标模板");
        map.put("body","拉新目标：指定日期，完成业绩提升20% \n活动类型：抽奖活动\n主要客群：老用户");
        templateList.add(map);
    }

    @ApiOperation(value = "0 活动价值模板", notes = "活动价值模板")
    @GetMapping(value = "/template/list")
    @ApiOperationSupport(order = 1)
    public R<List<Map<String,Object>>> templateList() {
        return new R<>(templateList);
    }

    @ApiOperation(value = "1 活动列表", notes = "活动列表")
    @GetMapping(value = "/list")
    @ApiOperationSupport(order = 1)
    public R<QueryData<List<ActivityBaseDTO>>> list(@Validated ActivityBaseListParam param) {
        return baseService.listByPage(param);
    }

    @ApiOperation(value = "2 设置活动失效", notes = "设置活动失效")
    @PutMapping(value = "/enable")
    @ApiOperationSupport(order = 2)
    public R<Boolean> setActivityEnable(Long id) {
        return baseService.setActivityEnable(id);
    }

    @ApiOperation(value = "3 分享保存和编辑", notes = "分享保存和编辑")
    @PostMapping(value = "/share/save")
    @ApiOperationSupport(order = 3)
    public R<Long> saveActivityShare(@Validated @RequestBody ActivityShareAddParam param) {
        return shareService.setActivityShare(param);
    }

    @ApiOperation(value = "4 获取分享详情", notes = "获取分享详情")
    @GetMapping(value = "/share/detail")
    @ApiOperationSupport(order = 4)
    public R<ActivityShareDTO> activityDetail(Long baseActivityId) {
        return shareService.getShareByBaseActivityId(baseActivityId);
    }

    @PostMapping(value = "/userreview/save")
    @ApiOperation(value = "用户回访信息添加", notes = "用户回访信息添加")
    public R<Boolean> saveUserReview(@RequestBody UserReviewParam userReviewParam) {
        return new R(userReviewService.saveReview(userReviewParam));
    }

    @GetMapping("/userreview/list")
    @ApiOperation(value = "回访列表展示", notes = "回访列表展示")
    public R<List<UserReviewDTO>> listUserReview(@Validated UserReviewParam userReviewParam) {
        return new R(userReviewService.listUserReview(userReviewParam));
    }

}
