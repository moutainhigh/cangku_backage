package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

/**
 * 天气记录
 *
 * @author baijie
 * @date 2020-02-27
 */
@Data
@Table(name = "weather_sun")
public class WeatherSun {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 11,isKey = true,isAutoIncrement = true,comment = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "search_text",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "搜索词")
    @ApiModelProperty(value = "搜索词")
    private String searchText;

    @Column(name = "city_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "城市代码")
    @ApiModelProperty(value = "城市代码")
    private String cityCode;

    @Column(name = "humidity",type = MySqlTypeConstant.DECIMAL, length = 10,decimalLength = 2,comment = "湿度")
    @ApiModelProperty(value = "湿度")
    private Double humidity;

    @Column(name = "temperature",type = MySqlTypeConstant.INT, length = 11,comment = "温度")
    @ApiModelProperty(value = "温度")
    private String temperature;

    @Column(name = "wind",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "风向")
    @ApiModelProperty(value = "风向")
    private String wind;

    @Column(name = "windp",type = MySqlTypeConstant.INT, length = 11,comment = "风级")
    @ApiModelProperty(value = "风级")
    private String windp;

    @Column(name = "date_time",type = MySqlTypeConstant.DATE,comment = "时间")
    @ApiModelProperty(value = "时间")
    private String dateTime;

    @Column(name = "pub_time",type = MySqlTypeConstant.TIME,comment = "刷新时间")
    @ApiModelProperty(value = "刷新时间")
    private String pubTime;

    @Column(name = "weather",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "天气")
    @ApiModelProperty(value = "天气")
    private String weather;

    @Column(name = "sunrise_time",type = MySqlTypeConstant.TIME, comment = "日出时间")
    @ApiModelProperty(value = "日出时间")
    private String sunriseTime;

    @Column(name = "sunset_time",type = MySqlTypeConstant.TIME, comment = "日落时间")
    @ApiModelProperty(value = "日落时间")
    private String sunsetTime;

    @Column(name = "forecast",type = MySqlTypeConstant.TEXT,comment = "预告天气")
    @ApiModelProperty(value = "预告天气")
    private String forecast;

    @Column(name = "spider_code",type = MySqlTypeConstant.VARCHAR, comment = "采集批次(爬虫试用)")
    @ApiModelProperty(value = "")
    private String spiderCode;

    @Column(name = "spider_time",type = MySqlTypeConstant.DATETIME, comment = "采集时间(爬虫试用)")
    @ApiModelProperty(value = "采集时间(爬虫试用)")
    private Date spiderTime;

    @Column(name = "md5",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "md5(爬虫试用)")
    @ApiModelProperty(value = "md5(爬虫试用)")
    private String md5;

    @Column(name = "maxtemp",type = MySqlTypeConstant.VARCHAR, length = 22,comment = "最高温")
    @ApiModelProperty(value = "最高温")
    private String maxtemp;

    @Column(name = "mixtemp",type = MySqlTypeConstant.VARCHAR, length = 22,comment = "最低温")
    @ApiModelProperty(value = "最低温")
    private String mixtemp;
}
