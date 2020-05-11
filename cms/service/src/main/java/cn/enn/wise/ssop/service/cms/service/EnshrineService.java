package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.EnshrineMapper;
import cn.enn.wise.ssop.service.cms.model.Enshrine;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * @date:2020/4/9
 * @author:hsq
 */
@Service("enshrineService")
@Slf4j
public class EnshrineService extends ServiceImpl<EnshrineMapper, Enshrine> {

    @Autowired
    EnshrineMapper enshrineMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 保持收藏记录
     * @param type
     * @param articleId
     * @param isVote
     * @return
     */

    public Boolean saveEnshrine(Byte type, Long articleId, Boolean isVote) {
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        //判断是否收藏
        if(isVote) {
            Enshrine enshrine = new Enshrine();
            enshrine.setArticleId(articleId);
            enshrine.setType(type);
            enshrine.setMemberId(Long.valueOf(memberId));
            Timestamp doVoteTime = Timestamp.valueOf(LocalDateTime.now());
            enshrine.setEnshrineTime(doVoteTime);
            boolean save ;
            try {
                save = this.save(enshrine);
                if (save) {
                    String voteHashKey = RedisKey.ServerName+String.format(
                            type == 1 ? RedisKey.SCENICS_ENSHRINE : RedisKey.PATH_ENSHRINE, type, articleId);
                    redisUtil.hset(voteHashKey,memberId,doVoteTime);
                    return true;
                }
            } catch (DuplicateKeyException e) {
                log.info("该数据已存在！");
            }
        } else {
            // TODO 删除操作 会拼上租户ID
            int num = enshrineMapper.deleteEnshrine(memberId, articleId, type);
            //Object [] obj={memberId, articleId, type};
            //String sql = " DELETE FROM enshrine WHERE member_id = ? AND article_id = ? AND type = ? ";
            //int update = jdbcTemplate.update(sql, obj);
            if (num > 0) {
                String voteHashKey = RedisKey.ServerName+String.format(
                        type == 1 ? RedisKey.SCENICS_ENSHRINE : RedisKey.PATH_ENSHRINE, type, articleId);
                redisUtil.hdel(voteHashKey, memberId);
                return true;
            }
        }
        return false;
    }

    /**
     * 查询是否收藏
     * @param memberId
     * @param articleId
     * @param type
     * @return
     */
    public Boolean getIsNotEnshrine(String memberId, Long articleId, byte type) {
        String voteHashKey = RedisKey.ServerName+String.format(
                type == 1 ? RedisKey.SCENICS_ENSHRINE : RedisKey.PATH_ENSHRINE, type, articleId);
        Object hget = redisUtil.hget(voteHashKey, memberId);
        if (Objects.nonNull(hget)) {
            return true;
        }
        QueryWrapper<Enshrine> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("article_id", articleId);
        wrapper.eq("type", type);
        Enshrine one = this.getOne(wrapper);
        if (Objects.nonNull(one)) {
            return true;
        }
        return false;
    }

    /**
     * 查询收藏列表
     * @param memberId
     * @param type
     * @return
     */
    public List<Enshrine> getEnshrineList(String memberId,byte type) {

        QueryWrapper<Enshrine> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("type", type);
        List<Enshrine> list = this.list(wrapper);

        return list;
    }

    /**
     * 收藏-初始化时数据存入redis缓存
     */
    //@PostConstruct
    public void init(){
        List<Enshrine> groupType = enshrineMapper.selectList(new LambdaQueryWrapper<Enshrine>()
                .groupBy(Enshrine::getType).isNotNull(Enshrine::getType));

        for (Enshrine en : groupType) {
            String voteHashKey = RedisKey.ServerName+String.format(
                    en.getType() == 1 ? RedisKey.SCENICS_ENSHRINE : RedisKey.PATH_ENSHRINE,
                    en.getType(), en.getArticleId());

            LambdaQueryWrapper<Enshrine> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(Enshrine::getType,Enshrine::getArticleId)
                    .eq(Enshrine::getType, en.getType())
                    .groupBy(Enshrine::getArticleId).isNotNull(Enshrine::getArticleId);
            List<Enshrine> groupByArticle = enshrineMapper.selectList(queryWrapper);

            for (Enshrine art : groupByArticle) {
                List<Enshrine> enshrines = enshrineMapper.selectList(new QueryWrapper<Enshrine>()
                        .eq("type", art.getType())
                        .eq("article_id", art.getArticleId()).isNotNull("member_id"));

                for (Enshrine mem : enshrines) {
                    redisUtil.hset(voteHashKey, String.valueOf(mem.getMemberId()), Timestamp.valueOf(LocalDateTime.now()));
                }
            }
        }
    }

}
