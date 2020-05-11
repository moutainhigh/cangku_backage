package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.AdvertiseSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAdvertiseDTO;
import cn.enn.wise.ssop.api.cms.facade.AdvertiseFacode;
import cn.enn.wise.ssop.service.cms.service.AdvertiseService;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shiz
 * 广告管理
 */
@RestController
@Api(value = "广告管理", tags = {"广告管理"})
@RequestMapping("/advertise")
public class AdvertiseController implements AdvertiseFacode {

    @Autowired
    AdvertiseService advertiseService;


    @ApiOperation(value = "分页查询广告", notes = "分页查询广告")
    @GetMapping(value = "/list")
    @Override
    public R<QueryData<SimpleAdvertiseDTO>> getAdvertiseList(@Validated QueryParam QueryParam) {
        return new R<>(advertiseService.getAdvertiseList(QueryParam));
    }

    @ApiOperation(value = "广告详情", notes = "根据id获取广告详情")
    @GetMapping(value = "/detail/{id}")
    @Override
    public R<SimpleAdvertiseDTO> getAdvertiseDetailById(@PathVariable Long id) {
        SimpleAdvertiseDTO detail = advertiseService.getAdvertiserDetailById(id);
        return detail!=null?R.success(detail):R.error("没有查到");
    }

    @ApiOperation(value = "保存广告", notes = "增加或修改广告")
    @PostMapping(value = "/save")
    @Override
    public R<Long> save(@Validated @RequestBody AdvertiseSaveParam AdvertiseAddParam) {
        return new R<>(advertiseService.saveAdvertise(AdvertiseAddParam));
    }

    @ApiOperation(value = "删除广告", notes = "根据id删除广告")
    @DeleteMapping(value = "/delete")
    @Override
    public R<Boolean> delete(Long id) {
        return R.success(advertiseService.delAdvertise(id));
    }

    @ApiOperation(value = "禁用广告", notes = "禁用广告")
    @PutMapping(value = "/lock")
    @Override
    public R<Boolean> lock(Long id) {
        return R.success(advertiseService.editState(id, Byte.valueOf("2")));
    }

    @ApiOperation(value = "启用广告", notes = "启用广告")
    @PutMapping(value = "/unlock")
    @Override
    public R<Boolean> unlock(Long id) {
        return R.success(advertiseService.editState(id, Byte.valueOf("1")));
    }

    @ApiOperation(value = "修改排序", notes = "修改排序")
    @PutMapping(value = "/doSort")
    @Override
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "sort",value = "排序数字",required = true,dataType = "Integer")
    })
    public R<Boolean> doSort(Long id,Integer sort) {
        return R.success(advertiseService.doSort(id, sort));
    }

}
