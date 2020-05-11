package cn.enn.wise.platform.mall.constants;

import java.util.HashMap;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/4 11:19
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:天气常量
 ******************************************/
public class WeatherConstant  {


    public static final Map<String,String> WEATHER_PICTUER_MAP = new HashMap<String,String>(){

        {
            put("晴", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CvaAfpPCAAADeVHD85A030.png");
            put("多云", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CvaAfpPCAAADeVHD85A030.png");
            put("阴", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("小雨", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("中雨", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("大雨", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("暴雨", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("雨夹雪", "http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
            put("其它","http://travel.enn.cn/group1/M00/00/B8/CiaAUl28CsCAeRbxAAAC51FYPm0063.png");
        }
    };

    public static final Map<String,String> WEATHER_PICTUER = new HashMap<String,String>(){
        {
            put("晴", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwFCAb8y3AAAHnreR7YA709.jpg");
            put("多云", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwJSAEb4lAAAH4tzeD40554.jpg");
            put("阴", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytxc-ADMPbAAAEFJVuCG4665.jpg");
            put("小雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwL2AOXYqAAAFi_ZiGDI549.jpg");
            put("中雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwNWAZrXcAAAGnBHsUH4467.jpg");
            put("大雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwPKAY9-sAAAHFxZWzuo691.jpg");
            put("暴雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwPKAY9-sAAAHFxZWzuo691.jpg");
            put("雨夹雪", "http://travel.enn.cn/group1/M00/00/1E/CiaAUlzRs_-AHdfSAAAa3zfF4q4842.png");
            put("其它","http://travel.enn.cn/group1/M00/00/10/CiaAUlytwJSAEb4lAAAH4tzeD40554.jpg");
        }
    };

    public static final Map<String,String> WEATHER_TELL = new HashMap<String,String>(){
        {
            put("1", "今天海面有鱼鳞状微小浪花，航行平稳。");
            put("2", "今天海面有轻微波纹和涌浪，晕船率低。");
            put("3", "今天海面有轻微浪花，航行略觉颠簸，晕船率12%。");
            put("4", "今天海面有轻微浪花，航行有些颠簸，晕船率18%。");
            put("5", "今天海面有明显波浪，航行明显颠簸，晕船率22%。");
            put("6", "今天海面有大浪，航行起伏加剧，晕船率47%。");
            put("7", "今天海面有巨浪，高大波峰随处可见，晕船率85%。");
            put("8", "今天海面有狂浪，航行颠簸严重，晕船率90%。");
            put("9", "今天海面狂浪，能见度低，游船停港靠岸，晕船率0。");
        }
    };


}
