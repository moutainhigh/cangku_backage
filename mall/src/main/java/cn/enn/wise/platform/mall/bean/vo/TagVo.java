package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.TagCategory;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
public class TagVo {
    private List<TagBo> tagList;
    private TagCategory category;
}
