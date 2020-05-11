package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.AddKnowledgeParams;
import cn.enn.wise.ssop.api.cms.dto.request.oldmall.*;
import cn.enn.wise.ssop.service.cms.consts.KnowledgeType;
import cn.enn.wise.ssop.service.cms.mapper.QAMapper;
import cn.enn.wise.ssop.service.cms.model.QAKnowledge;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:11
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
public class QAService extends ServiceImpl<QAMapper, QAKnowledge> {

    

    
    public R<ResPageInfoVO<List<KnowledgeVo>>> listKnowledge(ReqPageInfoQry<KnowledgeParams> pageQry) {

        QueryWrapper<QAKnowledge> wrapper = new QueryWrapper<>();

        KnowledgeParams params = pageQry.getReqObj();

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
        Page<QAKnowledge> pageParam = new Page<>(pageQry.getPageNum(), pageQry.getPageSize());
        // 根据条件查询数据库
        IPage<QAKnowledge> dbPageInfo = this.page(pageParam, wrapper);
        ResPageInfoVO<List<KnowledgeVo>> resPageInfoVO = new ResPageInfoVO<>();
        resPageInfoVO.setPageNum(dbPageInfo.getCurrent());
        resPageInfoVO.setPageSize(dbPageInfo.getSize());
        resPageInfoVO.setTotal(dbPageInfo.getTotal());
        resPageInfoVO.setRecords(getKnowledgeVo(dbPageInfo.getRecords()));

        return R.success(resPageInfoVO);
    }

    public R<QAKnowledge> addKnowledge(AddKnowledgeParams params) {

        QAKnowledge knowledgeBo = new QAKnowledge();

        // 编辑,如果类型改变,编号重新计算
        if (params.getId() != 0) {
            knowledgeBo = this.baseMapper.selectById(params.getId());
            knowledgeBo.setAnswer(params.getAnswer());
            knowledgeBo.setTitle(params.getTitle());
            knowledgeBo.setAttachment(params.getAttachment());
            knowledgeBo.setBusinessCategory(params.getBusinessCategory());
            knowledgeBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledgeBo.setKnowledgeType(params.getKnowledgeType());
            knowledgeBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            this.baseMapper.updateById(knowledgeBo);
        }else{
            knowledgeBo.setAnswer(params.getAnswer());
            knowledgeBo.setTitle(params.getTitle());
            knowledgeBo.setAttachment(params.getAttachment());
            knowledgeBo.setBusinessCategory(params.getBusinessCategory());
            String code = getCode(params.getBusinessCategory());
            knowledgeBo.setCode(code);
            knowledgeBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledgeBo.setKnowledgeType(params.getKnowledgeType());
            knowledgeBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            this.baseMapper.insert(knowledgeBo);
        }
        return R.success(knowledgeBo);
    }

    /**
     * 获取编号
     *
     * @param businessCategory
     * @return
     */
    private String getCode(Integer businessCategory) {
        QueryWrapper<QAKnowledge> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_delete", 2);
        wrapper.eq("business_category", businessCategory);
        Page<QAKnowledge> page = new Page<>(1, 10);
        IPage<QAKnowledge> iPage = this.page(page, wrapper);
        String str = String.format("%03d", iPage.getTotal() + 1);
        String code = businessCategory + str;
        return code;
    }

    public R<QAKnowledge> deleteKnowledge(List<Long> params) {
        for (Long id : params) {
            this.removeById(id);
        }
        return R.success(null);
    }

    public R<KnowledgeVo> detailKnowledge(Long id) {

        QAKnowledge knowledgeBo = this.getById(id);
        KnowledgeVo knowledgeVo = getVo(knowledgeBo);
        return R.success(knowledgeVo);
    }

    public R<List<QAKnowledge>> findBusinessCategory(Integer businessType) {
        QueryWrapper<QAKnowledge> wrapper = new QueryWrapper<>();
        List<QAKnowledge> knowledgeBos = baseMapper.selectList(wrapper);
        knowledgeBos.stream().forEach(cl ->{
            KnowledgeType parse = KnowledgeType.parse(cl.getBusinessCategory());
            cl.setBusinessName(parse.getText());
        });
        QAKnowledge knowledgeBo1 = knowledgeBos.stream().filter(e -> e != null).max(Comparator.comparingInt(QAKnowledge::getBusinessCategory)).orElse(null);
        int businessTypes = knowledgeBo1.getBusinessCategory() + 1;

        // TODO 上线前修改
        QAKnowledge knowledgeBo = new QAKnowledge();
        knowledgeBo.setAnswer("0779-6015900");
        knowledgeBo.setBusinessCategory(businessTypes);
        knowledgeBo.setBusinessName("一键求助");
        knowledgeBo.setId(-1L);
        knowledgeBos.add(knowledgeBo);
        List<QAKnowledge> collect = knowledgeBos.stream().sorted(Comparator.comparing(QAKnowledge::getBusinessCategory)).collect(Collectors.toList());
        return R.success(collect);

    }


    /**
     * 获取ListVo
     *
     * @param records
     * @return
     */
    private List<KnowledgeVo> getKnowledgeVo(List<QAKnowledge> records) {

        List<KnowledgeVo> knowledgeVos = new ArrayList<>();
        for (QAKnowledge knowledgeBo : records) {
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
    private KnowledgeVo getVo(QAKnowledge knowledgeBo) {
        KnowledgeVo knowledgeVo = new KnowledgeVo();
        knowledgeVo.setAnswer(knowledgeBo.getAnswer());
        knowledgeVo.setBusinessCategory(knowledgeBo.getBusinessCategory());
        knowledgeVo.setCode(knowledgeBo.getCode());
        knowledgeVo.setCreateTime(knowledgeBo.getCreateTime());
        knowledgeVo.setId(knowledgeBo.getId());
        knowledgeVo.setKnowledgeType(knowledgeBo.getKnowledgeType());
        knowledgeVo.setTitle(knowledgeBo.getTitle());
        knowledgeVo.setAttachment(knowledgeBo.getAttachment());
        return knowledgeVo;
    }
}
