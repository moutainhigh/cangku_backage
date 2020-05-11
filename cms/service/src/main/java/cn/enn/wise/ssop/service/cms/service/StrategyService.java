package cn.enn.wise.ssop.service.cms.service;


import cn.enn.wise.ssop.api.authority.dto.response.DistributionUserDTO;
import cn.enn.wise.ssop.api.authority.facade.SystemUserFacade;
import cn.enn.wise.ssop.api.cms.dto.request.RichText;
import cn.enn.wise.ssop.api.cms.dto.request.StrategySaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleStrategyDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyDTO;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.CategoryMapper;
import cn.enn.wise.ssop.service.cms.mapper.StrategyMapper;
import cn.enn.wise.ssop.service.cms.model.Category;
import cn.enn.wise.ssop.service.cms.model.Strategy;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryOffsetParam;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.QueryOffsetData;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.common.ByteEnum;
import cn.enn.wise.uncs.common.http.GeneUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.SATEGORY_STATUS_ISENABLE;
import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.SATEGORY_TITLE_ISEXIST;


/**
 * @author shiz
 * 攻略管理
 */
@Service("strategyService")
public class StrategyService extends ServiceImpl<StrategyMapper, Strategy> {


    @Autowired
    StrategyMapper strategyMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    StrategyAppService strategyAppService;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SystemUserFacade systemUserFacade;


    public Long saveStrategy(StrategySaveParam strategyAddParam) {

        String title = strategyAddParam.getTitle();

        if(strategyAddParam.getId()==null){
            //title查重,断言标题重复
            int count = this.count(new LambdaQueryWrapper<Strategy>().eq(Strategy::getTitle, title));
            SATEGORY_TITLE_ISEXIST.assertIsTrue(count==0,title);
        }

        Strategy strategy = new Strategy();
        BeanUtils.copyProperties(strategyAddParam,strategy, GeneUtil.getNullPropertyNames(strategyAddParam));

        String content = JSON.toJSONString(strategyAddParam.getContentList());
        strategy.setContent(content);
        boolean flag = this.saveOrUpdate(strategy);

        //数据同步到redis
        if(flag){
            strategyAppService.syncRedis(strategy);
        }

        return strategy.getId();
    }

    public QueryData<SimpleStrategyDTO> getStrategyList(QueryParam queryParam) {

        IPage<SimpleStrategyDTO> page = strategyMapper.getStrategyList(new Page<>(queryParam.getPageNo(),queryParam.getPageSize()));

        return new QueryData<>(page);
    }
    private SimpleStrategyDTO toSimpleDTO(Strategy strategy) {
        SimpleStrategyDTO dto = new SimpleStrategyDTO();
        BeanUtils.copyProperties(strategy,dto);
        return dto;
    }

    @Cacheable(value="CMS", key="T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).STATEGY_DETAIL,#id)")
    public StrategyDTO getStrategyrDetailByApp(Long id) {
        StrategyDTO strategyDTO = getStrateByDB(id);

        return strategyDTO;
    }

    public StrategyDTO getStrategyrDetail(Long id) {
        StrategyDTO strategyDTO = getStrateByDB(id);

        return strategyDTO;
    }

    private StrategyDTO getStrateByDB(Long id) {
        Strategy strategy = this.getById(id);

        StrategyDTO strategyDTO = new StrategyDTO();
        if(strategy==null) return strategyDTO;
        BeanUtils.copyProperties(strategy,strategyDTO);

        //分类名称
        Category category = categoryMapper.selectById(strategyDTO.getCategoryId());
        if(category!=null){
            String categoryName = category.getCategoryName();
            strategyDTO.setCategoryName(categoryName);
        }

        List<RichText> contentList = new ArrayList<>();
        if(!Strings.isEmpty(strategy.getContent())){
            contentList = JSON.parseArray(strategy.getContent(),RichText.class);
        }

        strategyDTO.setContentList(contentList);
        return strategyDTO;
    }



    public Boolean delStrategy(Long id) {
        Strategy strategy = this.getById(id);
        //断言攻略状态是否开启，开启不能删除
        SATEGORY_STATUS_ISENABLE.assertIsTrue(strategy.getState()!=1,id);
        boolean remove = this.removeById(id);
        if (remove) {
            String redisStategyId = RedisKey.ServerName + String.format(RedisKey.STATEGY_DETAIL, id);
            redisUtil.del(redisStategyId);
        }
        return remove;
    }

    public Boolean editState(Long id, byte state) {
        Strategy strategy = new Strategy();
        strategy.setId(id);
        strategy.setState(state);
        if(state == ByteEnum.byte1){
            strategy.setPublishTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        return this.updateById(strategy);
    }




    public void test() {
        R<List<DistributionUserDTO>> info = systemUserFacade.info(new Long[]{1L});
//        channelFacade.channelDetail(1L);
        System.out.println(info);
    }


    /**
     * 管理端分页例子
     * QueryParam 可以被继承 加参数
     * @param queryParam
     * @return
     */
    public QueryData<Strategy> managePageDemo(QueryParam queryParam){

        //单表
        Page page = new Page(queryParam.getPageNo(), queryParam.getPageSize());
        Page single = this.page(page);

        //自定义sql,  注意page必须第一个参数
        String name="demo";
        IPage<Strategy> coustomQuery = strategyMapper.selectManageList(page, name);
        return new QueryData<>(coustomQuery);
    }
    /**
     * 小程序端分页例子, 使用offset,size
     * QueryParam 可以被继承 加参数
     * @param queryOffsetParam
     * @return
     */
    public QueryOffsetData<Strategy> managePageDemo(QueryOffsetParam queryOffsetParam){
        Integer offset = queryOffsetParam.getOffset();
        Integer pageSize = queryOffsetParam.getPageSize();

        //单表
        QueryOffsetParam param = new QueryOffsetParam(offset, pageSize);
        LambdaQueryWrapper<Strategy> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("limit "+param.getOffset()+","+param.getPageSize());

        List<Strategy> single = this.list(wrapper);

        //自定义sql,  自己实现 limit 分页
        String name="demo";
        List<Strategy> coustomQuery = strategyMapper.selectAppletList(name,offset,pageSize);

        return new QueryOffsetData<>(coustomQuery,offset,pageSize);
    }
}
