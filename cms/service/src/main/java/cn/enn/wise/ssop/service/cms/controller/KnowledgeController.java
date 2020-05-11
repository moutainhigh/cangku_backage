package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.KnowledgeSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.KnowledgeDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleKnowledgeDTO;
import cn.enn.wise.ssop.service.cms.service.KnowledgeService;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shiz
 * 知识接口
 */
@RestController
@Api(value = "知识管理", tags = {"知识管理"})
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    KnowledgeService knowledgeService;


    @ApiOperation(value = "分页查询知识", notes = "分页查询知识")
    @GetMapping(value = "/list")
    public R<QueryData<SimpleKnowledgeDTO>> getKnowledgeList(@Validated QueryParam QueryParam) {
        return new R<>(knowledgeService.getKnowledgeList(QueryParam));
    }

    @ApiOperation(value = "知识详情", notes = "根据id获取知识详情")
    @GetMapping(value = "/detail/{id}")
    public R<KnowledgeDTO> getKnowledgeDetailById(@PathVariable Long id) {
        KnowledgeDTO detail = knowledgeService.getKnowledgeDetailById(id);
        return detail!=null?R.success(detail):R.error("没有查到");
    }

    @ApiOperation(value = "保存知识", notes = "增加或修改知识")
    @PostMapping(value = "/save")
    public R<Long> save(@Validated @RequestBody KnowledgeSaveParam KnowledgeAddParam) {
        return new R<>(knowledgeService.saveKnowledge(KnowledgeAddParam));
    }

    @ApiOperation(value = "删除知识", notes = "根据id删除知识")
    @DeleteMapping(value = "/delete")
    public R<Boolean> delete(Long id) {
        return R.success(knowledgeService.delKnowledge(id));
    }

    @ApiOperation(value = "禁用知识", notes = "禁用知识")
    @PutMapping(value = "/lock")
    public R<Boolean> lock(Long id) {
        return R.success(knowledgeService.editState(id, Byte.valueOf("2")));
    }

    @ApiOperation(value = "启用知识", notes = "启用知识")
    @PutMapping(value = "/unlock")
    public R<Boolean> unlock(Long id) {
        return R.success(knowledgeService.editState(id, Byte.valueOf("1")));
    }

    @ApiOperation(value = "全部知识select", notes = "全部知识select")
    @GetMapping(value = "/select")
    public R<List<SelectData>> getAllKnowledgeSelect() {
        return new R<>(knowledgeService.getAllKnowledgeSelect());
    }

}
