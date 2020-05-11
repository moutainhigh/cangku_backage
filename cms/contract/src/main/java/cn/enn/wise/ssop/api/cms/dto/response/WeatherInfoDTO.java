package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "客户端详情")
@Data
public class WeatherInfoDTO {


    @ApiModelProperty("日期-小时")
    private String dateTimeHour;

    @ApiModelProperty("温度")
    private String temperature;

}
