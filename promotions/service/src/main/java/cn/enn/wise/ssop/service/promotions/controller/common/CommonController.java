package cn.enn.wise.ssop.service.promotions.controller.common;

import cn.enn.wise.ssop.api.promotions.dto.request.PartnerQueryParam;
import cn.enn.wise.ssop.api.promotions.dto.request.PartnerSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerAddRespDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.SimplePartnerDTO;
import cn.enn.wise.ssop.service.promotions.service.ChannelService;
import cn.enn.wise.ssop.service.promotions.service.EnumService;
import cn.enn.wise.ssop.service.promotions.service.PartnerService;
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
 * @author jiabaiye
 * 渠道管理接口
 */
@RestController
@Api(value = "营销公共管理接口", tags = {"营销公共管理接口"})
@RequestMapping("/common")
public class CommonController {

    @Autowired
    EnumService enumService;


    @ApiOperation(value = "查询页面显示的枚举数据", notes = "查询页面显示的枚举数据", position = 1)
    @GetMapping(value = "/getEnum/{pageName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageName", value = "页面名称", paramType = "query", dataType = "String"),
    })
    public R getPartnerList(@PathVariable String pageName) {
        return enumService.getEnum(pageName);
    }



}
