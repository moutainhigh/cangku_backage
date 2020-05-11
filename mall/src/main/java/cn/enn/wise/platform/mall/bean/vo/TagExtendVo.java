package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
public class TagExtendVo extends  TagBo {

    @ApiModelProperty("主键")
    private Long checkId;

    @ApiModelProperty("商品id 或者 项目id")
    private Long businessId;

    @ApiModelProperty("标签id")
    private Long tagId;

    @ApiModelProperty("是否选中")
    private String isChecked;


}
