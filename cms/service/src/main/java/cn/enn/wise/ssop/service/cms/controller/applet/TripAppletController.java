package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.response.TripListAppletDTO;
import cn.enn.wise.ssop.service.cms.model.Enshrine;
import cn.enn.wise.ssop.service.cms.service.EnshrineService;
import cn.enn.wise.ssop.service.cms.service.TripService;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.common.ByteEnum;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @date:2020/4/2
 * @author:hsq
 */
@RestController
@Api(value = "小程序行程接口", tags = {"小程序行程接口"})
@RequestMapping("/applet/cms")
public class TripAppletController {

    @Autowired
    TripService tripService;
    @Autowired
    EnshrineService enshrineService;


    @ApiOperation(value = "行程列表", notes = "行程列表")
    @GetMapping(value = "/trip/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lonLat",value = "当前经纬度  逗号分割",required = false)
    })
    public R<List<TripListAppletDTO>> getAllTripList(@RequestParam(required = false) String lonLat) {
        List<TripListAppletDTO> list = tripService.getAllTripList(lonLat);
        return R.success(list);
    }

    @ApiOperation(value = "行程是否收藏 null为无收藏", notes = "行程是否收藏 null为无收藏")
    @GetMapping(value = "/trip/isEnshrine")
    public R<Long> isEnshrine() {

        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        List<Enshrine> enshrineList = enshrineService.getEnshrineList(memberId, ByteEnum.byte2);
        if(enshrineList.size()==0) return R.success(null);

        return R.success(enshrineList.get(0).getArticleId());
    }

    @ApiOperation(value = "根据租户行程Id获取行程code", notes = "根据租户行程Id获取行程code")
    @GetMapping(value = "/getCompanyRouteCodeList")
    public R<List<String>> getCompanyRouteCodeList(@RequestParam("compnayRouteId") Long compnayRouteId) {
        List<String> routeIds = tripService.getCompanyRouteInfo(compnayRouteId);
        return R.success(routeIds);
    }





}
