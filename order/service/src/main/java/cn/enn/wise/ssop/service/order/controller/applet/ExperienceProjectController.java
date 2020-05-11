package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.api.order.dto.request.ExperienceProjectOrderParam;
import cn.enn.wise.ssop.api.order.dto.request.PackageOrderParam;
import cn.enn.wise.ssop.service.order.service.impl.ExperienceProjectServiceImpl;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 套餐下单接口
 * @author baijie
 * @date 2020-05-03
 */
@RestController
@RequestMapping("/applets/experience")
@Api(value = "小程序体验项目API", tags = {"小程序体验项目API"})
@Slf4j
public class ExperienceProjectController {

    @Autowired
    private ExperienceProjectServiceImpl experienceProjectService;

    @PostMapping("/save")
    @ApiOperation(value = "统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveExperienceProjectOrder(@RequestBody @Valid ExperienceProjectOrderParam experienceProjectOrderParam){
        Long orderId= experienceProjectService.saveOrder(experienceProjectOrderParam);
        return R.success(orderId);
    }
}
