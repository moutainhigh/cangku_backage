package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/5
 */

@Data
@ApiModel("活动请求参数")
public class RejectPromotion {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("name")
    private String name;
}


