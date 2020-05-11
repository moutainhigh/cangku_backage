package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.response.ScenicAreaDTO;
import cn.enn.wise.ssop.api.cms.dto.response.ScenicFineDTO;
import cn.enn.wise.ssop.api.cms.dto.response.ScenicInfoDTO;
import cn.enn.wise.ssop.service.cms.service.EnshrineService;
import cn.enn.wise.ssop.service.cms.service.KnowledgeService;
import cn.enn.wise.ssop.service.cms.service.ScenicsInfoService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value = "小程序景点内容", tags = {"小程序景点接口"})
@RequestMapping("/applet/scenics")
public class ScenicresAppletController {

    @Autowired
    ScenicsInfoService scenicsInfoService;
    @Autowired
    KnowledgeService knowledgeService;
    @Autowired
    EnshrineService enshrineService;


    /**
     * 查询景点内容详情
     */
    @ApiOperation(value = "查询景点内容详情", notes = "查询景点内容详情接口")
    @GetMapping(value = "/detail/{id}")
    public R<ScenicInfoDTO> getScenicsInfoAppletById(@PathVariable Integer id) {
        ScenicInfoDTO scenicInfoDTO = scenicsInfoService.getScenicsInfoAppletById(id);
        return new R<>(scenicInfoDTO);
    }

    /**
     * 查询全部美景
     */
    @ApiOperation(value = "查询全部美景", notes = "查询全部美景接口")
    @GetMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lonLat",value = "当前经纬度  逗号分割",required = false)
    })
    public R<List<ScenicFineDTO>> getScenicsInfoAppletList(@RequestParam(required = false) String lonLat) {
        return R.success(scenicsInfoService.getAppletFineScenicsList(lonLat));
    }

    /**
     * 查询全部美景
     */
    @ApiOperation(value = "发现页面美景列表", notes = "发现页面美景列表")
    @GetMapping(value = "/spot/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lonLat",value = "当前经纬度  逗号分割",required = false)
    })
    public R<List<ScenicFineDTO>> getScenicsSpotList(@RequestParam(required = false) String lonLat) {
        return R.success(scenicsInfoService.getScenicsSpotList(lonLat));
    }

    /**
     * 保存收藏记录
     */
    @ApiOperation(value = "保存收藏记录", notes = "保存App收藏记录")
    @PostMapping(value = "/saveVote")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "1景点 2路线",type = "byte",required = true),
            @ApiImplicitParam(name = "articleId",value = "文章id",type = "long",required = true),
            @ApiImplicitParam(name = "isVote",value = "是否收藏， 否是取消收藏",type = "boolean",required = true)
    })
    public R<Boolean> saveEnshrine(Byte type,Long articleId,Boolean isVote) {
        Boolean aBoolean = enshrineService.saveEnshrine(type, articleId, isVote);
        return new R<>(aBoolean);
    }


    @ApiOperation(value = "获取当前景区信息", notes = "获取当前景区信息")
    @GetMapping(value = "/getNowScenicAreaInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lonLat",value = "当前经纬度  逗号分割",required = true)
    })
    public R<ScenicAreaDTO> getNowScenicAreaInfo(@RequestParam() String lonLat) {
        return R.success(scenicsInfoService.getNowScenicAreaInfo(lonLat));
    }

}
