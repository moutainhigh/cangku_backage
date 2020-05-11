package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/23
 */
@ApiModel("航线")
@Data
public class LineVo {
    @ApiModelProperty("起点")
    private String lineFrom;

    @ApiModelProperty("终点")
    private String lineTo;
}
