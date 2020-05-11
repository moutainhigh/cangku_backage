package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(description = "客户端详情")
@Data
public class WeatherDTO {

    @ApiModelProperty("当前温度")
    private String todayTemperature;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("风向")
    private String windDirection;

    @ApiModelProperty("风级")
    private String windScale;

    @ApiModelProperty("周")
    private String week;

    @ApiModelProperty("日期")
    private String dateTimeDay;

    @ApiModelProperty("24小时温度")
    private List<WeatherInfoDTO> weatherInfoList;

    @ApiModelProperty("温馨提醒")
    private String admonish;

    @ApiModelProperty("预告天气")
    private String forecast;

    @ApiModelProperty("天气图片url")
    private String weatherImgUrl;

    @ApiModelProperty("最高温度")
    private int highest;

    @ApiModelProperty("最低温度")
    private int lowest;

}
