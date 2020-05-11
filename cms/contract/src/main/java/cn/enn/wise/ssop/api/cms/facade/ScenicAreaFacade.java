package cn.enn.wise.ssop.api.cms.facade;

import cn.enn.wise.ssop.api.cms.dto.response.LocationDTO;
import cn.enn.wise.ssop.api.cms.dto.response.POIListDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 景区接口
 * @author lishuiquan
 * @since 2019-11-18
 */
@Api(value = "景区接口", tags = {"景区接口"})
@FeignClient(value = "cms-service")
public interface ScenicAreaFacade {


    @ApiOperation("所属景区Select列表")
    @GetMapping("/scenicArea/getScenicAreaSelectList")
    R<List<SelectData>> getScenicAreaSelectList();

    @ApiOperation("所属景区全部POI Select列表")
    @GetMapping("/scenicArea/getPOISelectList")
    R<List<POIListDTO>> getPOISelectList(@RequestParam("likePOIName") String likePOIName,@RequestParam("POIType") Integer POIType);

    @ApiOperation("批量获取POI经纬度")
    @GetMapping("/scenicArea/getPOILocationBatch")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "POIIdList",value = "POI主键列表",required = true,readOnly = true)
    })
    R<List<LocationDTO>> getPOILocationBatch(@RequestParam("POIIdList") Long[] POIIdList,@RequestParam(value = "lonLat",required = false) String lonLat);

    @ApiOperation("辖下景区Select列表")
    @GetMapping("/getAllScenicAreaList")
    R<List<SelectData>> getAllScenicAreaList();
}
