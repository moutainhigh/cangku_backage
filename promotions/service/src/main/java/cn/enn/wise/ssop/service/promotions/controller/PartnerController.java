package cn.enn.wise.ssop.service.promotions.controller;

import cn.enn.wise.ssop.api.promotions.dto.request.PartnerQueryParam;
import cn.enn.wise.ssop.api.promotions.dto.request.PartnerSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerAddRespDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.SimplePartnerDTO;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.ssop.service.promotions.service.PartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shiz
 * 合作伙伴接口
 */
@RestController
@Api(value = "合作伙伴", tags = {"合作伙伴"})
@RequestMapping("/partner")
public class PartnerController {

    @Autowired
    PartnerService partnerService;


    @ApiOperation(value = "分页查询合作伙伴", notes = "运营端根据条件查询合作伙伴", position = 1)
    @GetMapping(value = "/list")
    public R<QueryData<SimplePartnerDTO>> getPartnerList(@Validated PartnerQueryParam partnerParam) {
        return new R<>(partnerService.getPartners(partnerParam));
    }

    @ApiOperation(value = "合作伙伴详情", notes = "根据id获取合作伙伴详情", position = 2)
    @GetMapping(value = "/detail/{id}")
    public R<PartnerDTO> getPartnerDetailById(@PathVariable Long id) {
        return new R<>(partnerService.getPartnerDetail(id));
    }

    @ApiOperation(value = "保存合作伙伴", notes = "增加或修改合作伙伴", position = 3)
    @PostMapping(value = "/save")
    public R<PartnerAddRespDTO> save(@Validated @RequestBody PartnerSaveParam partnerAddParam) {
        return new R<>(partnerService.addPartner(partnerAddParam));
    }

    @ApiOperation(value = "删除合作伙伴", notes = "根据id删除合作伙伴", position = 4)
    @DeleteMapping(value = "/delete/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return new R<>(partnerService.delPartner(id));
    }

    @ApiOperation(value = "禁用合作伙伴", notes = "", position = 5)
    @PutMapping(value = "/lock/{id}")
    public R<Boolean> lock(@PathVariable Long id) {
        return new R<>(partnerService.editState(id, 1));
    }

    @ApiOperation(value = "启用合作伙伴", notes = "", position = 6)
    @PutMapping(value = "/unlock/{id}")
    public R<Boolean> unlock(@PathVariable Long id) {
        return new R<>(partnerService.editState(id, 2));
    }

    @ApiOperation(value = "根据appKey获取合作伙伴详情", notes = "根据id获取合作伙伴详情", position = 2)
    @GetMapping(value = "/detail/appKey/{appKey}")
    public R<PartnerDTO> detailByAppKey(@PathVariable String appKey) {
        return new R<>(partnerService.getPartnerDetailByAppKey(appKey));
    }

}
