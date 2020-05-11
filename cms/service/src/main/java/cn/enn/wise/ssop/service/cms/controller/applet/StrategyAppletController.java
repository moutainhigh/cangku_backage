package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.response.HomeStrategyListDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyAppDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyAppListDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyDTO;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.service.StrategyAppService;
import cn.enn.wise.ssop.service.cms.service.StrategyService;
import cn.enn.wise.ssop.service.cms.service.VoteService;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.QueryOffsetData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


/**
 * 攻略APP管理
 */
@RestController
@Api(value = "小程序攻略接口", tags = {"小程序攻略接口"})
@RequestMapping("/applet/strategy")
public class StrategyAppletController {
    @Autowired
    StrategyService strategyService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    StrategyAppService strategyAppService;


    @ApiOperation(value = "首页攻略类别", notes = "首页攻略类别")
    @GetMapping(value = "/homeStratoryList")
    public R<List<HomeStrategyListDTO>> homeStratoryList() {
        return R.success(strategyAppService.homeStratoryList());
    }


    @ApiOperation(value = "APP攻略详情", notes = "根据id获取攻略APP详情")
    @GetMapping(value = "/detail/{id}")
    public R<StrategyAppDTO> getStrategyAppDetailById(@PathVariable Long id) {
        StrategyDTO detail = strategyService.getStrategyrDetailByApp(id);
        if(detail==null){
            R.error("没有查到");
        }
        //查询用户是否已点赞
        Boolean flag = strategyAppService.isVote(detail.getId());
        //查询点赞数量
        Long count = strategyAppService.voteCount(detail.getId());

        StrategyAppDTO strategyAppDTO = new StrategyAppDTO();
        BeanUtils.copyProperties(detail, strategyAppDTO);
        strategyAppDTO.setIsVote(flag);
        strategyAppDTO.setVoteCount(count);

        //浏览量加一
        String strategyKey = RedisKey.ServerName + String.format(RedisKey.PAGE_VIEW, CmsEnum.CMS_TYPE_STRATEGY.getValue());
        redisUtil.zZsetByKeyValueAddScore(strategyKey, id);
        return R.success(strategyAppDTO);
    }

    @ApiOperation(value = "根据类别查询攻略列表", notes = "根据类别查询攻略列表")
    @GetMapping(value = "/listBycategoryId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId",value = "分类id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "offset",value = "位置",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "数量",required = true,dataType = "Integer")
    })
    public R<QueryOffsetData<StrategyAppListDTO>> getStrategyListByCategory
            (Long categoryId, Integer offset, Integer pageSize) {

        QueryOffsetData<StrategyAppListDTO> strategyAppList =
                strategyAppService.getStrategyAppList(categoryId, offset, pageSize);
        if (Objects.isNull(strategyAppList)) return R.error(null);

        strategyAppService.assemblyData(strategyAppList);
        return new R<>(strategyAppList);
    }


}



