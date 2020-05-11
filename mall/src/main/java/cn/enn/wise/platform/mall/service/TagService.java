package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.param.TagParam;

import java.util.List;


/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */

public interface TagService {
    /**
     * 添加标签
     * @param param
     * @return
     */
    TagBo addTag(TagParam param);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Integer deleteTag(List<Long> id);
}
