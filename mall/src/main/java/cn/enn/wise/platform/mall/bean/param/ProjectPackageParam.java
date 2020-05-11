package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/18
 */
@Data
@ApiModel("添加")
public class ProjectPackageParam {

    @ApiModelProperty("项目id")
    private Long projectIdList;
}
