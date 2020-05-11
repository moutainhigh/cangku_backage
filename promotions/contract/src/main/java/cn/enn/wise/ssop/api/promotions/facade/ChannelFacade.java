package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelPriceDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelRuleDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.GoodsExtendDTO;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author jiabaiye
 * 渠道管理接口
 */
@FeignClient("promotions-service")
@Repository
public interface ChannelFacade {

    @ApiOperation(value = "渠道列表", notes = "渠道列表", position = 1)
    @GetMapping(value = "/channel/channelList")
    R<QueryData<ChannelDTO>> getChannelList(@Validated ChannelListParam channelListParam);

    @ApiOperation(value = "渠道名与id列表", notes = "渠道名与id列表", position = 1)
    @GetMapping(value = "/channel/nameAndIdList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelType", value = "渠道类型", paramType = "query" ,dataType="Byte")
    })
    R<List<ChannelDTO>> getNameAndIdList(@Validated Byte channelType);

    @ApiOperation(value = "保存渠道", notes = "保存渠道", position = 1)
    @PostMapping(value = "/channel/saveChannel")
    R<ChannelDTO> saveChannel(@Validated @RequestBody ChannelParam channelParam);

    @ApiOperation(value = "修改渠道", notes = "修改渠道", position = 2)
    @PostMapping(value = "/channel/updateChannel")
    R<ChannelDTO> updateChannel(@Validated @RequestBody ChannelParam channelParam) ;

    @ApiOperation(value = "渠道详情", notes = "渠道详情", position = 3)
    @GetMapping(value = "/channel/channelDetail/{id}")
    R<ChannelDTO> channelDetail(@PathVariable  Long id);

    @ApiOperation(value = "关闭渠道", notes = "关闭渠道", position = 5)
    @PutMapping(value = "/channel/lock/{id}")
    R<Boolean> lock(@PathVariable Long id) ;

    @ApiOperation(value = "启用渠道", notes = "启用渠道", position = 6)
    @PutMapping(value = "/channel/unlock/{id}")
    R<Boolean> unlock(@PathVariable Long id) ;

    @ApiOperation(value = "保存渠道政策", notes = "保存渠道政策", position = 1)
    @PostMapping(value = "/channel/saveChannelRule")
    R<Boolean>  saveChannelRule(@Validated @RequestBody ChannelRuleParam channelRuleParam);

    @ApiOperation(value = "查询渠道政策(单个)", notes = "查询渠道政策", position = 1)
    @GetMapping(value = "/channel/channelRuleDetail")
    R<List<ChannelRuleDTO>> channelRuleDetail(@Validated  ChannelRuleQueryParam channelRuleQueryParam);

    @ApiOperation(value = "查询渠道商品", notes = "查询渠道商品", position = 1)
    @GetMapping(value = "/channel/channelGoodsDetail")
    R<List<GoodsExtendDTO>> channelGoodsDetail(@Validated  ChannelGoodsQueryParam channelGoodsQueryParam) ;

    @ApiOperation(value = "删除渠道政策", notes = "删除渠道政策", position = 1)
    @DeleteMapping(value = "/channel/deleteChannelRule")
    R<Boolean> deleteChannelRule(@Validated  ChannelGoodsDeleteParam channelGoodsDeleteParam);

    @ApiOperation(value = "查询渠道政策价格和返利", notes = "查询渠道政策价格和返利", position = 1)
    @GetMapping(value = "/channel/channelPriceDetail")
    R<List<ChannelPriceDTO>> channelPriceDetail(@Validated  ChannelPriceQueryParam channelPriceQueryParam) ;

}
