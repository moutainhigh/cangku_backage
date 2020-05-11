package cn.enn.wise.ssop.service.cms.service;


import cn.enn.wise.ssop.api.cms.dto.response.*;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.StrategyMapper;
import cn.enn.wise.ssop.service.cms.model.Strategy;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryOffsetParam;
import cn.enn.wise.uncs.base.pojo.response.QueryOffsetData;
import cn.enn.wise.uncs.common.http.GeneUtil;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;



/**
 * @author hsq
 * 小程序攻略管理
 */
@Service("StrategyAppService")
public class StrategyAppService extends ServiceImpl<StrategyMapper, Strategy> {


    @Autowired
    StrategyMapper strategyMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    VoteService voteService;
    @Autowired
    StrategyAppService strategyAppService;


    /**
     * 初始化时攻略列表数据存入redis缓存
     */
    //@PostConstruct
    public void init() {
        QueryWrapper<Strategy> queryCompany = new QueryWrapper<>();
        List<Strategy> companyIds = strategyMapper.selectList(queryCompany.groupBy("company_id"));
        for (Strategy company : companyIds) {
            QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
            List<Strategy> groupBy = strategyMapper.selectList(queryWrapper
                    .eq("company_id", company.getCompanyId())
                    .groupBy("category_id"));
            for (Strategy i : groupBy) {
                QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
                List<Strategy> strategies = strategyMapper.selectList(wrapper.eq("category_id", i.getCategoryId()));
                for (Strategy str : strategies) {
                    //存入redis
                    syncRedis(str);
                }
            }
        }
    }

    /**
     * 服务初始化和添加修改攻略时 数据同步redis
     * @param strategy
     */
    public void syncRedis(Strategy strategy) {
        Map<String, Object> strategyListMap = new HashMap<>();
        String stategyIdsKey = RedisKey.ServerName +
                String.format(RedisKey.STATEGY_IDS, strategy.getCompanyId(), strategy.getCategoryId());

        redisUtil.zAdd(stategyIdsKey,String.valueOf(strategy.getId()), (double) strategy.getPublishTime().getTime());

        StrategyAppListDTO dto = new StrategyAppListDTO();
        BeanUtils.copyProperties(strategy,dto);
        dto.setArticleId(strategy.getId());
        dto.setMemberId(strategy.getCompanyId());
        dto.setType(Byte.valueOf(strategy.getCategoryId().toString()));
        strategyListMap.put(String.valueOf(strategy.getId()), dto);

        String redisStategyId = RedisKey.ServerName + String.format(RedisKey.STRATEGY_LIST_DTO, strategy.getId());
        redisUtil.hmset(redisStategyId, strategyListMap);

    }


    public Boolean isVote(Long id) {
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        String voteRedisKey = RedisKey.ServerName + String.format(RedisKey.STATEGY_VOTE, id);
        return redisUtil.hget(voteRedisKey,memberId)!=null;
    }


    public Long voteCount(Long id) {
        RedisTemplate<String, Object> redisTemplate = redisUtil.getRedisTemplate();
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String voteRedisKey = RedisKey.ServerName+String.format(RedisKey.STATEGY_VOTE, id);
        Long size = opsForHash.size(voteRedisKey);
        return size;
    }

    /**
     * 根据类别查询攻略列表
     * @return
     */
    public QueryOffsetData<StrategyAppListDTO> getStrategyAppList (Long categoryId, Integer offset, Integer pageSize) {
        String companyId = GeneUtil.GetHttpHeader(request,"company_id");

        QueryOffsetParam param = new QueryOffsetParam(offset, pageSize);
        LambdaQueryWrapper<Strategy> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Strategy::getCategoryId,categoryId)
                .orderByDesc(Strategy::getPublishTime)
                .last("limit "+param.getOffset()+","+param.getPageSize());

