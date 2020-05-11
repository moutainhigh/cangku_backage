package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/1 15:01
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class HourWeather {

    @ApiModelProperty(value = "时间段")
    private String time;

    @ApiModelProperty(value = "温度")
    private int temperature;

    @ApiModelProperty(value = "天气")
    private String circumstance;

    @ApiModelProperty(value = "天气图")
    private String circumstanceImg;

    @ApiModelProperty(value = "风级")
    private String windp;

    @ApiModelProperty(value = "最高最低温度")
    private String minMaxTemperature;

    public HourWeather(String time, String circumstance, int temperature, String circumstanceImg,String windp) {
        this.time = time;
        this.circumstance = circumstance;
        this.temperature = temperature;
        this.circumstanceImg = circumstanceImg;
        this.windp = windp;
    }

    public HourWeather(){
    }
}
