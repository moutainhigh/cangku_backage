package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.param.TagGoodsParam;
import cn.enn.wise.platform.mall.bean.param.TagParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiabaiye
 * @Description
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/tag")
@Api(value = "标签管理", tags = {"标签管理"})
public class TagController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagCategoryService tagCategoryService;

    @Autowired
    TagService tagService;


    @Autowired
    TagGoodsService tagGoodsService;

    @Autowired
    TagProjectService tagProjectService;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @PostMapping("/list")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "type", value = "标签类型 1 项目 2 商品", paramType = "query")
    })
    public ResponseEntity<List<TagVo>> tagList(String type) {

        try {
            List<TagVo> list = tagCategoryService.getTagList(type);
            return new ResponseEntity(GeneConstant.INT_1, "标签列表", list);
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());
        }
    }

    @GetMapping("/goods/list")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    public ResponseEntity<List<TagGoodsVo>> goodsTagList( Long goodsId) {

        try {
            List<TagGoodsVo> list = tagCategoryService.getGoodsTagList(goodsId);
            return new ResponseEntity(GeneConstant.INT_1, "商品标签列表", list);
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());
        }
    }

    @PostMapping("/category/list")
    @ApiOperation(value = "标签分类列表", notes = "标签管理")
    public ResponseEntity<List<TagCategoryVo>> tagCategoryList() {

        try {
            List<TagCategoryVo> list = tagCategoryService.getTagCategoryList();
            return new ResponseEntity(GeneConstant.INT_1, "标签分类列表", list);
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());
        }
    }

    @PostMapping("/goods/update")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    public ResponseEntity goodsTagUpdate(@RequestBody List<TagGoodsParam> param) {
        try {
            tagGoodsService.updateGoodsTagList(param);
            remoteServiceUtil.deleteKey(RemoteServiceUtil.TAG_REDIS_KEY);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功");
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());

        }
    }

    @PostMapping("/project/update")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    public ResponseEntity projectTagUpdate(@RequestBody List<TagGoodsParam> param) {
        try {
            tagProjectService.updateProjectTagList(param);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功");
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());

        }
    }



    @GetMapping("/project/list")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    public ResponseEntity<List<TagProjectVo>> goodsProjectList(Long projectId) {

        try {
            List<TagProjectVo> list = tagCategoryService.getProjectTagList(projectId);
            return new ResponseEntity(GeneConstant.INT_1, "项目标签列表", list);
        } catch (Exception e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,e.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "标签列表", notes = "标签管理")
    public ResponseEntity<List<TagVo>> update(@RequestBody TagParam param) {

        if (param.getCategoryId() == 0 || StringUtils.isEmpty(param.getName()) || param.getType() == 0) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR, "参数错误");
        }
        TagBo tagBo = tagService.addTag(param);
        return new ResponseEntity(GeneConstant.INT_1, "标签修改", tagBo);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public ResponseEntity<List<TagVo>> delete(@RequestBody List<Long> ids) {

        try {
            Integer result = tagService.deleteTag(ids);
            return new ResponseEntity(GeneConstant.INT_1, "标签删除", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "删除失败", e.getMessage());
        }
    }
}
