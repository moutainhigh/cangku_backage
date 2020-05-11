package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.autotable.TagCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
public class TagGoodsVo {

    @ApiModelProperty(value = "标签列表")
    private List<TagExtendVo> tagList;
    @ApiModelProperty(value = "分类")
    private TagCategory category;
}
