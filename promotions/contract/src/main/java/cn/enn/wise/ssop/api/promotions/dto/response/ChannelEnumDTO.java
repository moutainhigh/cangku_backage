package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "渠道界面枚举信息")
@Data
public class ChannelEnumDTO {

    
    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("标识")
    private int index;

}
