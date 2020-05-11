package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.TagGoodsParam;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/19
 */
public interface TagProjectService {
    /**
     * 更新项目标签
     * @param param
     * @return
     */
    void updateProjectTagList(List<TagGoodsParam> param);
}
