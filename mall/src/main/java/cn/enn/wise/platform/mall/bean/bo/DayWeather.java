package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/9 14:20
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class DayWeather {

    @ApiModelProperty(value = "时间段天气")
    private List<HourWeather> hourWeathers = new ArrayList();

    public DayWeather() {
    }
}
