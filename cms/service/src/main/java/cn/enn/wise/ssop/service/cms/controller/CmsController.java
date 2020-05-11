package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.CategoryParam;
import cn.enn.wise.ssop.api.cms.dto.request.StrategySaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.CategoryDTO;
import cn.enn.wise.ssop.api.cms.dto.response.ContentListDTO;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.model.Strategy;
import cn.enn.wise.ssop.service.cms.service.ContentService;
import cn.enn.wise.uncs.base.constant.BusinessEnumInit;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shiz
 * 内容接口
 */
@RestController
@Api(value = "内容接口/分类接口", tags = {"内容接口/分类接口"})
@RequestMapping("/")
public class CmsController {

    @Autowired
    ContentService contentService;


    @ApiOperation(value = "内容列表", notes = "内容列表模糊和分类搜索", position = 1)
    @GetMapping(value = "/content/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText", value = "搜索文本",required = false,paramType = "query"),
            @ApiImplicitParam(name = "typeId", value = "内容类型id",required = true,paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo",required = true,paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize",required = true,paramType = "query",defaultValue = "10")
    })
    public R<QueryData<ContentListDTO>> getCmsContentList(String searchText, Integer typeId, Integer pageNo, Integer pageSize) {
        return new R<>(contentService.getCmsContentList(searchText,typeId,pageNo,pageSize));
    }

    @ApiOperation("枚举列表")
    @GetMapping("/enumList")
    public R<Map<String,List<SelectData>>> getCmsContentList(Timestamp date) {
        StrategySaveParam strategySaveParam = new StrategySaveParam();
        strategySaveParam.setPublishTime(date);

        Strategy strategy = new Strategy();
        BeanUtils.copyProperties(strategySaveParam,strategy);
        return new R<>(BusinessEnumInit.enumMap);
    }

    @ApiOperation("查询分类列表")
    @GetMapping("/category/all")
    public R<List<CategoryDTO>> getAllCMSCategory(){
        return R.success(contentService.getAllCMSCategory());
    }

    @ApiOperation("保存分类")
    @PostMapping("/category/save")
    public R<Boolean> saveAllCMSCategory(@Validated @RequestBody List<CategoryParam> categoryParamList){
        return R.success(contentService.saveAllCMSCategory(categoryParamList));
    }

    @ApiOperation("增加一级分类")
    @PostMapping("/category/addOneLevel")
    public R<Boolean> addOneLevel(@Validated @RequestBody CategoryParam categoryParam){
        return R.success(contentService.addOneLevel(categoryParam));
    }

    @ApiOperation("删除一个分类")
    @DeleteMapping("/category/{id}")
    public R<Boolean> delCategory(@PathVariable("id")Long id){
        return R.success(contentService.delCategory(id));
    }



}
