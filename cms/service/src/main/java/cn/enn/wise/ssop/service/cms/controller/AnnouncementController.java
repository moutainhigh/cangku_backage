package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.AnnouncementSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.AnnouncementDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAnnouncementDTO;
import cn.enn.wise.ssop.service.cms.service.AnnouncementService;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shiz
 * 公告接口
 */
@RestController
@Api(value = "公告管理", tags = {"公告管理"})
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;


    @ApiOperation(value = "分页查询公告", notes = "分页查询公告")
    @GetMapping(value = "/list")
    public R<QueryData<SimpleAnnouncementDTO>> getAnnouncementList(@Validated QueryParam QueryParam) {
        return R.success(announcementService.getAnnouncementList(QueryParam));
    }

    @ApiOperation(value = "公告详情", notes = "根据id获取公告详情")
    @GetMapping(value = "/detail/{id}")
    public R<AnnouncementDTO> getAnnouncementDetailById(@PathVariable Long id) {
        AnnouncementDTO detail = announcementService.getAnnouncementrDetailById(id);
        return detail!=null?R.success(detail):R.error("没有查到");
    }

    @ApiOperation(value = "保存公告", notes = "增加或修改公告")
    @PostMapping(value = "/save")
    public R<Long> save(@Validated @RequestBody AnnouncementSaveParam AnnouncementAddParam) {
        return R.success(announcementService.saveAnnouncement(AnnouncementAddParam));
    }

    @ApiOperation(value = "删除公告", notes = "根据id删除公告")
    @DeleteMapping(value = "/delete")
    public R<Boolean> delete(Long id) {
        return R.success(announcementService.delAnnouncement(id));
    }

    @ApiOperation(value = "禁用公告", notes = "禁用公告")
    @PutMapping(value = "/lock")
    public R<Boolean> lock(Long id) {
        return R.success(announcementService.editState(id, Byte.valueOf("2")));
    }

    @ApiOperation(value = "启用公告", notes = "启用公告")
    @PutMapping(value = "/unlock")
    public R<Boolean> unlock(Long id) {
        return R.success(announcementService.editState(id, Byte.valueOf("1")));
    }

}
