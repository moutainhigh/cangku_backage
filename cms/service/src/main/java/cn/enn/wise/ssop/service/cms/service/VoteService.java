package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.VoteMapper;
import cn.enn.wise.ssop.service.cms.model.Vote;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @date:2020/4/2
 * @author:hsq
 */
@Service("voteService")
public class VoteService extends ServiceImpl<VoteMapper, Vote> {
    @Autowired
    VoteMapper voteMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 保存点赞记录并放到redis里
     * @param type
     * @param articleId
     * @param isVote
     * @return
     */
    public Boolean saveVote(Byte type, Long articleId, Boolean isVote) {
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        //判断是否点赞
        if(isVote){
            Timestamp doVoteTime = Timestamp.valueOf(LocalDateTime.now());
            Vote vote = new Vote();

            vote.setArticleId(articleId);
            vote.setMemberId(Long.valueOf(memberId));
            vote.setType(type);
            vote.setVoteTime(doVoteTime);

            boolean isSave = this.save(vote);
            if(isSave){
                String voteHashKey = RedisKey.ServerName+String.format(
                        type == 1 ? RedisKey.STATEGY_VOTE : RedisKey.KNOWLEDGE_VOTE, vote.getArticleId());
                redisUtil.hset(voteHashKey,memberId,doVoteTime);
                return true;
            }
        }else{
            QueryWrapper<Vote> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("member_id", memberId);
            queryWrapper.eq("article_id", articleId);
            queryWrapper.eq("type", type);
            Vote vote =  voteMapper.selectOne(queryWrapper);
            if (Objects.isNull(vote)) return false;
            int num = voteMapper.deleteVoteById(vote.getId());
            if(num >0){
                String voteHashKey = RedisKey.ServerName+String.format(
                        type == 1 ? RedisKey.STATEGY_VOTE : RedisKey.KNOWLEDGE_VOTE, vote.getArticleId());
                redisUtil.hdel(voteHashKey,memberId);
                return true;
            }
        }
        return false;
    }


    /**
     * 点赞-初始化时数据存入redis缓存
     */
    //@PostConstruct
    public void init(){
        //1 点赞所有文章id
        List<Object> articleIdObjList = voteMapper.selectObjs(new LambdaQueryWrapper<Vote>().groupBy(Vote::getArticleId));
        for (Object obj : articleIdObjList) {
            Long articleId = (Long) obj;

            //文章下的点赞数据
            LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(Vote::getMemberId,Vote::getVoteTime);
            List<Map<String, Object>> voteList = voteMapper.selectMaps(wrapper);

            HashMap<String, Object> redisHashMap = new HashMap<>();
            for (Map<String, Object> map : voteList) {
                String memberId = String.valueOf(map.get("member_id"));
                Object voteTime = map.get("vote_time");
                redisHashMap.put(memberId,voteTime);
            }
            String stategyVoteKey = RedisKey.ServerName+String.format(RedisKey.STATEGY_VOTE, articleId);
            redisUtil.hmset(stategyVoteKey,redisHashMap);
        }
    }

    /**
     * 查询是否 点赞 收藏
     * @param memberId
     * @param articleId
     * @param type
     * @return
     */
    public Boolean getIsNotVote(Long memberId, Long articleId, byte type) {
        String voteHashKey = RedisKey.ServerName+String.format(
                type == 1 ? RedisKey.STATEGY_VOTE : RedisKey.KNOWLEDGE_VOTE, articleId);
        Object hget = redisUtil.hget(voteHashKey, String.valueOf(memberId));
        if (Objects.nonNull(hget)) {
            return true;
        }
        QueryWrapper<Vote> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId).eq("article_id", articleId).eq("type", type);
        Vote vote = voteMapper.selectOne(wrapper);
        if (Objects.nonNull(vote)) {
            return true;
        }
        return false;
    }

}