        List<Strategy> strategyList = strategyMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(strategyList)){

            List<StrategyAppListDTO> strategyAppListDTOList = strategyList.stream().map(t -> {
                StrategyAppListDTO strategyAppListDTO = new StrategyAppListDTO();
                BeanUtils.copyProperties(t, strategyAppListDTO);
                strategyAppListDTO.setArticleId(t.getId());
                strategyAppListDTO.setMemberId(Long.valueOf(companyId));
                strategyAppListDTO.setType(Byte.valueOf(t.getCategoryId().toString()));

                //查询出后加入到缓存
                syncRedis(t);
                return strategyAppListDTO;
            }).collect(Collectors.toList());

            return new QueryOffsetData<>(strategyAppListDTOList,param.getOffset(),param.getPageSize());
        }

       /* //查询redis，查不到查数据库
        String stategyIdsKey = RedisKey.ServerName + String.format(RedisKey.STATEGY_IDS, companyId, categoryId);
        //取出Zset中的id列表
        Set<Object> idList = redisUtil.getPage(stategyIdsKey,offset,pageSize);

        Iterator<Object> it = idList.iterator();
        QueryOffsetData<StrategyAppListDTO> offsetData = new QueryOffsetData<>();
        List<StrategyAppListDTO> list = new ArrayList<>();
        while(it.hasNext()){
            String stategyId = String.valueOf(it.next());
            String redisStategyId = RedisKey.ServerName + String.format(RedisKey.STRATEGY_LIST_DTO, stategyId);
            StrategyAppListDTO strategyAppListDTO = (StrategyAppListDTO) redisUtil.hmget(redisStategyId).get(stategyId);

            list.add(strategyAppListDTO);

        }
        offsetData.setRecords(list);
        if(CollectionUtils.isEmpty(list)){

        }*/
        return null;
    }

    public List<HomeStrategyListDTO> homeStratoryList() {
        //写死首页攻略分类列表
        List<HomeStrategyListDTO> list = new ArrayList<HomeStrategyListDTO>(){{
            add(new HomeStrategyListDTO(1L,"",
                    "http://travel.enn.cn/group1/M00/01/9C/CiaAUl6ZaJOAJkQVAAxuG5f8EXY802.png",
                    "#民俗","那一世 我翻遍十万大山 不为修来世只为路中能与你相遇转过山,转过水,漫漫转山道上,留下的是最虔诚的寄托"));
            add(new HomeStrategyListDTO(2L,
                    "http://travel.enn.cn/group1/M00/01/9C/CiaAUl6ZWaSALjiEAAALwhO6jkg828.png",
                    "http://travel.enn.cn/group1/M00/01/9D/CiaAUl6ZaNWAGuQsAANKcN2XCys624.png",
                    "美食","一碗酥油茶挑逗你的异域味蕾"));
            add(new HomeStrategyListDTO(3L,
                    "http://travel.enn.cn/group1/M00/01/9C/CiaAUl6ZWX2ALYv5AAAQrA2rMLU881.png",
                    "http://travel.enn.cn/group1/M00/01/9D/CiaAUl6ZaOaAfRYwAAEIX2_4yz4076.png",
                    "文化","传自“东方的荷马史诗”为您讲述格萨尔王的传说"));
        }};
        return list;
    }

    public void assemblyData(QueryOffsetData<StrategyAppListDTO> strategyAppList) {
        String strategyKey = RedisKey.ServerName + String.format(RedisKey.PAGE_VIEW, CmsEnum.CMS_TYPE_STRATEGY.getValue());
        for (StrategyAppListDTO str : strategyAppList.getRecords()) {
            //是否点赞
            Boolean isNotVote = voteService.getIsNotVote(str.getMemberId(), str.getArticleId(), CmsEnum.CMS_TYPE_COMMODITY.getValue());
            str.setIsVote(isNotVote);
            //攻略浏览量
            Double score = redisUtil.getRedisTemplate().opsForZSet().score(strategyKey, String.valueOf(str.getId()));
            str.setViewNumber(score);
            //查询点赞数量
            Long count = strategyAppService.voteCount(str.getId());
            str.setVoteNumber(count);
        }
    }
}
