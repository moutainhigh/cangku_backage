package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单信息")
@TableName("weather_sun")
public class WeatherSun {

    @ApiModelProperty(value = "天气预报")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "天气预报")
    private String forecast;

    @ApiModelProperty(value = "湿度")
    private Float humidity;

    @ApiModelProperty(value = "风力")
    private Integer windp;


}
