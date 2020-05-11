package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/12
 */
@Data
@ApiModel("提醒实体")
public class RemindVo {
    @ApiModelProperty("提醒类型:1天气预警；2航班预警；3消费引导；")
    private String type;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("提醒内容")
    private String content;
}
