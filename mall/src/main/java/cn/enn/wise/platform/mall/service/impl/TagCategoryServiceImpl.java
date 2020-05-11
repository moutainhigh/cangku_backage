package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.bo.TagCategoryBo;
import cn.enn.wise.platform.mall.bean.bo.TagGoodsBo;
import cn.enn.wise.platform.mall.bean.bo.TagProjectBo;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.TagCategoryService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Service("tagCategoryService")
@DS("slave_1")
public class TagCategoryServiceImpl extends ServiceImpl<TagCategoryMapper, TagCategoryBo> implements TagCategoryService {

    @Resource
    TagMapper tagMapper;

    @Resource
    TagGoodsMapper tagGoodsMapper;

    @Resource
    TagProjectMapper tagProjectMapper;

    @Resource
    TagAppletsMapper tagAppletsMapper;

    @Override
    public List<TagVo> getTagList(String type) {

        List<TagVo> tagList = new ArrayList<>();

        List<TagCategoryBo> tagCategoryBoList = this.list();

        for(TagCategoryBo bo : tagCategoryBoList){
            TagVo tagVo =new TagVo();
            tagVo.setCategory(bo);
            QueryWrapper<TagBo> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id",bo.getId());
            wrapper.eq("type",type);
            tagVo.setTagList( tagMapper.selectList(wrapper));

            tagList.add(tagVo);
        }
        return tagList;
    }

    @Override
    public List<TagGoodsVo> getGoodsTagList(Long goodsId) {


        List<TagGoodsVo> goodsVoList = new ArrayList<>();
        //获取标签G
        List<TagVo> tagVoList = getTagList("2");
        //获取项目标签
        QueryWrapper<TagGoodsBo> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        List<TagGoodsBo> tagGoodsBoList = tagGoodsMapper.selectList(wrapper);

        for(TagVo vo :tagVoList){
            TagGoodsVo goods = new TagGoodsVo();
            goods.setCategory(vo.getCategory());

            List<TagExtendVo> extendVoList = new ArrayList<>();
            for (TagBo tag : vo.getTagList()) {

                TagExtendVo tagExtendVo = new TagExtendVo();

                tagExtendVo.setName(tag.getName());
                tagExtendVo.setTagId(tag.getId());


                for (TagGoodsBo projectBo : tagGoodsBoList) {
                    if (projectBo.getTagId().equals(tag.getId())) {
                        tagExtendVo.setCheckId(projectBo.getId());
                        tagExtendVo.setBusinessId(projectBo.getGoodsId());
                    }
                }
                extendVoList.add(tagExtendVo);
            }

            goods.setTagList(extendVoList);
            goodsVoList.add(goods);
        }

        return goodsVoList;
    }



    @Override
    public List<TagProjectVo> getProjectTagList(Long projectId) {

        List<TagProjectVo> tagProjectVoList = new ArrayList<>();

        //获取标签
        List<TagVo> tagVoList = getTagList("1");

        //获取项目标签
        QueryWrapper<TagProjectBo> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        List<TagProjectBo> tagProjectBoList = tagProjectMapper.selectList(wrapper);

        // 组装返回值
        for(TagVo vo :tagVoList){
            TagProjectVo project = new TagProjectVo();
            project.setCategory(vo.getCategory());

            List<TagExtendVo> extendVoList = new ArrayList<>();
            for (TagBo tag : vo.getTagList()) {

                TagExtendVo tagExtendVo = new TagExtendVo();

                tagExtendVo.setTagId(tag.getId());
                tagExtendVo.setName(tag.getName());

                for (TagProjectBo projectBo : tagProjectBoList) {
                    if (projectBo.getTagId().equals(tag.getId())) {
                        tagExtendVo.setCheckId(projectBo.getId());
                        tagExtendVo.setBusinessId(projectBo.getProjectId());
                    }
                }
                extendVoList.add(tagExtendVo);
            }

            project.setTagList(extendVoList);
            tagProjectVoList.add(project);
        }
        return tagProjectVoList;
    }

    @Override
    public List<TagAppletsVo> getTagVoList() {
        return tagAppletsMapper.getTagList();
    }

    @Override
    public TagAppletsVo getTagVoById(String categoryId) {
        List<TagAppletsVo> tagList = tagAppletsMapper.getTagList();
        if(CollectionUtils.isNotEmpty(tagList)){
            for (TagAppletsVo tagAppletsVo : tagList) {
                if(categoryId.equals(tagAppletsVo.getId())){
                    return tagAppletsVo;
                }
            }
        }
        return new TagAppletsVo();
    }

    @Override
    public List<TagCategoryVo> getTagCategoryList() {
        List<TagCategoryVo> tagCategoryVoList = new ArrayList<>();
        List<TagCategoryBo> tagCategoryBoList  = this.list();
        for(TagCategoryBo tagCategoryBo:tagCategoryBoList){
            TagCategoryVo tagCategoryVo  = new TagCategoryVo();
            tagCategoryVo.setId(tagCategoryBo.getId());
            tagCategoryVo.setName(tagCategoryBo.getName());
            tagCategoryVoList.add(tagCategoryVo);
        }
        return tagCategoryVoList;

    }
}
