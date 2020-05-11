package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.KnowledgeBo;
import cn.enn.wise.platform.mall.bean.bo.WeatherSun;
import cn.enn.wise.platform.mall.bean.param.AddKnowledgeParams;
import cn.enn.wise.platform.mall.bean.param.KnowledgeParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.KnowledgeVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.constants.KnowledgeType;
import cn.enn.wise.platform.mall.mapper.KnowledgeMapper;
import cn.enn.wise.platform.mall.mapper.WeatherSunMapper;
import cn.enn.wise.platform.mall.service.KnowledgeService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.stream.Collectors;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:11
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, KnowledgeBo> implements KnowledgeService {

    @Autowired
    WeatherSunMapper weatherSunMapper;

    @Override
    public ResponseEntity<ResPageInfoVO<List<KnowledgeVo>>> listKnowledge(ReqPageInfoQry<KnowledgeParams> pageQry) {

        QueryWrapper<KnowledgeBo> wrapper = new QueryWrapper<>();

        KnowledgeParams params = pageQry.getReqObj();

        wrapper.eq("is_delete", 2);

        if (params.getTitle() != null && !"".equals(params.getTitle())) {
            wrapper.like("title", params.getTitle());
        }
        if (params.getKnowledgeType() != null && params.getKnowledgeType() != 0) {
            wrapper.eq("knowledge_type", params.getKnowledgeType());
        }
        if (params.getBusinessCategory() != null && params.getBusinessCategory() != 0) {
            wrapper.like("business_category", params.getBusinessCategory());
        }
        if (params.getStartTime() != null && !"".equals(params.getStartTime()) && !"".equals(params.getEndTime()) && params.getEndTime() != null) {
            wrapper.gt("create_time", params.getStartTime());
            wrapper.lt("create_time", params.getEndTime());
        }
        wrapper.orderByDesc("id");

        // 分页参数
        Page<KnowledgeBo> pageParam = new Page<>(pageQry.getPageNum(), pageQry.getPageSize());
        // 根据条件查询数据库
        IPage<KnowledgeBo> dbPageInfo = this.page(pageParam, wrapper);
        ResPageInfoVO<List<KnowledgeVo>> resPageInfoVO = new ResPageInfoVO<>();
        resPageInfoVO.setPageNum(dbPageInfo.getCurrent());
        resPageInfoVO.setPageSize(dbPageInfo.getSize());
        resPageInfoVO.setTotal(dbPageInfo.getTotal());
        resPageInfoVO.setRecords(getKnowledgeVo(dbPageInfo.getRecords()));

        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "列表", resPageInfoVO);
    }

    @Override
    public ResponseEntity<KnowledgeBo> addKnowledge(AddKnowledgeParams params) {

        KnowledgeBo knowledgeBo = new KnowledgeBo();

        // 编辑,如果类型改变,编号重新计算
        if (params.getId() != 0) {
            knowledgeBo = this.baseMapper.selectById(params.getId());
            knowledgeBo.setAnswer(params.getAnswer());
            knowledgeBo.setTitle(params.getTitle());
            knowledgeBo.setAttachment(params.getAttachment());
            knowledgeBo.setBusinessCategory(params.getBusinessCategory());
            knowledgeBo.setCreateBy(-1L);
            knowledgeBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledgeBo.setKnowledgeType(params.getKnowledgeType());
            knowledgeBo.setUpdateBy(-1L);
            knowledgeBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            this.baseMapper.updateById(knowledgeBo);
        }else{
            knowledgeBo.setAnswer(params.getAnswer());
            knowledgeBo.setTitle(params.getTitle());
            knowledgeBo.setAttachment(params.getAttachment());
            knowledgeBo.setBusinessCategory(params.getBusinessCategory());
            String code = getCode(params.getBusinessCategory());
            knowledgeBo.setCode(code);
            knowledgeBo.setCreateBy(-1L);
            knowledgeBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledgeBo.setKnowledgeType(params.getKnowledgeType());
            knowledgeBo.setUpdateBy(-1L);
            knowledgeBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            knowledgeBo.setIsDelete(new Byte("2"));
            this.baseMapper.insert(knowledgeBo);
        }
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", knowledgeBo);
    }

    /**
     * 获取编号
     *
     * @param businessCategory
     * @return
     */
    private String getCode(Integer businessCategory) {
        QueryWrapper<KnowledgeBo> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_delete", 2);
        wrapper.eq("business_category", businessCategory);
        Page<KnowledgeBo> page = new Page<>(1, 10);
        IPage<KnowledgeBo> iPage = this.page(page, wrapper);
        String str = String.format("%03d", iPage.getTotal() + 1);
        String code = businessCategory + str;
        return code;
    }

    @Override
    public ResponseEntity<KnowledgeBo> deleteKnowledge(List<Long> params) {
        for (Long id : params) {
            KnowledgeBo knowledgeBo = this.getById(id);
            Byte isDelete = new Byte("1");
            knowledgeBo.setIsDelete(isDelete);
            this.updateById(knowledgeBo);
        }
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", null);
    }

    @Override
    public ResponseEntity<KnowledgeVo> detailKnowledge(Long id) {

        KnowledgeBo knowledgeBo = this.getById(id);
        KnowledgeVo knowledgeVo = getVo(knowledgeBo);
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", knowledgeVo);
    }

    @Override
    public ResponseEntity<List<KnowledgeBo>> findBusinessCategory(Integer businessType) {
        QueryWrapper<KnowledgeBo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 2);
        List<KnowledgeBo> knowledgeBos = baseMapper.selectList(wrapper);
        knowledgeBos.stream().forEach(cl ->{
            KnowledgeType parse = KnowledgeType.parse(cl.getBusinessCategory());
            cl.setBusinessName(parse.getText());
        });
        KnowledgeBo knowledgeBo1 = knowledgeBos.stream().filter(e -> e != null).max(Comparator.comparingInt(KnowledgeBo::getBusinessCategory)).orElse(null);
        int businessTypes = knowledgeBo1.getBusinessCategory() + 1;

        // TODO 上线前修改
        KnowledgeBo knowledgeBo = new KnowledgeBo();
        knowledgeBo.setAnswer("0779-6015900");
        knowledgeBo.setBusinessCategory(businessTypes);
        knowledgeBo.setBusinessName("一键求助");
        knowledgeBo.setId(-1L);
        knowledgeBos.add(knowledgeBo);
        List<KnowledgeBo> collect = knowledgeBos.stream().sorted(Comparator.comparing(KnowledgeBo::getBusinessCategory)).collect(Collectors.toList());
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", collect);

    }

    @Override
    public String[] getWeatherByDate(String Date) {
        QueryWrapper<WeatherSun> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        WeatherSun weatherSun = weatherSunMapper.selectOne(wrapper);
        String forecast = weatherSun.getForecast();
        String[] forecasts=forecast.split(";");
        for (String item:forecasts) {
            String[] items  = item.split(",");
            if(Date.equals(items[0])){
                return items;
            }
        }
        return null;
    }


    /**
     * 获取ListVo
     *
     * @param records
     * @return
     */
    private List<KnowledgeVo> getKnowledgeVo(List<KnowledgeBo> records) {

        List<KnowledgeVo> knowledgeVos = new ArrayList<>();
        for (KnowledgeBo knowledgeBo : records) {
            KnowledgeVo knowledgeVo = getVo(knowledgeBo);
            knowledgeVos.add(knowledgeVo);
        }
        return knowledgeVos;
    }

    /**
     * 获取Vo
     *
     * @param knowledgeBo
     * @return
     */
    private KnowledgeVo getVo(KnowledgeBo knowledgeBo) {
        KnowledgeVo knowledgeVo = new KnowledgeVo();
        knowledgeVo.setAnswer(knowledgeBo.getAnswer());
        knowledgeVo.setBusinessCategory(knowledgeBo.getBusinessCategory());
        knowledgeVo.setCode(knowledgeBo.getCode());
        knowledgeVo.setCreateBy(knowledgeBo.getCreateBy());
        knowledgeVo.setCreateTime(knowledgeBo.getCreateTime());
        knowledgeVo.setId(knowledgeBo.getId());
        knowledgeVo.setKnowledgeType(knowledgeBo.getKnowledgeType());
        knowledgeVo.setTitle(knowledgeBo.getTitle());
        knowledgeVo.setAttachment(knowledgeBo.getAttachment());
        return knowledgeVo;
    }
}
