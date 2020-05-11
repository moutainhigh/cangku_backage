package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 活动规则参数
 *
 * @author anhui
 * @since 2019-09-12
 */
@Data
@ApiModel("规则请求参数1")
public class GroupRuleParam {

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动状态")
    private Byte status;

}
