package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.PlayMedia;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicInfo;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicSpotVo;
import cn.enn.wise.ssop.service.cms.service.ScenicsInfoService;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 景点信息管理， 旧系统迁移过来
 */
@Api(value = "景点信息管理", tags = {"景点信息管理"})
@RestController
@RequestMapping("/scenicsSpot")
@Slf4j
public class ScenicsSpotController {

    @Autowired
    ScenicsInfoService scenicsInfoService;

    @ApiOperation(value = "景点内容添加",notes = "景点内容添加")
    @PostMapping("editScenicsInfo")
    public R<String> insertScenicsInfo(@RequestBody ScenicInfo scenicInfo){
        log.info(" insertScenicsInfo start");
        try {
            scenicsInfoService.updateScenics(scenicInfo);
            log.info(" insertScenicsInfo end");
            return R.success("添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error(" insertScenicsInfo error");
            return R.error("error");
        }
    }


    @ApiOperation(value = "添加图集",notes = "添加图集")
    @PostMapping("api/info/addImg")
    public R<String> addImg(@RequestBody List<PlayMedia> list){
        log.info(" insertScenicsInfo start");
        try {

            scenicsInfoService.updatePlayMediaByScenicId(list);
            log.info(" addImg end");
            return R.success("添加成功！");
        }catch (Exception e) {
            e.printStackTrace();
            log.error(" addImg error");

            return R.error("error");
        }

    }


    @ApiOperation(value = "根据景区ID获取景区简介",notes = "根据景区ID获取景区简介" )
    @GetMapping("api/info/getInfoById")
    public R<ScenicInfo> getInfoById(@RequestParam("id") String id){
        try {
            ScenicInfo scenicsInfoById = scenicsInfoService.getScenicsInfoById(id);
            return R.success(scenicsInfoById);
        }catch (Exception e){
            e.printStackTrace();
            log.info("getInfoById faild"+e.getMessage());
            return R.error("faild");
        }

    }
    @ApiOperation(value = "根据景区ID获取对应图集",notes = "根据景区ID获取景区简介" )
    @GetMapping("api/info/getUrlList")
    public R<List<PlayMedia>>  getPlayMediaByScenicId(@RequestParam("scenicId") String scenicId){
        try {
            List<PlayMedia> playMediaByScenicId = scenicsInfoService.getPlayMediaByScenicId(scenicId);
            return R.success(playMediaByScenicId);
        }catch (Exception e){
            e.printStackTrace();
            log.info("getPlayMediaByScenicId faild"+e.getMessage());
            return R.error("faild");
        }
    }

    @ApiOperation(value = "获取景点信息列表" ,notes = "获取景点信息列表")
    @PostMapping("api/info/scenicInfoList")
    public R<PageInfo<ScenicInfo>> getScenicInfoList(@RequestBody @ApiParam(name = "ScenicInfo", value = "json类型", required = true) ScenicInfo scenicInfo) {

        try {
            PageInfo pageInfo = scenicsInfoService.getScenicInfoList(scenicInfo);
            return R.success(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(" getScenicInfoList faild"+e.getMessage());
            return R.error("faild");
        }
    }


    @ApiOperation(value = "获取关联景点" ,notes = "获取关联景点")
    @PostMapping("api/info/getScenicSpotVo")
    public R<List<ScenicSpotVo>> getScenicSpotVo() {

        try {
            List<ScenicSpotVo> list = scenicsInfoService.getScenicSpotVo();
            return R.success(list);
        }catch (Exception e){
            e.printStackTrace();
            log.info(" getScenicSpotVo faild"+e.getMessage());
            return R.error("faild");
        }
    }

    @ApiOperation(value = "删除景点信息" ,notes = "删除景点信息")
    @PostMapping("api/info/delScenicInfoById")
    public R<Boolean> delScenicInfoById(@RequestParam("id") Integer id) {

        try {
            boolean isOk = scenicsInfoService.delScenicInfoById(id);
            return new R(isOk);
        }catch (Exception e){
            e.printStackTrace();
            log.info(" getScenicSpotVo faild"+e.getMessage());
            return R.error("faild");
        }
    }


    @ApiOperation(value = "景点信息上架下架" ,notes = "删除景点信息")
    @PostMapping("api/info/scenicInfoUpDown")
    public R<Boolean> scenicInfoUpDown(@RequestParam("id") Integer id, @RequestParam("state") Integer state) {

        try {
            boolean isOk = scenicsInfoService.scenicInfoUpDown(id,state);
            return new R(isOk);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("faild");
        }
    }

    @ApiOperation(value = "根据景点图片id删除图片",notes = "根据景点图片id删除图片" )
    @PostMapping("api/info/scenicInfo/gallery/delete/{id}")
    public R<Boolean>  deleteScenicInfoPhotoByPid(@PathVariable("id") Integer id){
        try {
            boolean isDeleteOk = scenicsInfoService.deleteScenicInfoPhotoByPid(id);
            return R.success(isDeleteOk);
        }catch (Exception e){
            e.printStackTrace();
            log.info("deleteScenicInfoPhotoByPid faild"+e.getMessage());
            return R.error("faild");
        }
    }
}
