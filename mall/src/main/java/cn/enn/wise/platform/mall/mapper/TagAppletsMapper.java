package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.vo.TagAppletsVo;
import cn.enn.wise.platform.mall.bean.vo.TagBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小程序标签展示相关mapper
 *
 * @author baijie
 * @date 2019-10-29
 */
@Mapper
public interface TagAppletsMapper {

    /**
     * 获取首页的tag及子标签集合
     * @return
     *          TagAppletsVo
     */
    List<TagAppletsVo> getTagList();


    /**
     * 根据分类Id获取该分类下的所有子标签
     * @param categoryId 分类Id
     * @return 分类下的子标签的列表
     */
    List<TagBo> getTagByCategoryId(@Param("categoryId") Long categoryId);


    /**
     * 根据分类Id获取该分类下的所有子标签
     * @param pidList pid 集合
     * @return 该pid下的所有的子标签集合
     */
    List<TagBo> getTagByPid(List<Long> pidList);

    /**
     * 获取商品运营标签
     * @param goodsIds
     * @return
     */
    List<TagBean> getGoodsOperationTag(List<Long> goodsIds);

    /**
     * 获取项目运营标签
     * @param projectIds
     * @return
     */
    List<TagBean> getProjectOperationTag(List<Long> projectIds);
}
