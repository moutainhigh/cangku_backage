package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/11/21
 */
@Data
@ApiModel("路线服务场地参数")
public class GoodsProjectParams {
    @ApiModelProperty("项目id")
    private Long projectId;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("线路id")
    private Long routeCheckedId;
    @ApiModelProperty("服务地点")
    private List<Long> placesCheckedIds;
}
