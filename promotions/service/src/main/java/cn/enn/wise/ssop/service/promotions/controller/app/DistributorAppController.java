package cn.enn.wise.ssop.service.promotions.controller.app;


import cn.enn.wise.ssop.api.promotions.dto.request.ChannelRuleDetailParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAddUpdateAppParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorRigisterParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.service.DistributorAccountService;
import cn.enn.wise.ssop.service.promotions.service.DistributorAddService;
import cn.enn.wise.ssop.service.promotions.service.DistributorBaseService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 耿小洋
 * App端分销商相关接口
 */
@RestController
@Api(value = "App端分销商相关接口", tags = {"App端分销商相关接口"})
@RequestMapping("/app/distributor")
public class DistributorAppController {

    @Autowired
    DistributorBaseService distributorBaseService;

    @Autowired
    DistributorAddService distributorAddService;


    @Autowired
    DistributorAccountService distributorAccountService;


    @ApiOperation(value = "App端分销商注册", notes = "App端分销商注册")
    @PostMapping(value = "/registerDistribution")
    public R registerDistribution(@Validated @RequestBody DistributorRigisterParam param) {
        return distributorBaseService.registerDistribution(param);
    }


    @ApiOperation(value = "根据分销商id获取补充信息", notes = "根据分销商id获取补充信息")
    @GetMapping(value = "/getDistributionAddByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<DistributorAddDTO> getDistributionAddByDistribuBaseId(Long distribuBaseId) {
        R distributionAddByDistribuBaseId = distributorAddService.getDistributionAddByDistribuBaseId(distribuBaseId);
        return distributionAddByDistribuBaseId;
    }



    @ApiOperation(value = "修改分销商补充信息", notes = "修改分销商补充信息")
    @PostMapping(value = "/updateDistributionAdd")
    public R<Boolean> updateDistributionAdd(@Validated @RequestBody DistributorAddUpdateAppParam param) {
        return distributorAddService.appUpdateDistributionAdd(param);
    }


    @ApiOperation(value = "通过用户id（即分销商账号id）判断用户是否是分销商账号", notes = "通过用户id（即分销商账号id）判断用户是否是分销商账号")
    @GetMapping(value = "/getUserIsDistributorByUserId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    public R<Boolean> getUserIsDistributorByUserId( Long userId) {
        return distributorAccountService.getUserIsDistributorByUserId(userId);
    }


    @ApiOperation(value = "根据分销商id获取基础信息", notes = "根据分销商id获取基础信息")
    @GetMapping(value = "/getDistributionBaseByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<DistributorBaseDTO> getDistributionBaseByDistribuBaseId(Long distribuBaseId) {
        return distributorBaseService.getDistributionBaseByDistribuBaseId(distribuBaseId);
    }


    @ApiOperation(value = "查询分销政策", notes = "根据分销商id查询分销政策")
    @GetMapping(value = "/getDistributionChannelRuleByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<ChannelRuleDetailDTO> getDistributionChannelRuleByDistribuBaseId(@Validated ChannelRuleDetailParam channelRuleDetailParam) {
        return new R<>(distributorBaseService.getDistributionChannelRuleByDistribuBaseId(channelRuleDetailParam));
    }


    @ApiOperation(value = "获取分销商App首页上半部分信息", notes = "获取分销商App首页上半部分信息")
    @GetMapping(value = "/getDistributorAppFirstPageHead")
    public R<DistributorAppFirstPageHeadDTO> getDistributorAppFirstPageHead() {
        return new R<>(distributorBaseService.getDistributorAppFirstPageHead());
    }


    @ApiOperation(value = "获取分销商App首页下部分信息", notes = "获取分销商App首页下半部分信息")
    @GetMapping(value = "/getDistributorAppFirstPageBottom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessUnitId", value = "业务范围id", required = false),
            @ApiImplicitParam(name = "searchKeyWord", value = "搜索关键字", required = false)
    })
    public  R<List<DistributorAppFirstPageBottomDTO>> getDistributorAppFirstPageBottom(Long businessUnitId,String searchKeyWord) {
        return distributorBaseService.getDistributorAppFirstPageBottom();
    }


}
