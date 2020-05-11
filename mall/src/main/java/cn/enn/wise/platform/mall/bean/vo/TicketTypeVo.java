package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/27
 */
@Data
@ApiModel("票型")
public class TicketTypeVo {

    @ApiModelProperty("票型名称")
    private String name;

    @ApiModelProperty("票型编码")
    private String code;

    @ApiModelProperty("票型描述")
    private String description;
}
