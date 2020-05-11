package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.PlayMedia;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicInfo;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicSpotVo;
import cn.enn.wise.ssop.api.cms.dto.response.*;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.entity.oldcms.LatAndLonEntity;
import cn.enn.wise.ssop.service.cms.entity.oldcms.ScenicAreaEntity;
import cn.enn.wise.ssop.service.cms.mapper.ScenicsInfoMapper;
import cn.enn.wise.ssop.service.cms.mapper.TenantScenicAreaMapper;
import cn.enn.wise.ssop.service.cms.model.Knowledge;
import cn.enn.wise.ssop.service.cms.model.TenantScenicArea;
import cn.enn.wise.ssop.service.cms.util.ConfirmScence;
import cn.enn.wise.ssop.service.cms.util.StringUtil;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import cn.enn.wise.uncs.common.http.GeneUtil;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import cn.enn.wise.uncs.common.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("scenicsInfoService")
@DS("encdata")
public class ScenicsInfoService {
    @Resource
    ScenicsInfoMapper scenicsInfoMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    EnshrineService enshrineService;
    @Autowired
    KnowledgeService knowledgeService;
    @Autowired
    TenantScenicAreaMapper tenantScenicAreaMapper;

    @Transactional(rollbackFor = Exception.class)
    public void updateScenics(ScenicInfo scenicInfo) {

        //更新关联知识和活动项目
        scenicInfo.setKnowledgesIds(StringUtil.listToString(scenicInfo.getKnowledgesIdList()));
        scenicInfo.setActinfoIds(StringUtil.listToString(scenicInfo.getActinfoIdList()));
        scenicsInfoMapper.updateScenics(scenicInfo);

        //根据景点Id查询是否存在关联信息
//        ScenicInfo infoById = scenicsInfoMapper.getScenicsInfoById();
        ScenicInfo infoById = scenicsInfoMapper.getScenicInfo(String.valueOf(scenicInfo.getId()));
        if (infoById != null) {
            scenicInfo.setInfo(scenicInfo.getHtml());
            //更新关联内容信息
            scenicsInfoMapper.updateScenicsInfo(scenicInfo);

        } else {
            scenicInfo.setType(1);
            scenicInfo.setState(1);
            scenicInfo.setInfo(scenicInfo.getHtml());
            //添加关联内容信息
            scenicsInfoMapper.insertScenicsInfo(scenicInfo);
        }
    }


    
    @Transactional(rollbackFor = Exception.class)
    public void updateState(ScenicInfo scenicInfo) {
        scenicsInfoMapper.updateState(scenicInfo);

    }

    
    @Transactional(rollbackFor = Exception.class)
    public void updateScenicsInfo(ScenicInfo scenicInfo) {
        scenicsInfoMapper.updateScenicsInfo(scenicInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertScenicsInfo(ScenicInfo scenicInfo) {
        scenicsInfoMapper.insertScenicsInfo(scenicInfo);
    }

    public ScenicInfo getScenicsInfoById(String id) {

        return scenicsInfoMapper.getScenicsInfoById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertToMedia(List<PlayMedia> list) {
        scenicsInfoMapper.insertToMedia(list);
    }

    public List<PlayMedia> getPlayMediaByScenicId(String scenicId) {
        return scenicsInfoMapper.getPlayMediaByScenicId(scenicId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlayMediaByScenicId(String scenicId) {
        scenicsInfoMapper.deletePlayMediaByScenicId(scenicId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePlayMediaByScenicId(List<PlayMedia> list) {
        if(list != null && list.size() != 0){
            //删除旧图片数据
//                scenicsInfoMapper.deletePlayMediaByScenicId(String.valueOf(list.get(0).getScenicId()));

            for (PlayMedia playMedia : list) {
                playMedia.setIsUsed("1");
                playMedia.setType("1");
            }
            // 添加新图片数据
            scenicsInfoMapper.insertToMedia(list);
        }
    }

    public PageInfo<ScenicInfo> getScenicInfoList(ScenicInfo scenicInfo) {
        String companyId = GeneUtil.GetHttpHeader(request,"company_id");
        PageHelper.startPage(scenicInfo.getPageNum(),scenicInfo.getPageSize());
        List<ScenicInfo> scenicInfoList = scenicsInfoMapper.getScenicInfoList(scenicInfo,companyId);

        return new PageInfo<>(scenicInfoList);
    }

    public List<ScenicSpotVo> getScenicSpotVo() {
        String companyId = GeneUtil.GetHttpHeader(request,"company_id");
        return scenicsInfoMapper.getScenicSpotVo(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delScenicInfoById(Integer id) {
        int count = scenicsInfoMapper.deleteScenicInfoById(id);
        //删除景点图库
        scenicsInfoMapper.deletePlayMediaByScenicId(String.valueOf(id));
        return count>0;
    }

    public boolean scenicInfoUpDown(Integer id, Integer state) {
        ScenicInfo info = new ScenicInfo();
        info.setId(id);
        info.setState(state);
        return scenicsInfoMapper.updateState(info)>0;
    }

    public boolean deleteScenicInfoPhotoByPid(Integer id) {
        int count = scenicsInfoMapper.deletePlayMediaById(id);
        return count==1;
    }

    @DS("master")
    public ScenicInfoDTO getScenicsInfoAppletById(Integer id) {
        ScenicInfoDTO scenicInfoDTO = scenicsInfoMapper.getScenicsInfoAppletById(id);
        Integer dtoId = scenicInfoDTO.getId();
        String memberId = HttpContextUtils.GetHttpHeader("member_id");
        Boolean notEnshrine = enshrineService.getIsNotEnshrine(memberId, Long.valueOf(String.valueOf(dtoId)), CmsEnum.ENSHRINE_TYPE_SCENICRES.getValue());
        scenicInfoDTO.setEnshrine(notEnshrine);
        if (StringUtils.isEmpty(scenicInfoDTO.getKnowledgesIds())) {
            return scenicInfoDTO;
        }
        String[] split = scenicInfoDTO.getKnowledgesIds().split(",");
        List<KnowledgeScenicDTO> knowledgeDTOS = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            Knowledge knowledge = knowledgeService.getById(split[i]);
            if (knowledge == null) continue;
            KnowledgeScenicDTO knowledgeDTO = new KnowledgeScenicDTO();
            BeanUtils.copyProperties(knowledge, knowledgeDTO, GeneUtil.getNullPropertyNames(knowledge));
            knowledgeDTOS.add(knowledgeDTO);
        }
        scenicInfoDTO.setKnowledgeDTOS(knowledgeDTOS);
        return scenicInfoDTO;
    }

//    @Cacheable(value="CMS", key="T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).SCENICS_DETAIL,#id)")
    @DS("master")
    public List<ScenicFineDTO> getAppletFineScenicsList(String lonLat) {
        String companyId = GeneUtil.GetHttpHeader(request,"company_id");
        //获取所属景区ids
        List<Object> scenicObjIds = tenantScenicAreaMapper.selectObjs(new LambdaQueryWrapper<TenantScenicArea>()
                .select(TenantScenicArea::getScenicAreaId)
                .eq(TenantScenicArea::getCompanyId, companyId));

        List<Long> scenicIds = scenicObjIds.stream().map(o -> (Long)o).collect(Collectors.toList());
        if(scenicIds.size()==0){
            scenicIds.add(-1L);
        }

        List<ScenicFineDTO> scenicInfoList = scenicsInfoMapper.getAppletFineScenicsList(scenicIds);
        if(!StringUtils.isEmpty(lonLat)){
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);
            for (ScenicFineDTO scenicFineDTO : scenicInfoList) {
                double distance = MapUtil.distance(lon, lat, scenicFineDTO.getLon(), scenicFineDTO.getLat());
                scenicFineDTO.setDistance((int)distance);
            }
        }
        return scenicInfoList;
    }


    public List<NearbyRestsAppDTO> getNearbyRestsList(byte type) {
        return scenicsInfoMapper.getNearbyRestsList(type);
    }


    public List<WeatherDTO> getWeatherById(Long id) {

        String cityCode;
        ScenicAreaEntity scenicAreaEntity = scenicsInfoMapper.selectScenicAaeaById(Integer.valueOf(String.valueOf(id)));
        if(scenicAreaEntity==null){
            cityCode="101140403005";
        }else{
            cityCode = scenicAreaEntity.getCityCode();
        }


        WeatherDTO weather = scenicsInfoMapper.getWeatherById(cityCode);
        if(weather==null) return new ArrayList<>();

        //返回 星期几
        weather.setWeek(getWeek(weather.getDateTimeDay()));
        //提示语
        setWarn(weather);
        //组装7天 日期
        List<WeatherDTO> arrayList = new ArrayList<>();
        arrayList.add(0,weather);
        for (int i = 1; i < 7; i++) {
            WeatherDTO dto = new WeatherDTO();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
            String dateKey = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            dto.setWeek(getWeek(dateKey));
            dto.setDateTimeDay(dateKey);
            arrayList.add(i, dto);
        }

        //后6天 数据组装
        String[] splitAll = weather.getForecast().split(";");
        String dateKey = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (WeatherDTO weatherDTO : arrayList) {
            List<WeatherInfoDTO> list = new ArrayList<>();
            List<Integer> temperature = new ArrayList<>();
            List<Integer> windScale = new ArrayList<>();
            //目前随机取风况、天气 状况
            String afterDayWeather = null;
            String afterDayWindDirection = null;
            for (int i = 0; i < splitAll.length; i++) {
                String substring = splitAll[i].substring(0, 10);
                String[] split = splitAll[i].split(",");
                if (substring.equals(weatherDTO.getDateTimeDay())){
                    WeatherInfoDTO infoDTO = new WeatherInfoDTO();
                    infoDTO.setDateTimeHour(splitAll[i].substring(11, 20));
                    infoDTO.setTemperature(split[2]);
                    list.add(infoDTO);
                    afterDayWeather = splitAll[i].split(",")[1];
                    afterDayWindDirection = splitAll[i].split(",")[3];
                }
                //组装数据：最低-最高温度;  风级 最低-最高
//                if (substring.equals(weatherDTO.getDateTimeDay()) && !substring.equals(dateKey)){
//
//                }
                windScale.add(Integer.valueOf(splitAll[i].split(",")[4]));
                temperature.add(Integer.valueOf(splitAll[i].split(",")[2]));
            }
            //组装数据：最低-最高温度 ;  风级 最低-最高
            Integer max = temperature.stream().reduce(Integer::max).get();//得到最大值
            Integer min = temperature.stream().reduce(Integer::min).get();//得到最小值

            Integer max1 = windScale.stream().reduce(Integer::max).get();//得到最大值
            Integer min1 = windScale.stream().reduce(Integer::min).get();//得到最小值
            weatherDTO.setWindScale(min1 + "-" + max1);

            weatherDTO.setHighest(max);
            weatherDTO.setLowest(min);

            weatherDTO.setWeather(afterDayWeather);
            weatherDTO.setWindDirection(afterDayWindDirection);
            weatherDTO.setWeatherInfoList(list);

            weatherDTO.setWeatherImgUrl(WEATHER_PICTUER_MAP.get(afterDayWeather));


        }
        //当前数据不足  24个节点 将明天数据加入
        if (arrayList.get(0).getWeatherInfoList().size() < 24) {
            List<WeatherInfoDTO> weatherInfoList = arrayList.get(1).getWeatherInfoList();
            int size = 24 - arrayList.get(0).getWeatherInfoList().size();
            for (int i = 0; i < size; i++) {
                arrayList.get(0).getWeatherInfoList().add(weatherInfoList.get(i));
            }
        }
        arrayList.get(0).setForecast(null);
        return arrayList;
    }


    /**
     * 天气预报图标
     */
    public static final Map<String,String> WEATHER_PICTUER_MAP = new HashMap<String,String>(){
        private static final long serialVersionUID = 4529540538606091277L;

        {
            put("晴", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwFCAb8y3AAAHnreR7YA709.jpg");
            put("多云", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwJSAEb4lAAAH4tzeD40554.jpg");
            put("阴", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytxc-ADMPbAAAEFJVuCG4665.jpg");
            put("小雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwL2AOXYqAAAFi_ZiGDI549.jpg");
            put("中雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwNWAZrXcAAAGnBHsUH4467.jpg");
            put("大雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwPKAY9-sAAAHFxZWzuo691.jpg");
            put("暴雨", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwPKAY9-sAAAHFxZWzuo691.jpg");
            put("雨夹雪", "http://travel.enn.cn/group1/M00/00/1E/CiaAUlzRs_-AHdfSAAAa3zfF4q4842.png");
            put("其他", "http://travel.enn.cn/group1/M00/00/10/CiaAUlytwJSAEb4lAAAH4tzeD40554.jpg");
            put("大雪", "http://travel.enn.cn/group1/M00/00/1E/CiaAUlzRs_-AHdfSAAAa3zfF4q4842.png");
            put("中雪", "http://travel.enn.cn/group1/M00/00/1E/CiaAUlzRs_-AHdfSAAAa3zfF4q4842.png");
            put("小雪", "http://travel.enn.cn/group1/M00/00/1E/CiaAUlzRs_-AHdfSAAAa3zfF4q4842.png");

        }
    };

    /**
     * 获取时间星期几
     * @return
     */
    private String getWeek(String date) {
        try {
            Date today = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String week = "";
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            if (weekday == 1) {
                week = "周日";
            } else if (weekday == 2) {
                week = "周一";
            } else if (weekday == 3) {
                week = "周二";
            } else if (weekday == 4) {
                week = "周三";
            } else if (weekday == 5) {
                week = "周四";
            } else if (weekday == 6) {
                week = "周五";
            } else if (weekday == 7) {
                week = "周六";
            }
            return week;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setWarn(WeatherDTO weatherDTO) {
        String circumstance = weatherDTO.getWeather();
        int temperature = Integer.valueOf(weatherDTO.getTodayTemperature());
        String warn = "";
        if(circumstance.contains("雪")||circumstance.contains("阵雪"))
            warn = "今日有雪，气温较低，请注意保暖防滑。";
        else if(circumstance.contains("雨夹雪"))
            warn = "雨雪天气，气温较低，请注意保暖防滑。";
        else if((circumstance.contains("雨")||circumstance.contains("阵雨"))&&temperature>9)
            warn = "今日有雨，请随身携带雨具。";
        else if((circumstance.contains("雨")||circumstance.contains("阵雨"))&&temperature<=9)
            warn = "今日有雨，气温较凉，请携带雨具，保暖防寒。";
        else if(temperature<0)
            warn = "天气寒冷，请注意保暖，减少外出。";
        else if(temperature<19)
            warn = "天气较凉，请注意保暖。";
        else if(temperature<24)
            warn = "气温舒适，适宜出行。";
        else
            warn = "气温炎热，请注意防暑降温。";
        weatherDTO.setAdmonish(warn);
    }

    /**
     * 获取当前租户所属景区id
     * @return
     */
    public List<Long> getMyScenicAreaIdList(){
        String companyId = GeneUtil.GetHttpHeader(request,"company_id");
        //获取所属景区ids
        List<Long> scenicObjIds = tenantScenicAreaMapper.selectObjs(new LambdaQueryWrapper<TenantScenicArea>()
                .select(TenantScenicArea::getScenicAreaId)
                .eq(TenantScenicArea::getCompanyId, companyId)).stream().map(o -> (Long)o).collect(Collectors.toList());
        return scenicObjIds;
    }

    @DS("encdata")
    public List<ScenicFineDTO> getScenicsSpotList(String lonLat) {

        String companyId = GeneUtil.GetHttpHeader(request,"company_id");
        //获取所属景区ids
        List<Long> scenicObjIds = tenantScenicAreaMapper.selectObjs(new LambdaQueryWrapper<TenantScenicArea>()
                .select(TenantScenicArea::getScenicAreaId)
                .eq(TenantScenicArea::getCompanyId, companyId)).stream().map(o -> (Long)o).collect(Collectors.toList());

        if(!StringUtils.isEmpty(lonLat)){
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);
            //获取是否在景区内
            ScenicAreaEntity scenicAreaEntity = getScenicAreaIdByLocation(scenicObjIds, new LatAndLonEntity(lon, lat));
            if(scenicAreaEntity!=null&&scenicAreaEntity.getId()>0){
                //在某个景区
                scenicObjIds = Arrays.asList(scenicAreaEntity.getId());
            }
        }
        List<ScenicFineDTO> scenicInfoList = scenicsInfoMapper.getAppletFineScenicsList(scenicObjIds);
        if(!StringUtils.isEmpty(lonLat)){
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);
            for (ScenicFineDTO scenicFineDTO : scenicInfoList) {
                double distance = MapUtil.distance(lon, lat, scenicFineDTO.getLon(), scenicFineDTO.getLat());
                scenicFineDTO.setDistance((int)distance);
            }
        }
        return scenicInfoList;
    }

    /**
     * 获取坐标是否在某个景区
     * @param scenicObjIds
     * @param latAndLonEntity
     * @return 景区
     */
    public ScenicAreaEntity getScenicAreaIdByLocation(List<Long> scenicObjIds,LatAndLonEntity latAndLonEntity){
        //景区边界
        List<ScenicAreaEntity> scenicAreaEntityList = scenicsInfoMapper.getScenicAreaSideByScneicAreaIds(scenicObjIds);

        if (GeneUtil.isNotNullAndEmpty(scenicAreaEntityList)) {
            // 根据经纬度确定景区
            for (ScenicAreaEntity scenicArea : scenicAreaEntityList) {
                List<LatAndLonEntity> sides = scenicArea.getSides();
                if (ConfirmScence.isPolygonContainsPoint(latAndLonEntity, sides)) {
                    return scenicArea;
                }
            }
        }

        return null;
    }

    /**
     * 根据经纬度 获取所在景区信息
     * @param lonLat
     * @return
     */
    public ScenicAreaDTO getNowScenicAreaInfo(String lonLat) {
        List<Long> areaIdList = getMyScenicAreaIdList();
        if(!StringUtils.isEmpty(lonLat)) {
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);

            ScenicAreaEntity scenicAreaIdByLocation = getScenicAreaIdByLocation(areaIdList, new LatAndLonEntity(lat, lon));
            if(scenicAreaIdByLocation==null) return new ScenicAreaDTO(-1L,"景区外");
            ScenicAreaDTO scenicAreaDTO = new ScenicAreaDTO();
            BeanUtils.copyProperties(scenicAreaIdByLocation, scenicAreaDTO);
            return scenicAreaDTO;
        }
        return new ScenicAreaDTO(-1L,"景区外");
    }
}
