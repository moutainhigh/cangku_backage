package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.CurrentWeather;
import cn.enn.wise.platform.mall.bean.bo.HourWeather;
import cn.enn.wise.platform.mall.bean.bo.ProdComm;
import cn.enn.wise.platform.mall.bean.param.ProdCommParam;
import cn.enn.wise.platform.mall.bean.vo.ProdCommVo;
import cn.enn.wise.platform.mall.bean.vo.ProjectCommentVo;
import cn.enn.wise.platform.mall.constants.WeatherConstant;
import cn.enn.wise.platform.mall.mapper.GoodsProjectOperationMapper;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.ProdCommMapper;
import cn.enn.wise.platform.mall.service.ProdCommService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.GeneUtil;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 14:01
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
@Slf4j
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    @Resource
    private ProdCommMapper prodCommMapper;

    @Resource
    private GoodsProjectOperationMapper goodsProjectOperationMapper;

    @Resource
    private OrderDao orderDao;

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    private static final int WEATHER_TYPE= 1;


    @Override
    public long saveProdComm(ProdCommParam prodCommParam) {
        long result = 0;
        ProdComm prodComm = new ProdComm();
        prodComm.setOrderId(prodCommParam.getOrderId());
        prodComm.setContent(prodCommParam.getContent());
        prodComm.setEvaluate(prodCommParam.getEvaluate());
        prodComm.setProjectId(prodCommParam.getProjectId());
        prodComm.setRecTime(new Date());
        prodComm.setStatus(2);
        prodComm.setUserId(prodCommParam.getUserId());
        prodComm.setScore(prodCommParam.getScore());
        prodComm.setProdCommLabel(prodCommParam.getProdCommLabel());
        result = prodCommMapper.insert(prodComm) > 0 ? orderDao.updateProdCommSts(prodCommParam.getOrderId())
                : GeneConstant.INT_0;
        return result;
    }

    @Override
    public ProdComm findProdCommDetail(String orderCode) {
        QueryWrapper<ProdComm> prodCommQueryWrapper = new QueryWrapper<>();
        prodCommQueryWrapper.lambda().eq(ProdComm::getOrderId, orderCode);
        ProdComm prodComm = prodCommMapper.selectOne(prodCommQueryWrapper);

        Map<Integer, String> listByCompanyId = GeneUtil.getLabelListByCompanyId(Long.valueOf(companyId));
        String commLabel = prodComm.getProdCommLabel();
        List<String> labelList = new ArrayList<>();
        if (StringUtils.isNotEmpty(commLabel)) {
            String[] split = commLabel.split(",");
            for (String s : split) {
                labelList.add(listByCompanyId.get(Integer.parseInt(s)));
            }

        }
        prodComm.setProdCommLabel(StringUtils.join(labelList, ","));
        return prodComm;
    }

    @Override
    public List<HourWeather> selectWeatherList(String cityCode) {
        Map<String, Object> weatherMap = goodsProjectOperationMapper.selectWeatherList(cityCode);
        if (weatherMap != null) {
            String forecast = (String) weatherMap.get("forecast");
            LocalDate localDate = LocalDate.now();

            List<String> weatherList = Arrays.asList(forecast.split(";")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList());

            String todayWeather = weatherList.stream().filter(weather -> weather.contains(localDate.toString())).collect(Collectors.joining(";"));

            String[] todayWeatherArray = getHourWeathersArray(todayWeather);

            List<HourWeather> hourWeathersList = new ArrayList<>();
            for (int i = 0; i < todayWeatherArray.length; i++) {
                HourWeather hourWeather = changeStrToHourWeather(todayWeatherArray[i],1);
                hourWeathersList.add(hourWeather);
            }
            LocalDateTime localDateTime = LocalDateTime.now();

            String hour = localDateTime.getHour() + ":00";

            List<HourWeather> collect = hourWeathersList.stream().filter(hourWeather -> hourWeather.getTime().contains(hour)).collect(Collectors.toList());

            return collect;
        }

        return Collections.emptyList();
    }

    @Override
    public PageInfo<ProdCommVo> findCommentList(ProdCommParam prodCommParam) {
        PageHelper.startPage(prodCommParam.getPageNum(), prodCommParam.getPageSize());
        return new PageInfo<>(prodCommMapper.findCommentList(prodCommParam));
    }

    @Override
    public CurrentWeather findWeatherList(String cityCode) {
        CurrentWeather currentWeather = new CurrentWeather();
        Map<String, Object> weatherMap = goodsProjectOperationMapper.selectWeatherList(cityCode);
        if (weatherMap != null) {
            String forecast = (String) weatherMap.get("forecast");
            Float humidity = (Float) weatherMap.get("humidity");
            LocalDate todayDate = LocalDate.now();
            List<String> weatherList = Arrays.asList(forecast.split(";")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList());

            String todayWeather = weatherList.stream().filter(weather -> weather.contains(todayDate.toString())).collect(Collectors.joining(";"));

            String[] todayWeatherArray = getHourWeathersArray(todayWeather);
            List<HourWeather> toDayHourWeathersList = new ArrayList<>();
            for (int i = 0; i < todayWeatherArray.length; i++) {
                HourWeather hourWeather = changeStrToHourWeather(todayWeatherArray[i],2);
                toDayHourWeathersList.add(hourWeather);
            }

            OptionalInt max = toDayHourWeathersList.stream().mapToInt(HourWeather::getTemperature).max();
            OptionalInt min = toDayHourWeathersList.stream().mapToInt(HourWeather::getTemperature).min();
            String minMax =String.valueOf(max.getAsInt()) +"/"+String.valueOf(min.getAsInt())+"°C";
            toDayHourWeathersList.stream().forEach(todayWeathers -> todayWeathers.setMinMaxTemperature(minMax));

            LocalDateTime localDateTime = LocalDateTime.now();

            String hour = localDateTime.getHour() + ":00";

            List<HourWeather> toDayWeather = toDayHourWeathersList.stream().filter(hourWeather -> hourWeather.getTime().contains(hour)).collect(Collectors.toList());
            currentWeather.setToDayWeatherList(toDayWeather);

            toDayWeather.stream().forEach(todayDates->{
//                String tell = WeatherConstant.WEATHER_TELL.get(todayDates.getWindp());
                //更换风级来源为气象站数据
                Object windp = weatherMap.get("windp");
                todayDates.setWindp(windp == null ? "9":windp.toString());
                String tell = WeatherConstant.WEATHER_TELL.get(todayDates.getWindp());
                if (Strings.isEmpty(tell)) {
                    tell = WeatherConstant.WEATHER_TELL.get("9");
                }
                currentWeather.setTell(tell);
            });

            List<HourWeather> tomorrowHourWeathersList = getTomorrowHourWeathersList(forecast);
            currentWeather.setTomorrowWeatherList(tomorrowHourWeathersList);
            //currentWeather.setTell("今天昼夜温差大,早晚出行建议适当添加衣物");
            currentWeather.setHumidity(humidity);

            return currentWeather;
        }
        return currentWeather;
    }


    public List<HourWeather> getTomorrowHourWeathersList(String forecast){
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        List<String> weatherList = Arrays.asList(forecast.split(";")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList());
        String tomorrowWeather = weatherList.stream().filter(weather  -> weather.contains(tomorrowDate.toString())).collect(Collectors.joining(";"));
        String[] tomorrowWeatherArray = getHourWeathersArray(tomorrowWeather);
        List<HourWeather> tomorrowHourWeathersList = new ArrayList<>();
        for(int i = 0 ; i < tomorrowWeatherArray.length ; i++){
            HourWeather hourWeather = changeStrToHourWeather(tomorrowWeatherArray[i],2);
            tomorrowHourWeathersList.add(hourWeather);
        }
        OptionalInt max = tomorrowHourWeathersList.stream().mapToInt(HourWeather::getTemperature).max();
        OptionalInt min = tomorrowHourWeathersList.stream().mapToInt(HourWeather::getTemperature).min();
        String minMax =String.valueOf(max.getAsInt()) +"/"+String.valueOf(min.getAsInt())+"°C";
        tomorrowHourWeathersList.stream().forEach(todayWeathers -> todayWeathers.setMinMaxTemperature(minMax));
        LocalDateTime localDateTime = LocalDateTime.now();

        String hour = localDateTime.getHour() + ":00";

        List<HourWeather> toDayWeather = tomorrowHourWeathersList.stream().filter(hourWeather -> hourWeather.getTime().contains(hour)).collect(Collectors.toList());

        return toDayWeather;
    }

    private static String[] getHourWeathersArray(String dayWeathersStr) {
        if (null == dayWeathersStr) {
            log.warn("getHourWeather datWeathersStr is null");
            return null;
        }
        String[] hourWeathersArray = dayWeathersStr.split(GeneConstant.SEMICOLON_MARK);
        if (hourWeathersArray == null) {
            log.warn("getHourWeather hourWeathersArray may be null or the length is not equal 40");
            return null;
        }
        return hourWeathersArray;
    }


    private static HourWeather changeStrToHourWeather(String hourWeatherStr,Integer type) {
        String[] hourWeatherArray = hourWeatherStr.split(GeneConstant.COMMA_MARK);
        if (hourWeatherArray == null) {
            log.warn("changeStrToHourWeather hourWeatherArray may be null or the length is not equal 5");
            return null;
        }
        String time = hourWeatherArray[0].split(GeneConstant.BLACK_MARK)[1].substring(0, 5);
        String temperature = hourWeatherArray[2];
        String circumstance = hourWeatherArray[1];
        String windp = hourWeatherArray[4];
        if (type.equals(WEATHER_TYPE)){
            String circumstanceImg = WeatherConstant.WEATHER_PICTUER_MAP.get(circumstance);
            if (Strings.isEmpty(circumstanceImg)) {
                circumstanceImg = WeatherConstant.WEATHER_PICTUER_MAP.get("其它");
            }
            return new HourWeather(time, hourWeatherArray[1], Integer.valueOf(temperature), circumstanceImg,windp);
        }
        String circumstanceImg = WeatherConstant.WEATHER_PICTUER.get(circumstance);
        if (Strings.isEmpty(circumstanceImg)) {
            circumstanceImg = WeatherConstant.WEATHER_PICTUER.get("其它");
        }
        return new HourWeather(time, hourWeatherArray[1], Integer.valueOf(temperature), circumstanceImg,windp);
    }

    @Override
    public long updateProdCommStatus(String orderCode, Integer status) {
        QueryWrapper<ProdComm> prodCommQueryWrapper = new QueryWrapper<>();
        prodCommQueryWrapper.lambda().eq(ProdComm::getOrderId, orderCode);
        ProdComm prodComm = new ProdComm();
        prodComm.setStatus(status);
        return prodCommMapper.update(prodComm, prodCommQueryWrapper);
    }



    @Override
    public Map<String, Object> getCommentList(Long projectId, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(8);

        //查询总数
        Integer total = prodCommMapper.selectCount(new QueryWrapper<ProdComm>().eq("project_id", projectId));

        Long longCompanyId = Long.parseLong(companyId);
        //获取标签
        Map<Integer, String> listByCompanyId = GeneUtil.getLabelListByCompanyId(longCompanyId);

        PageHelper.startPage(pageNum, pageSize);
        //查询评论列表
        List<ProjectCommentVo> commentList = prodCommMapper.getCommentList(projectId);
        Set<String> userIds = new HashSet<>();
        //组合数据
        commentList.stream().forEach(x -> {
            userIds.add(x.getUserId());
            if (x.getStatus() != 1) {
                x.setContent(null);
            }
            String commLabel = x.getProdCommLabel();
            List<String> labelList = new ArrayList<>();
            if (StringUtils.isNotEmpty(commLabel)) {
                String[] split = commLabel.split(",");
                for (String s : split) {
                    labelList.add(listByCompanyId.get(Integer.parseInt(s)));
                }

            }
            x.setLabelList(labelList);

        });

        //请求获取头像
        List<Map<String, Object>> memberInfo = remoteServiceUtil.getMemberInfo(GeneUtil.listToString(userIds), longCompanyId);

        commentList.stream().forEach(x -> {
            memberInfo.stream().forEach(
                    y -> {
                        if (y.get("id").toString().equals(x.getUserId())) {
                            x.setHeadImg(y.get("headImg") == null ? null : y.get("headImg").toString());
                        }
                    });
        });

        map.put("total", total);
        map.put("list", commentList);
        return map;
    }
}
