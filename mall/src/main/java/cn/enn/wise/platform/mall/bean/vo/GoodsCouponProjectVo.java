package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/18
 */
@Data
@ApiModel("项目列表")
public class GoodsCouponProjectVo {

    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

}
