package cn.enn.wise.ssop.service.cms.service;


import cn.enn.wise.ssop.api.cms.dto.request.CategoryParam;
import cn.enn.wise.ssop.api.cms.dto.response.CategoryDTO;
import cn.enn.wise.ssop.api.cms.dto.response.ContentListDTO;
import cn.enn.wise.ssop.service.cms.mapper.CategoryMapper;
import cn.enn.wise.ssop.service.cms.mapper.ContentMapper;
import cn.enn.wise.ssop.service.cms.mapper.ScenicsInfoMapper;
import cn.enn.wise.ssop.service.cms.model.Category;
import cn.enn.wise.ssop.service.cms.model.Strategy;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.CATEGORY_ASSOCIATED_ISEXIST;
import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.CATEGORY_NAME_ISEXIST;


/**
 * @author shiz
 * 内容管理
 */
@Service("contentService")
@Slf4j
public class ContentService{

    @Autowired
    ContentMapper contentMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ScenicsInfoMapper scenicsInfoMapper;

    public List<SelectData> getScenicAreaSelectList() {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        List<SelectData> list = scenicsInfoMapper.selectScenicAreaSelectList(companyId);
        return list;
    }

    @Transactional
    public Boolean delCategory(Long id) {
        int count = categoryMapper.selectAssociatedCount(id);
        if(count>0){
            return false;
        }else{
            categoryMapper.deleteById(id);
            return true;
        }
    }


    @Service("categoryService") class CategoryService extends ServiceImpl<CategoryMapper,Category>{}
    @Autowired
    CategoryService categoryService;


    public QueryData<ContentListDTO> getCmsContentList(String searchText, Integer typeId, Integer pageNo, Integer pageSize) {
        searchText = searchText.trim();
        return new QueryData<>(contentMapper.selectContentListByTextAndTypeId(new Page(pageNo,pageSize),searchText,typeId));
    }

    public List<CategoryDTO> getAllCMSCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //先id asc 排序， 后sort desc排序。
        wrapper.orderByAsc(Category::getId);
        wrapper.orderByDesc(Category::getSort);

        List<Category> categories = categoryMapper.selectList(wrapper);

        List<CategoryDTO> categoryDTOList = categories.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);
            return categoryDTO;
        }).collect(Collectors.toList());
        return categoryDTOList;
    }

    @Transactional
    public Boolean saveAllCMSCategory(List<CategoryParam> categoryParamList) {
        ArrayList<Long> ids = new ArrayList<>();
        List<Category> beanList = categoryParamList.stream().map(categoryParam -> {
            Long id = categoryParam.getId();
            if(id!=null) ids.add(id);

            Category category = new Category();
            BeanUtils.copyProperties(categoryParam, category);
            return category;
        }).collect(Collectors.toList());

        //删除数据
        if(ids.size()==0) ids.add(-1L);
        List<Object> delIdsO = categoryService.listObjs(new LambdaQueryWrapper<Category>().notIn(Category::getId, ids));
        List<Long> delIds = delIdsO.stream().map(o -> (Long) o).collect(Collectors.toList());
        if (delIds.size() > 0) {
            categoryService.removeByIds(delIds);
        }
        try{
            if(beanList.size()>0)
            categoryService.saveOrUpdateBatch(beanList);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            CATEGORY_NAME_ISEXIST.assertFail();
            return false;
        }
        return true;
    }

    public Boolean addOneLevel(CategoryParam categoryParam) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryParam, category);
        try{
            return categoryService.save(category);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            CATEGORY_NAME_ISEXIST.assertFail();
        }
        return false;
    }

}
