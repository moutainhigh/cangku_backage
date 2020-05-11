package cn.enn.wise.ssop.service.cms.controller.applet;

import cn.enn.wise.ssop.api.cms.dto.response.KnowledgeAppDTO;
import cn.enn.wise.ssop.api.cms.dto.response.KnowledgeDTO;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.service.KnowledgeService;
import cn.enn.wise.ssop.service.cms.service.VoteService;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 详情APP管理
 */
@RestController
@Api(value = "小程序知识接口", tags = {"小程序知识接口"})
@RequestMapping("/applet/knowledge")
public class KnowledgeAppletController {
    @Autowired
    KnowledgeService knowledgeService;
    @Autowired
    VoteService voteService;
    @Autowired
    RedisUtil redisUtil;


    @ApiOperation(value = "知识详情接口", notes = "根据id获取APP知识详情接口")
    @GetMapping(value = "/detail/{id}")
    public R<KnowledgeAppDTO> getKnowledgeAppletDetailById(@PathVariable Long id) {
        KnowledgeDTO detail = knowledgeService.getKnowledgeDetail(id);
        if(detail==null){
            R.error("没有查到");
        }
        //查询用户是否已点赞
        Boolean flag = knowledgeService.isVote(detail.getId());
        //查询点赞数量
        Long count = knowledgeService.voteCount(detail.getId());
        KnowledgeAppDTO knowledgeAppDTO = new KnowledgeAppDTO();
        BeanUtils.copyProperties(detail,knowledgeAppDTO);
        knowledgeAppDTO.setIsVote(flag);
        knowledgeAppDTO.setVoteCount(count);
        //浏览量加一
        String strategyKey = RedisKey.ServerName + String.format(RedisKey.PAGE_VIEW, CmsEnum.CMS_TYPE_KNOWLEDGE.getValue());
        redisUtil.zZsetByKeyValueAddScore(strategyKey, id);
        return R.success(knowledgeAppDTO);
    }

}



