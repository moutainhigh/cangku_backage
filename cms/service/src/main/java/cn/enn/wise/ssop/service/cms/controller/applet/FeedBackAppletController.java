package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.FeedBackVo;
import cn.enn.wise.ssop.api.cms.dto.request.oldmall.FeedBackParam;
import cn.enn.wise.ssop.service.cms.model.FeedBackExpand;
import cn.enn.wise.ssop.service.cms.service.FeedBackService;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序建议接口
 */
@RestController
@Api(value = "小程序建议反馈接口", tags = {"小程序建议反馈接口"})
@RequestMapping("/applet/feedBack")
public class FeedBackAppletController {

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping("/add")
    @ApiOperation(value = "H5建议反馈", notes = "H5建议反馈")
    public R<Integer> addFeedBack(@RequestBody FeedBackVo feedBackVo){
        return new R<>(feedBackService.addFeedBack(feedBackVo));
    }

    @PostMapping("/query/type")
    @ApiOperation(value = "查询景区反馈类型", notes = "查询景区反馈类型")
    public R<List<FeedBackExpand>> findAllFeedBackType(){
        return new R<>(feedBackService.findAllFeedBackType());
    }

    @PostMapping("/query/all")
    @ApiOperation(value = "分页查询所有反馈信息", notes = "分页查询所有反馈信息")
    public R<PageInfo<FeedBackVo>> findAllFeedBackList(@RequestBody FeedBackParam feedBackParam){
        PageHelper.startPage(feedBackParam.getPageNum(),feedBackParam.getPageSize());
        PageInfo<FeedBackVo> pageInfo = feedBackService.findAllFeedBackList(feedBackParam);
        return new R<>(pageInfo);
    }


    @PostMapping("/reply")
    @ApiOperation(value = "回复或忽略", notes = "回复或忽略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "1.回复 2.忽略",type = "byte",required = true),
            @ApiImplicitParam(name = "id",value = "回复或忽略标识",type = "long",required = true)
    })
    public R<Boolean> modifyStatus(Long id, byte type) {
        return new R<>(feedBackService.updateStatus(id, type));
    }


    @GetMapping("/details")
    @ApiOperation(value = "查询反馈详情", notes = "查询反馈详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "详情标识", required = true)
    })
    public R<FeedBackVo> findFeedBackDetails(@RequestParam("id") Integer id){
        return new R<>(feedBackService.findFeedBackDetails(id));
    }

}
