package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/21
 */
@Data
@ApiModel("地点展示")
public class ServicePlaceVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("name")
    private String servicePlaceName;
}
