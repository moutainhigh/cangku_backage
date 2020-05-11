package cn.enn.wise.ssop.service.promotions.controller;

import cn.enn.wise.ssop.api.promotions.dto.request.AppSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.AppDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.SimpleAppDTO;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.ssop.service.promotions.service.PartnerAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shiz
 * 应用程序接口
 */
@RestController
@Api(value = "应用程序", tags = {"应用程序"})
@RequestMapping("/")
public class PartnerAppController {

    @Autowired
    PartnerAppService partnerAppService;

    @ApiOperation(value = "分页查询应用程序", notes = "运营端传入查询条件搜索应用程序",position = 1)
    @GetMapping(value = "/partner/{partnerId}/apps")
    public R<QueryData<SimpleAppDTO>> list(@PathVariable("partnerId") Long partnerId, @Validated QueryParam queryParam) {
        return new R<>(partnerAppService.getApps(partnerId,queryParam));
    }

    @ApiOperation(value = "应用程序详情", notes = "获取应用程序的所有信息",position = 2)
    @GetMapping(value = "/app/detail/{id}")
    public R<AppDTO> detail(@PathVariable Long id) {
        return new R<>(partnerAppService.getAppDetail(id));
    }

    @ApiOperation(value = "保存应用程序", notes = "新增或修改应用程序",position = 3)
    @PostMapping(value = "/app/save")
    public R<Boolean> save(@Validated @RequestBody AppSaveParam appSaveParam) {
        return new R<>(partnerAppService.saveApp(appSaveParam));
    }

    @ApiOperation(value = "删除应用程序", notes = "用过id删除应用程序",position = 4)
    @DeleteMapping(value = "/app/delete/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return new R<>(partnerAppService.delApp(id));
    }

    @ApiOperation(value = "禁用应用程序", notes = "",position = 5)
    @PutMapping(value = "/app/lock/{id}")
    public R<Boolean> lock(@PathVariable Long id) {
        return new R<>(partnerAppService.editState(id,2));
    }

    @ApiOperation(value = "启用应用程序", notes = "",position = 6)
    @PutMapping(value = "/app/unlock/{id}")
    public R<Boolean> unlock(@PathVariable Long id) {
        return new R<>(partnerAppService.editState(id,1));
    }












}
