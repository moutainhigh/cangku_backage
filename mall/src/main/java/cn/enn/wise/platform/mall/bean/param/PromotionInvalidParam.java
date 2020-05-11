package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/5
 */
@Data
@ApiModel("活动失效")
public class PromotionInvalidParam {

    @ApiModelProperty("ids")
    private List<Integer> ids;

    @ApiModelProperty("原因")
    private String reason;
}
