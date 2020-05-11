package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.response.*;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.service.ScenicsInfoService;
import cn.enn.wise.ssop.service.cms.service.*;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @date:2020/4/2
 * @author:hsq
 */
@RestController
@Api(value = "小程序内容接口", tags = {"小程序内容接口"})
@RequestMapping("/applet/cms")
public class CmsAppletController {
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    VoteService voteService;
    @Autowired
    AdvertiseService advertiseService;
    @Autowired
    ScenicsInfoService scenicsInfoService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    KnowledgeService knowledgeService;



    /**
     * 保存点赞记录
     */
    @ApiOperation(value = "保存点赞记录", notes = "保存App点赞记录")
    @PostMapping(value = "/saveVote")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "1攻略 2知识",type = "byte",required = true),
            @ApiImplicitParam(name = "articleId",value = "文章id",type = "long",required = true),
            @ApiImplicitParam(name = "isVote",value = "是否点赞， 否是取消点赞",type = "boolean",required = true)
    })
    public R<Boolean> saveVote(Byte type,Long articleId,Boolean isVote) {
        return new R<>(voteService.saveVote(type,articleId,isVote));
    }

    /**
     * 公告列表
     */
    @ApiOperation(value = "首页公告列表", notes = "APP首页公告列表")
    @GetMapping(value = "/announcement")
    public R<List<AnnouncementAppDTO>> getAnnouncementList() {
        List<AnnouncementAppDTO> announcementAppList = announcementService.getAnnouncementList();
        return announcementAppList!=null?R.success(announcementAppList):R.error("没有查到");
    }

    @ApiOperation(value = "广告详情", notes = "根据id获取广告详情")
    @GetMapping(value = "/detail/{id}")
    public R<SimpleAdvertiseDTO> getAdvertiseDetailById(@PathVariable Long id) {
        SimpleAdvertiseDTO detail = advertiseService.getAdvertiserDetail(id);
        if (Objects.isNull(detail)) return R.error("没有查到");
        String click = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_CLICK_LOAD, CmsEnum.ADVERTISE_CLICK.getValue());
        //点击量加一
        redisUtil.zZsetByKeyValueAddScore(click, id);
        return R.success(detail);
    }

    /**
     * 广告列表
     */
    @ApiOperation(value = "首页广告列表", notes = "APP首页广告列表")
    @GetMapping(value = "/advertiset")
    public R<List<AdvertiseAppDTO>> getAdvertiseList() {
        List<AdvertiseAppDTO> advertisetAppList = advertiseService.getadvertiseList();
        advertiseService.addLoadNumber(advertisetAppList);
        return advertisetAppList!=null?R.success(advertisetAppList):R.error("没有查到");
    }

    /**
     * 发现-周边其他
     */
    @ApiOperation(value = "周边其他", notes = "周边其他-景区内")
    @GetMapping(value = "/nearbyRests")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", type = "byte",required = true,
                    value = "2-restroom-洗手间、5-hotels-酒店、8-tourist-游客服务中心、10-parking-停车场")
    })
    public R<List<NearbyRestsAppDTO>> getNearbyRestsList(byte type) {
        List<NearbyRestsAppDTO> nearbyRestsList = scenicsInfoService.getNearbyRestsList(type);
        return nearbyRestsList!=null?R.success(nearbyRestsList):R.error("没有查到");
    }

    /**
     * 分享量
     */
    @ApiOperation(value = "分享量回调接口", notes = "分享量回调接口")
    @PutMapping(value = "/updateShare")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "2攻略 3知识",type = "byte",required = true),
            @ApiImplicitParam(name = "articleId",value = "文章id",type = "long",required = true)
    })
    public R<Boolean> updateShareNumber(Byte type, Long articleId) {
        return new R<>(knowledgeService.updateShareNumber(type, articleId));
    }


    @ApiOperation(value = "景区天气", notes = "根据景区Id查询天气")
    @GetMapping(value = "/weather")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lonLat",value = "当前经纬度  逗号分割",required = false)
    })
    public R<List<WeatherDTO>> getWeatherById(@RequestParam(required = false) String lonLat) {
        ScenicAreaDTO areaInfo = scenicsInfoService.getNowScenicAreaInfo(lonLat);
        return R.success(scenicsInfoService.getWeatherById(areaInfo.getId()));
    }


    @ApiOperation(value = "搜索历史", notes = "搜索历史")
    @GetMapping(value = "/search/history")
    public R<List<String>> searchHistory() {
        return R.success(Arrays.asList(
                "大峡谷","南迦巴瓦峰","优惠门票","热气球"
        ));
    }

    @ApiOperation(value = "清除搜索历史", notes = "清除搜索历史")
    @PostMapping(value = "/search/cleanHistory")
    public R<Boolean> cleanHistory() {
        return R.success(true);
    }

    @ApiOperation(value = "搜索发现列表", notes = "搜索发现列表")
    @GetMapping(value = "/search/discover")
    public R<List<String>> searchDiscover() {
        return R.success(Arrays.asList(
                "纳木错","南迦巴瓦峰","羊湖","鲁朗临海"
        ));
    }
}
