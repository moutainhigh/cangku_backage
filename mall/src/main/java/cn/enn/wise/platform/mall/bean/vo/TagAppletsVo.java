package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 首页分类标签Vo
 *
 * @author baijie
 * @date 2019-10-29
 */
@Data
@ApiModel("首页分类实体")
public class TagAppletsVo {

    @ApiModelProperty("分类Id")
    private String id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("分类下子标签集合")
    private List<TagBean> tag;

}
