package cn.enn.wise.ssop.service.cms.service;


import cn.enn.wise.ssop.api.cms.dto.request.KnowledgeSaveParam;
import cn.enn.wise.ssop.api.cms.dto.request.RichText;
import cn.enn.wise.ssop.api.cms.dto.response.KnowledgeDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleKnowledgeDTO;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.CategoryMapper;
import cn.enn.wise.ssop.service.cms.mapper.KnowledgeMapper;
import cn.enn.wise.ssop.service.cms.model.Category;
import cn.enn.wise.ssop.service.cms.model.Knowledge;
import cn.enn.wise.ssop.service.cms.model.Strategy;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.KNOWLEDGE_STATUS_ISENABLE;
import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.KNOWLEDGE_TITLE_ISEXIST;


/**
 * @author shiz
 * 知识管理
 */
@Service("knowledgeService")
public class KnowledgeService extends ServiceImpl<KnowledgeMapper, Knowledge> {

    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    StrategyService strategyService;
    @Autowired
    RedisUtil redisUtil;


    public Long saveKnowledge(KnowledgeSaveParam knowledgeAddParam) {
        String title = knowledgeAddParam.getTitle();
        if(knowledgeAddParam.getId()==null){
            //title查重,断言标题重复
            int count = this.count(new LambdaQueryWrapper<Knowledge>().eq(Knowledge::getTitle, title));
            KNOWLEDGE_TITLE_ISEXIST.assertIsTrue(count==0,title);
        }

        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeAddParam,knowledge);

        String content = JSON.toJSONString(knowledgeAddParam.getContentList());
        knowledge.setContent(content);

        boolean saveOrUpdate = this.saveOrUpdate(knowledge);
        if (saveOrUpdate) {
            KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
            BeanUtils.copyProperties(knowledge,knowledgeDTO);
            String redisStategyId = RedisKey.ServerName + String.format(RedisKey.KNOWLEDGE_DETAIL, knowledge.getId());
            redisUtil.set(redisStategyId, knowledgeDTO);
        }
        return knowledge.getId();
    }

    public QueryData<SimpleKnowledgeDTO> getKnowledgeList(QueryParam queryParam) {

        IPage<SimpleKnowledgeDTO> page = knowledgeMapper.selectListPage(new Page(queryParam.getPageNo(),queryParam.getPageSize()));

        return new QueryData<>(page);
    }

//    @Cacheable(value = "CMS",key = "T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).STATEGY_DETAIL,#id)")
    public KnowledgeDTO getKnowledgerDetail(Long id) {

        Knowledge knowledge = this.getById(id);

        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        if(knowledge==null) return null;
        BeanUtils.copyProperties(knowledge,knowledgeDTO);

        //分类名称
        Category category = categoryMapper.selectById(knowledge.getCategoryId());
        if(category!=null){
            String categoryName = category.getCategoryName();
            knowledgeDTO.setCategoryName(categoryName);
        }


        List<RichText> contentList = new ArrayList<>();
        if(!Strings.isEmpty(knowledge.getContent())){
            contentList = JSON.parseArray(knowledge.getContent(),RichText.class);
        }

        knowledgeDTO.setContentList(contentList);

        return knowledgeDTO;
    }

    public Boolean delKnowledge(Long id) {
        Knowledge knowledge = this.getById(id);
        //断言知识状态是否开启，开启不能删除
        KNOWLEDGE_STATUS_ISENABLE.assertIsTrue(knowledge.getState()!=1,id);
        boolean remove = this.removeById(id);
        if (remove) {
            String redisStategyId = RedisKey.ServerName + String.format(RedisKey.KNOWLEDGE_DETAIL, id);
            redisUtil.del(redisStategyId);
        }
        return remove;
    }

    public Boolean editState(Long id, byte state) {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(id);knowledge.setState(state);
        return this.updateById(knowledge);
    }

    public List<SelectData> getAllKnowledgeSelect() {

        List<Knowledge> list = this.list();
        List<SelectData> dataList = list.stream().map(knowledge -> SelectData.data(knowledge.getTitle(), knowledge.getId().toString())).collect(Collectors.toList());

        return dataList;
    }

    @Cacheable(value="CMS", key="T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).KNOWLEDGE_DETAIL,#id)")
    public KnowledgeDTO getKnowledgeDetail(Long id) {
        return getKnowledgeDetailById(id);
    }

    public KnowledgeDTO getKnowledgeDetailById(Long id) {
        Knowledge knowledge = this.getById(id);

        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        if(knowledge==null) return knowledgeDTO;
        BeanUtils.copyProperties(knowledge,knowledgeDTO);

        //分类名称
        Category category = categoryMapper.selectById(knowledgeDTO.getCategoryId());
        if(category!=null){
            String categoryName = category.getCategoryName();
            knowledgeDTO.setCategoryName(categoryName);
        }

        List<RichText> contentList = new ArrayList<>();
        if(!Strings.isEmpty(knowledge.getContent())){
            contentList = JSON.parseArray(knowledge.getContent(),RichText.class);
        }

        knowledgeDTO.setContentList(contentList);
        return knowledgeDTO;
    }

    public Boolean isVote(Long id) {
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        String voteRedisKey = RedisKey.ServerName+String.format(RedisKey.KNOWLEDGE_VOTE, id);
        return redisUtil.hget(voteRedisKey,memberId)!=null;
    }


    public Long voteCount(Long id) {
        RedisTemplate<String, Object> redisTemplate = redisUtil.getRedisTemplate();
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String voteRedisKey = RedisKey.ServerName+String.format(RedisKey.KNOWLEDGE_VOTE, id);
        Long size = opsForHash.size(voteRedisKey);
        return size;
    }

    public Boolean updateShareNumber(Byte type, Long articleId) {

        if (type.equals(CmsEnum.CMS_TYPE_STRATEGY.getValue())) {
            Strategy strategy = strategyService.getById(articleId);
            if (Objects.isNull(strategy)) return false;
            UpdateWrapper<Strategy> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", articleId);
            wrapper.set("share_number", strategy.getShareNumber() + 1);
            return strategyService.update(wrapper);

        } else if (type.equals(CmsEnum.CMS_TYPE_KNOWLEDGE.getValue())) {
            Knowledge knowledge = this.getById(articleId);
            if (Objects.isNull(knowledge)) return false;
            UpdateWrapper<Knowledge> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", articleId);
            wrapper.set("share_number", knowledge.getShareNumber() + 1);
            return this.update(wrapper);
        }

        return false;
    }
}
