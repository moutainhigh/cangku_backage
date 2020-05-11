package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.AddKnowledgeParams;
import cn.enn.wise.ssop.api.cms.dto.request.oldmall.*;
import cn.enn.wise.ssop.service.cms.model.QAKnowledge;
import cn.enn.wise.ssop.service.cms.service.QAService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/7
 */
@RestController
@RequestMapping("/qa")
@Api(value = "知识库", tags = {"知识库管理"})
public class QaController {


    @Resource
    QAService qAService;

    /**
     * 根据条件查询知识列表
     *
     * @param pageQry 分页查询条件/数据筛选条件
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询商品列表", notes = "根据条件查询商品列表")
    public R<ResPageInfoVO<List<KnowledgeVo>>> listKnowledge(@RequestBody ReqPageInfoQry<KnowledgeParams> pageQry) {
       return qAService.listKnowledge(pageQry);
    }


    /**
     * 添加知识库
     * @param params 添加知识库
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加知识库", notes = "添加知识库")
    public R<QAKnowledge> addKnowledge(@RequestBody AddKnowledgeParams params) {
        return qAService.addKnowledge(params);
    }

    /**
     * 删除知识库
     * @param params 删除知识库
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除知识库", notes = "删除知识库")
    public R deleteKnowledge(@RequestBody List<Long> params) {
        return qAService.deleteKnowledge(params);
    }

    /**
     * 详情
     * @param id 详情
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "详情", notes = "详情")
    public R<KnowledgeVo> detailKnowledge(@RequestBody Long id) {
        return qAService.detailKnowledge(id);
    }

    /**
     * 小程序问题列表
     * @param
     * @return
     */
    @RequestMapping(value = "/ai", method = RequestMethod.GET)
    @ApiOperation(value = "智能小新问题列表", notes = "智能小新问题列表")
    public R<List<QAKnowledge>> findBusinessCategory(Integer businessType) {
        return qAService.findBusinessCategory(businessType);
    }

}
