package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.CompanyRouteSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.TripListAppletDTO;
import cn.enn.wise.ssop.service.cms.service.TripService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date:2020/4/2
 * @author:shiz
 */
@RestController
@Api(value = "行程管理接口", tags = {"行程管理接口"})
@RequestMapping("/trip")
public class TripController {

    @Autowired
    TripService tripService;


    @ApiOperation(value = "保存行程(关联多景区)", notes = "保存行程(关联多景区)")
    @PostMapping(value = "/saveCompanyRoute")
    public R<Boolean> saveCompanyRoute(@Param("companyRouteSaveParam") CompanyRouteSaveParam param) {
        Boolean isSaveOk = tripService.saveCompanyRoute(param);
        return R.success(isSaveOk);
    }

    @ApiOperation(value = "根据租户行程Id获取行程code", notes = "根据租户行程Id获取行程code")
    @GetMapping(value = "/getCompanyRouteCodeList")
    public R<List<String>> getCompanyRouteCodeList(@RequestParam("compnayRouteId") Long compnayRouteId) {
        List<String> routeIds = tripService.getCompanyRouteInfo(compnayRouteId);
        return R.success(routeIds);
    }






}
