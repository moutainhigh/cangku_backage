package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.response.LocationDTO;
import cn.enn.wise.ssop.api.cms.dto.response.POIListDTO;
import cn.enn.wise.ssop.api.cms.facade.ScenicAreaFacade;
import cn.enn.wise.ssop.service.cms.service.ScenicAreaService;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
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
 * @author shiz
 * 景区管理
 */
@RestController
@Api(value = "景区管理", tags = {"景区管理"})
@RequestMapping("/scenicArea")
public class ScenicAreaController implements ScenicAreaFacade {

    @Autowired
    ScenicAreaService scenicAreaService;

    @ApiOperation("辖下景区Select列表")
    @GetMapping("/getScenicAreaSelectList")
    @Override
    public R<List<SelectData>> getScenicAreaSelectList() {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        List<SelectData> list = scenicAreaService.getScenicAreaSelectList(companyId);
        return R.success(list);
    }

    @ApiOperation("辖下景区POI Select列表，根据类型查询")
    @GetMapping("/getPOISelectList")
    @Override
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likePOIName",value = "poi名称(模糊搜索)",dataTypeClass = String.class,required = false),
            @ApiImplicitParam(name = "POIType",value = "-1 全部 POI类型 1、core-景点，2、restroom-洗手间，3、restaurant-餐馆，4、performance-娱乐演出，5、hotels-酒店，6、station-观光车站， 7、shop-商店，8、tourist-游客服务中心，9、specialty-特产， 10、parking-停车场，11、entrance-入口，12、road-道路，13、exit-出口，14、cross-交叉点",dataTypeClass = Integer.class,required = true)
    })
    public R<List<POIListDTO>> getPOISelectList(@RequestParam(required = false) String likePOIName, Integer POIType) {
        List<POIListDTO> list = scenicAreaService.getPOISelectList(likePOIName,POIType);
        return R.success(list);
    }

    @ApiOperation("批量获取POI经纬度")
    @GetMapping("/getPOILocationBatch")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "POIIdList",value = "POI主键列表 Array类型",dataTypeClass = Long.class,required = true),
            @ApiImplicitParam(name = "lonLat",value = "经纬度， 逗号分割",dataTypeClass = String.class,required = false)
    })
    @Override
    public R<List<LocationDTO>> getPOILocationBatch(@RequestParam("POIIdList") Long[] POIIdList,
                                                    @RequestParam(value = "lonLat",required = false) String lonLat) {
        List<LocationDTO> list = scenicAreaService.getPOILocationBatch(POIIdList,lonLat);
        return R.success(list);
    }

    @ApiOperation("辖下景区Select列表")
    @GetMapping("/getAllScenicAreaList")
    @Override
    public R<List<SelectData>> getAllScenicAreaList() {
        List<SelectData> list = scenicAreaService.getAllScenicAreaList();
        return R.success(list);
    }

}
