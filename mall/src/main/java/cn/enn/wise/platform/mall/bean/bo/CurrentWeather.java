package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/9 14:35
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class CurrentWeather {

    @ApiModelProperty(value = "当天温度")
    private List<HourWeather> toDayWeatherList;

    @ApiModelProperty(value = "明天温度")
    private List<HourWeather> tomorrowWeatherList;

    @ApiModelProperty("温馨提示")
    private String tell;

    @ApiModelProperty(value = "湿度")
    private Float humidity;

}
