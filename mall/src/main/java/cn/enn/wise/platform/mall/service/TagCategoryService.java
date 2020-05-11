package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.vo.*;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */

public interface TagCategoryService {

    /**
     * 获取
     * @return
     */
    List<TagVo> getTagList(String type);


    /**
     * 商品标签列表
     * @return
     */
    List<TagGoodsVo> getGoodsTagList(Long goodsId);

    /**
     * 项目标签列表
     * @return
     */
    List<TagProjectVo> getProjectTagList(Long projectId);

    /**
     * 小程序获取分类及其子标签列表
     * @return  分类和标签列表实体
     *          TagAppletsVo
     */
    List<TagAppletsVo> getTagVoList();

    /**
     * 根据分类Id获取详情
     * @param categoryId 分类Id
     * @return 分类详情
     */
    TagAppletsVo getTagVoById(String categoryId);

    /**
     * 获取标签分类
     * @return
     */
    List<TagCategoryVo> getTagCategoryList();
}
