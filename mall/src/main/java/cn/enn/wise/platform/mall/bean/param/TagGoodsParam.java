package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/20
 */
@Data
public class TagGoodsParam {

    @ApiModelProperty("标签id")
    private  Long tagId;

    @ApiModelProperty("商品id 或者 项目id")
    private Long businessId;

    @ApiModelProperty("商品标签id 或者 项目标签id")
    private Long checkId;

    @ApiModelProperty("是否选中 1 选中 2 未选中")
    private Long isChecked;
}
