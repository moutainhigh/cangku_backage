package cn.enn.wise.ssop.api.cms.facade;

import cn.enn.wise.ssop.api.cms.dto.request.AdvertiseSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAdvertiseDTO;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "广告", tags = {"广告"})
@FeignClient(value = "cms-service")
public interface AdvertiseFacode {
    @ApiOperation(value = "分页查询广告", notes = "分页查询广告")
    @GetMapping(value = "/list")
    R<QueryData<SimpleAdvertiseDTO>> getAdvertiseList(@Validated QueryParam QueryParam);

    @ApiOperation(value = "广告详情", notes = "根据id获取广告详情")
    @GetMapping(value = "/detail/{id}")
    R<SimpleAdvertiseDTO> getAdvertiseDetailById(@PathVariable Long id);

    @ApiOperation(value = "保存广告", notes = "增加或修改广告")
    @PostMapping(value = "/save")
    R<Long> save(@Validated @RequestBody AdvertiseSaveParam AdvertiseAddParam);

    @ApiOperation(value = "删除广告", notes = "根据id删除广告")
    @DeleteMapping(value = "/delete")
    R<Boolean> delete(Long id);

    @ApiOperation(value = "禁用广告", notes = "禁用广告")
    @PutMapping(value = "/lock")
    R<Boolean> lock(Long id);

    @ApiOperation(value = "启用广告", notes = "启用广告")
    @PutMapping(value = "/unlock")
    R<Boolean> unlock(Long id);

    @ApiOperation(value = "修改排序", notes = "修改排序")
    @PutMapping(value = "/doSort")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "sort",value = "排序数字",required = true,dataType = "Integer")
    })
    R<Boolean> doSort(Long id, Integer sort);
}
