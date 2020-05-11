package cn.enn.wise.ssop.service.promotions.controller.pc;


import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.api.promotions.facade.ChannelFacade;
import cn.enn.wise.ssop.service.promotions.service.ChannelRuleService;
import cn.enn.wise.ssop.service.promotions.service.ChannelService;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
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
 * @author jiabaiye
 * 渠道管理接口
 */
@RestController
@Api(value = "渠道管理接口", tags = {"渠道管理接口"})
@RequestMapping("/channel")
public class ChannelController implements ChannelFacade {

    @Autowired
    ChannelService channelService;

    @Autowired
    ChannelRuleService channelRuleService;

    @ApiOperation(value = "渠道列表", notes = "渠道列表", position = 1)
    @GetMapping(value = "/channelList")
    @Override
    public R<QueryData<ChannelDTO>> getChannelList(@Validated ChannelListParam channelListParam) {
        return  new R<>(channelService.getChannelList(channelListParam));
    }

    @ApiOperation(value = "渠道名与id列表", notes = "渠道名与id列表", position = 1)
    @GetMapping(value = "/nameAndIdList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelType", value = "渠道类型", paramType = "query" ,dataType="Byte")
    })
    @Override
    public R<List<ChannelDTO>> getNameAndIdList(@Validated Byte channelType) {
        return  new R(channelService.getNameAndIdList(channelType));
    }

    @ApiOperation(value = "保存渠道", notes = "保存渠道", position = 1)
    @PostMapping(value = "/saveChannel")
    @Override
    public R<ChannelDTO> saveChannel(@Validated @RequestBody ChannelParam channelParam) {
        return channelService.addChannel(channelParam);
    }

    @ApiOperation(value = "修改渠道", notes = "修改渠道", position = 2)
    @PostMapping(value = "/updateChannel")
    @Override
    public R<ChannelDTO> updateChannel(@Validated @RequestBody ChannelParam channelParam) {
        return channelService.updateChannel(channelParam);
    }

    @ApiOperation(value = "渠道详情", notes = "渠道详情", position = 3)
    @GetMapping(value = "/channelDetail/{id}")
    @Override
    public R<ChannelDTO> channelDetail(@PathVariable  Long id) {
        return channelService.channelDetail(id);
    }

    @ApiOperation(value = "关闭渠道", notes = "关闭渠道", position = 5)
    @PutMapping(value = "/lock/{id}")
    @Override
    public R<Boolean> lock(@PathVariable Long id) {
        return new R<>(channelService.editChannelState(id, GeneConstant.BYTE_2));
    }

    @ApiOperation(value = "启用渠道", notes = "启用渠道", position = 6)
    @PutMapping(value = "/unlock/{id}")
    @Override
    public R<Boolean> unlock(@PathVariable Long id) {
        return new R<>(channelService.editChannelState(id, GeneConstant.BYTE_1));
    }


    @ApiOperation(value = "保存渠道政策", notes = "保存渠道政策", position = 1)
    @PostMapping(value = "/saveChannelRule")
    @Override
    public R<Boolean> saveChannelRule(@Validated @RequestBody ChannelRuleParam channelRuleParam) {
        return new R(channelRuleService.addChannelRule(channelRuleParam));
    }

    @ApiOperation(value = "查询渠道政策(单个)", notes = "查询渠道政策", position = 1)
    @GetMapping(value = "/channelRuleDetail")
    @Override
    public R<List<ChannelRuleDTO>> channelRuleDetail(@Validated  ChannelRuleQueryParam channelRuleQueryParam) {
        return new R(channelRuleService.getChannelRule(channelRuleQueryParam));
    }

    @ApiOperation(value = "查询渠道商品", notes = "查询渠道商品", position = 1)
    @GetMapping(value = "/channelGoodsDetail")
    @Override
    public R<List<GoodsExtendDTO>> channelGoodsDetail(@Validated  ChannelGoodsQueryParam channelGoodsQueryParam) {
        return channelRuleService.getChannelGoods(channelGoodsQueryParam);
    }

    @ApiOperation(value = "删除渠道政策", notes = "删除渠道政策", position = 1)
    @DeleteMapping(value = "/deleteChannelRule")
    @Override
    public R<Boolean> deleteChannelRule(@Validated  ChannelGoodsDeleteParam channelGoodsDeleteParam) {
        return new R(channelRuleService.deleteChannelRule(channelGoodsDeleteParam));
    }

    @ApiOperation(value = "查询渠道政策价格和返利", notes = "查询渠道政策价格和返利", position = 1)
    @GetMapping(value = "/channelPriceDetail")
    @Override
    public R<List<ChannelPriceDTO>> channelPriceDetail(@Validated  ChannelPriceQueryParam channelPriceQueryParam) {
        return channelRuleService.getChannelPrice(channelPriceQueryParam);
    }


}
